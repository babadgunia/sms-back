import {RouterModule, Routes} from "@angular/router";

import {HomeComponent} from "./component/home/home.component";
import {LoginComponent} from "./component/login/login.component";
import {RegisterComponent} from "./component/register/register.component";
import {AuthGuard} from "./utils/auth.guard";

const appRoutes: Routes = [
	{path: '', component: HomeComponent, canActivate: [AuthGuard]},
	{path: 'login', component: LoginComponent},
	{path: 'register', component: RegisterComponent},

	// otherwise redirect to home
	{path: '**', redirectTo: ''}
];

export const routing = RouterModule.forRoot(appRoutes);