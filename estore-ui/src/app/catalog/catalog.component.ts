import { Component, OnInit } from "@angular/core";

import { ProductService } from "../product.service";
import { Product } from "../product";
import { CredentialsService } from "../credentials.service";
import { UserService } from './../user.service';

@Component({
	selector: "app-catalog",
	templateUrl: "./catalog.component.html",
	styleUrl: "./catalog.component.css",
})
export class CatalogComponent implements OnInit {
	products: Product[] = [];
	searchQuery: string = "";

	constructor(
		private productService: ProductService,
		private credentialService: CredentialsService,
		private userService: UserService
		) {}

	ngOnInit(): void {
		this.getProducts();
	}

	getProducts(): void {
		this.productService.getProducts().subscribe((products) => {
			if (products.body) {
				this.products = products.body;
			} else {
				this.products = [];
			}
		});
	}

	searchProducts(query: string): void {
		this.productService.searchProduct(query).subscribe(
			(response) => {
				if (response.body) {
					this.products = response.body;
				}
			},
			(error) => {
				this.products = [];
			}
		);
	}

	itemInCart(product: Product): boolean {
		const curUser = this.credentialService.getUser();
		const curCart = curUser?.cart;
		return Boolean(curCart?.some((productId: number) => productId == product.id));
	}

	maxCartSize():boolean {
		const curUser = this.credentialService.getUser();
		let curCart = curUser?.cart;
		if (curCart)
		{
			return (curCart.length >= 2);
		}
		else
		{
			return false;
		}
	}
	
	addToCart(product: Product): void {
		let curUser = this.credentialService.getUser();
		if (curUser) {
			if (!curUser.cart) {
				curUser.cart = [];
			}
	
			curUser.cart.push(product.id);
	
			this.credentialService.storeCurrentUser({...curUser});
			this.userService.updateUser(curUser).subscribe();
		} 
	}

	removeFromCart(productID: number): void {
		let curUser = this.credentialService.getUser();
	 
		 if (curUser && curUser.cart) {
	 
			 curUser.cart = curUser.cart.filter(id => id !== productID);
			 this.credentialService.storeCurrentUser({...curUser});
			
			 this.userService.updateUser(curUser).subscribe({});
		 }
	 }
	
}
