export const API_BASE_URL: string = 'api/';
export const AUTH_SERVICE_URL: string = 'http://localhost:8080/auth';
export const HERO_SERVICE_URL: string = API_BASE_URL + 'hero';
export const TRANSLATION_SERVICE_URL: string = API_BASE_URL + 'translation';
export const USER_SERVICE_URL: string = API_BASE_URL + 'user';

export const INJECTABLE_CONSTANTS: Array<any> = [
	{provide: API_BASE_URL, useValue: API_BASE_URL},
	{provide: AUTH_SERVICE_URL, useValue: AUTH_SERVICE_URL},
	{provide: HERO_SERVICE_URL, useValue: HERO_SERVICE_URL},
	{provide: TRANSLATION_SERVICE_URL, useValue: TRANSLATION_SERVICE_URL},
	{provide: USER_SERVICE_URL, useValue: USER_SERVICE_URL}
];