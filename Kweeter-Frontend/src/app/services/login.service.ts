import { Injectable } from '@angular/core';
import {IProfile} from "../profile";

@Injectable()
export class LoginService {

  constructor() { }

  public saveLoginInfo(profile:IProfile) {
    return localStorage.setItem("login", JSON.stringify(profile));
  }

  public isLoggedIn():boolean {
    return this.getLoginInfo() !== undefined && this.getLoginInfo() !== null;
  }

  public getLoginInfo():IProfile|null {
    const possibleProfile = localStorage.getItem("login");
    const parseResult = JSON.parse(possibleProfile);
    return parseResult;
  }

}
