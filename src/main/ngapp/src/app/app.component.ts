import {Component} from "@angular/core";
import {AuthenticationService} from "./service/authentication.service";
import {Router} from "@angular/router";

@Component({
	selector: 'app',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
	providers: []
})
export class AppComponent {

	constructor(private router: Router) {

	}

	logout (): void{
		AuthenticationService.logout();
		this.router.navigate(['login']);
	}

	isLoggedIn(): boolean {
		return AuthenticationService.isLoggedIn();
	}
}