import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

class MessageWebSocketService {
    constructor() {
        this.client = null;
        this.connected = false;
    }

    connect(userId, onMessageReceived) {
        const socket = new SockJS('http://localhost:8080/ws');
        this.client = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
            onConnect: () => {
                // Debug log
                // console.log("Connected to Message WebSocket Service with userId: " + userId);
                this.connected = true;
                this.client.subscribe(`/topic/messages/${userId}`, (message) => {
                    if (message.body) {
                        const chatMessage = JSON.parse(message.body);
                        // Debug log
                        // console.log("Received message:", chatMessage);
                        onMessageReceived(chatMessage)
                    }
                })

                // Notification user join in 
                this.client.publish({
                    destination: '/app/chat.addUser',
                    body: JSON.stringify({
                        sender: userId,
                        type: 'JOIN'
                    })
                });
            },
            onDisconnect: () => {
                // console.log('Disconnected from WebSocket');
                this.connected = false;
            },
            onStompError: (frame) => {
                console.error('Broker reported error: ' + frame.headers['message']);
                console.error('Additional details: ' + frame.body);
            }
        });

        this.client.activate();
    }

    disconnect() {
        if (this.client) {
            this.client.deactivate();
        }
    }

    sendMessage(message) {
        if (this.connected && this.client) {
            this.client.publish({
                destination: '/app/chat.sendMessage',
                body: JSON.stringify(message)
            });
        }
    }
}

export default new MessageWebSocketService();