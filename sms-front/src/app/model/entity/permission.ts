// model > entity
import {AbstractEntity} from './abstract-entity';

export class Permission extends AbstractEntity {

	id: number;

	permissionGroup: string;

	permissionTypes: string[];
}