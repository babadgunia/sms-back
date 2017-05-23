import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AppComponent} from "./app.component";
import {TodoDataService} from "./service/todo-data.service";
import {TodoListHeaderComponent} from "./component/todo-list-header/todo-list-header.component";
import {TodoListComponent} from "./component/todo-list/todo-list.component";
import {TodoListItemComponent} from "./component/todo-list-item/todo-list-item.component";
import {TodoListFooterComponent} from "./component/todo-list-footer/todo-list-footer.component";

@NgModule({
	declarations: [
		AppComponent,
		TodoListHeaderComponent,
		TodoListComponent,
		TodoListItemComponent,
		TodoListFooterComponent
	],
	imports: [
		BrowserModule,
		FormsModule,
		HttpModule
	],
	providers: [TodoDataService],
	bootstrap: [AppComponent]
})
export class AppModule {
}