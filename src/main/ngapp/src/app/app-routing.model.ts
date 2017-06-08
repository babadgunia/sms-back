import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";

import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {HeroesComponent} from "./component/heroes/heroes.component";
import {HeroDetailComponent} from "./component/hero-detail/hero-detail.component";
import {HomepageComponent} from "./component/homepage/homepage.component";
import {LoginComponent} from "./component/login/login.component";
import {CanActivateAuthGuard} from "./utils/can-activate.authguard";

const routes: Routes = [
	{path: '', redirectTo: 'homepage', pathMatch: 'full'},
	{path: 'login', component: LoginComponent},
	{path: 'homepage', component: HomepageComponent, canActivate: [CanActivateAuthGuard]},
	{path: 'dashboard', component: DashboardComponent, canActivate: [CanActivateAuthGuard]},
	{path: 'heroes', component: HeroesComponent, canActivate: [CanActivateAuthGuard]},
	{path: 'detail/:id', component: HeroDetailComponent, canActivate: [CanActivateAuthGuard]}
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {
}