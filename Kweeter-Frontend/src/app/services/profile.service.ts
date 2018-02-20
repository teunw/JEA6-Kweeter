import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Kweet} from '../kweet';
import {ConfigService} from './config.service';
import {HttpClient} from '@angular/common/http';
import {Profile} from '../profile';

@Injectable()
export class ProfileService {

  constructor(private httpClient: HttpClient, private configService: ConfigService) {
  }

  getProfiles(): Observable<Profile[]> {
    return this.httpClient.get(`${this.configService.getKweeterEndpoint()}/profiles`) as Observable<Profile[]>;
  }

  getProfile(id: number): Observable<Profile>|null {
    return this.httpClient.get(`${this.configService.getKweeterEndpoint()}/api/profiles/${id}`) as Observable<Profile>;
  }
}
