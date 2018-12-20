import {Component, OnInit} from '@angular/core';
import {TaskService} from '../services/task.service';
import {Page} from '../models/Page';

@Component({
  selector: 'app-tasklist',
  providers: [TaskService],
  templateUrl: './tasklist.component.html',
  styleUrls: ['./tasklist.component.css']
})
export class TasklistComponent implements OnInit {

  page: Page<any> = new Page<any>();
  tasks: Array<any>;
  sizes: [5, 10, 15];
  cols: any[];

  constructor(private taskService: TaskService) {
  }

  ngOnInit() {
    this.cols = [
      { header: 'Author' },
      { header: 'Name' },
      { header: 'Profile' },
      { header: 'Status' },
    ];
    this.getTasksByPage(0, 5);
  }

  getTasksByPage(page, size): void {
    this.taskService.getTasksByPage(page, size)
      .subscribe(value => {
        this.page = value;
        this.tasks = value.content;
        console.log(this.tasks);
      });
  }

}
