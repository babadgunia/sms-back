// angular > core
import {Component, OnInit} from '@angular/core';
// model > entity
import {Permission} from '../../model/entity/permission';
import {UserGroup} from '../../model/entity/user-group';
// model > filter
import {UserGroupFilter} from '../../model/filter/user-group-filter';
// model > enum
import {PermissionGroupType} from '../../model/enum/permission-group-type.enum';
import {PermissionType} from '../../model/enum/permission-type.enum';
// component
import {AbstractComponent} from '../abstract-component';
// service
import {UserGroupService} from '../../service/user-group.service';
// util
import {SYSTEM_PERMISSIONS} from '../../util/system-permissions';
import {SystemPermission} from '../../util/system-permission';
import {isNullOrUndefined} from 'util';
import {Utils} from '../../util/utils';
// primeng > model
import {LazyLoadEvent} from 'primeng/components/common/lazyloadevent';
import {TreeNode} from 'primeng/components/common/treenode';
// primeng > service
import {ConfirmationService} from 'primeng/components/common/confirmationservice';
import {MessageService} from 'primeng/components/common/messageservice';

@Component({
	selector: 'user-groups',
	templateUrl: './user-groups.component.html',
	styleUrls: ['./user-groups.component.css']
})
export class UserGroupsComponent extends AbstractComponent implements OnInit {

	private entities: UserGroup[];

	private entity: UserGroup = new UserGroup();

	private filter: UserGroupFilter;

	// tree fields

	private permissions: TreeNode[];

	private selectedPermissions: TreeNode[];

	public constructor(private service: UserGroupService, confirmationService: ConfirmationService, messageService: MessageService) {
		super(confirmationService, messageService);
	}

	public ngOnInit(): void {
		this.permissions = [];

		SYSTEM_PERMISSIONS.forEach((systemPermission: SystemPermission) => {
			let children: TreeNode[] = [];

			systemPermission.types.forEach((permissionType: PermissionType) => {
				let permissionTypeNode: TreeNode = {
					label: PermissionType[permissionType],
					data: PermissionType[permissionType]
				};

				children.push(permissionTypeNode);
			});

			let permissionGroupNode: TreeNode = {
				label: PermissionGroupType[systemPermission.group],
				data: PermissionGroupType[systemPermission.group],
				expanded: true,
				children: children
			};

			this.permissions.push(permissionGroupNode);
		});
	}

	private resetFilter(idField: HTMLInputElement, nameField: HTMLInputElement): void {
		idField.value = '';
		nameField.value = '';

		this.filter = {};
	}

	private initFilter(id: number, name: string): void {
		this.filter = {};
		super.initPagingFilter(this.filter);
		this.initFilterFields(id, name);
	}

	private initFilterFields(id: number, name: string): void {
		this.filter.id = id;
		this.filter.name = name;
	}

	private initTableFilter(event: LazyLoadEvent, id: number, name: string): void {
		this.filter = {};
		super.initLazyPagingFilter(this.filter, event);
		this.initFilterFields(id, name);
	}

	private initAdd(): void {
		this.entity = new UserGroup();

		this.selectedPermissions = [];
	}

	private save(): void {
		if (!this.isValidEntity()) {
			return;
		}

		this.initSelectedPermissions();

		if (this.isAdd) {
			this.add();
		} else {
			this.update();
		}
	}

	private isValidEntity(): boolean {
		if (Utils.isBlank(this.entity.name)) {
			super.showErrorMessage('CANNOT_BE_EMPTY', this.getMessage('NAME'));

			return false;
		}

		return true;
	}

	private initSelectedPermissions(): void {
		let selectedPermissionsMap: Map<string, string[]> = new Map<string, string[]>();

		this.selectedPermissions.forEach((node: TreeNode) => {
			if (isNullOrUndefined(node.parent)) {
				if (isNullOrUndefined(selectedPermissionsMap.get(node.label))) {
					selectedPermissionsMap.set(node.label, []);
				}
			} else {
				if (isNullOrUndefined(selectedPermissionsMap.get(node.parent.label))) {
					selectedPermissionsMap.set(node.parent.label, []);
				}
				selectedPermissionsMap.get(node.parent.label).push(node.label);
			}
		});

		selectedPermissionsMap.forEach((value: string[], key: string) => {
			let permission: Permission = new Permission();

			permission.permissionGroup = key;
			permission.permissionTypes = value;

			this.entity.permissions.push(permission);
		});
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

			this.selectedPermissions = [];

			this.entity.permissions.forEach((permission: Permission) => {
				this.permissions.forEach((permissionGroupNode: TreeNode) => {
					if (permission.permissionGroup === permissionGroupNode.label) {
						permission.permissionTypes.forEach((permissionType: string) => {
							permissionGroupNode.children.forEach((permissionTypeNode: TreeNode) => {
								if (permissionType === permissionTypeNode.label) {
									this.selectedPermissions.push(permissionTypeNode);
								}
							});
						});
					}
				});
			});
		}, (error: any) => super.handleError(error));
	}

	private getList(): void {
		this.loading = true;

		this.service.getCount(this.filter).subscribe((count: number) => {
			this.tableTotalRecords = count;
		}, (error: any) => super.handleError(error));

		this.service.getList(this.filter).subscribe((entities: UserGroup[]) => {
			this.entities = entities;
			this.loading = false;
		}, (error: any) => super.handleError(error));
	}
}