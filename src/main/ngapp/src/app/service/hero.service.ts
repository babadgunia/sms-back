import "rxjs/add/operator/toPromise";
import "rxjs/add/operator/map";

import {Observable} from "rxjs/Observable";

import {Inject, Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";

import {Hero} from "../model/hero";
import {AuthenticationService} from "./authentication.service";
import {HERO_SERVICE_URL} from "app/utils/injectable-constants";

@Injectable()
export class HeroService {

	constructor(private http: Http, private authenticationService: AuthenticationService, @Inject(HERO_SERVICE_URL) private apiUrl: string) {}

	add(name: string): Promise<Hero> {
		const url = `${this.apiUrl}/add`;

		return this.http.post(url, JSON.stringify({name: name}), {headers: HeroService.getHeaders()}).toPromise().then(result => result.json() as Hero).catch(HeroService.handleError);
	}

	static getHeaders(): Headers {
		return new Headers({
			'Content-Type': 'application/json',
			'Authorization': AuthenticationService.getToken().toString()
		});
	}

	private static handleError(error: any): Promise<any> {
		console.error('An error occurred', error);

		return Promise.reject(error.message || error);
	}

	update(hero: Hero): Promise<Hero> {
		const url = `${this.apiUrl}/update`;

		return this.http.put(url, JSON.stringify(hero), {headers: HeroService.getHeaders()}).toPromise().then(result => result.json() as Hero).catch(HeroService.handleError);
	}

	delete(id: number): Promise<void> {
		const url = `${this.apiUrl}/delete/${id}`;

		return this.http.delete(url, {headers: HeroService.getHeaders()}).toPromise().then(() => null).catch(HeroService.handleError);
	}

	get (id: number): Promise<Hero> {
		const url = `${this.apiUrl}/get/${id}`;

		return this.http.get(url, {headers: HeroService.getHeaders()}).toPromise().then(response => response.json() as Hero).catch(HeroService.handleError);
	}

	getList(): Promise<Hero[]> {
		const url = `${this.apiUrl}/getList?name=${''}`;

		return this.http.get(url, {headers: HeroService.getHeaders()}).toPromise().then(response => response.json() as Hero[]).catch(HeroService.handleError);
	}

	getListSlowly(): Promise<Hero[]> {
		const DELAY = 5000;

		return new Promise(resolve => {
			setTimeout(() => resolve(this.getList()), DELAY);
		});
	}

	search(term: string): Observable<Hero[]> {
		const url = `${this.apiUrl}/getList?name=${term}`;

		return this.http.get(url, {headers: HeroService.getHeaders()}).map(response => response.json() as Hero[]).catch(HeroService.handleError);
	}
}