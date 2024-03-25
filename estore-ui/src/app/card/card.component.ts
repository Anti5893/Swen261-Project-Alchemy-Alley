import { Component, Input, OnChanges, SimpleChanges } from "@angular/core";

import { Product } from "../product";
import { CredentialsService } from "../credentials.service";
import { UserService } from "../user.service";

@Component({
	selector: "app-card",
	templateUrl: "./card.component.html",
	styleUrl: "./card.component.css",
})
export class CardComponent {
	@Input({ required: true }) product!: Product;
	@Input() ignoreClick: boolean = false;

	constructor(private credentialsService: CredentialsService, private userService: UserService) {}

	ngOnInit(): void {
		this.formClasses();
	}

	formClasses(): string {
		var classes = "";
		if (this.isInCart()) {
			classes += "card-selected ";
		}
		if (this.isUnlocked()) {
			classes += "card-unlocked ";
		}
		if (!this.ignoreClick && this.maxCartSize() && !this.isInCart()) {
			classes += "card-blocked ";
		}
		classes += "card-" + this.product.type.toLowerCase();
		return classes;
	}

	isUnlocked(): boolean {
		const isUnlocked = this.credentialsService.getUser()?.unlocked.includes(this.product.id);
		if (isUnlocked !== undefined) {
			return isUnlocked;
		}
		return false;
	}

	isInCart(): boolean {
		const curUser = this.credentialsService.getUser();
		const curCart = curUser!.cart;
		return curCart?.includes(this.product.id);
	}

	maxCartSize(): boolean {
		const curUser = this.credentialsService.getUser();
		let curCart = curUser?.cart;
		if (curCart) {
			return curCart.length >= 2;
		} else {
			return false;
		}
	}

	toggleCartStatus(): void {
		if (this.ignoreClick) {
			return;
		}
		if (this.isInCart()) {
			this.removeFromCart();
			return;
		}
		if (this.maxCartSize()) {
			return;
		}
		this.addToCart();
	}

	addToCart(): void {
		let curUser = this.credentialsService.getUser();
		if (curUser) {
			if (!curUser.cart) {
				curUser.cart = [];
			}

			curUser.cart.push(this.product.id);

			this.credentialsService.storeCurrentUser({ ...curUser });
			this.userService.updateUser(curUser).subscribe();
		}
	}

	removeFromCart(): void {
		let curUser = this.credentialsService.getUser();

		if (curUser && curUser.cart) {
			curUser.cart = curUser.cart.filter((id) => id !== this.product.id);
			this.credentialsService.storeCurrentUser({ ...curUser });

			this.userService.updateUser(curUser).subscribe({});
		}
	}
}
