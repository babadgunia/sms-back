// model > entity
import {AbstractEntity} from "./abstract-entity";
// model > enum
import {LanguageType} from "../enum/language-type.enum";
import {StatusType} from "../enum/status-type.enum";

export class User extends AbstractEntity {

	id: number;

	username: string;

	name: string;

	status: StatusType;

	language: LanguageType;
}