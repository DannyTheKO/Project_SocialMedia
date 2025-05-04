import {Client} from "@stomp/stompjs";
import SockJS from "sockjs-client";

class NotificationWebSocketService {
    constructor() {
        this.client = null;
        this.connected = false;
    }

    connect(userId, onNotificationReceived) {
        if (this.connected) {
            console.log("Already connected to Notification WebSocket Service");
            return;
        }

        const socket = new SockJS("http://localhost:8080/ws");
        this.client = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,

            debug: function (str) {
                console.log(`STOMP: ${str}`);
            },

            onConnect: () => {
                console.log("Connected to Notification WebSocket Service with userId: " + userId);
                this.connected = true;
                this.client.subscribe(`/topic/notifications/${userId}`, (notification) => {
                    const notificationData = JSON.parse(notification.body);
                    console.log("Received notification:", notificationData);
                    if (onNotificationReceived) {
                        onNotificationReceived(notificationData);
                    }
                });
            },

            onDisconnect: () => {
                console.log("Disconnected from Notification WebSocket Service");
                this.connected = false;
            },

            onStompError: (frame) => {
                console.error("STOMP Error:", frame.headers['message']);
                console.error("Additional details:", frame.body);
            }
        })

        this.client.activate();
    }

    disconnect() {
        if (this.client && this.connected) {
            this.client.deactivate();
            this.connected = false;
            console.log("Disconnected from Notification WebSocket Service");
        }
    }

    // Method to mark notification as read
    markAsRead(notificationId) {
        if (!this.connected || !this.client) {
            console.error("Not connected to WebSocket server");
            return;
        }

        this.client.publish({
            destination: `/app/notifications/${notificationId}/read`,
        });
    }

    // Method to mark all notifications as read
    markAllAsRead() {
        if (!this.connected || !this.client) {
            console.error("Not connected to WebSocket server");
            return;
        }

        this.client.publish({
            destination: '/app/notifications/readAll',
        });
    }

    // Method to delete notification
    deleteNotification(notificationId) {
        if (!this.connected || !this.client) {
            console.error("Not connected to WebSocket server");
            return;
        }

        this.client.publish({
            destination: `/app/notifications/${notificationId}/delete`,
        });
    }

    // Method to delete all notification for the current user
    deleteAllNotifications() {
        if (!this.connected || !this.client) {
            console.error("Not connected to WebSocket server");
            return;
        }

        this.client.publish({
            destination: `/app/notifications/deleteAll`
        });
    }
}

export default new NotificationWebSocketService;