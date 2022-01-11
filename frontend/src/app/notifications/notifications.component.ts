import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';

export interface User {
  firstName: string;
  lastName: string;
  username: string;
  role: string;
}

const ELEMENT_DATA: User[] = [
  {firstName: 'Iulia', lastName: 'Stoica', username: 'iustoica', role: 'student'},
  {firstName: 'Mara', lastName: 'Patac', username: 'mpatac', role: 'student'},
  {firstName: 'Stefan', lastName: 'Popescu', username: 'spopescu',role: 'teacher'},
];


declare var $: any;
@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  displayedColumns = [];
  dataSource: MatTableDataSource<any>;
  @ViewChild(MatSort) sort: MatSort;


  columnNames = [{
    id: 'firstName',
    value: 'First Name',

  }, {
    id: 'lastName',
    value: 'Last Name',
  },{
    id: 'username',
    value: 'Username',
  },{
      id: 'role',
      value: 'Role',
    }
  ];


  constructor() { }

  ngOnInit() {
    this.displayedColumns = ['firstName', 'lastName', 'username', 'role', 'actions'];
    this.dataSource = new MatTableDataSource(ELEMENT_DATA);
    this.dataSource.sort = this.sort;
  }

}
