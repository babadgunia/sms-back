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

	constructor(private router: Router, private heroService: HeroService) {}

	ngOnInit() {
		this.heroService.getList().then(heroes => this.heroes = heroes, error => {
			this.router.navigate(['login']);
			console.error('An error occurred in dashboard component, navigating to login: ', error);
		});
	}

	onSelect(hero: Hero): void {
		this.selectedHero = hero;
	}

	gotoDetail(): void {
		this.router.navigate(['/detail', this.selectedHero.id]);
	}

	add(name: string): void {
		name = name.trim();
		if (!name) {
			return;
		}

		this.heroService.add(name).then(hero => {
			this.heroes.push(hero);
			this.selectedHero = null;
		});
	}

	delete(hero: Hero): void {
		this.heroService.delete(hero.id).then(() => {
			this.heroes = this.heroes.filter(h => h !== hero);
			if (this.selectedHero === hero) {
				this.selectedHero = null;
			}
		});
	}
}