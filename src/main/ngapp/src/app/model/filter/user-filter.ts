// model > filter
import {AbstractFilter} from "./abstract-filter";

export class UserFilter extends AbstractFilter {

	id?: number;

	username?: string;

	name?: string;

	status?: string;

	language?: string;
}