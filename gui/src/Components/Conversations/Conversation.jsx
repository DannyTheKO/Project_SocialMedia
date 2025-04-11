import React, { useState, useEffect } from 'react';
import './Conversation.css';
// import WebSocketService from '../../Services/WebSocket/webSocket';
import CloseIcon from '@mui/icons-material/Close';
import CallIcon from '@mui/icons-material/Call';
import VideocamIcon from '@mui/icons-material/Videocam';
import MoreVertIcon from '@mui/icons-material/MoreVert'

const Conversation = () => {

    const [messages, setMessages] = useState([
        { sender: 'Thái Tuấn', content: 'moi lam ao cong cai bao nhiem bao viet', type: 'CHAT' },
        { sender: 'User', content: 'mình khám bv khác', type: 'CHAT' },
        { sender: 'Thái Tuấn', content: 'ben hoa hào tơi 7tr', type: 'CHAT' },
        { sender: 'Thái Tuấn', content: 'khám bệnh có hoàn dc', type: 'CHAT' },
        { sender: 'User', content: 'đang hiu có hoàn dc', type: 'CHAT' },
        { sender: 'Thái Tuấn', content: 'oke', type: 'CHAT' },
    ]);

    const [newMessage, setNewMessage] = useState('');

    useEffect(() => {
        WebSocketService.connect((chatMessage) => {
            setMessages((prevMessages) => [...prevMessages, chatMessage])
        })

        return () => {
            WebSocketService.disconnect();
        }
    }, [])

    const handleSendMessage = (e) => {
        e.preventDefault();
        if (newMessage.trim()) {
            const message = {
                sender: 'User',
                content: newMessage,
                type: 'CHAT'
            }
            WebSocketService.sendMessage(message);
            setMessages('');
        }
    }


    return (
        <div className="conversation">
            <div className="conversation-header">
                <div className="header-left">
                    <img src={user.avatar} alt="" className="avatar" />
                    <span className="name">{user.name}</span>
                </div>
                <div className="header-right">
                    <CallIcon className="action-icon" style={{ fontSize: '28px' }} />
                    <VideocamIcon className="action-icon" style={{ fontSize: '28px' }} />
                    <MoreVertIcon className="action-icon" style={{ fontSize: '28px' }} />
                    <CloseIcon className="action-icon" onClick={onClose} />
                </div>
            </div>
            <div className="conversation-messages">
                {messages.map((msg, index) => (
                    <div
                        key={index}
                        className={`message ${msg.sender === 'User' ? 'message-right' : 'message-left'
                            }`}
                    >
                        {msg.sender !== 'User' && (
                            <img src={user.avatar} alt="" className="message-avatar" />
                        )}
                        <div className="message-content">
                            <p>{msg.content}</p>
                        </div>
                    </div>
                ))}
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
                    Send
                </button>
            </form>
        </div>
    );
};

export default Conversation;