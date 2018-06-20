import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../service/authentication.service';
import {MessageService} from 'primeng/components/common/messageservice';


@Component({
	selector: 'forgotPassword',
	templateUrl: 'forgot-password.component.html',
	styleUrls: ['forgot-password.component.css']
})

export class ForgotPasswordComponent {

	model: any = {};

	loading = false;

	error = '';

	constructor(private router: Router, private authenticationService: AuthenticationService, private messageService: MessageService) {}

	public passwordRestorationLink() {
		this.loading = true;
		this.authenticationService.resetPassword(this.model.emailUsername).subscribe(() => {
			this.messageService.add({severity: 'success', summary: 'Password reset link was successfully sent to your mail' + this.model.emailUsername});
		}, (error: any) => this.messageService.add({severity: 'error', summary: 'Error while sending password reset link to your mail'}));

		this.loading = false;
	}

}
