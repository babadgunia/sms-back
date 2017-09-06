// util
import {isNullOrUndefined} from "util";

export class Utils {

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
}