import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { DataSource } from '@angular/cdk/table';

import { MatTableModule } from '@angular/material/table'  ;
import { Router } from '@angular/router';
import { GetResponseUsers, UserService } from 'app/services/user.service';
import { User } from 'app/models/user';

@Component({
  selector: 'students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.scss']
})
export class StudentsComponent implements OnInit {

  displayedColumns = [];
  dataSource: MatTableDataSource<any>;
  @ViewChild(MatSort) sort: MatSort;

  private users: User[] = [];

  private number: number = 1;
  private pageSize: number = 20;
  private totalElements: number = 0;

  private firstName: string = "";
  private lastName: string = "";

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

  constructor(private router: Router,
              private userService: UserService) { }

  ngOnInit() {
    this.handleListUsersByRoleId() ;
    this.displayedColumns = ['firstName', 'lastName', 'roleId', 'actions'];
    this.dataSource = new MatTableDataSource(this.users);
    this.dataSource.sort = this.sort;
  }
  onEdit(){
    this.router.navigate(['/user-profile']);
  }

  handleListUsersByRoleId() {
    this.userService.getUsersByRoleId(3, this.number-1, this.pageSize).subscribe(this.processServiceResult());
    console.log(this.users);
  }

  setPageSize(pageSize: number) {
    this.number = 1;
    this.pageSize = pageSize;
    this.handleListUsersByRoleId();
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
