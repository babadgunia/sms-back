// angular > core
import {Component} from "@angular/core";
// model > entity
import {User} from "../../model/entity/user";
// model > enum
import {LanguageType} from "../../model/enum/language-type.enum";
import {StatusType} from "app/model/enum/status-type.enum";
// model > filter
import {UserFilter} from "../../model/filter/user-filter";
// component
import {AbstractComponent} from "../abstract-component";
// service
import {UserService} from "../../service/user.service";
// util
import {isNullOrUndefined} from "util";
import {Utils} from "../../util/utils";
// primeng > model
import {LazyLoadEvent} from "primeng/components/common/lazyloadevent";
// primeng > component
import {DataTable} from "primeng/components/datatable/datatable";
import {Dropdown} from "primeng/components/dropdown/dropdown";
// primeng > service
import {ConfirmationService} from "primeng/components/common/confirmationservice";
import {MessageService} from "primeng/components/common/messageservice";

@Component({
	selector: 'users',
	templateUrl: './users.component.html',
	styleUrls: ['./users.component.css']
})
export class UsersComponent extends AbstractComponent {

	private users: User[];

	private user: User = new User();

	private filter: UserFilter = {};

	// lazy dropdown filters

	private statusFilter: string;

	private languageFilter: string;

	public constructor(private service: UserService, confirmationService: ConfirmationService, messageService: MessageService) {
		super(confirmationService, messageService);
	}

	private resetFilters(table: DataTable, idField: HTMLInputElement, usernameField: HTMLInputElement, emailField: HTMLInputElement, nameField: HTMLInputElement,
											 statusBox: Dropdown, languageBox: Dropdown): void {
		this.resetCustomFilter(idField, usernameField, emailField, nameField, statusBox, languageBox);
		this.resetTableFilter();

		this.filter = {};

		table.reset();
	}

	private resetCustomFilter(idField: HTMLInputElement, usernameField: HTMLInputElement, emailField: HTMLInputElement, nameField: HTMLInputElement,
														statusBox: Dropdown, languageBox: Dropdown): void {
		idField.value = '';
		usernameField.value = '';
		emailField.value = '';
		nameField.value = '';
		statusBox.selectedOption = null;
		languageBox.selectedOption = null;
	}

	private resetTableFilter(): void {
		this.statusFilter = null;
		this.languageFilter = null;
	}

	private initCustomFilter(table: DataTable, id: number, username: string, email: string, name: string, status: string, language: string): void {
		this.resetTableFilter();

		this.filter = {};

		table.reset();

		super.initPagingFilter(this.filter);

		this.filter.id = id;
		this.filter.username = username;
		this.filter.email = email;
		this.filter.name = name;
		this.filter.status = status;
		this.filter.language = language;
	}

	private initTableFilter(event: LazyLoadEvent): void {
		this.filter = {};

		super.initLazyPagingFilter(this.filter, event);

		if (!isNullOrUndefined(event.filters.id)) {
			this.filter.id = event.filters.id.value;
		}
		if (!isNullOrUndefined(event.filters.username)) {
			this.filter.username = event.filters.username.value;
		}
		if (!isNullOrUndefined(event.filters.email)) {
			this.filter.email = event.filters.email.value;
		}
		if (!isNullOrUndefined(event.filters.name)) {
			this.filter.name = event.filters.name.value;
		}
		if (!isNullOrUndefined(event.filters.status)) {
			this.filter.status = event.filters.status.value;
		}
		if (!isNullOrUndefined(event.filters.language)) {
			this.filter.language = event.filters.language.value;
		}
	}

	private initAdd(): void {
		this.user = new User();

		this.user.status = StatusType[StatusType.ACTIVE];
		this.user.language = LanguageType[LanguageType.EN];
	}

	private save(): void {
		if (!this.isValidUser()) {
			return;
		}

		if (this.isAdd) {
			this.add();
		} else {
			this.update();
		}
	}

	private isValidUser(): boolean {
		if (Utils.isBlank(this.user.username)) {
			super.showErrorMessage('CANNOT_BE_NULL', this.getMessage('USERNAME'));

			return false;
		}
		if (Utils.isBlank(this.user.email)) {
			super.showErrorMessage('CANNOT_BE_NULL', this.getMessage('EMAIL'));

			return false;
		}
		if (Utils.isBlank(this.user.name)) {
			super.showErrorMessage('CANNOT_BE_NULL', this.getMessage('NAME'));

			return false;
		}
		if (Utils.isBlank(this.user.status)) {
			super.showErrorMessage('CANNOT_BE_NULL', this.getMessage('STATUS'));

			return false;
		}

		if (Utils.isBlank(this.user.language)) {
			super.showErrorMessage('CANNOT_BE_NULL', this.getMessage('LANGUAGE'));

			return false;
		}

		return true;
	}

	private add(): void {
		this.service.add(this.user).subscribe((user: User) => {
			this.users.push(user);
			this.tableTotalRecords++;

			this.showDialog = false;
		}, (error: any) => super.handleError(error));
	}

	private update(): void {
		this.service.update(this.user).subscribe((user: User) => {
			let index: number = this.users.findIndex((element: User) => element.id === user.id);
			this.users.splice(index, 1, user);

			this.showDialog = false;
		}, (error: any) => super.handleError(error));
	}

	private confirmDeleteAction(user: User): void {
		super.confirmAction(() => {
			this.service.delete(user.id).subscribe(() => {
				let index: number = this.users.findIndex((element: User) => element.id === user.id);
				this.users.splice(index, 1);
				this.tableTotalRecords--;
			}, (error: any) => super.handleError(error));
		});
	}

	private get(user: User): void {
		this.service.get(user.id).subscribe((user: User) => {
			this.user = user;
		}, (error: any) => super.handleError(error));
	}

	private getList(): void {
		this.service.getCount(this.filter).subscribe((count: number) => {
			this.tableTotalRecords = count;
		}, (error: any) => super.handleError(error));

		this.service.getList(this.filter).subscribe((users: User[]) => {
			this.users = users;
		}, (error: any) => super.handleError(error));
	}

	private resetPassword(): void {
		this.service.resetPassword(this.user.id).subscribe(() => {}, (error: any) => super.handleError(error));
	}
}