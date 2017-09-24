// model > filter
import {AbstractFilter} from "../model/filter/abstract-filter";
// model > enum
import {LanguageType} from "../model/enum/language-type.enum";
import {StatusType} from "../model/enum/status-type.enum";
// util
import {messages} from "../util/messages";
import {AuthUtils} from "../util/auth-utils";
// primeng > model
import {LazyLoadEvent} from "primeng/components/common/lazyloadevent";
import {SelectItem} from "primeng/components/common/selectitem";
// primeng > component
import {Dropdown} from "primeng/components/dropdown/dropdown";
// primeng > service
import {ConfirmationService} from "primeng/components/common/confirmationservice";
import {MessageService} from "primeng/components/common/messageservice";
// rxjs
import {isNumeric} from "rxjs/util/isNumeric";

export abstract class AbstractComponent {

	// general constants

	protected readonly logoutButtonIcon: string = "fa-sign-out";

	// search filter constants

	protected readonly searchFilterComponentClass: string = "c-full-width";

	protected readonly searchFilterFieldWrapperClass: string = "c-no-left-padding";

	protected readonly searchFilterButtonWrapperClass: string = "c-no-right-padding";

	protected readonly searchFilterClearButtonIcon: string = "fa-minus";

	protected readonly searchFilterSearchButtonIcon: string = "fa-search";

	// search table general constants

	protected readonly searchTableRows: number = 10;

	protected readonly searchTablePageLinks: number = 3;

	// search table filter constants

	protected readonly searchTableDropdownFilterStyle: object = {'width': '100%'};

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

	protected readonly formResetPasswordButtonIcon: string = "fa-retweet";

	protected readonly formSaveButtonIcon: string = "fa-check";

	protected readonly formCancelButtonIcon: string = "fa-close";

	// confirm dialog constants

	protected readonly confirmDialogIcon: string = "fa-question-circle";

	// lists for dropdowns

	protected readonly languages: SelectItem[] = [];

	protected readonly statuses: SelectItem[] = [];

	// table fields

	protected tableTotalRecords: number = 0;

	// dialog fields

	protected showDialog: boolean = false;

	protected isAdd: boolean = false;

	protected isEdit: boolean = false;

	protected isView: boolean = false;

	protected constructor(private confirmationService: ConfirmationService, private messageService: MessageService) {
		// init language list for dropdown
		this.languages.push({label: '', value: ''});

		let languageStrings: string[] = Object.keys(LanguageType).filter(key => !isNumeric(key));
		languageStrings.forEach((language: string) => {
			this.languages.push({label: this.getMessage('LANGUAGE_TYPE_' + language), value: language});
		});

		// init status list for dropdown
		this.statuses.push({label: '', value: ''});

		let statusStrings: string[] = Object.keys(StatusType).filter(key => !isNumeric(key));
		statusStrings.forEach((status: string) => {
			this.statuses.push({label: this.getMessage('STATUS_TYPE_' + status), value: status});
		});
	}

	// check user permission
	protected hasPermission(permission: string): boolean {
		return AuthUtils.hasPermission(permission);
	}

	// get i18n message
	protected getMessage(key: string, ...params: any[]): string {
		// TODO get user language
		let value: string = messages[key]['en'];

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

	// updates dialog states and shows it
	protected updateDialogStates(isAdd: boolean, isEdit: boolean, isView: boolean): void {
		this.isAdd = isAdd;
		this.isEdit = isEdit;
		this.isView = isView;

		this.showDialog = true;
	}

	protected hideDialog(): void {
		this.showDialog = false;
	}

	// resets the dropdown component if an empty value is selected
	protected resetDropdown(value: any, box: Dropdown): void {
		if (value === '') {
			box.selectedOption = null;
			box.value = null;
		}
	}
}