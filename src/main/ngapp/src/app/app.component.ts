// angular > core
import {Component} from "@angular/core";
// angular > router
import {Router} from "@angular/router";
// util
import {AuthUtils} from "./util/auth-utils";

@Component({
	selector: 'app',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
	providers: []
})
export class AppComponent {

	constructor(private router: Router) {}

	logout(): void {
		AuthUtils.logout();
		this.router.navigate(['login']);
	}

	isLoggedIn(): boolean {
		return AuthUtils.isLoggedIn();
	}
}