// angular
import {Http} from "@angular/http";
import {Inject, Injectable} from "@angular/core";
// model
import {User} from "../model/user";
import {UserFilter} from "../model/filter/user-filter";
// services
import {AbstractService} from "./abstract-service";
// utils
import {USER_SERVICE_URL} from "../utils/injectable-constants";
// rxjs
import {Observable} from "rxjs/Observable";

@Injectable()
export class UserService extends AbstractService {

	constructor(private http: Http, @Inject(USER_SERVICE_URL) private apiUrl: string) {
		super();
	}

	delete(id: number): Observable<void> {
		const url = `${this.apiUrl}/delete/${id}`;

		return this.http.delete(url, {headers: super.getApiHeaders()}).catch(error => super.handleError(error));
	}

	getCount(filter: UserFilter): Observable<number> {
		const url = `${this.apiUrl}/getCount`;

		return this.http.post(url, filter, {headers: super.getApiHeaders()}).map(response => response.json() as number).catch(error => super.handleError(error));
	}

	getList(filter: UserFilter): Observable<User[]> {
		const url = `${this.apiUrl}/getList`;

		return this.http.post(url, filter, {headers: super.getApiHeaders()}).map(response => response.json() as User[]).catch(error => super.handleError(error));
	}
}