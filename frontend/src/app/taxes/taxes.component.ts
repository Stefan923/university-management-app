import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { DataSource } from '@angular/cdk/table';

import { MatTableModule } from '@angular/material/table'  ;


export interface User {
  firstName: string;
  lastName: string;
  role: string;
}

const ELEMENT_DATA: User[] = [
  {firstName: 'Iulia', lastName: 'Stoica', role: 'student'},
  {firstName: 'Mara', lastName: 'Patac', role: 'student'},
  {firstName: 'Stefan', lastName: 'Popescu', role: 'teacher'},
];

@Component({
  selector: 'app-taxes',
  templateUrl: './taxes.component.html',
  styleUrls: ['./taxes.component.css']
})
export class TaxesComponent implements OnInit {

  displayedColumns = [];
  dataSource: MatTableDataSource<any>;
  @ViewChild(MatSort) sort: MatSort;

  columnNames = [{
    id: 'firstName',
    value: 'First Name',

  }, {
    id: 'lastName',
    value: 'Last Name',
  },
    {
      id: 'role',
      value: 'Role',
    }
  ];


  constructor() { }

  ngOnInit() {
    this.displayedColumns = ['firstName', 'lastName', 'role', 'actions'];
    this.dataSource = new MatTableDataSource(ELEMENT_DATA);
    this.dataSource.sort = this.sort;
  }

}
