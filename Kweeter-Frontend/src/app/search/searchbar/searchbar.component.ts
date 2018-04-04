import {Component, OnInit} from '@angular/core';
import {SearchService} from '../../services/search/search.service';

@Component({
  selector: 'app-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.css']
})
export class SearchbarComponent implements OnInit {

  public searchQuery: string;
  public currentTimeout:number = null;

  constructor(public searchService: SearchService) {
  }

  ngOnInit() {
  }

  refreshSearchCountdown() {
    if (this.currentTimeout != null) {
      clearTimeout(this.currentTimeout);
    }
    this.currentTimeout = setTimeout(() => {
      this.commitSearch();
    }, 500);

  }

  public commitSearch() {
    console.log(`Searching ${this.searchQuery}`);
    this.searchService.getSearchResultsForQuery(this.searchQuery);
  }
}
