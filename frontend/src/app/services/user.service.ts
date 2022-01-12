import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user';
import { map } from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private static readonly BASE_URL = "http://localhost:8080";

  constructor(private httpClient: HttpClient) { }

  getUsersByName(firstName: string, lastName: string, page: number, pageSize: number): Observable<GetResponseUsers> {
    const url = `${UserService.BASE_URL}/users/search/byName`
      + `?firstName=${firstName}&lastName=${lastName}&page=${page}&size=${pageSize}`;
    return this.httpClient.get<GetResponseUsers>(url);
  }

  getUsersByRoleId(roleId: number, page: number, pageSize: number): Observable<GetResponseUsers> {
    const url = `${UserService.BASE_URL}/users/search/byRole`
      + `?roleId=${roleId}&page=${page}&size=${pageSize}`;

    return this.httpClient.get<GetResponseUsers>(url);
  }

  getUsersByUsername(username: string, page: number, pageSize: number): Observable<GetResponseUsers> {
    const url = `${UserService.BASE_URL}/users/search/byUsername`
      + `?username=${username}}&page=${page}&size=${pageSize}`;

    return this.httpClient.get<GetResponseUsers>(url);
  }

  getUserById(userId: number): Observable<User> {
    const url = `${UserService.BASE_URL}/users/${userId}`;

    return this.httpClient.get<User>(url);
  }

}

export interface GetResponseUsers {
  _embedded: {
    users: User[];
  },
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  }
}
