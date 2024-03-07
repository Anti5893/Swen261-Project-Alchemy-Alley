import { Component, OnInit } from "@angular/core";

import { ProductService } from "../product.service";
import { Product } from "../product";

@Component({
	selector: "app-catalog",
	templateUrl: "./catalog.component.html",
	styleUrl: "./catalog.component.css",
})
export class CatalogComponent implements OnInit {
	products: Product[] = [];
	searchQuery: string = "";

	constructor(private productService: ProductService) {}

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
}
