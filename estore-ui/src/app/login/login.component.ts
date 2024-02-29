import { Component , Input} from '@angular/core';

import { User } from '../../user';
import { UserService } from '../user.service';
import { CredentialsService } from '../credentials.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username : string = '';
  password : string = '';
  users: User[] = [];


  constructor(private userService : UserService, private credentialsService : CredentialsService){ }

  authenticateUser(username : string, password : string){
    return this.userService.authenticateUser({username,password} as User)
    .subscribe(user => {
      this.users.push(user);
    });
  }

  storeCurrentUser(){
    this.credentialsService.storeCurrentUser(this.username, this.password);
  }
}
