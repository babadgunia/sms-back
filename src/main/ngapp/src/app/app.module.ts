import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {BaseRequestOptions, HttpModule} from "@angular/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// services
import {AuthenticationService} from "./service/authentication.service";
import {HeroService} from "./service/hero.service";
import {UserService} from "./service/user.service";
// utils
import {AppRoutingModule} from "./app-routing.model";
import {CanActivateAuthGuard} from "./utils/can-activate.authguard";
// components
import {AppComponent} from "./app.component";
import {LoginComponent} from "./component/login/login.component";
import {HomepageComponent} from "./component/homepage/homepage.component";
import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {HeroesComponent} from "./component/heroes/heroes.component";
import {HeroDetailComponent} from "./component/hero-detail/hero-detail.component";
import {SearchComponent} from "./component/search/search.component";
import {UsersComponent} from "./component/users/users.component";
import {ButtonModule, DataTableModule, SharedModule, DialogModule, InputTextModule} from "primeng/primeng";

@NgModule({
	declarations: [
		AppComponent,
		LoginComponent,
		HeroDetailComponent,
		HeroesComponent,
		DashboardComponent,
		SearchComponent,
		HomepageComponent,
		UsersComponent
	],
	imports: [
		BrowserModule,
		FormsModule,
		HttpModule,
		AppRoutingModule,
		DataTableModule,
		SharedModule,
		ButtonModule,
		DialogModule,
		BrowserAnimationsModule,
		InputTextModule
	],
	providers: [
		HeroService,
		UserService,
		AuthenticationService,
		BaseRequestOptions,
		CanActivateAuthGuard
	],
	bootstrap: [AppComponent]
})

export class AppModule {
}