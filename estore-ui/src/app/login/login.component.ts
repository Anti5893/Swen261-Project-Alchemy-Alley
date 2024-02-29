import { Component , Input} from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs';
import { tap } from 'rxjs'; 

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

  
  constructor(private userService : UserService, private credentialsService : CredentialsService){ }

  getAuthenticationStatus(username: string, password: string): Observable<number> {
    return this.userService.authenticateUser({ username, password } as User).pipe(
      map((response: HttpResponse<User>) => response.status),
    );
  }

  storeCurrentUser(username : string, password : string){
    this.credentialsService.storeCurrentUser({username, password} as User)
  }

  authenticateUser() {
    this.getAuthenticationStatus(this.username, this.password).subscribe((code: number) => {
      this.isAuthenticated = code !== 401;
    });
    return this.isAuthenticated;
  }
}
