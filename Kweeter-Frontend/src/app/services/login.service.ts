import { Injectable } from '@angular/core';

@Injectable()
export class LoginService {

  constructor() { }

  public saveLoginEmail(email:string) {
    return localStorage.setItem("login-email", email);
  }

  public getLoginEmail() {
    return localStorage.getItem("login-email");



  }

}
