import { PRODUCTS } from './../mock-products';
import { Product } from '../product';
import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NgIf, NgFor } from '@angular/common';


@Component({
  standalone: true,
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
  imports: [
    NgIf,
    NgFor,
  ]
})
export class CartComponent {
  products = PRODUCTS;


  getTotalPrice(): number {
    return this.products.reduce((total, product) => total + product.price, 0);
  }

  removeProduct(productId: number): void {
    this.products = this.products.filter(product => product.id !== productId);
  }
}

