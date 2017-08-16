import "rxjs/add/operator/toPromise";
import "rxjs/add/operator/map";

import {Inject, Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";

import {AuthenticationService} from "./authentication.service";
import {TRANSLATION_SERVICE_URL} from "app/utils/injectable-constants";
import {Observable} from "rxjs/Observable";

@Injectable()
export class TranslationService {

	constructor(private http: Http, private authenticationService: AuthenticationService, @Inject(TRANSLATION_SERVICE_URL) private apiUrl: string) {}

	get (): Observable<Map<string, Map<string, string>>> {
		class JsonResponseResult {

			parameters: Map<string, Map<string, string>>;

			constructor(json: any) {
				this.parameters = new Map<string, Map<string, string>>();
				Object.keys(json).forEach(key => {
					let params: Map<string, string> = new Map<string, string>();
					Object.keys(json[key]).forEach(paramKey => {
						params.set(paramKey, json[key][paramKey]);
					});

					this.parameters.set(key, params);
				});
			}
		}

		const url = `${this.apiUrl}/get`;

		return this.http.get(url, {headers: TranslationService.getHeaders()}).map(response => new JsonResponseResult(response.json()).parameters);
	}

	static getHeaders(): Headers {
		return new Headers({
			'Content-Type': 'application/json',
			'Authorization': AuthenticationService.getToken().toString()
		});
	}
}