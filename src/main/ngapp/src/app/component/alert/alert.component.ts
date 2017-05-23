import {Component} from "@angular/core";

import {AlertService} from "../../service/alert.service";

@Component({
	moduleId: module.id.toString(),
	selector: 'alert',
	templateUrl: 'alert.component.html'
})

export class AlertComponent {
	message: any;

	constructor(private alertService: AlertService) {
	}

	ngOnInit() {
		this.alertService.getMessage().subscribe(message => {
			this.message = message;
		});
	}
}