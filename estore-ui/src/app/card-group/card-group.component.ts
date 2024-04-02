import { Component, Input } from "@angular/core";
import { Product } from "../product";

@Component({
	selector: "app-card-group",
	templateUrl: "./card-group.component.html",
	styleUrl: "./card-group.component.css",
})
export class CardGroupComponent {

	@Input({ required: true }) products!: Product[];
    @Input() title!: string;
	
}
