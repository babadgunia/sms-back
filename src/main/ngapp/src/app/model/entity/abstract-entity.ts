export abstract class AbstractEntity {

	version: number;

	fromJSON(json: string) {
		var jsonObj = JSON.parse(json);
		for (var propName in jsonObj) {
			this[propName] = jsonObj[propName]
		}
	}
}