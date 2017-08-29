import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";

import {AuthenticationService} from "../service/authentication.service";

@Injectable()
export class CanActivateAuthGuard implements CanActivate {

	constructor(private router: Router, private authService: AuthenticationService) {}

	canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
		if (AuthenticationService.isLoggedIn()) {
			return true;
		}

		this.router.navigate(['/login']);

		return false;
	}
}