// model > filter
import {AbstractFilter} from './abstract-filter';

export class UserFilter extends AbstractFilter {

	id?: number;

	username?: string;

	email?: string;

	name?: string;

	status?: string;

	language?: string;

	userGroupId?: number;
}