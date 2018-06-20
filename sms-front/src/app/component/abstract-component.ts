// angular > core
import {OnInit} from '@angular/core';
// model > filter
import {AbstractFilter} from '../model/filter/abstract-filter';
// model > enum
import {LanguageType} from '../model/enum/language-type.enum';
import {StatusType} from '../model/enum/status-type.enum';
// util
import {MESSAGES} from '../util/messages';
import {AuthenticationUtils} from '../util/authentication-utils';
// primeng > model
import {LazyLoadEvent} from 'primeng/components/common/lazyloadevent';
import {SelectItem} from 'primeng/components/common/selectitem';
// primeng > service
import {ConfirmationService} from 'primeng/components/common/confirmationservice';
import {MessageService} from 'primeng/components/common/messageservice';
// rxjs
import {isNumeric} from "rxjs/internal-compatibility";

export abstract class AbstractComponent implements OnInit {

	// search filter constants

	protected readonly searchFilterClearButtonIcon: string = "fa-minus";

	protected readonly searchFilterSearchButtonIcon: string = "fa-search";

	// search table general constants

	protected readonly searchTableRows: number = 15;

	protected readonly searchTablePageLinks: number = 3;

	// search table action column constants

	protected readonly searchTableActionColumnClass: string = "c-search-table-action-column";

	protected readonly searchTableActionColumnButtonClass: string = "c-full-width";

	protected readonly searchTableActionColumnAddButtonIcon: string = "fa-plus";

	protected readonly searchTableActionColumnViewEditButtonWrapperClass: string = "c-search-table-action-column-view-edit-button-wrapper";

	protected readonly searchTableActionColumnViewButtonIcon: string = "fa-envelope-open";

	protected readonly searchTableActionColumnEditButtonIcon: string = "fa-pencil";

	protected readonly searchTableActionColumnDeleteButtonWrapperClass: string = "c-search-table-action-column-delete-button-wrapper";

	protected readonly searchTableActionColumnDeleteButtonIcon: string = "fa-remove";

	// form dialog constants

	protected readonly formDialogClass: string = "c-form-dialog";

	protected readonly formDialogContentStyle: object = {'overflow': 'visible'};

	protected readonly formComponentClass: string = "c-full-width";

	protected readonly formSaveButtonIcon: string = "fa-check";

	protected readonly formCancelButtonIcon: string = "fa-close";

	// confirm dialog constants

	protected readonly confirmDialogIcon: string = "fa-question-circle";

	// enum lists

	protected readonly languageTypes: string[] = Object.keys(LanguageType).filter((key: string) => !isNumeric(key));

	protected readonly statusTypes: string[] = Object.keys(StatusType).filter((key: string) => !isNumeric(key));

	// lists for dropdowns

	protected readonly languages: SelectItem[] = [];

	protected readonly statuses: SelectItem[] = [];

	protected readonly texts: SelectItem[] = [];

	// table fields

	protected tableTotalRecords: number = 0;

	protected loading: boolean = true;

	// dialog fields

	protected showDialog: boolean = false;

	protected isAdd: boolean = false;

	protected isEdit: boolean = false;

	protected isView: boolean = false;

	protected constructor(private confirmationService: ConfirmationService, private messageService: MessageService) {}

	public ngOnInit(): void {
		// init languages for dropdown
		this.languages.push({label: null, value: null});

		this.languageTypes.forEach((language: string) => {
			this.languages.push({label: this.getMessage('LANGUAGE_TYPE_' + language), value: language});
		});

		// init statuses for dropdown
		this.statuses.push({label: null, value: null});

		this.statusTypes.forEach((status: string) => {
			this.statuses.push({label: this.getMessage('STATUS_TYPE_' + status), value: status});
		});

		// init texts for dropdown
		// this.texts.push({label: null, value: null});
		//
		// this.textService.getTextListForSelection().subscribe((texts: Text[]) => {
		// 	texts.forEach((text: Text) => {
		// 		this.texts.push({label: text.key, value: text});
		// 	});
		// }, (error: any) => this.handleError(error));
	}

	// check user permission
	protected hasPermission(permission: string): boolean {
		return AuthenticationUtils.hasPermission(permission);
	}

	// get i18n message
	protected getMessage(key: string, ...params: any[]): string {
		// TODO get user language
		let value: string = MESSAGES[key]['en'];

		for (let i = 0; i < params.length; i++) {
			value = value.replace('{' + i + '}', params[i]);
		}

		return value;
	}

	// adds error message to the growl component
	protected showErrorMessage(message: string, ...params: any[]): void {
		this.messageService.add({severity: 'error', detail: this.getMessage(message, params)});
	}

	// handles component errors
	protected handleError(error: any): void {
		console.error(error);
	}

	// initializes abstract filter paging fields
	protected initPagingFilter(filter: AbstractFilter): void {
		filter.offset = 0;
		filter.numRows = this.searchTableRows;
	}

	// initializes abstract filter paging fields through a lazy load event
	protected initLazyPagingFilter(filter: AbstractFilter, event: LazyLoadEvent): void {
		filter.offset = event.first;
		filter.numRows = event.rows;
	}

	// initializes confirm dialog
	protected confirmAction(action: () => any): void {
		this.confirmationService.confirm({
			header: this.getMessage('CONFIRMATION'),
			message: this.getMessage('CONFIRM_ACTION_MESSAGE'),
			icon: this.confirmDialogIcon,
			accept: () => {
				action();
			}
		});
	}

	// updates form dialog states and shows it
	protected updateDialogStates(isAdd: boolean, isEdit: boolean, isView: boolean): void {
		this.isAdd = isAdd;
		this.isEdit = isEdit;
		this.isView = isView;

		this.showDialog = true;
	}

	// hides form dialog
	protected hideDialog(): void {
		this.showDialog = false;
	}
}