import {Component, OnInit} from "@angular/core";

import {User} from "../../model/user";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {ActivatedRoute} from "@angular/router";

@Component({
	selector: 'users',
	templateUrl: './users.component.html',
	styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

	users: User[];

	searchUser: User;

	constructor(private router: Router, private route: ActivatedRoute, private userService: UserService) {
	}

	ngOnInit() {
		this.loadUsers('', 0);
	}

	loadUsers(name: string, id: number): void {
		this.userService.getList(name, id).then(users => this.users = users, error => {
			this.router.navigate(['login']);
			console.error('An error occurred in dashboard component, navigating to login: ', error);
		});
	}

	delete(id: number): void {
		this.userService.delete(id).then(() => {

		});
	}
}