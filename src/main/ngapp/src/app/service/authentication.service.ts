import {Inject, Injectable} from "@angular/core";
import {Headers, Http, RequestOptionsArgs, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch";
import "rxjs/add/observable/throw";
import {AUTH_SERVICE_URL} from "../util/injectable-constants";

@Injectable()
export class AuthenticationService {

	private headers = new Headers({'Content-Type': 'application/json'});

	constructor(private http: Http, @Inject(AUTH_SERVICE_URL) private apiUrl: string) {}

	static hasPermission(permission: string) {
		return localStorage.getItem('permissions').toLowerCase().indexOf(permission.toLowerCase()) > -1;
	}

	static getToken(): string {
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

	static getApiHeaders(): RequestOptionsArgs {
		let headers: Headers = new Headers({
			'Content-Type': 'application/json',
			'Authorization': AuthenticationService.getToken()
		});

		return {headers: headers};
	}

	login(username: string, password: string): Observable<boolean> {
		return this.http.post(this.apiUrl, JSON.stringify({username: username, password: password}), {headers: this.headers})
			.map((response: Response) => {
				let token = response.json() && response.json().token;
				if (token) {
					localStorage.setItem('currentUser', JSON.stringify({username: username, token: token}));

					var permissionsStr = this.decodeToken(token).PERMISSIONS.map(function (element) {
						return element.authority;
					}).join();
					localStorage.setItem('permissions', permissionsStr);

					return true;
				} else {
					return false;
				}
			}).catch((error: any) => Observable.throw(error.json().error || 'Server error'));
	}

	private urlBase64Decode(str: string) {
		var output = str.replace(/-/g, '+').replace(/_/g, '/');
		switch (output.length % 4) {
			case 0: { break; }
			case 2: {
				output += '==';
				break;
			}
			case 3: {
				output += '=';
				break;
			}
			default: {
				throw 'Illegal base64url string!';
			}
		}
		return decodeURIComponent(window.atob(output));
	}

	private decodeToken(token: string) {
		var parts = token.split('.');
		if (parts.length !== 3) {
			throw new Error('JWT must have 3 parts');
		}
		var decoded = this.urlBase64Decode(parts[1]);
		if (!decoded) {
			throw new Error('Cannot decode the token');
		}
		return JSON.parse(decoded);
	}
}