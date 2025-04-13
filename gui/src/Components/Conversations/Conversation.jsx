import React, {useEffect, useRef, useState} from 'react';
import './Conversation.css';
import WebSocketService from '../../Services/WebSocket/webSocket';
import CloseIcon from '@mui/icons-material/Close';
import CallIcon from '@mui/icons-material/Call';
import VideocamIcon from '@mui/icons-material/Videocam';
import MoreVertIcon from '@mui/icons-material/MoreVert'
import SendIcon from '@mui/icons-material/Send';
import {getMessagesData} from '../../Services/MessageService/messageService';

const Conversation = ({user, onClose}) => {

    const [messages, setMessages] = useState([]);

    const [newMessage, setNewMessage] = useState('');

    const messagesEndRef = useRef(null)

    const currentUserId = 2 // user real UserId

    // Scroll to latest message function
    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({behavior: 'smooth'})
    }

    // Scroll to latest message every time messages change
    useEffect(() => {
        scrollToBottom()
    }, [messages])

    // Get messages data from server whenever open chat frame
    useEffect(() => {
        const fectchMessages = async () => {
            try {
                // fetch messages of currentUser and choosen User
                const respone = await getMessagesData(currentUserId, user.id)
                setMessages(respone.data)
            } catch (error) {
                console.error('Error fetching messages data from MongoDB: ', error);
            }
        }

        fectchMessages();
    }, [user.id])

    // Connect Socker server of currentUser and choosen user
    useEffect(() => {
        WebSocketService.connect(currentUserId, (chatMessage) => {
            if (
                (chatMessage.senderId === currentUserId && chatMessage.receiverId === user.id) ||
                (chatMessage.senderId === user.id && chatMessage.receiverId === currentUserId)
            ) {
                setMessages((prevMessages) => {
                    const updatedMessages = [...prevMessages, chatMessage];
                    // Debug log
                    console.log("Updated messages:", updatedMessages);
                    return updatedMessages;
                });
            }
        })

        return () => {
            WebSocketService.disconnect();
        }
    }, [currentUserId, user.id]) // change everytime 1 of 2 userId changed


    // Handle message structure and sendMessage function
    const handleSendMessage = (e) => {
        e.preventDefault();
        if (newMessage.trim()) {
            const message = {
                senderId: currentUserId,
                receiverId: user.id,
                content: newMessage,
                type: 'CHAT'
            }
            WebSocketService.sendMessage(message);
            // Clear the input field
            setNewMessage('');
        }
    }

    return (
        <div className="conversation">
            <div className="conversation-header">
                <div className="header-left">
                    <img src={user.avatar} alt="" className="avatar"/>
                    <span className="name">{user.name}</span>
                </div>
                <div className="header-right">
                    <CallIcon className="action-icon" style={{fontSize: '28px'}}/>
                    <VideocamIcon className="action-icon" style={{fontSize: '28px'}}/>
                    <MoreVertIcon className="action-icon" style={{fontSize: '28px'}}/>
                    <CloseIcon className="action-icon" onClick={onClose}/>
                </div>
            </div>
            <div className="conversation-messages">
                {Array.isArray(messages) && messages.map((msg, index) => (
                    <div
                        key={index}
                        className={`message ${msg.senderId === currentUserId ? 'message-right' : 'message-left'
                        }`}
                    >
                        {msg.sender !== 'User' && (
                            <img src={user.avatar} alt="" className="message-avatar"/>
                        )}
                        <div className="message-content">
                            <p>{msg.content}</p>
                        </div>
                    </div>
                ))}
                <div ref={messagesEndRef}></div>
            </div>
            <form onSubmit={handleSendMessage} className="conversation-input">
                <input
                    type="text"
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                    placeholder="Aa"
                    className="input-field"
                />
                <button type="submit" className="send-button">
                    <SendIcon style={{fontSize: '28px'}}/>
                </button>
            </form>
        </div>
    );
};

export default Conversation;