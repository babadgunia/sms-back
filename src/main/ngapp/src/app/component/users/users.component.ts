import {Component, OnInit} from "@angular/core";

import {User} from "../../model/user";
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {LazyLoadEvent} from "primeng/components/common/lazyloadevent";
import {UserFilter} from "../../model/filter/user-filter";

@Component({
	selector: 'users',
	templateUrl: './users.component.html',
	styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

	users: User[];

	searchUser: User;

	showUserDialog: boolean;

	selectedUser: User = new User();

	totalRecords: number;

	constructor(private router: Router, private route: ActivatedRoute, private userService: UserService) {}

	ngOnInit() {
		// this.loadUsers();
	}

	loadUsers(event: LazyLoadEvent): void {
		let filter: UserFilter = {
			offset: event.first,
			numRows: event.rows
		};

		this.userService.getCount(filter).then(count => this.totalRecords = count, error => {
			console.error(error);
		});

		this.userService.getList(filter).then(users => this.users = users, error => {
			console.error(error);
		});
	}

	load(event: LazyLoadEvent) {
		//event.first = First row offset
		//event.rows = Number of rows per page
		//event.sortField = Field name to sort with
		//event.sortOrder = Sort order as number, 1 for asc and -1 for dec
		//filters: FilterMetadata object having field as key and filter value, filter matchMode as value

		//imitate db connection over a network
		// setTimeout(() => {
		// 	if (this.datasource) {
		// 		this.cars = this.datasource.slice(event.first, (event.first + event.rows));
		// 	}
		// }, 250);
	}

	add(): void {}

	update(): void {}

	delete(): void {}

	selectItem(user: User) {
		this.selectedUser = user;
		this.showUserDialog = true;
	}
}