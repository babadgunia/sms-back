// model > entity
import {AbstractEntity} from './abstract-entity';

export class I18nText extends AbstractEntity {

	id: number;

	language: string;

	value: string;
}