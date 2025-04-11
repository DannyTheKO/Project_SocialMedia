// import { Client } from "@stomp/stompjs";
// import SockJS from "sockjs-client";

// class WebSocketService {
//     constructor() {
//         this.client = null;
//         this.connected = false;
//     }

//     connect(onMessageReceived) {
//         const socket = new SockJS('http://localhost:8080/ws');
//         this.client = new Client({
//             webSocketFactory: () => socket,
//             reconnectDelay: 5000,
//             onConnect: () => {
//                 console.log("Connected to WebSocket");
//                 this.connected = true;
//                 this.client.subscribe('/topic/public', (message) => {
//                     if (message.body) {
//                         const chatMessage = JSON.parse(message.body);
//                         onMessageReceived(chatMessage)
//                     }
//                 })

//                 // Gửi tin nhắn thông báo người dùng tham gia
//                 this.client.publish({
//                     destination: '/app/chat.addUser',
//                     body: JSON.stringify({
//                         sender: 'User', // Thay bằng tên người dùng thực tế
//                         type: 'JOIN'
//                     })
//                 });
//             },
//             onDisconnect: () => {
//                 console.log('Disconnected from WebSocket');
//                 this.connected = false;
//             },
//             onStompError: (frame) => {
//                 console.error('Broker reported error: ' + frame.headers['message']);
//                 console.error('Additional details: ' + frame.body);
//             }
//         });

//         this.client.activate();
//     }

//     disconnect() {
//         if (this.client) {
//             this.client.deactivate();
//         }
//     }

//     sendMessage(message) {
//         if (this.connected && this.client) {
//             this.client.publish({
//                 destination: '/app/chat.sendMessage',
//                 body: JSON.stringify(message)
//             });
//         }
//     }
// }

// export default new WebSocketService();