import { Component, Input } from '@angular/core';

import { User } from '../../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})

export class RegisterComponent {
  username  : string = '';
  password  : string = '';
  passwordConfirm : string = '';
  users: User[] = [];
constructor(private service : UserService){}

validInfo(){
  return (this.username != '' && (this.password == this.passwordConfirm) && (this.password != '' && this.passwordConfirm != ''))
}

register(username : string, password : string): void {
  this.service.addUser({username, password} as User)
  .subscribe(hero => {
    this.users.push(hero);
  });
  console.log(username, password);
  }
}
