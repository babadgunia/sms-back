import {Inject, Injectable} from "@angular/core";
import {Headers, Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch";
import "rxjs/add/observable/throw";
import {AUTH_SERVICE_URL} from "../utils/injectable-constants";

@Injectable()
export class AuthenticationService {

	private headers = new Headers({'Content-Type': 'application/json'});

	constructor(private http: Http, @Inject(AUTH_SERVICE_URL) private apiUrl: string) {
	}

	login(username: string, password: string): Observable<boolean> {
		return this.http.post(this.apiUrl, JSON.stringify({username: username, password: password}), {headers: this.headers})
			.map((response: Response) => {
				let token = response.json() && response.json().token;
				if (token) {
					localStorage.setItem('currentUser', JSON.stringify({username: username, token: token}));

					return true;
				} else {
					return false;
				}
			}).catch((error: any) => Observable.throw(error.json().error || 'Server error'));
	}

	static getToken(): String {
		let currentUser = JSON.parse(localStorage.getItem('currentUser'));
		let token = currentUser && currentUser.token;

		return token ? token : "";
	}

	static logout(): void {
		localStorage.removeItem('currentUser');
	}

	static isLoggedIn(): boolean {
		let token: String = AuthenticationService.getToken();

		return token && token.length > 0;
	}
}