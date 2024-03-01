import { Component , Input} from '@angular/core';
import { Observable, ObservedValueOf } from 'rxjs';
import { map } from 'rxjs';
import { Router } from '@angular/router';

import { User } from '../../user';
import { UserService } from '../user.service';
import { CredentialsService } from '../credentials.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username : string = '';
  password : string = '';
  users: User[] = [];
  isAuthenticated = false;
  errorMessage = '';
  buttonClicked = false;

  
  constructor(private userService : UserService, private credentialsService : CredentialsService, private router : Router){ }

  

  storeCurrentUser(username : string, password : string){
    if(this.username == 'Admin' && this.password == 'Admin123'){
      this.credentialsService.storeCurrentUser({username,password,isAdmin:true} as User)
    }
    else{
      this.credentialsService.storeCurrentUser({username, password} as User)
    }
    
  }

  getAuthenticationStatus(username: string, password: string): Observable<number> {
    return this.userService.authenticateUser({ username, password } as User).pipe(
      map((response: HttpResponse<User>) => response.status)
    );
  }

  authenticateUser() : Observable<boolean>{
    return new Observable<boolean>(observer =>{
      this.getAuthenticationStatus(this.username,this.password).subscribe(
        (code : Number) => {
          this.isAuthenticated = (code !== 400);
          observer.next(this.isAuthenticated)
          observer.complete()
        }
      );
    });
  }

  showErrorMesssage(){
    return (this.buttonClicked && !this.isAuthenticated)
  }

  onClick(){
    this.buttonClicked = true;
    this.authenticateUser().subscribe(isAuthenticated =>{
      if(isAuthenticated){
        this.storeCurrentUser(this.username, this.password);
        this.router.navigate(['/catalog']);
        this.errorMessage = '';
      }
    })
  }
}
