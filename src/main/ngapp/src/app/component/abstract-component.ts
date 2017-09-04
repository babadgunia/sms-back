// model > filter
import {AbstractFilter} from "../model/filter/abstract-filter";
// model > enum
import {LanguageType} from "../model/enum/language-type.enum";
import {StatusType} from "../model/enum/status-type.enum";
// service
import {AuthenticationService} from "../service/authentication.service";
// util
import {messages} from "../util/messages";
// primeng > util
import {LazyLoadEvent} from "primeng/components/common/lazyloadevent";
import {Message} from "primeng/components/common/message";
import {SelectItem} from "primeng/components/common/selectitem";
// primeng > service
import {ConfirmationService} from "primeng/components/common/confirmationservice";
// rxjs
import {isNumeric} from "rxjs/util/isNumeric";

export abstract class AbstractComponent {

	protected readonly searchFilterComponentClass: string = "c-full-width";

	protected readonly searchFilterFieldWrapperClass: string = "c-no-left-padding";

	protected readonly searchFilterButtonWrapperClass: string = "c-no-right-padding";

	protected readonly searchFilterClearButtonIcon: string = "fa-minus";

	protected readonly searchFilterSearchButtonIcon: string = "fa-search";

	protected readonly searchTableRows: number = 10;

	protected readonly searchTablePageLinks: number = 3;

	protected readonly searchTableLoadingIcon: string = "fa-cog";

	protected readonly searchTableFilterComponentStyle: object = {'width': '100%'};

	protected readonly searchTableActionColumnClass: string = "c-search-table-action-column";

	protected readonly searchTableActionColumnButtonClass: string = "c-full-width";

	protected readonly searchTableActionColumnAddButtonIcon: string = "fa-plus";

	protected readonly searchTableActionColumnViewEditButtonWrapperClass: string = "c-search-table-action-column-view-edit-button-wrapper";

	protected readonly searchTableActionColumnViewButtonIcon: string = "fa-envelope-open";

	protected readonly searchTableActionColumnEditButtonIcon: string = "fa-pencil";

	protected readonly searchTableActionColumnDeleteButtonWrapperClass: string = "c-search-table-action-column-delete-button-wrapper";

	protected readonly searchTableActionColumnDeleteButtonIcon: string = "fa-remove";

	protected readonly formDialogClass: string = "c-form-dialog";

	protected readonly formDialogContentStyle: object = {'overflow': 'visible'};

	protected readonly formComponentClass: string = "c-full-width";

	protected readonly formSaveButtonIcon: string = "fa-check";

	protected readonly formCancelButtonIcon: string = "fa-close";

	protected readonly confirmDialogIcon: string = "fa-question-circle";

	protected readonly statuses: SelectItem[] = [];

	protected readonly languages: SelectItem[] = [];

	protected messages: Message[] = [];

	protected tableTotalRecords: number = 0;

	protected tableLoading: boolean = true;

	protected showDialog: boolean = false;

	protected isAdd: boolean = false;

	protected isEdit: boolean = false;

	protected isView: boolean = false;

	protected constructor(private confirmationService: ConfirmationService) {
		this.statuses.push({label: '', value: null});

		let statusStrings: string[] = Object.keys(StatusType).filter(key => !isNumeric(key));
		statusStrings.forEach((statusString: string) => {
			this.statuses.push({label: statusString, value: statusString});
		});

		this.languages.push({label: '', value: null});

		let languageStrings: string[] = Object.keys(LanguageType).filter(key => !isNumeric(key));
		languageStrings.forEach((languageString: string) => {
			this.languages.push({label: languageString, value: languageString});
		});
	}

	protected hasPermission(permission: string): boolean {
		return AuthenticationService.hasPermission(permission);
	}

	protected getMessage(key: string, ...params: any[]): string {
		let value: string = messages[key]['en'];

		for (let i = 0; i < params.length; i++) {
			value = value.replace('{' + i + '}', params[i]);
		}

		return value;
	}

	protected showErrorMessage(message: string, ...params: any[]): void {
		this.messages.push({severity: 'error', detail: this.getMessage(message, params)});
	}

	protected handleError(error: any): void {
		console.error(error);
	}

	protected abstractClearFilter(): void {
		[].forEach.call(document.getElementsByClassName(this.searchFilterComponentClass), element => {
			if (element.type === 'text') {
				element.value = '';
			}
		});
	}

	protected abstractInitFilter(filter: AbstractFilter): void {
		filter.offset = 0;
		filter.numRows = this.searchTableRows;
	}

	protected abstractInitLazyFilter(filter: AbstractFilter, event: LazyLoadEvent): void {
		filter.offset = event.first;
		filter.numRows = event.rows;
	}

	protected abstractConfirmAction(action: () => any): void {
		this.confirmationService.confirm({
			header: this.getMessage('CONFIRMATION'),
			message: this.getMessage('CONFIRM_ACTION_MESSAGE'),
			icon: this.confirmDialogIcon,
			accept: () => {
				action();
			}
		});
	}

	protected updateDialogStates(isAdd: boolean, isEdit: boolean, isView: boolean) {
		this.isAdd = isAdd;
		this.isEdit = isEdit;
		this.isView = isView;

		this.showDialog = true;
	}
}