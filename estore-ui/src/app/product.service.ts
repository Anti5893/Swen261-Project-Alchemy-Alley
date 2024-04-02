import { Injectable } from "@angular/core";
import { HttpClient, HttpResponse, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";

import { Product } from "./product";

@Injectable({
	providedIn: "root",
})
export class ProductService {

	private productsUrl = "http://localhost:8080/inventory/products";

	httpOptions = {
		headers: new HttpHeaders({ "Content-Type": "application/json" }),
		observe: "response" as "response",
	};

	constructor(private http: HttpClient) {}

	getProducts(): Observable<HttpResponse<Product[]>> {
		return this.http.get<Product[]>(`${this.productsUrl}/`, this.httpOptions);
	}

	getProduct(id: number): Observable<HttpResponse<Product>> {
		const url = `${this.productsUrl}/${id}`;
		return this.http.get<Product>(url, this.httpOptions);
	}

	searchProduct(name: string): Observable<HttpResponse<Product[]>> {
		const url = `${this.productsUrl}?name=${name}`;
		return this.http.get<Product[]>(url, this.httpOptions);
	}

	addProduct(product: Product): Observable<HttpResponse<Product>> {
		return this.http.post<Product>(this.productsUrl, product, this.httpOptions);
	}

	updateProduct(product: Product): Observable<HttpResponse<Product>> {
		return this.http.put<Product>(this.productsUrl, product, this.httpOptions);
	}

	deleteProduct(id: number): Observable<HttpResponse<Product>> {
		const url = `${this.productsUrl}/${id}`;
		return this.http.delete<Product>(url, this.httpOptions);
	}
	
}
