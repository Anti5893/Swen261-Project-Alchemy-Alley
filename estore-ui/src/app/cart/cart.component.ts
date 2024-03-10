import { UserService } from './../user.service';
import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NgIf, NgFor } from '@angular/common';

import { Product } from '../product';
import { CredentialsService } from '../credentials.service';
import { ProductService } from '../product.service';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent {
	products: Product[] = [];


	constructor(
		private credentialService: CredentialsService,
    private productService: ProductService,
    private userService: UserService
		) {}

	ngOnInit(): void {
		this.getProducts();
	}

	getProducts(): void {
		let cart = this.credentialService.getUser()?.cart;
    cart?.forEach(productId => {
      this.productService.getProduct(productId).subscribe(
          response => {
              if (response.body) {
                  this.products.push(response.body);
              }
          },
          error => {
              console.error(`Error fetching product with ID ${productId}:`, error);
          }
      );
  });
		};

  getTotalPrice(): number {
    return this.products.reduce((total, product) => total + product.price, 0);
  }

  removeProduct(productID: number): void {
   let curUser = this.credentialService.getUser();

    if (curUser && curUser.cart) {

        curUser.cart = curUser.cart.filter(id => id !== productID);
        this.credentialService.storeCurrentUser({...curUser});
        this.products = this.products.filter(product => product.id !== productID);
       
        this.userService.updateUser(curUser).subscribe({});
    }
  }
  handlePurchase(): void {
    let curUser = this.credentialService.getUser();
    if(curUser && curUser.cart)
    {
      curUser.cart = [];
      curUser.unlocked // Handle new Unlocked
      this.credentialService.storeCurrentUser({...curUser});
      this.products = [];
      this.userService.updateUser(curUser).subscribe({});
    }
  }
  isCartEmpty(): boolean {
    return (this.products.length == 0);
  }
}

