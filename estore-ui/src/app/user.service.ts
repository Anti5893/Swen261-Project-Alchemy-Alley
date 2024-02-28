import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

import { User } from '../user';
import { Observable } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class UserService {
  
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  private url = 'http://localhost:8080/users';

  constructor(private http: HttpClient){}

  addUser(user : User) : Observable<User>{
    return this.http.post<User>(this.url, user,this.httpOptions)
  }
}