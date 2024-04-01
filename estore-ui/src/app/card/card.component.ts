import { Component, Input } from "@angular/core";

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
	@Input() fitToSize: boolean = false;

	private colorMap = {
		FIRE: "#ff4800",
		WATER: "#0099ff",
		AIR: "#f6aeff",
		EARTH: "#e58900",
		ENERGY: "#e100ff",
	};
	lockedImageUrl = "https://i.imgur.com/qPuLjji.png";
	placeholderFireballImageUrl =
		"https://static.vecteezy.com/system/resources/previews/021/698/212/original/ball-of-fire-glowing-magma-sphere-fireball-large-sphere-of-red-energy-fantasy-game-spell-icon-generative-ai-png.png";

	constructor(private credentialsService: CredentialsService, private userService: UserService) {}

	getColor(): string {
		if (this.isUnlocked()) {
			const color = this.colorMap[this.product.type];
			if (color !== undefined) {
				return color;
			}
			return "lightgrey";
		}
		return "";
	}

	// getColorStyle(): object {
	// 	if (this.isUnlocked()) {
	// 		const color = this.colorMap[this.product.type];
	// 		if (color !== undefined) {
	// 			return {
	// 				"--card-color": color,
	// 			};
	// 		}
	// 	}
	// 	return {};
	// }

	formClasses(): string {
		var classes = "";
		if (this.isInCart()) {
			classes += "card-selected ";
		}
		if (this.isUnlocked()) {
			classes += "card-unlocked ";
			// classes += "card-" + this.product.type.toLowerCase() + " ";
		}
		if (!this.ignoreClick && this.maxCartSize() && !this.isInCart() && this.isUnlocked()) {
			classes += "card-blocked ";
		}
		console.log(`${this.product.name}'s classes are going to include ${classes}`);
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
		if (this.ignoreClick || !this.isUnlocked()) {
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
