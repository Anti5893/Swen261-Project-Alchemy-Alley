import { Product } from './../product';
import { UserService } from './../user.service';
import { Component } from '@angular/core';

import { CredentialsService } from '../credentials.service';
import { ProductService } from '../product.service';
import { delay } from 'rxjs';

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
    private userService: UserService) {}
    private purchased: boolean = false;
    private unlocked: Product | null = null

	ngOnInit(): void {
		this.getProducts();
	}

	getProducts(): void {
		let cart = this.credentialService.getUser()?.cart;
    cart?.forEach((productId) => {
      this.productService.getProduct(productId).subscribe(
        (response) => {
          if (response.body) {
              this.products.push(response.body);
          }
        },
        (error) => {
          console.error(`Error fetching product with ID ${productId}:`, error);
        }
      );
    });
	}
  getUnlocked(): Product{
      return this.unlocked!;
  }

  getTotalPrice(): number {
    return this.products.reduce((total, product) => total + product.price, 0);
  }

  removeProduct(productID: number): void {
    let curUser = this.credentialService.getUser();

    if(curUser && curUser.cart) {
      curUser.cart = curUser.cart.filter(id => id !== productID);
      this.credentialService.storeCurrentUser({...curUser});
      this.products = this.products.filter(product => product.id !== productID);
     
      this.userService.updateUser(curUser).subscribe({});
    }
  }

  handlePurchase(): void {
    // Apply animation to fade out
    let cartProducts = document.getElementsByClassName("card-product") as HTMLCollectionOf<HTMLElement>;
    for(let i = 0; i < cartProducts.length; i++) {
      cartProducts[i].style.animation = "craft-fade-out 2s ease-in-out forwards";
    }

    // Call backend after they fade
    setTimeout(() => {
      let curUser = this.credentialService.getUser();
      this.purchased = true;
      if (curUser && curUser.cart) {
        this.userService.doCraft(curUser).subscribe(
          (response) => {
            this.unlocked = response.body;
          },
          (error) => {
            this.unlocked = null;
          }
        );
        curUser.cart = [];
        if (curUser.unlocked && this.unlocked?.id) {
          curUser.unlocked.push(this.unlocked.id);
        }
        this.credentialService.storeCurrentUser({...curUser});
        this.products = [];
      }
    }, 2000);
  }

  isCartEmpty(): boolean {
    return this.products.length == 0;
  }

  isPurchased(): boolean{
    return this.purchased;
  }
  
  hasInvalidFields(name: HTMLInputElement, card: HTMLInputElement, exp: HTMLInputElement, ccv: HTMLInputElement): boolean {
    return name.value.trim().length === 0 || card.value.trim().length === 0 || exp.value.trim().length === 0 || ccv.value.trim().length === 0;
  }

}