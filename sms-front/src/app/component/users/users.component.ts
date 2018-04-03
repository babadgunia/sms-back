// angular > core
import {Component, OnInit} from '@angular/core';
// model > entity
import {User} from '../../model/entity/user';
import {UserGroup} from '../../model/entity/user-group';
// model > enum
import {LanguageType} from '../../model/enum/language-type.enum';
import {StatusType} from '../../model/enum/status-type.enum';
// model > filter
import {UserFilter} from '../../model/filter/user-filter';
// component
import {AbstractComponent} from '../abstract-component';
// service
import {UserGroupService} from '../../service/user-group.service';
import {UserService} from '../../service/user.service';
// util
import {Utils} from '../../util/utils';
// primeng > model
import {LazyLoadEvent} from 'primeng/components/common/lazyloadevent';
import {SelectItem} from 'primeng/components/common/selectitem';
// primeng > component
import {Dropdown} from 'primeng/components/dropdown/dropdown';
// primeng > service
import {ConfirmationService} from 'primeng/components/common/confirmationservice';
import {MessageService} from 'primeng/components/common/messageservice';
import {isNullOrUndefined} from 'util';

@Component({
	selector: 'users',
	templateUrl: './users.component.html',
	styleUrls: ['./users.component.css']
})
export class UsersComponent extends AbstractComponent implements OnInit {

	protected readonly userGroups: SelectItem[] = [];

	private entities: User[];

	private entity: User = new User();

	private filter: UserFilter;

	public constructor(private service: UserService, private userGroupService: UserGroupService, confirmationService: ConfirmationService, messageService: MessageService) {
		super(confirmationService, messageService);
	}

	public ngOnInit(): void {
		super.ngOnInit();

		// init user groups  for dropdown
		this.userGroups.push({label: null, value: null});

		this.userGroupService.getListForSelection().subscribe((userGroups: UserGroup[]) => {
			userGroups.forEach((userGroup: UserGroup) => {
				this.userGroups.push({label: userGroup.name, value: userGroup});
			});
		}, (error: any) => super.handleError(error));
	}

	private resetFilter(idField: HTMLInputElement, usernameField: HTMLInputElement, emailField: HTMLInputElement, nameField: HTMLInputElement,
											statusBox: Dropdown, languageBox: Dropdown, userGroupBox: Dropdown): void {
		idField.value = '';
		usernameField.value = '';
		emailField.value = '';
		nameField.value = '';

		statusBox.value = null;
		statusBox.selectedOption = null;
		languageBox.value = null;
		languageBox.selectedOption = null;
		userGroupBox.value = null;
		userGroupBox.selectedOption = null;

		this.filter = {};
	}

	private initFilter(id: number, username: string, email: string, name: string, status: string, language: string, userGroup: UserGroup): void {
		this.filter = {};
		super.initPagingFilter(this.filter);
		this.initFilterFields(id, username, email, name, status, language, userGroup);
	}

	private initFilterFields(id: number, username: string, email: string, name: string, status: string, language: string, userGroup: UserGroup): void {
		this.filter.id = id;
		this.filter.username = username;
		this.filter.email = email;
		this.filter.name = name;
		this.filter.status = status;
		this.filter.language = language;

		if (!isNullOrUndefined(userGroup)) {
			this.filter.userGroupId = userGroup.id;
		}
	}

	private initLazyFilter(event: LazyLoadEvent, id: number, username: string, email: string, name: string, status: string, language: string, userGroup: UserGroup): void {
		this.filter = {};
		super.initLazyPagingFilter(this.filter, event);
		this.initFilterFields(id, username, email, name, status, language, userGroup);
	}

	private initAdd(): void {
		this.entity = new User();

		this.entity.status = StatusType[StatusType.ACTIVE];
		this.entity.language = LanguageType[LanguageType.EN];
	}

	private save(): void {
		if (!this.isValidEntity()) {
			return;
		}

		if (this.isAdd) {
			this.add();
		} else {
			this.update();
		}
	}

	private isValidEntity(): boolean {
		if (Utils.isBlank(this.entity.username)) {
			super.showErrorMessage('CANNOT_BE_EMPTY', this.getMessage('USERNAME'));

			return false;
		}
		if (Utils.isBlank(this.entity.email)) {
			super.showErrorMessage('CANNOT_BE_EMPTY', this.getMessage('EMAIL'));

			return false;
		}
		if (Utils.isBlank(this.entity.name)) {
			super.showErrorMessage('CANNOT_BE_EMPTY', this.getMessage('NAME'));

			return false;
		}
		if (Utils.isBlank(this.entity.status)) {
			super.showErrorMessage('CANNOT_BE_EMPTY', this.getMessage('STATUS'));

			return false;
		}

		if (Utils.isBlank(this.entity.language)) {
			super.showErrorMessage('CANNOT_BE_EMPTY', this.getMessage('LANGUAGE'));

			return false;
		}

		if (isNullOrUndefined(this.entity.userGroup) || (typeof this.entity.userGroup === 'string' && Utils.isBlank(this.entity.userGroup))) {
			super.showErrorMessage('CANNOT_BE_EMPTY', this.getMessage('USER_GROUP'));

			return false;
		}

		return true;
	}

	private add(): void {
		this.service.add(this.entity).subscribe((entity: User) => {
			this.entities.push(entity);
			this.tableTotalRecords++;

			this.showDialog = false;
		}, (error: any) => super.handleError(error));
	}

	private update(): void {
		this.service.update(this.entity).subscribe((entity: User) => {
			let index: number = this.entities.findIndex((element: User) => element.id === entity.id);
			this.entities.splice(index, 1, entity);

			this.showDialog = false;
		}, (error: any) => super.handleError(error));
	}

	private confirmDeleteAction(entity: User): void {
		super.confirmAction(() => {
			this.service.delete(entity.id).subscribe(() => {
				let index: number = this.entities.findIndex((element: User) => element.id === entity.id);
				this.entities.splice(index, 1);
				this.tableTotalRecords--;
			}, (error: any) => super.handleError(error));
		});
	}

	private get(entity: User): void {
		this.service.get(entity.id).subscribe((entity: User) => {
			this.entity = entity;
		}, (error: any) => super.handleError(error));
	}

	private getList(): void {
		this.loading = true;

		this.service.getCount(this.filter).subscribe((count: number) => {
			this.tableTotalRecords = count;
		}, (error: any) => super.handleError(error));

		this.service.getList(this.filter).subscribe((entities: User[]) => {
			this.entities = entities;
			this.loading = false;
		}, (error: any) => super.handleError(error));
	}
}