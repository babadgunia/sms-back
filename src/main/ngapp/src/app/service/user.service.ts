import "rxjs/add/operator/toPromise";
import "rxjs/add/operator/map";

import {Observable} from "rxjs/Observable";

import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";

import {User} from "../model/user";
import {AuthenticationService} from "./authentication.service";

const DELAY = 5000;

const USERS_URL = 'api/user';

@Injectable()
export class UserService {

	constructor(private http: Http, private authenticationService: AuthenticationService) {
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

	getList(): Promise<User[]> {
		const url = `${USERS_URL}/getList?name=${''}`;
		UserService.getHeaders().append('Authorization', AuthenticationService.getToken().toString());

		return this.http.get(url, {headers: UserService.getHeaders()}).toPromise().then(response => response.json() as User[]).catch(UserService.handleError);
	}

	getListSlowly(): Promise<User[]> {
		return new Promise(resolve => {
			setTimeout(() => resolve(this.getList()), DELAY);
		});
	}

}