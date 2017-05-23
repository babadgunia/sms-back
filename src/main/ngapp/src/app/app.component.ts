import {Component} from "@angular/core";
import {Todo} from "./model/todo";
import {TodoDataService} from "./service/todo-data.service";

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
	providers: []
})
export class AppComponent {

	constructor(private todoDataService: TodoDataService) {
	}

	onAddTodo(todo: Todo) {
		this.todoDataService.addTodo(todo);
	}

	onToggleTodoComplete(todo: Todo) {
		this.todoDataService.toggleTodoComplete(todo);
	}

	onRemoveTodo(todo: Todo) {
		this.todoDataService.deleteTodoById(todo.id);
	}

	get todos() {
		return this.todoDataService.getAllTodos();
	}
}