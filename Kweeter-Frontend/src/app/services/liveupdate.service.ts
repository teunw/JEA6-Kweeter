import {ConfigService} from "./config.service";
import {Injectable} from "@angular/core";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Kweet} from "../kweet";

@Injectable()
export class LiveupdateService {

  public _liveKweets = new BehaviorSubject(null);
  public liveKweets = this._liveKweets.asObservable();

  private ws: WebSocket;

  constructor(private configService: ConfigService) {
    this.ws = new WebSocket(`${this.configService.getSocketEndpoint()}/update/kweets`);
  }


  getLiveKweetUpdates() {
    this.ws.onmessage = ev => {
      console.log(ev)
    };
  }

  postKweetUpdates(kweet: Kweet) {
    this.ws.send(kweet);
    console.log(`Sending kweet over websocket: ${kweet.textContent}`)
  }
}
