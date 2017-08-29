// angular > core
import {Component} from "@angular/core";
// angular > router
import {Router} from "@angular/router";
// service
import {AuthenticationService} from "./service/authentication.service";

@Component({
	selector: 'app',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
	providers: []
})
export class AppComponent {

	constructor(private router: Router) {}

	logout(): void {
		AuthenticationService.logout();
		this.router.navigate(['login']);
	}

	isLoggedIn(): boolean {
		return AuthenticationService.isLoggedIn();
	}
}