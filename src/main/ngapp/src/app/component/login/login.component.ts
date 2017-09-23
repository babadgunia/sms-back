import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../service/authentication.service";
import {AuthUtils} from "../../util/auth-utils";

@Component({
	moduleId: module.id,
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
				AuthUtils.login(this.model.username, token);

				this.router.navigate(['users']);
			} else {
				this.loading = false;
				this.error = 'Username or password is incorrect';
			}
		}, error => {
			this.loading = false;
			this.error = error;
			console.error(error);
		});
	}

}