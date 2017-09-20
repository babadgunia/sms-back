// angular > core
import {Component} from "@angular/core";
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

	public constructor(private service: UserGroupService, confirmationService: ConfirmationService, messageService: MessageService) {
		super(confirmationService, messageService);
	}

}
