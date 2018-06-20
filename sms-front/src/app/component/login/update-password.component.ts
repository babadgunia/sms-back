import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import {AuthenticationService} from '../../service/authentication.service';
import {MessageService} from 'primeng/components/common/messageservice';


@Component({
	selector: 'updatePassword',
	templateUrl: 'update-password.component.html',
	styleUrls: ['update-password.component.css']
})

export class UpdatePasswordComponent implements OnInit {

	model: any = {};

	loading = false;

	private token: string;

	private id: string;

	error = '';

	constructor(private router: Router, private activatedRoute: ActivatedRoute, private authenticationService: AuthenticationService, private messageService: MessageService) {}

	ngOnInit() {
		this.activatedRoute.queryParams.subscribe((params: Params) => {
			this.token =  params['token'];
			this.id =  params['id'];
		});
	}

	public savePassword() {
		if (this.model.password !== this.model.repeatPassword) {
			this.messageService.add({severity: 'error', summary: 'Passwords dont match'});
			return;
		}
		this.loading = true;

		this.authenticationService.savePassword(this.model.password, this.token, this.id).subscribe(() => {
			this.messageService.add({severity: 'success', summary: 'Password successfully changed'});
		}, (error: any) => this.messageService.add({severity: 'error', summary: 'Error while saving password'}));

		this.loading = false;
	}

}
