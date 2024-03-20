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
  types: string[] = Object.keys(ElementType).filter(k => isNaN(Number(k)));
  showValidityError: boolean = false;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts().subscribe(response => this.products = response.body!);
  }

  save(product: Product, name: string, type: string, price: HTMLInputElement, quantity: HTMLInputElement): void {
    if(!price.checkValidity() || !quantity.checkValidity()) {
      this.showValidityError = true;
      return;
    }

    product.name = name;
    product.type = Object.keys(ElementType).filter(k => k === type)[0] as ElementType;
    product.price = Number(price.value);
    product.quantity = Number(quantity.value);
    
    this.productService.updateProduct(product).subscribe();
    this.showValidityError = false;
  }

  delete(id: number): void {
    this.products = this.products.filter(p => p.id !== id);
    this.productService.deleteProduct(id).subscribe();
  }

  create(name: string, type: string, price: HTMLInputElement, quantity: HTMLInputElement): void {
    if(!price.checkValidity() || !quantity.checkValidity()) {
      this.showValidityError = true;
      return;
    }

    let product: Product = {
      id: 0,
      name: name,
      type: Object.keys(ElementType).filter(k => k === type)[0] as ElementType,
      price: Number(price.value),
      quantity: Number(quantity.value)
    };

    this.productService.addProduct(product).subscribe(p => this.products.push(p.body!)); // TODO: Handle duplicate names
    this.showValidityError = false;
  }
  
}
