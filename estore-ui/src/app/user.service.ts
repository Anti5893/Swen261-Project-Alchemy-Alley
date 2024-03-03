import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

import { User } from '../user';
import { Observable } from 'rxjs';
import { Product } from './product';


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

  updateCart(user: User, newCart: number[]) : Observable<User>{
    user.cart = newCart;
    return this.http.put<User>(this.url, user, this.httpOptions);
  }
}