// angular > core
import {Component} from "@angular/core";
// model > entity
// model > enum
// component
import {AbstractComponent} from "../abstract-component";
// util
// primeng > component
// primeng > service
import {ConfirmationService} from "primeng/components/common/confirmationservice";
import {UserGroupService} from "../../service/userGroup.service";

@Component({
	selector: 'userGroups',
	templateUrl: './userGroups.component.html',
	styleUrls: ['./userGroups.component.css']
})
export class UserGroupsComponent extends AbstractComponent {

	public constructor(private service: UserGroupService, confirmationService: ConfirmationService) {
		super(confirmationService);
	}

}
