export enum ElementType {
    EARTH = "EARTH",
    FIRE = "FIRE",
    WATER = "WATER",
    AIR = "AIR"
}

export interface Product {
    id: number,
    name: string,
    type: ElementType,
    price: number,
    quantity: number
}