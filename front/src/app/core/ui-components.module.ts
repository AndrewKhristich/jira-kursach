import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';


import {ButtonModule} from 'primeng/button';
import {CardModule} from 'primeng/card';
import {InputTextModule} from 'primeng/inputtext';
import {MenubarModule} from 'primeng/menubar';
import {PanelModule} from 'primeng/panel';
import {PasswordModule} from 'primeng/password';
import {TableModule} from 'primeng/table';
import {PaginatorModule} from 'primeng/paginator';
import {DialogModule} from 'primeng/dialog';
import {CalendarModule} from 'primeng/calendar';
import {ListboxModule} from 'primeng/listbox';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    ButtonModule,
    CardModule,
    InputTextModule,
    MenubarModule,
    PanelModule,
    PasswordModule,
    DialogModule,
    ListboxModule,
    // TabViewModule,
    TableModule,
    PaginatorModule,
    CalendarModule
    // PaginatorModule,
    // DataTableModule
  ],
  declarations: []
})
export class UiComponentsModule {
}
