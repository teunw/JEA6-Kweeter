import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaderResponse, HttpHeaders} from '@angular/common/http';
import {ConfigService} from './config.service';
import {Observable} from 'rxjs/Observable';
import {IKweet, IKweetPost} from '../kweet';
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

  createKweet(kweet: IKweetPost) {
    console.log("Posting ....");
    console.log(kweet);
    const headers =  new HttpHeaders();
    headers.append("Accepts", "application/json");

    return this.httpClient.post<IKweet>(
      `${this.configService.getKweeterEndpoint()}/kweets`,
      kweet
    ).subscribe(res => {
      console.log("Got");
      console.log(res);
    });
  }
}
