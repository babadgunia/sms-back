import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";

import {Hero} from "../../model/entity/hero";
import {HeroService} from "../../service/hero.service";

@Component({
	selector: 'dashboard',
	templateUrl: './dashboard.component.html',
	styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

	heroes: Hero[] = [];

	constructor(private router: Router, private heroService: HeroService) {}

	ngOnInit(): void {
		this.heroService.getList().then(heroes => this.heroes = heroes.slice(0, 4), error => {
			this.router.navigate(['login']);
			console.error('An error occurred in dashboard component, navigating to login: ', error);
		});
	}
}