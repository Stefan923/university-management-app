import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, Subject, throwError, of, BehaviorSubject } from 'rxjs';
import { map, mergeMap, switchMap, catchError, tap } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { UserModel } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isLoggedIn = new BehaviorSubject(false);

  onLogin = new Subject<any>(); // deprecated
  onLogout = new Subject<any>(); // deprecated

  private token: string = null;
  private userData: UserModel = null;

  constructor(
    private http: HttpClient,
  ) {
    // try and find out if there was a localstorage token was set
    this.resolveToken();
  }

  validateTokenOnServer() {
    return this.http.get(environment['apiBaseUrl'] + '/api/auth/validate-token')
      .pipe(
        map(data => {
          return data['user'] ? data['user'] : false;
        }
        ),
        tap((status) => { if (status) { this.userData = status['user']; } }),
        tap((status) => { if (!status) { this.isLoggedIn.next(false); } }),
        catchError(err => {
          return of(false);
        }),
      );
  }

  // check if localstorage token was set
  // if so, set the token in the service
  // and set the login status
  resolveToken(): boolean {
    this.token = localStorage.getItem('token');
    this.isLoggedIn.next(this.token ? true : false);
    return this.token ? true : false;
  }

  getToken(): string {
    return this.token;
  }

  hasToken(): boolean {
    return this.getToken() ? true : false;
  }

  async logout() {
    return this.http.get(environment['apiBaseUrl'] + '/logout').toPromise().then(
      () => {
        // clear any current data
        this.clearData();

        // tell the rest of the application about the logout
        this.isLoggedIn.next(false);
        return true;
      },
      (err) => {
        return false;
      }
    );
  }

  async login({ username, password }): Promise<any> {
    // clear some data
    this.clearData();

    // create the payload data for the api request
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    console.log(`Login: ${body}`);
    
    const data = await this.http.post('http://localhost:8080/login',
      body.toString(),
      {
        headers: new HttpHeaders()
          .set('Content-Type', 'application/x-www-form-urlencoded')
      }
    ).toPromise();

    console.log(`Http Response: `, data);
    
    // chrome.exe --user-data-dir="C://Chrome dev session" --disable-web-security
    // this part only gets executed when the promise is resolved
    if (data['token'] && data['username']) {

      this.setDataAfterLogin(data);
      this.isLoggedIn.next(true); // how do I unit test this?

      return data['username'];
    } else {
      return false;
    }
  }

  clearData() {
    this.userData = null;
    this.token = null;
    localStorage.clear();
  }

  getUserData(): UserModel {
    return this.userData;
  }

  private setDataAfterLogin(data) {
    this.token = data['token'];

    // store some user data in the service
    this.userData = data['user'];

    // store some data in local storage (webbrowser)
    localStorage.setItem('token', this.token);
    localStorage.setItem('usermeta', JSON.stringify(this.userData));
  }
}
