// model > entity
import {AbstractEntity} from './abstract-entity';
import {Permission} from './permission';

export class UserGroup extends AbstractEntity {

	id: number;

	name: string;

	permissions: Permission[] = [];
}