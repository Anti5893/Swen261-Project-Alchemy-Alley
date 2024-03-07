export interface User{
    username: string
    password: string,
    admin : boolean,
    unlocked  : number[],
    cart  : number[]
}