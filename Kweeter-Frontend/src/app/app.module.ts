import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { LoginComponentComponent } from './login-component/login-component.component';
import {MatListModule} from '@angular/material';
import {ProfileService} from './services/profile.service';
import {HttpClientModule} from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponentComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatListModule
  ],
  providers: [HttpClientModule, ProfileService],
  bootstrap: [AppComponent]
})
export class AppModule { }
