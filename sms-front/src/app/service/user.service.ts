// angular > core
import {Inject, Injectable} from '@angular/core';
// angular > http
import {HttpClient} from '@angular/common/http';
// model > entity
import {User} from '../model/entity/user';
// model > filter
import {UserFilter} from '../model/filter/user-filter';
// model > enum
import {HttpRequestType} from '../model/enum/http-request-type.enum';
// service
import {AbstractService} from './abstract-service';
// util
import {USER_SERVICE_URL} from '../util/injectable-constants';
// rxjs
import {Observable} from "rxjs/internal/Observable";

@Injectable()
export class UserService extends AbstractService {

	public constructor(http: HttpClient, @Inject(USER_SERVICE_URL) baseUrl: string) {
		super(http, baseUrl);
	}

	public add(entity: User): Observable<User> {
		return super.httpRequest(HttpRequestType.POST, "add", entity);
	}

	public update(entity: User): Observable<User> {
		return super.httpRequest(HttpRequestType.POST, "update", entity);
	}

	public delete(id: number): Observable<void> {
		return super.httpRequest(HttpRequestType.DELETE, `delete/${id}`);
	}

	public get(id: number): Observable<User> {
		return super.httpRequest(HttpRequestType.GET, `get/${id}`);
	}

	public getCount(filter: UserFilter): Observable<number> {
		return super.httpRequest(HttpRequestType.POST, "getCount", filter);
	}

	public getList(filter: UserFilter): Observable<User[]> {
		return super.httpRequest(HttpRequestType.POST, "getList", filter);
	}
}