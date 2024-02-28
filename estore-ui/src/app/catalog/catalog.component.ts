import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';

@Component({
	selector: 'app-catalog',
	templateUrl: './catalog.component.html',
	styleUrl: './catalog.component.css',
})
export class CatalogComponent implements OnInit {
	products: Product[] = [];

	constructor(private productService: ProductService) {}

	ngOnInit(): void {
		console.log('catalog initializing...');
		this.getProducts();
		console.log('catalog initialized');
	}

	getProducts(): void {
		this.productService
			.getProducts()
			.subscribe((products) => (this.products = products));
	}
}
