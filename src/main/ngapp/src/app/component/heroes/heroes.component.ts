import {Component, OnInit} from "@angular/core";

import {Hero} from "../../model/hero";
import {HeroService} from "../../service/hero.service";
import {Router} from "@angular/router";

@Component({
	selector: 'heroes',
	templateUrl: './heroes.component.html',
	styleUrls: ['./heroes.component.css']
})
export class HeroesComponent implements OnInit {

	heroes: Hero[];

	selectedHero: Hero;

	constructor(private router: Router, private heroService: HeroService) {
	}

	ngOnInit() {
		this.heroService.getHeroes().then(heroes => this.heroes = heroes);
	}

	onSelect(hero: Hero): void {
		this.selectedHero = hero;
	}

	gotoDetail(): void {
		this.router.navigate(['/detail', this.selectedHero.id]);
	}
}