export enum ElementType {
    EARTH = "EARTH",
    FIRE = "FIRE",
    WATER = "WATER",
    AIR = "AIR",
    ENERGY = "ENERGY"
}

export interface Product {
    id: number,
    name: string,
    type: ElementType,
    price: number,
    quantity: number
}
