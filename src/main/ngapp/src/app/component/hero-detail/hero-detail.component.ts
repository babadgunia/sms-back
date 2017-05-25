import "rxjs/add/operator/switchMap";

import {Component, OnInit} from "@angular/core";
import {Location} from "@angular/common";
import {ActivatedRoute, Params} from "@angular/router";

import {Hero} from "../../model/hero";
import {HeroService} from "../../service/hero.service";

@Component({
	selector: 'hero-detail',
	templateUrl: './hero-detail.component.html',
	styleUrls: ['./hero-detail.component.css']
})
export class HeroDetailComponent implements OnInit {

	hero: Hero;

	constructor(private heroService: HeroService, private route: ActivatedRoute, private location: Location) {
	}

	ngOnInit() {
		this.route.params.switchMap((params: Params) => this.heroService.getHero(+params['id'])).subscribe(hero => this.hero = hero);
	}

	goBack(): void {
		this.location.back();
	}
}