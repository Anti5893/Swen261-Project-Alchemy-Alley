import { Injectable } from '@angular/core';




@Injectable({
  providedIn: 'root'
})
export class CredentialsService {
  
  storeCurrentUser(username : string, userPassword : string){
    localStorage.setItem('currentUsername', username);
    localStorage.setItem('currentUserPassword',userPassword);
  }
  removeCurrentUser(){
    localStorage.removeItem('currentUsername');
    localStorage.removeItem('currentUserPassword');
  }
}
