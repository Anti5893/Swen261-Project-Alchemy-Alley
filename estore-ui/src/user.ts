export interface User{
    username: string
    password: string,
    isAdmin : boolean,
    unlocked ? : number[],
    cart ? : number[]
}