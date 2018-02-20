import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {ViewKweetsComponent} from './kweets/view-kweets/view-kweets.component';
import {ConfigService} from './services/config.service';
import {KweetService} from './services/kweet.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import {MomentModule} from 'angular2-moment';
import { ProfileComponent } from './profiles/profile/profile.component';
import {ProfileService} from './services/profile.service';

const appRoutes: Routes = [
  { path: 'kweets', component: ViewKweetsComponent },
  { path: 'kweets/:id', component: ViewKweetsComponent },
  { path: 'profiles/:id', component: ProfileComponent },
  { path: 'profiles/', component: ProfileComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    ViewKweetsComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, {enableTracing: true}),
    MomentModule
  ],
  providers: [HttpClient, ConfigService, KweetService, ProfileService],
  bootstrap: [AppComponent],
})
export class AppModule {
}
