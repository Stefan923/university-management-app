import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { DataSource } from '@angular/cdk/table';

import { MatTableModule } from '@angular/material/table'  ;
import { Router } from '@angular/router';


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
  selector: 'students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.scss']
})
export class StudentsComponent implements OnInit {

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


  constructor(private router: Router) { }

  ngOnInit() {
    this.displayedColumns = ['firstName', 'lastName', 'role', 'actions'];
    this.dataSource = new MatTableDataSource(ELEMENT_DATA);
    this.dataSource.sort = this.sort;
  }

  onEdit(){
    this.router.navigate(['/user-profile']);
  }

}
