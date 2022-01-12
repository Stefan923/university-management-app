import { Component, Input, OnInit } from '@angular/core';

declare const $: any;
declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
    type: number;
}
export const ROUTES: RouteInfo[] = [
    { path: '/user-profile', title: 'User Profile',  icon:'person', class: '' , type:0},
    { path: '/all-users', title: 'All Users',  icon:'content_paste', class: '',type:0 },
    { path: '/pending-accounts', title: 'Pending Accounts',  icon:'notifications', class: '',type:1 },
    { path: '/taxes', title: 'Taxes',  icon:'library_books', class: '',type:1 },
    { path: '/students', title: 'Students',  icon:'library_books', class: '',type:0 },
    { path: '/logout', title: 'Log Out',  icon:'', class: '',type:0 },
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})

export class SidebarComponent implements OnInit {
  menuItems: any[];
  @Input() user: number;

  constructor() { }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
  }
  isMobileMenu() {
      if ($(window).width() > 991) {
          return false;
      }
      return true;
  };

}
