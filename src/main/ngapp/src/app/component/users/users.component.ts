// angular
import {Component} from "@angular/core";
// model
import {User} from "../../model/entity/user";
import {UserFilter} from "../../model/filter/user-filter";
// components
import {AbstractComponent} from "../abstract-component";
// services
import {UserService} from "../../service/user.service";
// primeng services
import {ConfirmationService} from "primeng/primeng";
// utils
import {isNullOrUndefined} from "util";
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

	private selectedUser: User = new User();

	constructor(private service: UserService, confirmationService: ConfirmationService) {
		super(confirmationService);
	}

	private clearFilter(): void {
		super.abstractClearFilter();

		this.initFilter(null, null, null);
	}

	private initFilter(id: number, username: string, name: string): void {
		super.abstractInitFilter(this.filter);

		this.filter.id = id;
		this.filter.username = username;
		this.filter.name = name;

		this.getList();
	}

	private initLazyFilter(event: LazyLoadEvent): void {
		super.abstractInitLazyFilter(this.filter, event);

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

	private confirmAction(user: User): void {
		super.abstractConfirmAction(() => {
			this.service.delete(user.id).subscribe(() => {
				this.users = this.users.filter(element => element !== user);
			}, error => super.handleError(error));
		});
	}

	private get(user: User): void {
		this.service.get(user.id).subscribe(user => {
			this.selectedUser = user;
			console.log(this.selectedUser);
			this.showDialog = true;
			this.isEdit = true;
		}, error => super.handleError(error));
	}

	private getList(): void {
		this.tableLoading = true;

		this.service.getCount(this.filter).subscribe(count => {
			this.tableTotalRecords = count;
		}, error => super.handleError(error));

		this.service.getList(this.filter).subscribe(users => {
			this.tableLoading = false;
			this.users = users;
		}, error => super.handleError(error));
	}
}