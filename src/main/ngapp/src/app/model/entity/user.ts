import {LanguageType} from "../enum/language-type";
import {AbstractEntity} from "./abstract-entity";

export class User extends AbstractEntity {

	id: number;

	username: string;

	name: string;

	language: LanguageType;
}