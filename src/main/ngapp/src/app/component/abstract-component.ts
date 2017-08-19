import {AbstractFilter} from "../model/filter/abstract-filter";
import {LazyLoadEvent} from "primeng/primeng";

export class AbstractComponent {

	protected readonly numRows: number = 1;

	protected totalRecords: number;

	protected initAbstractFilter(filter: AbstractFilter): void {
		filter.offset = 0;
		filter.numRows = this.numRows;
	}

	protected initAbstractLazyFilter(filter: AbstractFilter, event: LazyLoadEvent): void {
		filter.offset = event.first;
		filter.numRows = event.rows;
	}

	protected clearFilter() {
		[].forEach.call(document.getElementsByClassName("c-search-filter-component"), element => {
			if (element.type === 'text') {
				element.value = '';
			}
		});
	}
}