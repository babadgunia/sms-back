// angular > core
import {Inject, Injectable} from "@angular/core";
// angular > http
import {Http, RequestMethod} from "@angular/http";
// model > entity
import {Text} from "../model/entity/text";
// model > filter
import {TextFilter} from "../model/filter/text-filter";
// service
import {AbstractService} from "./abstract-service";
// util
import {TEXT_SERVICE_URL} from "../util/injectable-constants";
// rxjs
import {Observable} from "rxjs/Observable";

@Injectable()
export class TextService extends AbstractService {

	public constructor(http: Http, @Inject(TEXT_SERVICE_URL) baseUrl: string) {
		super(http, baseUrl);
	}

	public add(entity: Text): Observable<Text> {
		return super.httpRequest(RequestMethod.Post, "add", entity);
	}

	public update(entity: Text): Observable<Text> {
		return super.httpRequest(RequestMethod.Post, "update", entity);
	}

	public delete(id: number): Observable<void> {
		return super.httpRequest(RequestMethod.Delete, `delete/${id}`);
	}

	public get(id: number): Observable<Text> {
		return super.httpRequest(RequestMethod.Get, `get/${id}`);
	}

	public getCount(filter: TextFilter): Observable<number> {
		return super.httpRequest(RequestMethod.Post, "getCount", filter);
	}

	public getList(filter: TextFilter): Observable<Text[]> {
		return super.httpRequest(RequestMethod.Post, "getList", filter);
	}

	// misc

	public getListForSelection(): Observable<Text[]> {
		return super.httpRequest(RequestMethod.Get, `getListForSelection`);
	}
}