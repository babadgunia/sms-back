// angular > http
import {HttpClient, HttpHeaders} from '@angular/common/http';
// model > enum
import {HttpRequestType} from '../model/enum/http-request-type.enum';
// util
import {AuthenticationUtils} from '../util/authentication-utils';
import {Utils} from '../util/utils';
// rxjs
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

export abstract class AbstractService {

	protected constructor(private http: HttpClient, private baseUrl: string) {}

	// makes an http request
	protected httpRequest(requestType: HttpRequestType, url: string, body?: any, headers?: HttpHeaders): Observable<any> {
		url = !Utils.isBlank(url) ? `${this.baseUrl}/${url}` : this.baseUrl;
		let requestOptions: HttpHeaders = headers ? headers : AuthenticationUtils.getApiHeaders();

		switch (requestType) {
			case HttpRequestType.GET: {
				return this.handleError(this.http.get(url, {headers: requestOptions}));
			}
			case HttpRequestType.POST: {
				return this.handleError(this.http.post(url, body, {headers: requestOptions}));
			}
			case HttpRequestType.PUT: {
				return this.handleError(this.http.put(url, body, {headers: requestOptions}));
			}
			case HttpRequestType.DELETE: {
				return this.handleError(this.http.delete(url, {headers: requestOptions}));
			}
		}
	}

	// handles service errors by throwing an error observable
	private handleError(observable: Observable<Object>): Observable<any> {
		return observable.catch((error: any) => Observable.throw(error));
	}
}