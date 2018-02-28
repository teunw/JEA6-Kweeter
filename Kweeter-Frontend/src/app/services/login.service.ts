import {Injectable, OnInit} from '@angular/core';
import {IProfile} from "../profile";
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Injectable()
export class LoginService {

  private _loginInfo = new BehaviorSubject(this.getLoginInfo());

  public loginInfo = this._loginInfo.asObservable();

  constructor() {
  }

  public saveLoginInfo(profile: IProfile) {
    return localStorage.setItem('login', JSON.stringify(profile));
  }

  public isLoggedIn(): boolean {
    return this.getLoginInfo() !== undefined && this.getLoginInfo() !== null;
  }

  public deleteLoginInfo() {
    return localStorage.removeItem('login');
  }

  private getLoginInfo(): IProfile | null {
    const possibleProfile = localStorage.getItem('login');
    const parseResult = JSON.parse(possibleProfile);
    return parseResult;
  }

}
