export const API_BASE_URL: string = 'api/';
export const AUTH_SERVICE_URL: string = 'http://localhost:8080/auth';
export const TEXT_SERVICE_URL: string = API_BASE_URL + 'text';
export const USER_GROUP_SERVICE_URL: string = API_BASE_URL + 'userGroup';
export const USER_SERVICE_URL: string = API_BASE_URL + 'user';

export const INJECTABLE_CONSTANTS: any[] = [
	{provide: AUTH_SERVICE_URL, useValue: AUTH_SERVICE_URL},
	{provide: TEXT_SERVICE_URL, useValue: TEXT_SERVICE_URL},
	{provide: USER_GROUP_SERVICE_URL, useValue: USER_GROUP_SERVICE_URL},
	{provide: USER_SERVICE_URL, useValue: USER_SERVICE_URL}
];