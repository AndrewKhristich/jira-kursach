import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-auth',
  providers: [AuthService],
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  public loginData = {username: '', password: ''};

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  login() {
    this.authService.obtainJwtToken(this.loginData);
  }
}
