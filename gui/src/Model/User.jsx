export class User {
    constructor(data) {
        this.userId = data.userId;
        this.username = data.username;
        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.email = data.email;
        this.bio = data.bio;
        this.profileImageUrl = data.profileImageUrl;
        this.bannerImageUrl = data.bannerImageUrl;
        this.createdAt = data.createdAt;
        this.lastLogin = data.lastLogin;
    }

    // Custom function for the object if needed
    getFullName() {
        return `${this.firstName} ${this.lastName}`;
    }
}