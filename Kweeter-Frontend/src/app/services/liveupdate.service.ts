import {ConfigService} from "./config.service";
import {Injectable} from "@angular/core";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {IKweet, Kweet} from "../kweet";
import {LoginService} from "./login.service";

@Injectable()
export class LiveupdateService {

  public _liveKweets = new BehaviorSubject([]);
  public liveKweets = this._liveKweets.asObservable();

  private ws: WebSocket;

  constructor(private configService: ConfigService, private loginService: LoginService) {
    this.ws = new WebSocket(`${this.configService.getSocketEndpoint()}/update/kweets`);
  }


  getLiveKweetUpdates() {
    this.ws.onmessage = ev => {
      console.log('Got update from WS');
      console.log(ev);
      const kweet = JSON.parse(ev.data) as IKweet;
      const newLiveKweets = this._liveKweets.getValue().concat(kweet) as IKweet[];
      this._liveKweets.next(newLiveKweets);
      console.log("New live kweets");
      console.log(newLiveKweets);
    };
  }

  postKweetUpdates(kweet: Kweet) {
    kweet.date = new Date();
    this.ws.send(JSON.stringify(kweet));
    console.log(`Sending kweet over websocket: ${kweet.textContent}`);
  }
}
