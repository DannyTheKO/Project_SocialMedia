import moment from "moment";

export class Comment {
    constructor(data) {
        this.commentId = data.commentId;
        this.username = data.username;
        this.content = data.content;
        this.media = data.media || [];
        this.createdAt = data.createdAt;
        this.userId = data.userId;
        this.postId = data.postId;
    }

    // Helper method to format date using moment
    getFormattedDate() {
        return moment(this.createdAt).fromNow();
    }

    // Helper method to check if comment has media
    hasMedia() {
        return this.media && this.media.length > 0;
    }
}
