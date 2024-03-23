import { Component, OnInit } from "@angular/core";

import { ProductService } from "../product.service";
import { Product } from "../product";
import { CredentialsService } from "../credentials.service";
import { UserService } from "./../user.service";

@Component({
	selector: "app-catalog",
	templateUrl: "./catalog.component.html",
	styleUrl: "./catalog.component.css",
})
export class CatalogComponent implements OnInit {
	products: Product[] = [];
	searchQuery: string = "";
	sortBy: string = "none";

	constructor(
		private productService: ProductService,
		private credentialService: CredentialsService,
		private userService: UserService
	) {}

	ngOnInit(): void {
		this.getProducts();
		this.sortProducts("alphabetically");
	}

	getProducts(): void {
		this.productService.getProducts().subscribe((products) => {
			if (products.body) {
				this.products = products.body;
				this.sortProducts(this.sortBy);
			} else {
				this.products = [];
			}
		});
	}

	compareAlphabeticalPosition(
		product1: Product,
		product2: Product,
		reverse: boolean = false
	): boolean {
		// Z -> A
		if (reverse) {
			return (
				product1.name.toLowerCase().charCodeAt(0) >
				product2.name.toLowerCase().charCodeAt(0)
			);
		}
		// A -> Z
		return (
			product1.name.toLowerCase().charCodeAt(0) < product2.name.toLowerCase().charCodeAt(0)
		);
	}

	comparePrice(product1: Product, product2: Product, reverse: boolean = true): boolean {
		// High -> Low
		if (reverse) {
			return product1.price > product2.price;
		}
		// Low -> High
		return product1.price < product2.price;
	}

	quickSort(items: Product[], compareMethod: Function, reverse: boolean = false): Product[] {
		if (items.length == 0) {
			return items;
		}

		var partition: Product | undefined = items.pop();

		if (partition == undefined) {
			return items;
		}

		var left_side: Product[] = [];
		var right_side: Product[] = [];

		items.forEach((product) => {
			if (compareMethod(product, partition, reverse)) {
				left_side.push(product);
			} else {
				right_side.push(product);
			}
		});

		left_side = this.quickSort(left_side, compareMethod);
		right_side = this.quickSort(right_side, compareMethod);

		left_side.push(partition);
		left_side.push(...right_side);
		return left_side;
	}

	sortProductsAlphabetically(reverse: boolean = false): void {
		this.products = this.quickSort(this.products, this.compareAlphabeticalPosition, reverse);
	}

	sortProductsByPrice(reverse: boolean = false): void {
		this.products = this.quickSort(this.products, this.comparePrice, reverse);
	}

	sortProducts(value: string): void {
		this.sortBy = value;
		switch (value) {
			case "alphabetically":
				this.sortProductsAlphabetically();
				break;
			case "high-low":
				this.sortProductsByPrice(true);
				break;
			case "low-high":
				this.sortProductsByPrice();
				break;
			case "none":
				break;
			default:
				console.log(`sortProduct's value was not recognized: ${value}`);
				break;
		}
	}

	searchProducts(query: string): void {
		this.productService.searchProduct(query).subscribe(
			(response) => {
				if (response.body) {
					this.products = response.body;
					this.sortProducts(this.sortBy);
				}
			},
			(error) => {
				this.products = [];
			}
		);
	}

	itemInCart(product: Product): boolean {
		const curUser = this.credentialService.getUser();
		const curCart = curUser!.cart;
		return curCart?.includes(product.id);
	}

	maxCartSize(): boolean {
		const curUser = this.credentialService.getUser();
		let curCart = curUser?.cart;
		if (curCart) {
			return curCart.length >= 2;
		} else {
			return false;
		}
	}

	addToCart(productID: number): void {
		let curUser = this.credentialService.getUser();
		if (curUser) {
			if (!curUser.cart) {
				curUser.cart = [];
			}

			curUser.cart.push(productID);

			this.credentialService.storeCurrentUser({ ...curUser });
			this.userService.updateUser(curUser).subscribe();
		}
	}

	removeFromCart(productID: number): void {
		let curUser = this.credentialService.getUser();

		if (curUser && curUser.cart) {
			curUser.cart = curUser.cart.filter((id) => id !== productID);
			this.credentialService.storeCurrentUser({ ...curUser });

			this.userService.updateUser(curUser).subscribe({});
		}
	}
}
