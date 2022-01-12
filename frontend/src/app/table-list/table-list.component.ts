import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { DataSource } from '@angular/cdk/table';

import { MatTableModule } from '@angular/material/table'  ;
import { GetResponseUsers, UserService } from 'app/services/user.service';
import { User } from 'app/models/user';

@Component({
  selector: 'app-table-list',
  templateUrl: './table-list.component.html',
  styleUrls: ['./table-list.component.css']
})
export class TableListComponent implements OnInit {

  users: User[] = [];

  number: number = 1;
  pageSize: number = 20;
  totalElements: number = 0;

  firstName: string = "";
  lastName: string = "";

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
      id: 'roleId',
      value: 'Role',
    }
  ];


  constructor(private userService: UserService) { }

  ngOnInit() {
    this.handleListUsersByName() ;
    this.displayedColumns = ['firstName', 'lastName', 'roleId', 'actions'];
    this.dataSource = new MatTableDataSource(this.users);
    this.dataSource.sort = this.sort;
  }

  handleListUsersByName() {
    this.userService.getUsersByName(this.firstName, this.lastName, this.number-1, this.pageSize).subscribe(this.processServiceResult());
    console.log(this.users);
  }

  setPageSize(pageSize: number) {
    this.number = 1;
    this.pageSize = pageSize;
    this.handleListUsersByName();
  }

  private processServiceResult() {
    return (data: GetResponseUsers): void => {
      this.users = data._embedded.users;
      this.number = data.page.number + 1;
      this.pageSize = data.page.size;
      this.totalElements = data.page.totalElements;
      this.dataSource = new MatTableDataSource(this.users);
    }
  }

}
