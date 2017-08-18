import {Component} from "@angular/core";

import {User} from "../../model/user";
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
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

	totalRecords: number;

	showUserDialog: boolean;

	selectedUser: User = new User();

	constructor(private router: Router, private route: ActivatedRoute, private userService: UserService) {
		super();
	}

	add(): void {}

	update(): void {}

	delete(): void {}

	get(user: User) {
		this.selectedUser = user;
		this.showUserDialog = true;
	}

	getList(event: LazyLoadEvent): void {
		let filter: UserFilter = {
			offset: event.first,
			numRows: event.rows
		};

		if (!isNullOrUndefined(event.filters.id)) {
			filter.id = event.filters.id.value;
		}

		if (!isNullOrUndefined(event.filters.username)) {
			filter.username = event.filters.username.value;
		}

		this.userService.getCount(filter).then(count => this.totalRecords = count, error => Utils.handleError(error));
		this.userService.getList(filter).then(users => this.users = users, error => Utils.handleError(error));
	}
}