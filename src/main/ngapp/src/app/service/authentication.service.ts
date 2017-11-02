// angular > core
import {Inject, Injectable} from "@angular/core";
// angular > http
import {HttpClient} from "@angular/common/http";
// model > enum
import {HttpRequestType} from "../model/enum/http-request-type.enum";
// service
import {AbstractService} from "./abstract-service";
// util
import {AUTH_SERVICE_URL} from "../util/injectable-constants";
import {AuthenticationUtils} from "app/util/authentication-utils";
// rxjs
import {Observable} from "rxjs/Observable";

@Injectable()
export class AuthenticationService extends AbstractService {

	public constructor(http: HttpClient, @Inject(AUTH_SERVICE_URL) baseUrl: string) {
		super(http, baseUrl);
	}

	public login(username: string, password: string): Observable<any> {
		return super.httpRequest(HttpRequestType.POST, "", {username: username, password: password}, AuthenticationUtils.getApiLoginHeaders());
	}
}