// angular > core
import {Inject, Injectable} from "@angular/core";
// angular > http
import {Http, RequestMethod} from "@angular/http";
// service
import {AbstractService} from "./abstract-service";
// util
import {AUTH_SERVICE_URL} from "../util/injectable-constants";
import {AuthUtils} from "app/util/auth-utils";
// rxjs
import {Observable} from "rxjs/Observable";

@Injectable()
export class AuthenticationService extends AbstractService {

	public constructor(http: Http, @Inject(AUTH_SERVICE_URL) baseUrl: string) {
		super(http, baseUrl);
	}

	public login(username: string, password: string): Observable<any> {
		return super.httpRequest(RequestMethod.Post, "", {username: username, password: password}, AuthUtils.getApiLoginHeaders());
	}
	
}