import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {ViewKweetsComponent} from './kweets/view-kweets/view-kweets.component';
import {ConfigService} from './services/config.service';
import {KweetService} from './services/kweet.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import {MomentModule} from 'angular2-moment';
import {ProfileComponent} from './profiles/profile/profile.component';
import {ProfileService} from './services/profile.service';
import {ViewKweetComponent} from './kweets/view-kweet/view-kweet.component';
import {SidebarComponent} from './kweeter/sidebar/sidebar.component';
import {AllKweetsComponent} from './kweeter/all-kweets/all-kweets.component';
import {HomeComponent} from './kweeter/home/home.component';
import {LoadComponent} from './load/load.component';
import {PostComponent} from './kweets/post/post.component';
import {FormsModule} from '@angular/forms';
import { NavbarComponent } from './kweeter/navbar/navbar.component';
import { LoginComponent } from './kweeter/account/login/login.component';
import {LoginService} from "./services/login.service";
import { ManageAccountComponent } from './kweeter/account/manage-account/manage-account.component';

const appRoutes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'kweets', component: AllKweetsComponent},
  {path: 'profiles/:id', component: ProfileComponent},
  {path: 'login', component: LoginComponent},
  {path: 'account', component: ManageAccountComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    ViewKweetsComponent,
    ProfileComponent,
    ViewKweetComponent,
    SidebarComponent,
    AllKweetsComponent,
    HomeComponent,
    LoadComponent,
    PostComponent,
    NavbarComponent,
    LoginComponent,
    ManageAccountComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, {enableTracing: false}),
    MomentModule
  ],
  providers: [HttpClient, ConfigService, KweetService, ProfileService, LoginService],
  bootstrap: [AppComponent],
})
export class AppModule {
}
