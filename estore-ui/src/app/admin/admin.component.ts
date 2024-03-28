import { Component } from '@angular/core';
import { OnInit } from '@angular/core';

import { Product, ElementType } from '../product';
import { ProductService } from '../products.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit {

  products: Product[] = [];
  types: string[] = [""].concat(Object.keys(ElementType).filter(k => isNaN(Number(k))));
  showValidityError: boolean = false;
  showDuplicateProductError: boolean = false;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts().subscribe(response => this.products = response.body!);
  }

  create(name: HTMLInputElement, type: string, price: HTMLInputElement, quantity: HTMLInputElement, imageURL: HTMLInputElement): void {
    // Validate input boxes
    if(name.value.trim().length == 0 || type.length === 0 || !price.checkValidity() || !quantity.checkValidity() || !imageURL.checkValidity()) {
      this.showValidityError = true;
      return;
    }

    // Create product instance
    let product: Product = {
      id: 0,
      name: name.value,
      type: Object.keys(ElementType).filter(k => k === type)[0] as ElementType,
      price: Number(price.value),
      quantity: Number(quantity.value),
      imageURL: ""
    };

    // Send request
    this.productService.addProduct(product).subscribe(
      (response) => {
        // Does not exist
        name.value = "";
        price.value = "";
        quantity.value = "";
        imageURL.value = "";
        this.products.push(response.body!);
        this.showDuplicateProductError = false;
      }, (error) => {
        // Already exists
        this.showDuplicateProductError = true;
    });
    this.showValidityError = false;
  }

  save(product: Product, name: string, type: string, price: HTMLInputElement, quantity: HTMLInputElement, imageURL: string): void {
    // Validate input boxes
    if(name.trim().length == 0 || type.length === 0 || !price.checkValidity() || !quantity.checkValidity() || imageURL.trim.length == 0) {
      this.showValidityError = true;
      return;
    }

    // Update product fields
    product.name = name;
    product.type = Object.keys(ElementType).filter(k => k === type)[0] as ElementType;
    product.price = Number(price.value);
    product.quantity = Number(quantity.value);
    product.imageURL = imageURL;
    
    // Send request
    this.productService.updateProduct(product).subscribe();
    this.showValidityError = false;
  }

  delete(id: number): void {
    this.products = this.products.filter(p => p.id !== id);
    this.productService.deleteProduct(id).subscribe();
  }

  hasChanged(product: Product, name: string, type: string, price: HTMLInputElement, quantity: HTMLInputElement, imageURL: string): boolean {
    // Check if any field has changed at all
    return product.name !== name || product.type !== type || 
           product.price !== Number(price.value) || product.quantity !== Number(quantity.value) || product.imageURL !== imageURL;
  }
  
}
