import {Component} from "@angular/core";
import {Todo} from "./model/todo";
import {Hero} from "./model/hero";
import {TodoDataService} from "./service/todo-data.service";

const HEROES: Hero[] = [
	{id: 1, name: 'Mr. Nice'},
	{id: 2, name: 'Narco'},
	{id: 3, name: 'Bombasto'},
	{id: 4, name: 'Celeritas'},
	{id: 5, name: 'Magneta'},
	{id: 6, name: 'RubberMan'},
	{id: 7, name: 'Dynama'},
	{id: 8, name: 'Dr IQ'},
	{id: 9, name: 'Magma'},
	{id: 10, name: 'Tornado'}
];

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
	providers: []
})
export class AppComponent {

	title = 'Tour of Heroes';

	selectedHero: Hero;

	heroes = HEROES;

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

	onSelect(hero: Hero): void {
		this.selectedHero = hero;
	}
}