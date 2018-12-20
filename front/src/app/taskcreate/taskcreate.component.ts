import {Component, OnInit} from '@angular/core';
import {TaskService} from '../services/task.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SelectItem} from 'primeng/api';

@Component({
  selector: 'app-taskcreate',
  providers: [TaskService],
  templateUrl: './taskcreate.component.html',
  styleUrls: ['./taskcreate.component.css']
})
export class TaskcreateComponent implements OnInit {

  profiles: SelectItem[];
  // profiles: string[];
  newTask: any = new Object();
  selectedProfile: any;
  id: string;

  constructor(private taskService: TaskService, private router: Router, private actRouter: ActivatedRoute) {
    this.actRouter.params.subscribe(value => this.id = value.id ? value.id : '');
  }

  ngOnInit() {
    this.profiles = [
      {label: 'WEB', value: 'WEB'},
      {label: 'MOBILE', value: 'MOBILE'},
      {label: 'BACKEND', value: 'BACKEND'},
      {label: 'DESIGN', value: 'DESIGN'},
    ];
  }

  createTask() {
    this.newTask.profile = this.selectedProfile;
    this.taskService.createTask(this.newTask, this.id)
      .subscribe(value => this.router.navigate(['/dashboard/tasklist/' + value.id.toString()]));
  }
}
