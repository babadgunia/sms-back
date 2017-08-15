import {Component, OnInit} from "@angular/core";
import {TranslationService} from "./service/translation.service";

@Component({
	selector: 'app',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
	providers: []
})
export class AppComponent implements OnInit {

	static translations: Map<string, Map<string, string>>;

	constructor(private translationService: TranslationService) {}

	ngOnInit(): void {
		this.translationService.get().then(result => {
			AppComponent.translations = result;
			console.log(AppComponent.translations)
		});
		console.log(AppComponent.translations);
	}

	static getTranslation(key: string): string {
		return this.translations.get('EN').get(key);
	}
}