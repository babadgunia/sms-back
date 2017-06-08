import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {BaseRequestOptions, HttpModule} from "@angular/http";
// services
import {AuthenticationService} from "./service/authentication.service";
import {HeroService} from "./service/hero.service";
// utils
import {AppRoutingModule} from "./heroes-routing.model";
// components
import {AppComponent} from "./app.component";
import {LoginComponent} from "./component/login/login.component";
import {HomepageComponent} from "./component/homepage/homepage.component";
import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {HeroesComponent} from "./component/heroes/heroes.component";
import {HeroDetailComponent} from "./component/hero-detail/hero-detail.component";
import {SearchComponent} from "./component/search/search.component";

@NgModule({
	declarations: [
		AppComponent,
		LoginComponent,
		HeroDetailComponent,
		HeroesComponent,
		DashboardComponent,
		SearchComponent,
		HomepageComponent
	],
	imports: [
		BrowserModule,
		FormsModule,
		HttpModule,
		AppRoutingModule
	],
	providers: [
		HeroService,
		AuthenticationService,
		BaseRequestOptions
	],
	bootstrap: [AppComponent]
})

export class AppModule {
}