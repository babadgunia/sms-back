import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../service/authentication.service";

@Component({
	moduleId: module.id,
	templateUrl: 'login.component.html',
	styleUrls: ['login.component.css']
})

export class LoginComponent implements OnInit {

	model: any = {};

	loading = false;

	error = '';

	constructor(private router: Router, private authenticationService: AuthenticationService) {
	}

	ngOnInit() {
		AuthenticationService.logout();
	}

	login() {
		this.loading = true;

		this.authenticationService.login(this.model.username, this.model.password)
			.subscribe(result => {
				if (result === true) {
					this.router.navigate(['homepage']);
				} else {
					this.loading = false;
					this.error = 'Username or password is incorrect';
				}
			}, error => {
				this.loading = false;
				this.error = error;
			});
	}
}