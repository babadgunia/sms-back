// angular > http
import {Http, RequestMethod, RequestOptionsArgs, Response} from "@angular/http";
// util
import {AuthUtils} from "../util/auth-utils";
import {Utils} from "../util/utils";
// rxjs
import {Observable} from "rxjs/Observable";

export abstract class AbstractService {

	protected constructor(private http: Http, private baseUrl: string) {}

	// makes an http request
	protected httpRequest(requestMethod: RequestMethod, url: string, body?: any, headers?: RequestOptionsArgs): Observable<any> {
		url = !Utils.isBlank(url) ? `${this.baseUrl}/${url}` : this.baseUrl;
		let requestOptions: RequestOptionsArgs = headers ? headers : AuthUtils.getApiHeaders();

		switch (requestMethod) {
			case RequestMethod.Get: {
				return this.parseResponse(this.http.get(url, requestOptions));
			}
			case RequestMethod.Post: {
				return this.parseResponse(this.http.post(url, body, requestOptions));
			}
			case RequestMethod.Put: {
				return this.parseResponse(this.http.put(url, body, requestOptions));
			}
			case RequestMethod.Delete: {
				return this.handleError(this.http.delete(url, requestOptions));
			}
		}
	}

	// converts raw observable to json observable
	private parseResponse(response: Observable<Response>): Observable<any> {
		return this.handleError(response.map((response: Response) => response.json()));
	}

	// handles service errors by throwing an error observable
	private handleError(observable: Observable<any>): Observable<any> {
		return observable.catch((error: any) => Observable.throw(error));
	}
}