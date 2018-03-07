import {Injectable} from '@angular/core';
import {IProfile} from '../profile';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ConfigService} from './config.service';
import {IAuthToken} from '../IAuthToken';

@Injectable()
export class LoginService {

  public static readonly AUTH_KEY = 'AUTH_KEY';
  public static readonly PROFILE_CACHE = 'PROFILE_CACHE';

  public _loginInfo = new BehaviorSubject(null);
  public loginInfo = this._loginInfo.asObservable();

  constructor(private httpClient: HttpClient, private configService: ConfigService) {

  }

  public async attemptLogin(email: String, password: String) {
    this.httpClient.post(`${this.configService.getKweeterEndpoint()}/auth/authenticate`, {
      email: email,
      password: password
    })
      .subscribe((res: IAuthToken) => {
        console.log(res);
        const profile = res.profile;
        localStorage.setItem(LoginService.AUTH_KEY, res.token);
        localStorage.setItem(LoginService.PROFILE_CACHE, JSON.stringify(profile));
        this._loginInfo.next(profile);
      });
  }

  public validateLogin(token: String) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    this.httpClient
      .get(`${this.configService.getKweeterEndpoint()}/auth/validate`, {headers: headers})
      .subscribe((resp: IAuthToken) => {
        const profile = resp.profile as IProfile;

        localStorage.setItem(LoginService.AUTH_KEY, resp.token);
        localStorage.setItem(LoginService.PROFILE_CACHE, JSON.stringify(profile));
        this._loginInfo.next(profile);
      });
  }

  public isLoggedIn() {
    return this.hasAuthToken() && localStorage.getItem(LoginService.PROFILE_CACHE) != null;
  }

  public getLoginInfo(): IProfile {
    return JSON.parse(localStorage.getItem(LoginService.PROFILE_CACHE));
  }

  public deleteLoginInfo(): void {
    localStorage.removeItem(LoginService.PROFILE_CACHE);
    localStorage.removeItem(LoginService.AUTH_KEY);
  }

  public getAuthToken(): string {
    return localStorage.getItem(LoginService.AUTH_KEY);
  }

  public hasAuthToken() {
    return this.getAuthToken() != null;
  }

}
