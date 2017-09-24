// angular > core
import {Inject, Injectable} from "@angular/core";
// angular > http
import {Http, RequestMethod} from "@angular/http";
// model > entity
import {UserGroup} from "../model/entity/user-group";
// model > filter
import {UserGroupFilter} from "../model/filter/user-group-filter";
// service
import {AbstractService} from "./abstract-service";
// util
import {USER_GROUP_SERVICE_URL} from "../util/injectable-constants";
// rxjs
import {Observable} from "rxjs/Observable";

@Injectable()
export class UserGroupService extends AbstractService {

	public constructor(http: Http, @Inject(USER_GROUP_SERVICE_URL) baseUrl: string) {
		super(http, baseUrl);
	}

	public add(entity: UserGroup): Observable<UserGroup> {
		return super.httpRequest(RequestMethod.Post, "add", entity);
	}

	public update(entity: UserGroup): Observable<UserGroup> {
		return super.httpRequest(RequestMethod.Post, "update", entity);
	}

	public delete(id: number): Observable<void> {
		return super.httpRequest(RequestMethod.Delete, `delete/${id}`);
	}

	public get(id: number): Observable<UserGroup> {
		return super.httpRequest(RequestMethod.Get, `get/${id}`);
	}

	public getCount(filter: UserGroupFilter): Observable<number> {
		return super.httpRequest(RequestMethod.Post, "getCount", filter);
	}

	public getList(filter: UserGroupFilter): Observable<UserGroup[]> {
		return super.httpRequest(RequestMethod.Post, "getList", filter);
	}
}