// angular > http
import {HttpClient, HttpHeaders} from '@angular/common/http';
// model > enum
import {HttpRequestType} from '../model/enum/http-request-type.enum';
// util
import {AuthenticationUtils} from '../util/authentication-utils';
import {Utils} from '../util/utils';
// rxjs
import {Observable} from "rxjs/internal/Observable";

export abstract class AbstractService {

	protected constructor(private http: HttpClient, private baseUrl: string) {}

	// makes an http request
	protected httpRequest(requestType: HttpRequestType, url: string, body?: any, headers?: HttpHeaders): Observable<any> {
		url = !Utils.isBlank(url) ? `${this.baseUrl}/${url}` : this.baseUrl;
		let requestOptions: HttpHeaders = headers ? headers : AuthenticationUtils.getApiHeaders();

		switch (requestType) {
			case HttpRequestType.GET: {
				return this.http.get(url, {headers: requestOptions});
			}
			case HttpRequestType.POST: {
				return this.http.post(url, body, {headers: requestOptions});
			}
			case HttpRequestType.PUT: {
				return this.http.put(url, body, {headers: requestOptions});
			}
			case HttpRequestType.DELETE: {
				return this.http.delete(url, {headers: requestOptions});
			}
		}
	}
}