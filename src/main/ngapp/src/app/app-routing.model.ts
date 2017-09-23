// angular > core
import {NgModule} from "@angular/core";
// angular > router
import {RouterModule, Routes} from "@angular/router";
// component
import {LoginComponent} from "./component/login/login.component";
import {ForgotPasswordComponent} from "./component/login/forgotPassword.component";
import {UserGroupsComponent} from "./component/userGroups/userGroups.component";
import {UsersComponent} from "./component/users/users.component";
// util
import {CanActivateAuthGuard} from "./util/can-activate.authguard";

const routes: Routes = [
	{path: '', redirectTo: 'userGroups', pathMatch: 'full'},
	{path: 'login', component: LoginComponent},
	{path: 'forgotPassword', component: ForgotPasswordComponent},
	{path: 'users', component: UsersComponent, canActivate: [CanActivateAuthGuard]},
	{path: 'userGroups', component: UserGroupsComponent, canActivate: [CanActivateAuthGuard]},
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {}