import { Component } from '@angular/core';
import { CredentialsService } from './credentials.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title: any;

  constructor(private credentialsService : CredentialsService){ }

  logOut(){
    this.credentialsService.removeCurrentUser()
  }
}
