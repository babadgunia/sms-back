import {Injectable} from "@angular/core";
import {Hero} from "../model/hero";
import {HEROES} from "../utils/mock-heroes";

const DELAY = 5000;

@Injectable()
export class HeroService {

	getHeroes(): Promise<Hero[]> {
		return Promise.resolve(HEROES);
	}

	getHeroesSlowly(): Promise<Hero[]> {
		return new Promise(resolve => {
			setTimeout(() => resolve(this.getHeroes()), DELAY);
		});
	}

	getHero(id: number): Promise<Hero> {
		return this.getHeroes().then(heroes => heroes.find(hero => hero.id === id));
	}
}