// angular > core
import {Inject, Injectable} from '@angular/core';
// angular > http
import {HttpClient} from '@angular/common/http';
// model > entity
import {Text} from '../model/entity/text';
// model > filter
import {TextFilter} from '../model/filter/text-filter';
// model > enum
import {HttpRequestType} from '../model/enum/http-request-type.enum';
// service
import {AbstractService} from './abstract-service';
// util
import {TEXT_SERVICE_URL} from '../util/injectable-constants';
// rxjs
import {Observable} from "rxjs/internal/Observable";

@Injectable()
export class TextService extends AbstractService {

	public constructor(http: HttpClient, @Inject(TEXT_SERVICE_URL) baseUrl: string) {
		super(http, baseUrl);
	}

	public add(entity: Text): Observable<Text> {
		return super.httpRequest(HttpRequestType.POST, "add", entity);
	}

	public update(entity: Text): Observable<Text> {
		return super.httpRequest(HttpRequestType.POST, "update", entity);
	}

	public delete(id: number): Observable<void> {
		return super.httpRequest(HttpRequestType.DELETE, `delete/${id}`);
	}

	public get(id: number): Observable<Text> {
		return super.httpRequest(HttpRequestType.GET, `get/${id}`);
	}

	public getCount(filter: TextFilter): Observable<number> {
		return super.httpRequest(HttpRequestType.POST, "getCount", filter);
	}

	public getList(filter: TextFilter): Observable<Text[]> {
		return super.httpRequest(HttpRequestType.POST, "getList", filter);
	}

	// misc

	public getListForSelection(): Observable<Text[]> {
		return super.httpRequest(HttpRequestType.GET, `getListForSelection`);
	}
}