// util
import {isNullOrUndefined} from 'util';

export class Utils {

	private constructor() {}

	// handles application errors
	public static handleError(error: any): void {
		console.error(error);
	}

	// checks if any of the passed strings is blank
	public static isBlank(...strings: string[]): boolean {
		if (isNullOrUndefined(strings) || strings.length == 0) {
			return true;
		}

		return strings.some((string: string) => (isNullOrUndefined(string) || string.trim().length == 0));
	}

	// get map example
	// public static getMap(): Observable<Map<string, Map<string, string>>> {
	// 	class JsonResponseResult {
	//
	// 		parameters: Map<string, Map<string, string>>;
	//
	// 		constructor(json: any) {
	// 			this.parameters = new Map<string, Map<string, string>>();
	// 			Object.keys(json).forEach(key => {
	// 				let params: Map<string, string> = new Map<string, string>();
	// 				Object.keys(json[key]).forEach(paramKey => {
	// 					params.set(paramKey, json[key][paramKey]);
	// 				});
	//
	// 				this.parameters.set(key, params);
	// 			});
	// 		}
	// 	}
	//
	// 	const url = `${this.apiUrl}/get`;
	//
	// 	return this.http.get(url, {headers: HeroService.getHeaders()}).map(response => new JsonResponseResult(response.json()).parameters);
	// }
}