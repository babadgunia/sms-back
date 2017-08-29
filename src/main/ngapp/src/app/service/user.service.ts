// angular > core
import {Inject, Injectable} from "@angular/core";
// angular > http
import {Http, RequestMethod} from "@angular/http";
// model > entity
import {User} from "../model/entity/user";
// model > filter
import {UserFilter} from "../model/filter/user-filter";
// service
import {AbstractService} from "./abstract-service";
// util
import {USER_SERVICE_URL} from "../util/injectable-constants";
// rxjs
import {Observable} from "rxjs/Observable";

@Injectable()
export class UserService extends AbstractService {

	constructor(http: Http, @Inject(USER_SERVICE_URL) private apiUrl: string) {
		super(http);
	}

	delete(id: number): Observable<void> {
		return super.httpRequest(RequestMethod.Delete, `${this.apiUrl}/delete/${id}`);
	}

	get(id: number): Observable<User> {
		return super.httpRequest(RequestMethod.Get, `${this.apiUrl}/get/${id}`);
	}

	getCount(filter: UserFilter): Observable<number> {
		return super.httpRequest(RequestMethod.Post, `${this.apiUrl}/getCount`, filter);
	}

	getList(filter: UserFilter): Observable<User[]> {
		return super.httpRequest(RequestMethod.Post, `${this.apiUrl}/getList`, filter);
	}
}