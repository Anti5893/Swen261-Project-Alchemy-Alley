import { Component, OnInit } from "@angular/core";

import { ProductService } from "../product.service";
import { Product } from "../product";
import { CredentialsService } from "../credentials.service";

@Component({
	selector: "app-catalog",
	templateUrl: "./catalog.component.html",
	styleUrl: "./catalog.component.css",
})
export class CatalogComponent implements OnInit {
	allProducts: Product[] = [];
	searchQuery: string = "";
	groupBy: string = "completion";
	groups!: Record<string, Product[]>;
	sortBy: string = "byID";
	filter: string = "none";

	constructor(
		private productService: ProductService,
		private credentialsService: CredentialsService
	) {}

	ngOnInit(): void {
		this.getProducts();
	}

	getProducts(): void {
		this.productService.getProducts().subscribe(
			(response) => {
				if (response.body) {
					this.allProducts = response.body;
					this.updateProducts();
				}
			},
			(error) => {
				this.allProducts = [];
			}
		);
	}

	searchProducts(query: string = ""): void {
		this.searchQuery = query;
		this.productService.searchProduct(query).subscribe(
			(response) => {
				if (response.body) {
					this.allProducts = response.body;
					this.updateProducts();
				}
			},
			(error) => {
				this.allProducts = [];
			}
		);
	}

	updateProducts(
		dummyVar: any = undefined,
		groupBy: string = this.groupBy,
		sortBy: string = this.sortBy,
		filter: string = this.filter
	): void {
		this.groupProducts(groupBy);
		this.sortGroups(sortBy);
		this.filterGroups(filter);
	}

	sortGroups(value: string): void {
		this.sortBy = value;
		Object.keys(this.groups).forEach((key) => {
			switch (value) {
				case "byID":
					this.groups[key] = this.sortGroupsById(this.groups[key]);
					break;
				case "alphabetically":
					this.groups[key] = this.sortGroupsAlphabetically(this.groups[key]);
					break;
				case "high-low":
					this.groups[key] = this.sortGroupsByPrice(this.groups[key], true);
					break;
				case "low-high":
					this.groups[key] = this.sortGroupsByPrice(this.groups[key]);
					break;
				case "none":
					break;
				default:
					console.log(`sortGroups's value was not recognized: ${value}`);
					break;
			}
		});
	}

	// TODO: Simplify this into one function. You're repeating yourself.
	sortGroupsById(products: Product[], reverse: boolean = false): Product[] {
		var sortedProducts = this.quickSort(
			products.filter((product) => this.isUnlocked(product)),
			this.compareID,
			reverse
		);
		sortedProducts.push(...products.filter((product) => !this.isUnlocked(product)));
		return sortedProducts;
	}

	sortGroupsAlphabetically(products: Product[], reverse: boolean = false): Product[] {
		var sortedProducts = this.quickSort(
			products.filter((product) => this.isUnlocked(product)),
			this.compareAlphabeticalPosition,
			reverse
		);
		sortedProducts.push(...products.filter((product) => !this.isUnlocked(product)));
		return sortedProducts;
	}

	sortGroupsByPrice(products: Product[], reverse: boolean = false): Product[] {
		var sortedProducts = this.quickSort(
			products.filter((product) => this.isUnlocked(product)),
			this.comparePrice,
			reverse
		);
		sortedProducts.push(...products.filter((product) => !this.isUnlocked(product)));
		return sortedProducts;
	}

	compareID(product1: Product, product2: Product, reverse: boolean = false): boolean {
		// 0 -> ...
		if (reverse) {
			return product1.id > product2.id;
		}
		// ... -> 0
		return product1.id < product2.id;
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

	comparePrice(product1: Product, product2: Product, reverse: boolean = false): boolean {
		// High -> Low
		if (reverse) {
			return product1.price > product2.price;
		}
		// Low -> High
		return product1.price < product2.price;
	}

	quickSort(items: Product[], compareMethod: Function, reverse: boolean = false): Product[] {
		var partition: Product | undefined = items.pop();

		if (partition === undefined) {
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

		left_side = this.quickSort(left_side, compareMethod, reverse);
		right_side = this.quickSort(right_side, compareMethod, reverse);

		left_side.push(partition);
		left_side.push(...right_side);
		return left_side;
	}

	isUnlocked(product: Product): boolean {
		const isUnlocked = this.credentialsService.getUser()?.unlocked.includes(product.id);
		if (isUnlocked) {
			return isUnlocked;
		}
		return false;
	}

	filterGroups(value: string): void {
		this.filter = value;
		Object.keys(this.groups).forEach((key) => {
			this.groups[key] = this.groups[key].filter((product) => {
				var filter: boolean | undefined;
				switch (value) {
					case "locked":
						filter = this.isUnlocked(product);
						break;
					case "unlocked":
						filter = !this.isUnlocked(product);
						break;
					case "none":
						filter = true;
						break;
					default:
						break;
				}
				return filter;
			});
		});
	}

	groupsKeys(): string[] {
		return Object.keys(this.groups);
	}

	groupProducts(groupBy: string): void {
		this.groupBy = groupBy;
		switch (groupBy) {
			case "completion":
				this.groups = {
					unlocked: this.allProducts.filter((product) => this.isUnlocked(product)),
					locked: this.allProducts.filter((product) => !this.isUnlocked(product)),
				};
				break;
			case "type":
				this.groups = {
					fire: this.allProducts.filter((product) => product.type == "FIRE"),
					water: this.allProducts.filter((product) => product.type == "WATER"),
					air: this.allProducts.filter((product) => product.type == "AIR"),
					earth: this.allProducts.filter((product) => product.type == "EARTH"),
					energy: this.allProducts.filter((product) => product.type == "ENERGY"),
				};
				break;
			case "cards":
				this.groups = {
					cards: this.allProducts,
				};
				break;
			default:
				console.log(`unknown groupBy value: ${groupBy}`);
				break;
		}
	}

	getCurrentUserCart(): number[] {
		const cart = this.credentialsService.getUser()?.cart;
		if (cart) {
			return cart;
		}
		return [];
	}

	getCurrentUserObjectCart(): Product[] {
		const cart = this.credentialsService.getUser()?.cart;
		if (cart) {
			return this.allProducts.filter((product) => cart.includes(product.id));
		}
		return [];
	}
}
