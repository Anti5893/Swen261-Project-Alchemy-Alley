import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from './user';
import { Product } from './product';

@Injectable({ providedIn: 'root' })
export class UserService {

  private usersUrl = 'http://localhost:8080/users';

  httpOptionsResponse = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    observe: 'response' as 'response'
  };

  constructor(private http: HttpClient) {}

  addUser(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>(this.usersUrl, user, this.httpOptionsResponse);
  }
  
  authenticateUser(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>(`${this.usersUrl}/auth`, user, this.httpOptionsResponse);
  }

  updateUser(user: User): Observable<HttpResponse<User>> {
    return this.http.put<User>(this.usersUrl, user, this.httpOptionsResponse);
  }

  doCraft(user: User): Observable<HttpResponse<Product>> {
    return this.http.post<Product>(`${this.usersUrl}/checkout`, user, this.httpOptionsResponse);
  }
}