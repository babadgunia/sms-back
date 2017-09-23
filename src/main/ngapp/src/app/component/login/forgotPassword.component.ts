import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../service/authentication.service";
import {AuthUtils} from "../../util/auth-utils";
import {MessageService} from "primeng/components/common/messageservice";
import {Message} from 'primeng/components/common/api';


@Component({
	moduleId: module.id,
	templateUrl: 'forgotPassword.component.html',
	styleUrls: ['forgotPassword.component.css']
})

export class ForgotPasswordComponent {

	model: any = {};

	loading = false;

	error = '';

	constructor(private router: Router, private authenticationService: AuthenticationService, private messageService: MessageService) {}

	passwordRestorationLink() {
		this.loading = true;

		this.messageService.add({severity:'success', summary:'Password reset link was successfully sent to your mail' + this.model.emailUsername});

		this.loading = false;
	}

}