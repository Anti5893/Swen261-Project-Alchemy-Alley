export enum ElementType {
    EARTH,
    FIRE,
    WATER,
    AIR
}

export interface Product {
    id: number,
    name: string,
    type: ElementType,
    price: number,
    quantity: number
}