import { Product } from "./../product";
import { UserService } from "./../user.service";
import { Component } from "@angular/core";

import { CredentialsService } from "../credentials.service";
import { ProductService } from "../product.service";

@Component({
	selector: "app-cart",
	templateUrl: "./cart.component.html",
	styleUrls: ["./cart.component.css"],
})
export class CartComponent {
	products: Product[] = [];
	purchased: boolean = false;
	unlocked: Product | null = null;

	constructor(
		private credentialService: CredentialsService,
		private productService: ProductService,
		private userService: UserService
	) {}

	ngOnInit(): void {
		this.getProducts();
	}

	getProducts(): void {
		this.products = [];
		let cart = this.credentialService.getUser()?.cart;
		cart?.forEach((productId) => {
			this.productService.getProduct(productId).subscribe(
				(response) => {
					if (response.body) {
						this.products.push(response.body);
					}
				},
				(error) => {
					console.error(`Error fetching product with ID ${productId}:`, error);
				}
			);
		});
	}

	getUnlocked(): Product {
		return this.unlocked!;
	}

	getTotalPrice(): number {
		return this.products.reduce((total, product) => total + product.price, 0);
	}

	handlePurchase(): void {
		let curUser = this.credentialService.getUser();
		if (curUser) {
			this.userService.doCraft(curUser).subscribe(
				(response) => {
					this.unlocked = response.body!;

					curUser = this.credentialService.getUser()!;
					curUser.unlocked.push(this.unlocked.id);
					this.credentialService.storeCurrentUser({ ...curUser });
				},
				(error) => {
					this.unlocked = null;
				}
			);

			curUser = this.credentialService.getUser()!;
			this.purchased = true;
			this.products = [];
			curUser.cart = [];
			this.credentialService.storeCurrentUser({ ...curUser });
		}
	}

	isPurchased(): boolean {
		return this.purchased;
	}

	hasInvalidFields(
		name: HTMLInputElement,
		card: HTMLInputElement,
		exp: HTMLInputElement,
		ccv: HTMLInputElement
	): boolean {
		return (
			name.value.trim().length === 0 ||
			card.value.trim().length === 0 ||
			exp.value.trim().length === 0 ||
			ccv.value.trim().length === 0
		);
	}

	isInStock(product: Product) {
		if (this.products.at(0)?.id === this.products.at(1)?.id) {
			return product.quantity > 1;
		} else {
			return product.quantity > 0;
		}
	}

	isCartInStock() {
		for (const product of this.products) {
			if (!this.isInStock(product)) {
				return false;
			}
		}
		return true;
	}
}
