import {Injectable} from '@angular/core';
import {AuthService} from './auth.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class TaskService {

  private cookieName = 'authToken';
  private baseUrl = 'http://localhost:8080/api/task';
  private usersUrl = 'http://localhost:8080/api/user';
  private readonly headers;

  constructor(private auth: AuthService, private http: HttpClient) {
    this.headers = this.auth.getHeaders();
  }

  getTasksByPage(page, size): Observable<any> {
    return this.http.get(this.baseUrl + '?page=' + page + '&size=' + size, this.headers);
  }

  checkGrand(taskId: number) {
    return this.http.get(this.baseUrl + '/check/' + taskId, this.headers);
  }

  getTasksById(id): Observable<any> {
    return this.http.get(this.baseUrl + '/' + id, this.headers);
  }

  doReport(reportDto) {
    return this.http.post(this.baseUrl + '/report', reportDto, this.headers)
      .subscribe(value => value);
  }

  createTask(createDto, id): Observable<any> {
    return this.http.post(this.baseUrl + '/' + id, createDto, this.headers);
  }

  update(obj, id: number) {
    return this.http.post(this.baseUrl + '/update/' + id, obj, this.headers);
  }

  findAll(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl + '/all', this.auth.getHeaders());
  }

  grand(userId: number, taskId: number) {
    const body = {userId: userId, taskId: taskId};
    return this.http.post(this.usersUrl + '/grand', body, this.auth.getHeaders());
  }
}
