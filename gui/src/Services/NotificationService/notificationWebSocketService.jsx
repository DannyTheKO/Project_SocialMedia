import {Client} from "@stomp/stompjs";
import SockJS from "sockjs-client";

class NotificationWebSocketService {
    constructor() {
        this.client = null;
        this.connected = false;
    }

    connect(userId, onNotificationReceived) {
        if (this.connected) {
            // console.log("Already connected to Notification WebSocket Service");
            return;
        }

        const socketUrl = "http://localhost:8080/ws";
        // console.log(`Attempting to connect to WebSocket server at: ${socketUrl}`);
        const socket = new SockJS(socketUrl);
        this.client = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,

            debug: function (str) {
                console.log(`STOMP: ${str}`);
            },

            onConnect: () => {
                // console.log("Connected to Notification WebSocket Service with userId: " + userId);
                this.connected = true;
                if (this.client.connected) {
                    this.client.subscribe(`/topic/notifications`, (notifications) => {
                        const notificationData = JSON.parse(notifications.body);
                        if (onNotificationReceived) {
                            onNotificationReceived(notificationData);
                        }
                    });
                } else {
                    this.connected = false;
                    console.error("STOMP client is not connected");
                }
            },

            onDisconnect: () => {
                console.log("Disconnected from Notification WebSocket Service");
                this.connected = false;
            },

            onStompError: (frame) => {
                this.connected = false;
                console.error("STOMP Error:", frame.headers['message']);
                console.error("Additional details:", frame.body);
            }
        });

        this.client.activate();
    }

    disconnect() {
        if (this.client && this.connected) {
            this.client.deactivate().catch(r => console.log(r));
            this.connected = false;
            console.log("Disconnected from Notification WebSocket Service");
        }
    }
}

export default NotificationWebSocketService;