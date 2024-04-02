import { Component, OnInit } from "@angular/core";
import { CredentialsService } from "../credentials.service";
import { ProductService } from "../product.service";

@Component({
	selector: "app-progress-bar",
	templateUrl: "./progress-bar.component.html",
	styleUrl: "./progress-bar.component.css",
})
export class ProgressBarComponent implements OnInit {
	progress: number = 0;

	constructor(
		private productService: ProductService,
		private credentialsService: CredentialsService
	) {}

	ngOnInit(): void {
		this.udpateProgress();
	}

	udpateProgress(): void {
		const unlocked = this.credentialsService.getUser()?.unlocked;
		this.productService.getProducts().subscribe((productsResponse) => {
			if (productsResponse.body && unlocked !== undefined) {
				this.progress = (unlocked.length / productsResponse.body.length) * 100;
			} else {
				this.progress = 0;
			}
		});
	}

	parsedProgress(): string {
		return parseFloat(this.progress.toString()).toFixed(2);
	}
}
