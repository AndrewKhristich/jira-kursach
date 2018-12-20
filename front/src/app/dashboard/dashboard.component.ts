import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-dashboard',
  providers: [AuthService],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  items: MenuItem[];

  barStyle = {
    'width' : '150px',
    'text-align' : 'center'
  };


  constructor(private auth: AuthService) { }

  ngOnInit() {
    this.auth.checkToken();
    this.items = [
      {
        label: 'Задачи',
        routerLink: 'tasklist',
        style: this.barStyle,
      },
      {
        label: 'Создать задачу',
        routerLink: 'createtask',
        style: this.barStyle,
      }
    ];
  }

  logout() {
    this.auth.logout();
  }
}
