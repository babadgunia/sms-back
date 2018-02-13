// model > entity
import {AbstractEntity} from './abstract-entity';
import {UserGroup} from './user-group';

export class User extends AbstractEntity {

	id: number;

	username: string;

	email: string;

	name: string;

	status: string;

	language: string;

	userGroup: UserGroup;
}