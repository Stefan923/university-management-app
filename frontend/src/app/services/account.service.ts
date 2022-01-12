import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Account } from '../models/account';
import { map } from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private static readonly BASE_URL = "http://localhost:8080";

  constructor(private httpClient: HttpClient) { }

  getAccounts(page: number, pageSize: number): Observable<GetResponseAccounts> {
    const url = `${AccountService.BASE_URL}/accounts?page=${page}&size=${pageSize}`;

    return this.httpClient.get<GetResponseAccounts>(url);
  }

  getAccountById(accountId: number): Observable<Account> {
    const url = `${AccountService.BASE_URL}/accounts/${accountId}`;

    return this.httpClient.get<Account>(url);
  }

}

export interface GetResponseAccounts {
  _embedded: {
    accounts: Account[];
  },
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  }
}
