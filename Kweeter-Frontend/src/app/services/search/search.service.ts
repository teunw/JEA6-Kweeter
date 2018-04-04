import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../config.service';
import {IProfile} from '../../profile';
import {IKweet} from '../../kweet';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Observable} from 'rxjs/Observable';

export class SearchResult {

  public linkTo: string;
  public asString: string;

  constructor(linkTo: string, asString: string) {
    this.linkTo = linkTo;
    this.asString = asString;
  }
}

@Injectable()
export class SearchService {

  private _lastSearchResult = new BehaviorSubject([]);
  public lastSearchResult: Observable<SearchResult[]> = this._lastSearchResult.asObservable();
  public hasSearchResult = false;

  constructor(private httpClient: HttpClient, private configService: ConfigService) {
  }

  public clearResults() {
    this._lastSearchResult.next([]);
    this.hasSearchResult = false;
  }

  public getSearchResultsForQuery(query: string) {
    this.httpClient
      .get(`${this.configService.getKweeterEndpoint()}/search/all/${query}`)
      .subscribe(data => {
        const res = (data as (IKweet | IProfile)[])
          .map(value => {
            if (value.hasOwnProperty('textContent')) {
              const ikweet = value as IKweet;
              console.log(ikweet);
              return new SearchResult(`kweets/${ikweet.publicId}`, `Kweet by @${ikweet.author.username}: ${ikweet.textContent}`)
            }

            if (value.hasOwnProperty('username')) {
              const iprofile = value as IProfile;
              console.log(iprofile);
              return new SearchResult(`profiles/${iprofile.id}`, `@${iprofile.username}'s profile: ${iprofile.bio}`)
            }

            throw new Error("Could not parse searchresult, needs to be either profile or kweet")
          });

        this._lastSearchResult.next(res);
        this.hasSearchResult = true;
      });
  }

}
