// angular
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {NgModule} from "@angular/core";
// utils
import {AppRoutingModule} from "./app-routing.model";
import {CanActivateAuthGuard} from "./utils/can-activate.authguard";
import {INJECTABLE_CONSTANTS} from "./utils/injectable-constants";
// services
import {AuthenticationService} from "./service/authentication.service";
import {HeroService} from "./service/hero.service";
import {UserService} from "./service/user.service";
// primeng services
import {ConfirmationService} from 'primeng/components/common/confirmationservice';
// components
import {AppComponent} from "./app.component";
import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {HeroDetailComponent} from "./component/hero-detail/hero-detail.component";
import {HeroesComponent} from "./component/heroes/heroes.component";
import {LoginComponent} from "./component/login/login.component";
import {SearchComponent} from "./component/search/search.component";
import {UsersComponent} from "./component/users/users.component";
// primeng components
import {ButtonModule} from "primeng/components/button/button";
import {ConfirmDialogModule} from 'primeng/components/confirmdialog/confirmdialog';
import {DataTableModule} from "primeng/components/datatable/datatable";
import {DialogModule} from "primeng/components/dialog/dialog";
import {InputTextModule} from "primeng/components/inputtext/inputtext";

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
		BrowserModule,
		BrowserAnimationsModule,
		FormsModule,
		HttpModule,
		// utils
		AppRoutingModule,
		// primeNG
		ButtonModule,
		ConfirmDialogModule,
		DataTableModule,
		DialogModule,
		InputTextModule
	],
	providers: [
		// utils
		CanActivateAuthGuard,
		INJECTABLE_CONSTANTS,
		// services
		AuthenticationService,
		HeroService,
		UserService,
		// primeng services
		ConfirmationService
	],
	bootstrap: [AppComponent]
})

export class AppModule {}