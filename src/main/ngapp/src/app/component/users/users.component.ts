import {Component} from "@angular/core";

import {User} from "../../model/user";
import {UserService} from "../../service/user.service";
import {LazyLoadEvent} from "primeng/components/common/lazyloadevent";
import {UserFilter} from "../../model/filter/user-filter";
import {AbstractComponent} from "../abstract-component";
import {Utils} from "../../utils/Utils";
import {isNullOrUndefined} from "util";

@Component({
	selector: 'users',
	templateUrl: './users.component.html',
	styleUrls: ['./users.component.css']
})
export class UsersComponent extends AbstractComponent {

	users: User[];

	filter: UserFilter = {};

	showUserDialog: boolean;

	selectedUser: User = new User();

	constructor(private userService: UserService) {
		super();
	}

	initFilter(id: number, username: string, name: string) {
		super.initAbstractFilter(this.filter);

		this.filter.id = id;
		this.filter.username = username;
		this.filter.name = name;

		this.getList();
	}

	initLazyFilter(event: LazyLoadEvent): void {
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

	add(): void {}

	update(): void {}

	delete(): void {}

	get(user: User) {
		this.selectedUser = user;
		this.showUserDialog = true;
	}

	getList(): void {
		this.userService.getCount(this.filter).then(count => this.totalRecords = count, error => Utils.handleError(error));
		this.userService.getList(this.filter).then(list => this.users = list, error => Utils.handleError(error));
	}
}