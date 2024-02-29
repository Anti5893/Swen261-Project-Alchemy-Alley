import { Injectable } from '@angular/core';
import { User } from '../user';


@Injectable({
  providedIn: 'root'
})
export class CredentialsService {
  
  storeCurrentUser(user : User){
    localStorage.setItem('currentUsername', user.username)
    localStorage.setItem('currentPassword', user.password)
    if(user.isAdmin == true){
      localStorage.setItem('currentAdmin', 'true')
    }
  }
  removeCurrentUser(){
    localStorage.removeItem('currentUsername');
    localStorage.removeItem('currentPassword');
  }

  isLoggedIn() : boolean{
    return (localStorage.getItem('currentUsername') != null && localStorage.getItem('currentPassword') != null);
  }

  isAdmin() : boolean{
    return (localStorage.getItem('currentAdmin') == 'true');
  }
}
