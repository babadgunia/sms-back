import {Component, OnInit} from "@angular/core";

@Component({
	selector: 'app',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
	providers: []
})
export class AppComponent implements OnInit {

	title = 'Tour of Heroes';

	constructor() {
	}

	ngOnInit(): void {
	}
}