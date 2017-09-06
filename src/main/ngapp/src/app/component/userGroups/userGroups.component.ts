// angular > core
import {Component} from "@angular/core";
// model > entity
import {UserGroup} from "../../model/entity/userGroup";
// model > enum
import {LanguageType} from "../../model/enum/language-type.enum";
import {StatusType} from "app/model/enum/status-type.enum";
// component
import {AbstractComponent} from "../abstract-component";
// util
import {isNullOrUndefined} from "util";
// primeng > component
import {DataTable} from "primeng/primeng";
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
