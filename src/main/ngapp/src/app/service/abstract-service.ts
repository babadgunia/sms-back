// angular
import {Headers} from "@angular/http";
// services
import {AuthenticationService} from "./authentication.service";
// rxjs
import {Observable} from "rxjs/Observable";

export abstract class AbstractService {

	protected getApiHeaders(): Headers {
		return new Headers({
			'Content-Type': 'application/json',
			'Authorization': AuthenticationService.getToken()
		});
	}

	protected handleError(error: any): Observable<any> {
		return Observable.throw(error);
	}
}