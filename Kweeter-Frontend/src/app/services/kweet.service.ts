import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaderResponse, HttpHeaders} from '@angular/common/http';
import {ConfigService} from './config.service';
import {Observable} from 'rxjs/Observable';
import {IKweet, IKweetPost} from '../kweet';
import {IProfile} from '../profile';
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Injectable()
export class KweetService {

  private _kweets: BehaviorSubject<IKweet[]> = new BehaviorSubject<IKweet[]>([]);

  public readonly kweets = this._kweets.asObservable();

  constructor(private httpClient: HttpClient, private configService: ConfigService) {
  }

  refreshKweets() {
    return this.getKweets();
  }

  getKweets(): Observable<IKweet[]> {
    this._kweets.next([]);
    const http = this.httpClient
      .get<IKweet[]>(`${this.configService.getKweeterEndpoint()}/kweets`);
    http.subscribe(data => {
      this._kweets.next(data as IKweet[]);
    });
    return http;
  }

  getKweetsForProfile(profile: IProfile): Observable<IKweet[]> {
    return this.httpClient.get<IKweet[]>(`${this.configService.getKweeterEndpoint()}/kweets/forprofile/${profile.id}`);
  }

  getKweet(id: number): Observable<IKweet> | null {
    return this.httpClient.get<IKweet>(`${this.configService.getKweeterEndpoint()}/api/kweets/${id}`);
  }

  createKweet(kweet: IKweetPost) {
    const headers = new HttpHeaders();
    headers.append("Accepts", "application/json");

    return this.httpClient.post<IKweet>(
      `${this.configService.getKweeterEndpoint()}/kweets`,
      kweet
    ).subscribe(res => {
      console.log("Got");
      console.log(res);
      this.getKweets();
    });
  }
}
