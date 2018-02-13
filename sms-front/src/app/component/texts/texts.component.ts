// angular > core
import {Component} from '@angular/core';
// model > entity
import {I18nText} from '../../model/entity/i18n-text';
import {Text} from '../../model/entity/text';
// model > filter
import {TextFilter} from '../../model/filter/text-filter';
// component
import {AbstractComponent} from '../abstract-component';
// service
import {TextService} from '../../service/text.service';
// util
import {Utils} from '../../util/utils';
// primeng > model
import {LazyLoadEvent} from 'primeng/components/common/lazyloadevent';
// primeng > service
import {ConfirmationService} from 'primeng/components/common/confirmationservice';
import {MessageService} from 'primeng/components/common/messageservice';

@Component({
	selector: 'texts',
	templateUrl: './texts.component.html',
	styleUrls: ['./texts.component.css']
})
export class TextsComponent extends AbstractComponent {

	private entities: Text[];

	private entity: Text = <Text>{};

	private filter: TextFilter;

	// i18n values map

	private i18nValuesMap: Map<string, string> = new Map<string, string>();

	public constructor(private service: TextService, confirmationService: ConfirmationService, messageService: MessageService) {
		super(confirmationService, messageService);
	}

	private resetFilter(keyField: HTMLInputElement, valueField: HTMLInputElement): void {
		keyField.value = '';
		valueField.value = '';

		this.filter = {};
	}

	private initFilter(key: string, value: string): void {
		this.filter = {};
		super.initPagingFilter(this.filter);
		this.initFilterFields(key, value);
	}

	private initFilterFields(key: string, value: string): void {
		this.filter.key = key;
		this.filter.value = value;
	}

	private initTableFilter(event: LazyLoadEvent, key: string, value: string): void {
		this.filter = {};
		super.initLazyPagingFilter(this.filter, event);
		this.initFilterFields(key, value);
	}

	private initAdd(): void {
		this.entity = new Text();

		this.initI18NValuesMap();
	}

	private initI18NValuesMap(): void {
		this.i18nValuesMap = new Map<string, string>();

		if (this.isAdd) {
			this.languageTypes.forEach((language: string) => {
				this.i18nValuesMap.set(language, null);
			});
		} else {
			this.entity.values.forEach((value: I18nText) => {
				this.i18nValuesMap.set(value.language, value.value);
			});
		}
	}

	private save(): void {
		if (!this.isValidEntity()) {
			return;
		}

		this.entity.key = this.entity.key.toUpperCase();
		this.initI18NValues();

		if (this.isAdd) {
			this.add();
		} else {
			this.update();
		}
	}

	private isValidEntity(): boolean {
		if (Utils.isBlank(this.entity.key)) {
			super.showErrorMessage('CANNOT_BE_EMPTY', this.getMessage('KEY'));

			return false;
		}

		let isBlankValue: boolean = true;
		this.i18nValuesMap.forEach((value: string, key: string) => {
			if (Utils.isBlank(value)) {
				isBlankValue = false;
			}
		});

		if (!isBlankValue) {
			super.showErrorMessage('CANNOT_BE_EMPTY', this.getMessage('VALUE'));

			return false;
		}

		return true;
	}

	private initI18NValues(): void {
		if (this.isAdd) {
			this.i18nValuesMap.forEach((value: string, key: string) => {
				let i18nText: I18nText = new I18nText();

				i18nText.language = key;
				i18nText.value = value;

				this.entity.values.push(i18nText);
			});
		} else {
			this.entity.values.forEach((value: I18nText) => {
				value.value = this.i18nValuesMap.get(value.language);
			});
		}
	}

	private add(): void {
		this.service.add(this.entity).subscribe((entity: Text) => {
			this.entities.push(entity);
			this.tableTotalRecords++;

			this.showDialog = false;
		}, (error: any) => super.handleError(error));
	}

	private update(): void {
		this.service.update(this.entity).subscribe((entity: Text) => {
			let index: number = this.entities.findIndex((element: Text) => element.id === entity.id);
			this.entities.splice(index, 1, entity);

			this.showDialog = false;
		}, (error: any) => super.handleError(error));
	}

	private confirmDeleteAction(entity: Text): void {
		super.confirmAction(() => {
			this.service.delete(entity.id).subscribe(() => {
				let index: number = this.entities.findIndex((element: Text) => element.id === entity.id);
				this.entities.splice(index, 1);
				this.tableTotalRecords--;
			}, (error: any) => super.handleError(error));
		});
	}

	private get(entity: Text): void {
		this.service.get(entity.id).subscribe((entity: Text) => {
			this.entity = entity;

			this.initI18NValuesMap();
		}, (error: any) => super.handleError(error));
	}

	private getList(): void {
		this.loading = true;

		this.service.getCount(this.filter).subscribe((count: number) => {
			this.tableTotalRecords = count;
		}, (error: any) => super.handleError(error));

		this.service.getList(this.filter).subscribe((entities: Text[]) => {
			this.entities = entities;
			this.loading = false;
		}, (error: any) => super.handleError(error));
	}
}