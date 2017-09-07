// angular > http
import {Headers, RequestOptionsArgs} from "@angular/http";
// util
import {Utils} from "./utils";

export class AuthUtils {

	private constructor() {}

	// gets auth token for rest api calls
	public static getApiToken(): string {
		let currentUser: any = JSON.parse(localStorage.getItem('currentUser'));

		return currentUser && currentUser.token;
	}

	// gets http headers for rest api login call
	public static getApiLoginHeaders(): RequestOptionsArgs {
		let headers: Headers = new Headers({
			'Content-Type': 'application/json'
		});

		return {headers: headers};
	}

	// gets http headers for rest api calls
	public static getApiHeaders(): RequestOptionsArgs {
		let headers: Headers = new Headers({
			'Content-Type': 'application/json',
			'Authorization': this.getApiToken()
		});

		return {headers: headers};
	}

	// checks user permission
	public static hasPermission(permission: string): boolean {
		return localStorage.getItem('permissions').includes(permission);
	}

	// checks if there is a logged in user
	public static isLoggedIn(): boolean {
		return !Utils.isBlank(this.getApiToken());
	}

	// logs in user
	public static login(username: string, token: string): void {
		localStorage.setItem('currentUser', JSON.stringify({username: username, token: token}));

		let permissionsStr = AuthUtils.decodeToken(token).PERMISSIONS.map(function (element) {
			return element.authority;
		}).join();
		localStorage.setItem('permissions', permissionsStr);
	}

	// logs out current user
	public static logout(): void {
		localStorage.removeItem('currentUser');
	}

	public static decodeToken(token: string): any {
		let parts = token.split('.');
		if (parts.length !== 3) {
			throw new Error('JWT must have 3 parts');
		}
		let decoded = this.urlBase64Decode(parts[1]);
		if (!decoded) {
			throw new Error('Cannot decode the token');
		}
		return JSON.parse(decoded);
	}

	private static urlBase64Decode(str: string) {
		let output = str.replace(/-/g, '+').replace(/_/g, '/');
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
}