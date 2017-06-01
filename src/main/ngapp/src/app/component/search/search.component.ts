import "rxjs/add/observable/of";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/debounceTime";
import "rxjs/add/operator/distinctUntilChanged";

import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";

import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";

import {Hero} from "../../model/hero";
import {HeroService} from "../../service/hero.service";

@Component({
	selector: 'hero-search',
	templateUrl: './search.component.html',
	styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

	heroes: Observable<Hero[]>;

	private searchTerms = new Subject<string>();

	constructor(private heroService: HeroService, private router: Router) {
	}

	search(term: string): void {
		this.searchTerms.next(term);
	}

	ngOnInit(): void {
		this.heroes = this.searchTerms.debounceTime(300).distinctUntilChanged().switchMap(term => term ? this.heroService.search(term) : Observable.of<Hero[]>([]))
			.catch(error => {
				console.log(error);

				return Observable.of<Hero[]>([]);
			});
	}

	gotoDetail(hero: Hero): void {
		let link = ['/detail', hero.id];
		this.router.navigate(link);
	}
}