// angular > core
import {Inject, Injectable} from "@angular/core";
// angular > http
import {Http, RequestMethod} from "@angular/http";
// model > entity
import {UserGroup} from "../model/entity/userGroup";
// model > filter
import {UserGroupFilter} from "../model/filter/userGroup-filter";
// service
import {AbstractService} from "./abstract-service";
// util
import {USER_GROUP_SERVICE_URL} from "../util/injectable-constants";
// rxjs
import {Observable} from "rxjs/Observable";

@Injectable()
export class UserGroupService extends AbstractService {

	constructor(http: Http, @Inject(USER_GROUP_SERVICE_URL) baseUrl: string) {
		super(http, baseUrl);
	}

	add(userGroup: UserGroup): Observable<UserGroup> {
		return super.httpRequest(RequestMethod.Post, "add", userGroup);
	}

	update(userGroup: UserGroup): Observable<UserGroup> {
		return super.httpRequest(RequestMethod.Post, "update", userGroup);
	}

	delete(id: number): Observable<void> {
		return super.httpRequest(RequestMethod.Delete, `delete/${id}`);
	}

	get(id: number): Observable<UserGroup> {
		return super.httpRequest(RequestMethod.Get, `get/${id}`);
	}

	getCount(filter: UserGroupFilter): Observable<number> {
		return super.httpRequest(RequestMethod.Post, "getCount", filter);
	}

	getList(filter: UserGroupFilter): Observable<UserGroup[]> {
		return super.httpRequest(RequestMethod.Post, "getList", filter);
	}
}