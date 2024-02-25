import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { User } from '../../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})

export class RegisterComponent {
@Input() username ? : string;
@Input() password ? : string;
constructor(service : UserService){
  }

  register(){
    if(this.username && this.password){
      console.log(this.username, this.password)
    }
  }
}
