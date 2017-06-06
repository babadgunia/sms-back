import "rxjs/add/operator/toPromise";
import "rxjs/add/operator/map";

import {Observable} from "rxjs/Observable";

import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";

import {Hero} from "../model/hero";

const DELAY = 5000;

const HEROES_URL = 'auth/api/hero';

@Injectable()
export class HeroService {

	private headers = new Headers({'Content-Type': 'application/json'});

	constructor(private http: Http) {
	}

	add(name: string): Promise<Hero> {
		const url = `${HEROES_URL}/add`;

		return this.http.post(url, JSON.stringify({name: name}), {headers: this.headers}).toPromise().then(result => result.json() as Hero).catch(HeroService.handleError);
	}

	private static handleError(error: any): Promise<any> {
		console.error('An error occurred', error);

		return Promise.reject(error.message || error);
	}

	update(hero: Hero): Promise<Hero> {
		const url = `${HEROES_URL}/update`;

		return this.http.put(url, JSON.stringify(hero), {headers: this.headers}).toPromise().then(result => result.json() as Hero).catch(HeroService.handleError);
	}

	delete(id: number): Promise<void> {
		const url = `${HEROES_URL}/delete/${id}`;

		return this.http.delete(url, {headers: this.headers}).toPromise().then(() => null).catch(HeroService.handleError);
	}

	get(id: number): Promise<Hero> {
		const url = `${HEROES_URL}/get/${id}`;

		return this.http.get(url).toPromise().then(response => response.json() as Hero).catch(HeroService.handleError);
	}

	getList(): Promise<Hero[]> {
		const url = `${HEROES_URL}/getList?name=${''}`;

		return this.http.get(url).toPromise().then(response => response.json() as Hero[]).catch(HeroService.handleError);
	}

	getListSlowly(): Promise<Hero[]> {
		return new Promise(resolve => {
			setTimeout(() => resolve(this.getList()), DELAY);
		});
	}

	search(term: string): Observable<Hero[]> {
		const url = `${HEROES_URL}/getList?name=${term}`;

		return this.http.get(url).map(response => response.json() as Hero[]).catch(HeroService.handleError);
	}
}