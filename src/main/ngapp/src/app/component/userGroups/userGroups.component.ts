// angular > core
import {Component} from "@angular/core";
// model > entity
import {UserGroup} from "../../model/entity/user-group";
// model > filter
import {UserGroupFilter} from "../../model/filter/user-group-filter";
// component
import {AbstractComponent} from "../abstract-component";
// service
import {UserGroupService} from "../../service/userGroup.service";
// primeng > service
import {ConfirmationService} from "primeng/components/common/confirmationservice";
import {MessageService} from "primeng/components/common/messageservice";

@Component({
	selector: 'userGroups',
	templateUrl: './userGroups.component.html',
	styleUrls: ['./userGroups.component.css']
})
export class UserGroupsComponent extends AbstractComponent {

	private userGroups: UserGroup[];

	private userGroup: UserGroup = new UserGroup();

	private filter: UserGroupFilter = {};

	public constructor(private service: UserGroupService, confirmationService: ConfirmationService, messageService: MessageService) {
		super(confirmationService, messageService);
	}
}