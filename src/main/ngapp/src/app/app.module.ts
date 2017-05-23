import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {BaseRequestOptions, HttpModule} from "@angular/http";
// services
import {TodoDataService} from "./service/todo-data.service";
import {AlertService} from "./service/alert.service";
import {AuthenticationService} from "./service/authentication.service";
import {UserService} from "./service/user.service";
// components
import {AppComponent} from "./app.component";
import {TodoListHeaderComponent} from "./component/todo-list-header/todo-list-header.component";
import {TodoListComponent} from "./component/todo-list/todo-list.component";
import {TodoListItemComponent} from "./component/todo-list-item/todo-list-item.component";
import {TodoListFooterComponent} from "./component/todo-list-footer/todo-list-footer.component";
import {AlertComponent} from "./component/alert/alert.component";
import {HomeComponent} from "./component/home/home.component";
import {LoginComponent} from "./component/login/login.component";
import {RegisterComponent} from "./component/register/register.component";
// utils
import {AuthGuard} from "./utils/auth.guard";
import {fakeBackendProvider} from "./utils/fake-backend";
import {MockBackend} from "@angular/http/testing";

import {routing} from "./app.routing";

@NgModule({
	declarations: [
		AppComponent,
		TodoListHeaderComponent,
		TodoListComponent,
		TodoListItemComponent,
		TodoListFooterComponent,
		AlertComponent,
		HomeComponent,
		LoginComponent,
		RegisterComponent
	],
	imports: [
		BrowserModule,
		FormsModule,
		HttpModule,
		routing
	],
	providers: [
		TodoDataService,
		AlertService,
		AuthenticationService,
		UserService,
		AuthGuard,
		fakeBackendProvider,
		MockBackend,
		BaseRequestOptions
	],
	bootstrap: [AppComponent]
})

export class AppModule {
}