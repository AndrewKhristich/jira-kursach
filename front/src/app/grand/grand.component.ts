import { Component, OnInit } from '@angular/core';
import {TaskService} from '../services/task.service';
import {UserService} from '../services/user.service';

@Component({
  selector: 'app-grand',
  providers: [TaskService, UserService],
  templateUrl: './grand.component.html',
  styleUrls: ['./grand.component.css']
})
export class GrandComponent implements OnInit {
  users: any[] = [];
  selectedUser: any;
  tasks: any[] = [];
  selectedTask: any;

  constructor(private taskService: TaskService, private userService: UserService) { }

  ngOnInit() {
    this.taskService.findAll().subscribe(value => this.tasks = value);
    this.userService.findAll().subscribe(value => this.users = value);
  }

  grand() {
    this.taskService.grand(this.selectedUser.id, this.selectedTask.id)
      .subscribe(value => {});
  }
}
