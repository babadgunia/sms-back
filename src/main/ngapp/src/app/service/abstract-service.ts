// angular > http
import {Http, RequestMethod, Response} from "@angular/http";
// service
import {AuthenticationService} from "./authentication.service";
// rxjs
import {Observable} from "rxjs/Observable";

export abstract class AbstractService {

	protected constructor(private http: Http) {}

	protected httpRequest(requestMethod: RequestMethod, url: string, body?: any): Observable<any> {
		switch (requestMethod) {
			case RequestMethod.Get: {
				return this.parseResponse(this.http.get(url, AuthenticationService.getApiHeaders()));
			}
			case RequestMethod.Post: {
				return this.parseResponse(this.http.post(url, body, AuthenticationService.getApiHeaders()));
			}
			case RequestMethod.Put: {
				return this.parseResponse(this.http.put(url, body, AuthenticationService.getApiHeaders()));
			}
			case RequestMethod.Delete: {
				return this.handleError(this.http.delete(url, AuthenticationService.getApiHeaders()));
			}
		}
	}

	private parseResponse(response: Observable<Response>): Observable<any> {
		return this.handleError(response.map((response: Response) => response.json()));
	}

	private handleError(observable: Observable<any>): Observable<any> {
		return observable.catch((error: any) => Observable.throw(error));
	}
}