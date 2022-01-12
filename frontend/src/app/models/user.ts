import { IdentityCard } from "./IdentityCard";

export class User{
    constructor(
        id: number,
        firstName: string,
        lastName: string,
        dateOfBirth: Date,
        phoneNumber: string,
        identityCard : IdentityCard,
        roleId: number,
    ){}
}