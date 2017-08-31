// angular > core
import {Component} from "@angular/core";
// model > entity
import {User} from "../../model/entity/user";
// model > filter
import {UserFilter} from "../../model/filter/user-filter";
// component
import {AbstractComponent} from "../abstract-component";
// service
import {UserService} from "../../service/user.service";
// util
import {isNullOrUndefined} from "util";
// primeng component
import {LazyLoadEvent} from "primeng/components/common/lazyloadevent";
// primeng service
import {ConfirmationService} from "primeng/components/common/confirmationservice";

@Component({
	selector: 'users',
	templateUrl: './users.component.html',
	styleUrls: ['./users.component.css']
})
export class UsersComponent extends AbstractComponent {

	private users: User[];

	private filter: UserFilter = {};

	private user: User = new User();

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

	private update(): void {
		this.service.update(this.user).subscribe(user => {
			let index: number = this.users.findIndex((element: User) => element.id === user.id);
			this.users.splice(index, 1, user);

			this.showDialog = false;
		}, error => super.handleError(error));
	}

	private confirmAction(user: User): void {
		super.abstractConfirmAction(() => {
			this.service.delete(user.id).subscribe(() => {
				this.users = this.users.filter(element => element !== user);
			}, error => super.handleError(error));
		});
	}

	private get(user: User): void {
		this.service.get(user.id).subscribe(user => {
			this.user = user;
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