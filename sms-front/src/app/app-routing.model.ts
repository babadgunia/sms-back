// angular > core
import {NgModule} from '@angular/core';
// angular > router
import {RouterModule, Routes} from '@angular/router';
// component
import {ForgotPasswordComponent} from './component/login/forgot-password.component';
import {UpdatePasswordComponent} from "./component/login/update-password.component";
import {LoginComponent} from './component/login/login.component';
import {TextsComponent} from './component/texts/texts.component';
import {UserGroupsComponent} from './component/user-groups/user-groups.component';
import {UsersComponent} from './component/users/users.component';
// util
import {CanActivateAuthGuard} from './util/can-activate.authguard';

const routes: Routes = [
	{path: '', redirectTo: 'userGroups', pathMatch: 'full'},
	{path: 'forgotPassword', component: ForgotPasswordComponent},
	{path: 'updatePassword', component: UpdatePasswordComponent},
	{path: 'login', component: LoginComponent},
	{path: 'texts', component: TextsComponent, canActivate: [CanActivateAuthGuard]},
	{path: 'userGroups', component: UserGroupsComponent, canActivate: [CanActivateAuthGuard]},
	{path: 'users', component: UsersComponent, canActivate: [CanActivateAuthGuard]},
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {}