import { Component, Input, Output, EventEmitter, Directive, OnInit } from "@angular/core";

import { Product } from "../product";
import { CredentialsService } from "../credentials.service";
import { UserService } from "../user.service";

@Component({
	selector: "app-card",
	templateUrl: "./card.component.html",
	styleUrl: "./card.component.css",
})
export class CardComponent implements OnInit {
	@Input({ required: true }) product!: Product;
	@Input({ required: true }) index: number = 0;
	@Input() enableStacking: boolean = true;
	@Input() showQuantity: boolean = true;
	@Input() showOutOfStock: boolean = true;
	@Input() fitToSize: boolean = false;
	@Output() removedEvent: EventEmitter<Product> = new EventEmitter<Product>();

	private colorMap = {
		FIRE: "#ff4800",
		WATER: "#0099ff",
		AIR: "#f6aeff",
		EARTH: "#e58900",
		ENERGY: "#e100ff",
	};
	lockedImageUrl = "https://i.imgur.com/qPuLjji.png";

	constructor(private credentialsService: CredentialsService, private userService: UserService) {}

	ngOnInit(): void {
		// if (this.index != -1) {
		// 	console.log("testing");
		// 	const query = ".index-" + this.index.toString();
		// 	console.log(query);
		// 	const card = document.querySelectorAll(query);
		// 	card.forEach((element) => {
		// 		element.animate(
		// 			[
		// 				{
		// 					transform: "translateY(40px)",
		// 					opacity: "0%",
		// 				},
		// 				{
		// 					transform: "translateY(0px)",
		// 					opacity: "100%",
		// 				},
		// 			],
		// 			{
		// 				duration: 500,
		// 				delay: 100 * this.index,
		// 				easing: "ease-out",
		// 				fill: "forwards",
		// 			}
		// 		);
		// 	});
		// }
	}

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

	getBackground(): string {
		if (this.isUnlocked()) {
			return `/assets/${this.product.type.toLowerCase()}-repeating-background.png`;
		}
		return "";
	}

	formClasses(): string {
		var classes = "";
		if (this.isInCart()) {
			classes += "card-selected ";
		}
		if (this.isUnlocked()) {
			classes += "card-unlocked ";
		}
		if ((this.maxCartSize() && !this.isInCart()) || !this.hasEnoughInStock()) {
			classes += "card-blocked ";
		}
		if (!this.hasEnoughInStock() && this.showOutOfStock) {
			classes += "card-out-of-stock ";
		}
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

	amountOfTimesInCart(): number {
		if (this.isInCart()) {
			const cart = this.credentialsService.getUser()?.cart;
			if (cart) {
				var count = 0;
				cart.forEach((productID) => {
					if (productID == this.product.id) {
						count += 1;
					}
				});
				return count;
			}
		}
		return 0;
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

	hasEnoughInStock(): boolean {
		return this.amountOfTimesInCart() + 1 <= this.product.quantity;
	}

	addToCart(): void {
		if (this.maxCartSize() || !this.isUnlocked() || !this.hasEnoughInStock()) {
			return;
		}
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

	removeFromCart(event: any): void {
		event.preventDefault();
		let curUser = this.credentialsService.getUser();

		if (curUser && curUser.cart && this.formClasses().includes("selected")) {
			const indexOfProduct = curUser.cart.indexOf(this.product.id);
			curUser.cart.splice(indexOfProduct, 1);

			this.credentialsService.storeCurrentUser({ ...curUser });
			this.userService.updateUser(curUser).subscribe({});

			this.removedEvent.emit(this.product);
		}
	}
}
