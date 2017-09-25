// angular > core
import {Component, OnInit} from "@angular/core";
// model > entity
import {Permission} from "../../model/entity/permission";
import {UserGroup} from "../../model/entity/user-group";
// model > filter
import {UserGroupFilter} from "../../model/filter/user-group-filter";
// model > enum
// component
import {AbstractComponent} from "../abstract-component";
// service
import {UserGroupService} from "../../service/user-group.service";
// util
import {isNullOrUndefined} from "util";
import {Utils} from "../../util/utils";
// primeng > model
import {LazyLoadEvent} from "primeng/components/common/lazyloadevent";
import {TreeNode} from "primeng/components/common/treenode";
// primeng > component
import {DataTable} from "primeng/components/datatable/datatable";
// primeng > service
import {ConfirmationService} from "primeng/components/common/confirmationservice";
import {MessageService} from "primeng/components/common/messageservice";

// rxjs

@Component({
	selector: 'user-groups',
	templateUrl: './user-groups.component.html',
	styleUrls: ['./user-groups.component.css']
})
export class UserGroupsComponent extends AbstractComponent implements OnInit {

	private entities: UserGroup[];

	private entity: UserGroup = new UserGroup();

	private filter: UserGroupFilter = {};

	private permissions: TreeNode[] = [];

	private selectedPermissions: TreeNode[] = [];

	public constructor(private service: UserGroupService, confirmationService: ConfirmationService, messageService: MessageService) {
		super(confirmationService, messageService);
	}

	public ngOnInit(): void {
		this.permissionGroupTypes.forEach((permissionGroupType: string) => {
			let children: TreeNode[] = [];

			this.permissionTypes.forEach((permissionType: string) => {
				let permissionNode: TreeNode = {
					label: permissionType,
					data: permissionType
				};

				children.push(permissionNode);
			});

			let permissionGroupNode: TreeNode = {
				label: permissionGroupType,
				data: permissionGroupType,
				children: children,
				expanded: true
			};

			this.permissions.push(permissionGroupNode);
		});
	}

	private resetFilters(table: DataTable, idField: HTMLInputElement, nameField: HTMLInputElement): void {
		this.resetCustomFilter(idField, nameField);
		this.resetTableFilter();

		this.filter = {};

		table.reset();
	}

	private resetCustomFilter(idField: HTMLInputElement, nameField: HTMLInputElement): void {
		idField.value = '';
		nameField.value = '';
	}

	private resetTableFilter(): void {}

	private initCustomFilter(table: DataTable, id: number, name: string): void {
		this.resetTableFilter();

		this.filter = {};

		table.reset();

		super.initPagingFilter(this.filter);

		this.filter.id = id;
		this.filter.name = name;
	}

	private initTableFilter(event: LazyLoadEvent): void {
		this.filter = {};

		super.initLazyPagingFilter(this.filter, event);

		if (!isNullOrUndefined(event.filters.id)) {
			this.filter.id = event.filters.id.value;
		}
		if (!isNullOrUndefined(event.filters.name)) {
			this.filter.name = event.filters.name.value;
		}
	}

	private initAdd(): void {
		this.entity = new UserGroup();
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
		if (Utils.isBlank(this.entity.name)) {
			super.showErrorMessage('CANNOT_BE_NULL', this.getMessage('NAME'));

			return false;
		}

		return true;
	}

	private add(): void {
		this.service.add(this.entity).subscribe((entity: UserGroup) => {
			this.entities.push(entity);
			this.tableTotalRecords++;

			this.showDialog = false;
		}, (error: any) => super.handleError(error));
	}

	private update(): void {
		this.service.update(this.entity).subscribe((entity: UserGroup) => {
			let index: number = this.entities.findIndex((element: UserGroup) => element.id === entity.id);
			this.entities.splice(index, 1, entity);

			this.showDialog = false;
		}, (error: any) => super.handleError(error));
	}

	private confirmDeleteAction(entity: UserGroup): void {
		super.confirmAction(() => {
			this.service.delete(entity.id).subscribe(() => {
				let index: number = this.entities.findIndex((element: UserGroup) => element.id === entity.id);
				this.entities.splice(index, 1);
				this.tableTotalRecords--;
			}, (error: any) => super.handleError(error));
		});
	}

	private get(entity: UserGroup): void {
		this.service.get(entity.id).subscribe((entity: UserGroup) => {
			this.entity = entity;

			this.entity.permissions.forEach((permission: Permission) => {
				let children: TreeNode[] = [];

				permission.permissionTypes.forEach((permissionType: string) => {
					let permissionNode: TreeNode = {
						label: permissionType,
						data: permissionType
					};

					children.push(permissionNode);
				});

				let permissionGroupNode: TreeNode = {
					label: permission.permissionGroup,
					data: permission.permissionGroup,
					children: children,
					expanded: true
				};

				this.selectedPermissions.push(permissionGroupNode);
			});
		}, (error: any) => super.handleError(error));
	}

	private getList(): void {
		this.service.getCount(this.filter).subscribe((count: number) => {
			this.tableTotalRecords = count;
		}, (error: any) => super.handleError(error));

		this.service.getList(this.filter).subscribe((entities: UserGroup[]) => {
			this.entities = entities;
		}, (error: any) => super.handleError(error));
	}
}