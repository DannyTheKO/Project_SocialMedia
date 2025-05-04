import React, { useContext, useEffect, useRef, useState } from 'react';
import './Conversation.css';
import MessageWebSocketService from '../../Services/MessageService/messageWebSocketService.jsx';
import CloseIcon from '@mui/icons-material/Close';
import CallIcon from '@mui/icons-material/Call';
import VideocamIcon from '@mui/icons-material/Videocam';
import MoreVertIcon from '@mui/icons-material/MoreVert'
import SendIcon from '@mui/icons-material/Send';
import DefaultProfilePic from '../../assets/defaultProfilePic.jpg';
import { getMessagesData } from '../../Services/MessageService/messageService';
import { AuthContext } from '../../Context/AuthContext';
import { toast } from 'react-toastify';
import moment from 'moment';
import 'moment/locale/vi';

const Conversation = ({ user, onClose }) => {

    const { currentUser, setCurrentUser } = useContext(AuthContext)

    const [messages, setMessages] = useState([]);

    const [newMessage, setNewMessage] = useState('');

    const messagesEndRef = useRef(null)

    // TODO: Set this to token, and send back to server
    const currentUserId = currentUser.userId// user real UserId

    // Scroll to latest message function
    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' })
    }

    // Scroll to latest message every time messages change
    useEffect(() => {
        scrollToBottom()
    }, [messages])

    // Get messages data from server whenever open chat frame
    useEffect(() => {
        const fetchMessages = async () => {
            try {
                // fetch messages of currentUser and choosen User
                const response = await getMessagesData(currentUserId, user.userId)
                // console.log('Fetched messages:', response.data);
                setMessages(response.data || []);
            } catch (error) {
                console.error('Error fetching messages data from MongoDB: ', error);
                toast.error('Không thể tải tin nhắn.');
            }
        }

        fetchMessages();
    }, [user.userId])

    // Connect Socker server of currentUser and choosen user
    useEffect(() => {
        MessageWebSocketService.connect(currentUserId, (chatMessage) => {
            // Debug log
            // console.log(chatMessage.senderId, chatMessage.receiverId)
            // console.log(chatMessage.senderId == currentUserId, chatMessage.receiverId === user.userId)
            // console.log(chatMessage.senderId == user.userId, chatMessage.receiverId === currentUserId)
            try {
                if (
                    (chatMessage.senderId == currentUserId && chatMessage.receiverId == user.userId) ||
                    (chatMessage.senderId == user.userId && chatMessage.receiverId == currentUserId)
                ) {
                    setMessages((prevMessages) => {
                        const updatedMessages = [...prevMessages, chatMessage];
                        // Debug log
                        // console.log("Updated messages:", updatedMessages);
                        return updatedMessages;
                    });
                }
            } catch (error) {
                console.error("Failed updating messages: ", error)
            }

        })

        return () => {
            MessageWebSocketService.disconnect();
        }
    }, [currentUserId, user.userId]) // change everytime 1 of 2 userId changed


    // Handle message structure and sendMessage function
    const handleSendMessage = (e) => {
        e.preventDefault();
        if (newMessage.trim()) {
            const message = {
                senderId: currentUserId,
                receiverId: user.userId,
                content: newMessage,
                type: 'CHAT',
                timestamp: new Date().toISOString(),
            }

            // console.log("Send message: ", message)
            MessageWebSocketService.sendMessage(message);
            // Clear the input field
            setNewMessage('');
        }
    }

    // format message timestamp
    const formatTimestamp = (timestamp) => {
        moment.locale('vi');
        if (!timestamp) {
            console.warn('Timestamp is missing');
            return 'Vừa xong';
        }

        const momentDate = moment(timestamp);
        if (momentDate.isValid()) {
            return momentDate.fromNow();
        }

        console.warn('Invalid timestamp format:', timestamp);
        return 'Vừa xong';
    };

    return (
        <div className="conversation">
            <div className="conversation-header">
                <div className="header-left">
                    <img src={user.profileImageUrl || DefaultProfilePic} alt="" className="avatar" />
                    <span className="name">{user.username}</span>
                </div>
                <div className="header-right">
                    <CallIcon className="action-icon" style={{ fontSize: '28px' }} />
                    <VideocamIcon className="action-icon" style={{ fontSize: '28px' }} />
                    <MoreVertIcon className="action-icon" style={{ fontSize: '28px' }} />
                    <CloseIcon className="action-icon" onClick={onClose} />
                </div>
            </div>
            <div className="conversation-messages">
                {Array.isArray(messages) && messages.map((msg, index) => (
                    <div
                        key={index}
                        className={`message ${msg.senderId == currentUserId ? 'message-right' : 'message-left'
                            }`}
                    >
                        {msg.sender !== 'User' && (
                            <img src={user.profileImageUrl || DefaultProfilePic} alt="" className="message-avatar" />
                        )}
                        <div className="message-content">
                            <p>{msg.content}</p>
                            <span className="message-timestamp">
                                {formatTimestamp(msg.timestamp)}
                            </span>
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
                    <SendIcon style={{ fontSize: '28px' }} />
                </button>
            </form>
        </div>
    );
};

export default Conversation;