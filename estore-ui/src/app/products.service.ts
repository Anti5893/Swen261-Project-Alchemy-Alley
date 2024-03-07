import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, of, tap } from 'rxjs';
import { Product } from './product';

@Injectable({
	providedIn: 'root',
})
export class ProductService {
	private productsUrl = 'http://localhost:8080/inventory/products';

	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
	};

	constructor(private http: HttpClient) {}

	getProducts(): Observable<Product[]> {
		return this.http.get<Product[]>(`${this.productsUrl}/`);
	}

	getProduct(id: number): Observable<Product> {
		const url = `${this.productsUrl}/${id}`;
		return this.http.get<Product>(url);
	}

	searchProduct(name: string): Observable<Product[]> {
		if (!name.trim) {
			return of([]);
		}
		const url = `${this.productsUrl}?name=${name}`;
		return this.http.get<Product[]>(url);
	}

	addProduct(product: Product): Observable<Product> {
		return this.http.post<Product>(
			`${this.productsUrl}`,
			product,
			this.httpOptions
		);
	}

	updateProduct(product: Product): Observable<Product> {
		return this.http.put<Product>(
			`${this.productsUrl}`,
			product,
			this.httpOptions
		);
	}

	deleteProduct(id: number): Observable<Product> {
		const url = `${this.productsUrl}/${id}`;
		return this.http.delete<Product>(url, this.httpOptions);
	}
}