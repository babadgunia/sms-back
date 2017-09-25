// angular > core
import {NgModule} from "@angular/core";
// angular > router
import {RouterModule, Routes} from "@angular/router";
// component
import {ForgotPasswordComponent} from "./component/login/forgot-password.component";
import {LoginComponent} from "./component/login/login.component";
import {UserGroupsComponent} from "./component/user-groups/user-groups.component";
import {UsersComponent} from "./component/users/users.component";
// util
import {CanActivateAuthGuard} from "./util/can-activate.authguard";

const routes: Routes = [
	{path: '', redirectTo: 'userGroups', pathMatch: 'full'},
	{path: 'forgotPassword', component: ForgotPasswordComponent},
	{path: 'login', component: LoginComponent},
	{path: 'userGroups', component: UserGroupsComponent, canActivate: [CanActivateAuthGuard]},
	{path: 'users', component: UsersComponent, canActivate: [CanActivateAuthGuard]},
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {}