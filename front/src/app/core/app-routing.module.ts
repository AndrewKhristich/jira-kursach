import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthComponent} from '../auth/auth.component';
import {DashboardComponent} from '../dashboard/dashboard.component';
import {TasklistComponent} from '../tasklist/tasklist.component';
import {TaskcreateComponent} from '../taskcreate/taskcreate.component';
import {SingletaskComponent} from '../singletask/singletask.component';
import {CreateUserComponent} from '../create-user/create-user.component';
import {GrandComponent} from '../grand/grand.component';
import {AdminDashboardComponent} from '../admin-dashboard/admin-dashboard.component';


const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'admin', component: AdminDashboardComponent, children: [
      { path: 'createuser', component: CreateUserComponent},
      { path: 'grand', component: GrandComponent},
    ]},
  { path: 'dashboard', component: DashboardComponent, children: [
      { path: 'tasklist', component: TasklistComponent },
      { path: 'tasklist/:id', component: SingletaskComponent},
      { path: 'tasklist/:id/createtask', component: TaskcreateComponent},
      { path: 'createtask', component: TaskcreateComponent},

    ]},
  { path: 'login', component: AuthComponent}
];

@NgModule({
  imports: [
     RouterModule.forRoot(routes)
  ],
  exports: [ RouterModule ],
  declarations: []
})
export class AppRoutingModule { }
