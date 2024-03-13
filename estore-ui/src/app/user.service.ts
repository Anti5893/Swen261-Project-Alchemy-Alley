import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../user';



@Injectable({ providedIn: 'root' })
export class UserService {
  
  httpOptionsResponse = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    observe: 'response' as 'response'
  };

  private usersUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient){}

  addUser(user : User) : Observable<HttpResponse<User>>{
    return this.http.post<User>(this.usersUrl, user,this.httpOptionsResponse)
  }
  
  authenticateUser(user : User) : Observable<HttpResponse<User>>{
    const authUrl = this.usersUrl + '/auth';
    return this.http.post<User>(authUrl,user,this.httpOptionsResponse)
  }
  updateUser(user: User) : Observable<HttpResponse<User>>{
    return this.http.put<User>(this.usersUrl, user,this.httpOptionsResponse)
  }
}



