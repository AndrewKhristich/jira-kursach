import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class AuthService {

  private cookieName = 'authToken';
  private baseUrl = 'http://localhost:8080/api/auth/';

  constructor(private router: Router, private http: HttpClient, private cookies: CookieService) {
  }

  obtainJwtToken(loginDate) {
    this.http.post(this.baseUrl + 'login', loginDate)
      .subscribe(value => this.saveToken(value));
  }

  saveToken(token) {
    const expireDate = new Date().getTime() + (1000 * token.expires_in);
    this.cookies.set(this.cookieName, token.accessToken, expireDate);
    console.log('navigate');
    this.router.navigate(['/']);
  }

  checkIfAdmin() {
    this.http.get(this.baseUrl + 'admin', this.getHeaders())
      .subscribe(value => {}, error1 => this.router.navigate(['/']));
  }

  checkToken() {
    if (!this.cookies.check(this.cookieName)) {
      this.router.navigate(['/login']);
    }
    this.http.get(this.baseUrl + 'check', this.getHeaders())
      .subscribe(
        value => {
        },
        error1 => this.router.navigate(['/']));
  }

  register(user) {
    this.http.post(this.baseUrl + 'register', user, this.getHeaders())
      .subscribe(value => {});
  }

  logout() {
    this.cookies.delete(this.cookieName);
    this.router.navigate(['/login']);
  }

  getHeaders() {
    const token = this.cookies.get(this.cookieName);
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token
    });
    const options = {headers: headers};
    return options;
  }

}
