import { Component , Input} from '@angular/core';
import { Observable } from 'rxjs';
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

  
  constructor(private userService : UserService, private credentialsService : CredentialsService, private router : Router){ }

  getAuthenticationStatus(username: string, password: string): Observable<number> {
    return this.userService.authenticateUser({ username, password } as User).pipe(
      map((response: HttpResponse<User>) => response.status),
    );
  }

  storeCurrentUser(username : string, password : string){
    if(this.username == 'Admin' && this.password == 'Admin123'){
      this.credentialsService.storeCurrentUser({username,password,isAdmin:true} as User)
    }
    else{
      this.credentialsService.storeCurrentUser({username, password} as User)
    }
    
  }

  authenticateUser() {
    this.getAuthenticationStatus(this.username, this.password).subscribe((code: number) => {
      this.isAuthenticated = code !== 401;
    });
    return this.isAuthenticated;
  }

  onClick(){
    this.authenticateUser()
    if(this.isAuthenticated){
      this.storeCurrentUser(this.username,this.password)
      this.router.navigate(['/catalog'])
    }
  }
}
