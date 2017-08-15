// angular
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {BaseRequestOptions, HttpModule} from "@angular/http";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
// utils
import {AppRoutingModule} from "./app-routing.model";
import {CanActivateAuthGuard} from "./utils/can-activate.authguard";
import {INJECTABLE_CONSTANTS} from "./utils/injectable-constants";
// services
import {AuthenticationService} from "./service/authentication.service";
import {HeroService} from "./service/hero.service";
import {TranslationService} from "./service/translation.service";
import {UserService} from "./service/user.service";
// components
import {AppComponent} from "./app.component";
import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {HeroDetailComponent} from "./component/hero-detail/hero-detail.component";
import {HeroesComponent} from "./component/heroes/heroes.component";
import {LoginComponent} from "./component/login/login.component";
import {SearchComponent} from "./component/search/search.component";
import {UsersComponent} from "./component/users/users.component";
// primeNG
import {ButtonModule} from "primeng/components/button/button";
import {DataTableModule} from "primeng/components/datatable/datatable";
import {DialogModule} from "primeng/components/dialog/dialog";
import {InputTextModule} from "primeng/components/inputtext/inputtext";
import {SharedModule} from "primeng/components/common/shared";

@NgModule({
	declarations: [
		// components
		AppComponent,
		DashboardComponent,
		HeroDetailComponent,
		HeroesComponent,
		LoginComponent,
		SearchComponent,
		UsersComponent
	],
	imports: [
		// angular
		FormsModule,
		HttpModule,
		BrowserModule,
		BrowserAnimationsModule,
		// utils
		AppRoutingModule,
		// primeNG
		ButtonModule,
		DataTableModule,
		DialogModule,
		InputTextModule,
		SharedModule
	],
	providers: [
		// angular
		BaseRequestOptions,
		// utils
		CanActivateAuthGuard,
		INJECTABLE_CONSTANTS,
		// services
		AuthenticationService,
		HeroService,
		TranslationService,
		UserService
	],
	bootstrap: [AppComponent]
})

export class AppModule {}