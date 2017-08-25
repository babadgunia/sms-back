// model
import {AbstractFilter} from "../model/filter/abstract-filter";
// services
import {AuthenticationService} from "../service/authentication.service";
// utils
import {messages} from "../utils/messages";
// primeng
import {LazyLoadEvent} from "primeng/components/common/lazyloadevent";

export class AbstractComponent {

	protected readonly searchFilterComponentClass: string = "c-full-width";

	protected readonly searchFilterFieldWrapperClass: string = "c-no-left-padding";

	protected readonly searchFilterButtonWrapperClass: string = "c-no-right-padding";

	protected readonly searchFilterClearButtonIcon: string = "fa-minus";

	protected readonly searchFilterSearchButtonIcon: string = "fa-search";

	protected readonly searchTableRows: number = 1;

	protected readonly searchTablePageLinks: number = 3;

	protected readonly searchTableLoadingIcon: string = "fa-cog";

	protected readonly searchTableActionColumnClass: string = "c-search-table-action-column";

	protected readonly searchTableActionColumnButtonClass: string = "c-full-width";

	protected readonly searchTableActionColumnAddButtonIcon: string = "fa-plus";

	protected readonly searchTableActionColumnViewEditButtonWrapperClass: string = "c-search-table-action-column-view-edit-button-wrapper";

	protected readonly searchTableActionColumnViewButtonIcon: string = "fa-envelope-open";

	protected readonly searchTableActionColumnEditButtonIcon: string = "fa-pencil";

	protected readonly searchTableActionColumnDeleteButtonWrapperClass: string = "c-search-table-action-column-delete-button-wrapper";

	protected readonly searchTableActionColumnDeleteButtonIcon: string = "fa-remove";

	protected tableTotalRecords: number;

	protected tableLoading: boolean = true;

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

	protected clearAbstractFilter(): void {
		[].forEach.call(document.getElementsByClassName(this.searchFilterComponentClass), element => {
			if (element.type === 'text') {
				element.value = '';
			}
		});
	}

	protected initAbstractFilter(filter: AbstractFilter): void {
		filter.offset = 0;
		filter.numRows = this.searchTableRows;
	}

	protected initAbstractLazyFilter(filter: AbstractFilter, event: LazyLoadEvent): void {
		filter.offset = event.first;
		filter.numRows = event.rows;
	}
}