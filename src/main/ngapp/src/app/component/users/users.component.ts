import {Component, OnInit} from "@angular/core";

import {User} from "../../model/user";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";

@Component({
	selector: 'users',
	templateUrl: './users.component.html',
	styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

	users: User[];

	selectedUser: User;

	constructor(private router: Router, private userService: UserService) {
	}

	ngOnInit() {
		this.userService.getList().then(users => this.users = users, error => {
			this.router.navigate(['login']);
			console.error('An error occurred in dashboard component, navigating to login: ', error);
		});
	}

	onSelect(user: User): void {
		this.selectedUser = user;
	}

	gotoDetail(): void {
		this.router.navigate(['/detail', this.selectedUser.id]);
	}

}