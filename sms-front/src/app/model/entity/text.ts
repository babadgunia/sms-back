// model > entity
import {AbstractEntity} from './abstract-entity';
import {I18nText} from './i18n-text';

export class Text extends AbstractEntity {

	id: number;

	key: string;

	values: I18nText[] = [];
}