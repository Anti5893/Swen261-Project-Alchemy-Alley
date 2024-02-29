import { Component , Input} from '@angular/core';

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

  authenticateResponseCode : number = 0


  constructor(private userService : UserService, private credentialsService : CredentialsService){ }

  authenticateUser(username : string, password : string){
    this.userService.authenticateUser({username, password} as User).subscribe((response : HttpResponse<User>) =>{
      this.authenticateResponseCode = response.status;
      console.log(this.authenticateResponseCode)
    })
  }

  storeCurrentUser(username : string, password : string){
    this.credentialsService.storeCurrentUser({username, password} as User);
  }
}
