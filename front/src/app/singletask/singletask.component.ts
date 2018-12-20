import {Component, OnInit} from '@angular/core';
import {TaskService} from '../services/task.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-singletask',
  providers: [TaskService],
  templateUrl: './singletask.component.html',
  styleUrls: ['./singletask.component.css']
})
export class SingletaskComponent implements OnInit {

  id: number;
  task: any = {
    author: new Object(),
    reports: [new Object()],
    subTasks: [new Object()],
    users: [new Object()]
  };
  updating: any;
  display = false;
  newReport: any = new Object();
  newDescr: string;
  private reportDto: any = new Object();
  private statuses: {}[] = [
    {name: 'PROGRESS', code: 'PROGRESS'},
    {name: 'TEST', code: 'TEST'},
    {name: 'RESOLVED', code: 'RESOLVED'},
  ];
  private newStatus: {name: string, code: string};

  constructor(private taskService: TaskService, private router: ActivatedRoute, private route: Router) {
    this.router.params.subscribe(params => {
      this.id = params.id;
    });
    this.taskService.checkGrand(this.id).subscribe(() => {}, error1 => {
      alert('No access for this task');
      route.navigate(['/dashboard/tasklist']);
    });
    this.refreshReport();
  }

  ngOnInit() {
    this.taskService.getTasksById(this.id)
      .subscribe(value => {
        this.task = value;
        this.newDescr = value.description;
      });
  }

  saveUpdated() {
    const updateObj = {status: this.newStatus.name, description: this.newDescr};
    this.taskService.update(updateObj, this.id)
      .subscribe(() => {
        this.task.status = this.newStatus.name;
        this.task.description = this.newDescr;
      });
  }

  startUpdate() {
    this.updating = !this.updating;
  }

  showDialog() {
    this.display = true;
  }

  doReport() {
    this.reportDto.taskId = this.id;
    this.reportDto.estimate = this.newReport.estimate;
    this.reportDto.description = this.newReport.description;
    this.reportDto.from = this.newReport.from;
    this.taskService.doReport(this.reportDto);
  }

  refreshReport() {
    this.newReport.estimate = 0;
    this.newReport.description = '';
    this.newReport.from = new Date();
  }

  createSub() {
    this.route.navigate(['/dashboard/tasklist/' + this.id + '/createtask']);
  }
}
