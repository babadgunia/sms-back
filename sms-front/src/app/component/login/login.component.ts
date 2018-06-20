import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../service/authentication.service';
import {AuthenticationUtils} from '../../util/authentication-utils';

@Component({
	selector: 'login',
	templateUrl: 'login.component.html',
	styleUrls: ['login.component.css']
})

export class LoginComponent {

	model: any = {};

	loading = false;

	error = '';

	constructor(private router: Router, private authenticationService: AuthenticationService) {}

	login() {
		this.loading = true;

		this.authenticationService.login(this.model.username, this.model.password).subscribe((response: any) => {
			let token = response.token;
			if (token) {
				AuthenticationUtils.login(this.model.username, token);

				this.router.navigate(['users']);
			} else {
				this.loading = false;
				this.error = 'Username or password is incorrect';
			}
		}, (error: any) => {
			this.loading = false;
			this.error = error;
			console.error(error);
		});
	}
}