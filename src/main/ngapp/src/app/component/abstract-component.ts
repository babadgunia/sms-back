import {AbstractFilter} from "../model/filter/abstract-filter";
import {LazyLoadEvent} from "primeng/primeng";

export class AbstractComponent {

	protected readonly tableRows: number = 1;

	protected readonly tablePageLinks: number = 3;

	protected tableTotalRecords: number;

	protected tableLoading: boolean = true;

	protected tableLoadingIcon = "fa-cog";

	protected initAbstractFilter(filter: AbstractFilter): void {
		filter.offset = 0;
		filter.numRows = this.tableRows;
	}

	protected static initAbstractLazyFilter(filter: AbstractFilter, event: LazyLoadEvent): void {
		filter.offset = event.first;
		filter.numRows = event.rows;
	}

	protected static clearAbstractFilter() {
		[].forEach.call(document.getElementsByClassName("c-search-filter-component"), element => {
			if (element.type === 'text') {
				element.value = '';
			}
		});
	}
}