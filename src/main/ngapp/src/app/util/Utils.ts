// util
import {isNullOrUndefined} from "util";

export class Utils {

	public static isBlank(...strings: string[]): boolean {
		if (isNullOrUndefined(strings) || strings.length == 0) {
			return true;
		}

		return strings.some((string: string) => (isNullOrUndefined(string) || string.trim().length == 0));
	}
}