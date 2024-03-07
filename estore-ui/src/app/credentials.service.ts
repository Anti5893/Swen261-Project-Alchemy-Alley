import { Injectable } from '@angular/core';
import { User } from '../user';
import { JitEvaluator } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class CredentialsService {
  
  storeCurrentUser(user : User | null){
    localStorage.setItem('user', JSON.stringify(user))
  }
  removeCurrentUser(){
    localStorage.removeItem('user');
  }

  isLoggedIn() : boolean{
   return localStorage.getItem('user') != null;
  }

  isAdmin() : boolean{
    const userJson = JSON.parse(localStorage.getItem('user')||'{}');
    return userJson.admin == true;
  }
}
