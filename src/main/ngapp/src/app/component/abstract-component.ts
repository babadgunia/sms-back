// model
import {AbstractFilter} from "../model/filter/abstract-filter";
// services
import {AuthenticationService} from "../service/authentication.service";
// primeng
import {LazyLoadEvent} from "primeng/components/common/lazyloadevent";

export class AbstractComponent {

	protected readonly searchFilterComponentClass: string = "c-search-filter-component";

	protected readonly searchFilterComponentWrapperClass: string = "c-search-filter-component-wrapper";

	protected readonly searchFilterClearButtonIcon: string = "fa-minus";

	protected readonly searchFilterSearchButtonIcon: string = "fa-search";

	protected readonly searchTableClass: string = "c-search-table";

	protected readonly tableRows: number = 1;

	protected readonly tablePageLinks: number = 3;

	protected readonly tableLoadingIcon: string = "fa-cog";

	protected readonly searchTableActionColumnClass: string = "c-search-table-action-column";

	protected readonly searchTableActionColumnAddButtonClass: string = "fa-plus";

	protected readonly searchTableActionColumnEditButtonClass: string = "fa-pencil";

	protected readonly searchTableActionColumnViewButtonClass: string = "fa-envelope-open";

	protected readonly searchTableActionColumnDeleteButtonClass: string = "fa-remove";

	protected tableTotalRecords: number;

	protected tableLoading: boolean = true;

	protected hasPermission(permission: string): boolean {
		return AuthenticationService.hasPermission(permission);
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
		filter.numRows = this.tableRows;
	}

	protected initAbstractLazyFilter(filter: AbstractFilter, event: LazyLoadEvent): void {
		filter.offset = event.first;
		filter.numRows = event.rows;
	}
}