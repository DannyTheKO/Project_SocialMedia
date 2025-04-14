export class Post {
    constructor(data) {
        this.postId = data.postId;
        this.userId = data.userId;
        this.content = data.content;
        this.imageUrl = data.imageUrl;
        this.createdAt = data.createdAt;
        this.updatedAt = data.updatedAt;
        this.likes = data.likes;
        this.comments = data.comments;
    }
}