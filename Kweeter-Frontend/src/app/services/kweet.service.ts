import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from './config.service';
import {Observable} from 'rxjs/Observable';
import {Kweet} from '../kweet';

@Injectable()
export class KweetService {

  constructor(private httpClient: HttpClient, private configService: ConfigService) {
  }

  getKweets(): Observable<Kweet[]> {
    return this.httpClient.get(`${this.configService.getKweeterEndpoint()}/kweets`) as Observable<Kweet[]>;
  }

  getKweet(id: number): Observable<Kweet>|null {
    return this.httpClient.get(`${this.configService.getKweeterEndpoint()}/api/kweets/${id}`) as Observable<Kweet>;
  }

}
