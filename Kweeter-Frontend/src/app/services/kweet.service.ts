import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from './config.service';
import {Observable} from 'rxjs/Observable';
import {IKweet} from '../kweet';
import {IProfile} from '../profile';

@Injectable()
export class KweetService {

  constructor(private httpClient: HttpClient, private configService: ConfigService) {
  }

  getKweets(): Observable<IKweet[]> {
    return this.httpClient.get<IKweet[]>(`${this.configService.getKweeterEndpoint()}/kweets`);
  }

  getKweetsForProfile(profile: IProfile): Observable<IKweet[]> {
    return this.httpClient.get<IKweet[]>(`${this.configService.getKweeterEndpoint()}/kweets/forprofile/${profile.id}`);
  }

  getKweet(id: number): Observable<IKweet> | null {
    return this.httpClient.get<IKweet>(`${this.configService.getKweeterEndpoint()}/api/kweets/${id}`);
  }

  createKweet(kweet: IKweet) {
    return this.httpClient.post(`${this.configService.getKweeterEndpoint()}/api/kweets`, kweet);
  }

}
