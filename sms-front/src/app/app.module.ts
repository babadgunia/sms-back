// angular > core
import {NgModule} from '@angular/core';
// angular > forms
import {FormsModule} from '@angular/forms';
// angular > http
import {HttpClientModule} from '@angular/common/http';
// angular > platform browser
import {BrowserModule} from '@angular/platform-browser';
// angular > platform browser > animations
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
// component
import {AppComponent} from './component/app/app.component';
import {ForgotPasswordComponent} from './component/login/forgot-password.component'
import {UpdatePasswordComponent} from "./component/login/update-password.component";
import {LoginComponent} from './component/login/login.component';
import {TextsComponent} from './component/texts/texts.component';
import {UserGroupsComponent} from './component/user-groups/user-groups.component';
import {UsersComponent} from './component/users/users.component';
// service
import {AuthenticationService} from './service/authentication.service';
import {TextService} from './service/text.service';
import {UserGroupService} from './service/user-group.service';
import {UserService} from './service/user.service';
// routing
import {AppRoutingModule} from './app-routing.model';
// util
import {CanActivateAuthGuard} from './util/can-activate.authguard';
import {INJECTABLE_CONSTANTS} from './util/injectable-constants';
// primeng > component
import {ButtonModule} from 'primeng/components/button/button';
import {ConfirmDialogModule} from 'primeng/components/confirmdialog/confirmdialog';
import {DataTableModule} from 'primeng/components/datatable/datatable';
import {DialogModule} from 'primeng/components/dialog/dialog';
import {DropdownModule} from 'primeng/components/dropdown/dropdown';
import {GrowlModule} from 'primeng/components/growl/growl';
import {InputTextModule} from 'primeng/components/inputtext/inputtext';
import {ToolbarModule} from 'primeng/components/toolbar/toolbar';
import {TreeModule} from 'primeng/components/tree/tree';
// primeng > service
import {ConfirmationService} from 'primeng/components/common/confirmationservice';
import {MessageService} from 'primeng/components/common/messageservice';

@NgModule({
	declarations: [
		// component
		AppComponent,
		ForgotPasswordComponent,
		UpdatePasswordComponent,
		LoginComponent,
		TextsComponent,
		UserGroupsComponent,
		UsersComponent
	],
	imports: [
		// angular > forms
		FormsModule,
		// angular > http
		HttpClientModule,
		// angular > platform browser
		BrowserModule,
		// angular > platform browser > animations
		BrowserAnimationsModule,
		// routing
		AppRoutingModule,
		// primeng > component
		ButtonModule,
		ConfirmDialogModule,
		DataTableModule,
		DialogModule,
		DropdownModule,
		GrowlModule,
		InputTextModule,
		ToolbarModule,
		TreeModule
	],
	providers: [
		// service
		AuthenticationService,
		TextService,
		UserGroupService,
		UserService,
		// util
		CanActivateAuthGuard,
		INJECTABLE_CONSTANTS,
		// primeng > service
		ConfirmationService,
		MessageService
	],
	bootstrap: [AppComponent]
})
export class AppModule {}