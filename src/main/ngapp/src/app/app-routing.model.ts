// angular > core
import {NgModule} from "@angular/core";
// angular > router
import {RouterModule, Routes} from "@angular/router";
// component
import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {HeroDetailComponent} from "./component/hero-detail/hero-detail.component";
import {HeroesComponent} from "./component/heroes/heroes.component";
import {LoginComponent} from "./component/login/login.component";
import {UsersComponent} from "./component/users/users.component";
// util
import {CanActivateAuthGuard} from "./util/can-activate.authguard";

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
export class AppRoutingModule {}