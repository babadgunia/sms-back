import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";

import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {HeroesComponent} from "./component/heroes/heroes.component";
import {UsersComponent} from "./component/users/users.component";
import {HeroDetailComponent} from "./component/hero-detail/hero-detail.component";
import {LoginComponent} from "./component/login/login.component";
import {CanActivateAuthGuard} from "./utils/can-activate.authguard";

const routes: Routes = [
	{path: '', redirectTo: 'users', pathMatch: 'full'},
	{path: 'login', component: LoginComponent},
	{path: 'dashboard', component: DashboardComponent, canActivate: [CanActivateAuthGuard]},
	{path: 'heroes', component: HeroesComponent, canActivate: [CanActivateAuthGuard]},
	{path: 'users', component: UsersComponent, canActivate: [CanActivateAuthGuard]},
	{path: 'detail/:id', component: HeroDetailComponent, canActivate: [CanActivateAuthGuard]}
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {
}