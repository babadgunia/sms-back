// model
import {AbstractFilter} from "../model/filter/abstract-filter";
// services
import {AuthenticationService} from "../service/authentication.service";
// primeng
import {LazyLoadEvent} from "primeng/components/common/lazyloadevent";

export class AbstractComponent {

	protected readonly tableRows: number = 1;

	protected readonly tablePageLinks: number = 3;

	protected readonly tableLoadingIcon: string = "fa-cog";

	protected tableTotalRecords: number;

	protected tableLoading: boolean = true;

	protected hasPermission(permission: string): boolean {
		return AuthenticationService.hasPermission(permission);
	}

	protected clearAbstractFilter(): void {
		[].forEach.call(document.getElementsByClassName("c-search-filter-component"), element => {
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