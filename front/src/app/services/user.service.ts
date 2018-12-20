import {Injectable} from '@angular/core';
import {AuthService} from './auth.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class UserService {

  private baseUrl = 'http://localhost:8080/api/user';
  private readonly headers;

  constructor(private auth: AuthService, private http: HttpClient) {
    this.headers = this.auth.getHeaders();
  }

  findAll(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl + '/all', this.auth.getHeaders());
  }

}
