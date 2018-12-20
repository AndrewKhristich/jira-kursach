import { Component, OnInit } from '@angular/core';
import {MenuItem} from 'primeng/api';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-admin-dashboard',
  providers: [AuthService],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  items: MenuItem[];

  barStyle = {
    'width' : '250px',
    'text-align' : 'center'
  };


  constructor(private auth: AuthService) { }

  ngOnInit() {
    this.auth.checkIfAdmin();
    this.items = [
      {
        label: 'Создать пользователя',
        routerLink: 'createuser',
        style: this.barStyle,
      },
      {
        label: 'Дать доступ',
        routerLink: 'grand',
        style: this.barStyle,
      }
    ];
  }

  logout() {
    this.auth.logout();
  }
}
