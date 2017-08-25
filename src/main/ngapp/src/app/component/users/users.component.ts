// angular
import {Component} from "@angular/core";
// model
import {User} from "../../model/user";
import {UserFilter} from "../../model/filter/user-filter";
// components
import {AbstractComponent} from "../abstract-component";
// services
import {UserService} from "../../service/user.service";
// utils
import {isNullOrUndefined} from "util";
import {Utils} from "../../utils/Utils";
// primeng
import {LazyLoadEvent} from "primeng/components/common/lazyloadevent";

@Component({
	selector: 'users',
	templateUrl: './users.component.html',
	styleUrls: ['./users.component.css']
})
export class UsersComponent extends AbstractComponent {

	private users: User[];

	private filter: UserFilter = {};

	private showUserDialog: boolean;

	private selectedUser: User = new User();

	constructor(private userService: UserService) {
		super();
	}

	private clearFilter(): void {
		super.clearAbstractFilter();

		this.initFilter(null, null, null);
	}

	private initFilter(id: number, username: string, name: string): void {
		super.initAbstractFilter(this.filter);

		this.filter.id = id;
		this.filter.username = username;
		this.filter.name = name;

		this.getList();
	}

	private initLazyFilter(event: LazyLoadEvent): void {
		super.initAbstractLazyFilter(this.filter, event);

		if (!isNullOrUndefined(event.filters.id)) {
			this.filter.id = event.filters.id.value;
		}
		if (!isNullOrUndefined(event.filters.username)) {
			this.filter.username = event.filters.username.value;
		}
		if (!isNullOrUndefined(event.filters.name)) {
			this.filter.username = event.filters.name.value;
		}

		this.getList();
	}

	private add(): void {}

	private update(): void {}

	private delete(): void {}

	private get(user: User): void {
		this.selectedUser = user;
		this.showUserDialog = true;
	}

	private getList(): void {
		this.tableLoading = true;

		this.userService.getCount(this.filter).then(count => this.tableTotalRecords = count, error => Utils.handleError(error));
		this.userService.getList(this.filter).then(list => {
			this.tableLoading = false;
			this.users = list;
		}, error => Utils.handleError(error));
	}
}