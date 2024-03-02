import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpResponse } from '@angular/common/http';
import { map, tap } from 'rxjs';

import { User } from '../user';
import { Observable } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class UserService {
  
  httpOptionsResponse = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    observe: 'response' as 'response'
  };

  httpOptionsUser = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  private usersUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient){}

  addUser(user : User) : Observable<User>{
    return this.http.post<User>(this.usersUrl, user,this.httpOptionsUser)
  }
  
  authenticateUser(user : User) : Observable<HttpResponse<User>>{
    const authUrl = this.usersUrl + '/auth';
    return this.http.post<User>(authUrl,user,this.httpOptionsResponse)
  }
}



