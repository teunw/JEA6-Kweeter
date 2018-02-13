import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Profile} from '../domain/profile';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class ProfileService {

  constructor(private httpClient: HttpClient) {

  }

  public getAllProfiles(): Observable<Array<Profile>> {
    return this.httpClient
      .get(`${environment.backendRoot}/profiles`, {headers: {'Accepts': 'application/json'}}) as Observable<Array<Profile>>;
  }

}
