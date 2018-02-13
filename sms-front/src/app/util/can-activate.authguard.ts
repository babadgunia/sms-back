// angular > core
import {Injectable} from '@angular/core';
// angular > router
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
// service
import {AuthenticationService} from '../service/authentication.service';
// util
import {AuthenticationUtils} from './authentication-utils';
import {Utils} from './utils';

@Injectable()
export class CanActivateAuthGuard implements CanActivate {

	constructor(private router: Router, private authService: AuthenticationService) {}

	public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
		if (AuthenticationUtils.isLoggedIn()) {
			return true;
		}

		this.router.navigate(['/login']).then((result: boolean) => {
				return false;
			}, (error: any) => {
				Utils.handleError(error);

				return false;
			}
		)
	}
}