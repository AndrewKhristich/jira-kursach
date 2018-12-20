import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AuthComponent} from './auth/auth.component';
import {TaskComponent} from './task/task.component';
import {AuthService} from './services/auth.service';
import {PanelModule} from 'primeng/panel';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule} from '@angular/forms';
import {UiComponentsModule} from './core/ui-components.module';
import {CookieService} from 'ngx-cookie-service';
import {AppRoutingModule} from './core/app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import { TasklistComponent } from './tasklist/tasklist.component';
import { TaskcreateComponent } from './taskcreate/taskcreate.component';
import { SingletaskComponent } from './singletask/singletask.component';
import {TaskService} from './services/task.service';
import {PaginatorModule} from 'primeng/primeng';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { GrandComponent } from './grand/grand.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    AuthComponent,
    TaskComponent,
    TasklistComponent,
    TaskcreateComponent,
    SingletaskComponent,
    AdminDashboardComponent,
    CreateUserComponent,
    GrandComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    UiComponentsModule,
    BrowserAnimationsModule,
    FormsModule,
    // PaginatorModule
  ],
  providers: [CookieService, AuthService, TaskService],
  bootstrap: [AppComponent]
})
export class AppModule { }
