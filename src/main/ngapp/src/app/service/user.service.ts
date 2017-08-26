import "rxjs/add/operator/toPromise";
import "rxjs/add/operator/map";

import {Inject, Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";

import {User} from "../model/user";
import {AuthenticationService} from "./authentication.service";
import {USER_SERVICE_URL} from "../utils/injectable-constants";
import {UserFilter} from "../model/filter/user-filter";

@Injectable()
export class UserService {

	constructor(private http: Http, private authenticationService: AuthenticationService, @Inject(USER_SERVICE_URL) private apiUrl: string) {}

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

	delete(id: number): Promise<void> {
		const url = `${this.apiUrl}/delete/${id}`;

		return this.http.delete(url, {headers: UserService.getHeaders()}).toPromise().then(() => null).catch(UserService.handleError);
	}

	getCount(filter: UserFilter): Promise<number> {
		const url = `${this.apiUrl}/getCount`;

		return this.http.post(url, filter, {headers: UserService.getHeaders()}).toPromise().then(response => response.json() as number).catch(UserService.handleError);
	}

	getList(filter: UserFilter): Promise<User[]> {
		const url = `${this.apiUrl}/getList`;

		return this.http.post(url, filter, {headers: UserService.getHeaders()}).toPromise().then(response => response.json() as User[]).catch(UserService.handleError);
	}
}