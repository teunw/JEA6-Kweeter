import {Component, OnInit} from '@angular/core';
import {SearchService} from "../../services/search/search.service";

@Component({
  selector: 'app-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.css']
})
export class SearchbarComponent implements OnInit {

  public searchQuery: string;

  constructor(public searchService: SearchService) {
  }

  ngOnInit() {
  }

  public commitSearch() {
    this.searchService.getSearchResultsForQuery(this.searchQuery);
  }

}
