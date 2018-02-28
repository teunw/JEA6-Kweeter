import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../config.service';
import {Explanation, SearchResponse} from 'elasticsearch';
import {IProfile} from '../../profile';
import {IKweet} from '../../kweet';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class SearchService {

  private _lastSearchResult = new BehaviorSubject([]);
  private lastSearchResult: Observable<Array<{
    _index: string;
    _type: string;
    _id: string;
    _score: number;
    _source: IProfile | IKweet;
    _version?: number;
    _explanation?: Explanation;
    fields?: any;
    highlight?: any;
    inner_hits?: any;
    sort?: string[];
  }>> = this._lastSearchResult.asObservable();

  constructor(private httpClient: HttpClient, private configService: ConfigService) {
  }

  public getSearchResultsForQuery(query: string) {
    const search = {
      'query': {
        'query_string': {
          'query': query
        }
      }
    };
    this.httpClient
      .post(`${this.configService.getSearchEndpoint()}/_search`, search)
      .subscribe(data => {
        const res = data as SearchResponse<IKweet[] | IProfile[]>;
        this._lastSearchResult.next(res.hits.hits);
      });
  }

}
