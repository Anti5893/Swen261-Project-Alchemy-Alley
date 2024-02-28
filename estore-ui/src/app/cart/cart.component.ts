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
  products = [];

  constructor(private http: HttpClient) {
    this.fetchProducts();
  }

  fetchProducts(): void {
    this.http.get<any>('http://localhost:8080/users').subscribe({
      next: (response) => {
        this.products = response.cart;
      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }

  getTotalPrice(): number {
    return this.products.reduce((total, product) => total + product.price, 0);
  }

  removeProduct(productId: number): void {
    this.products = this.products.filter(product => product.id !== productId);
  }
}

