export class ApiResponse {
    constructor(message, data) {
        this.message = message;
        this.data = data;
    }

    // Helper methods to check response status
    isSuccess() {
        return this.message === "Success";
    }

    isFailed() {
        return this.message === "Failed";
    }

    isError() {
        return this.message === "Error";
    }
}