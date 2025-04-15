import React, {useState} from 'react'
import DefaultProfilePic from '../../../Assets/defaultProfilePic.jpg';
import './Message.css'
import Conversation from '../../Conversations/Conversation';

const Message = () => {

    const [openConversation, setOpenConversation] = useState(false)
    const [selectedUser, setSelectedUser] = useState(null)

    const handleOpenConversation = (user) => {
        setSelectedUser(user);
        setOpenConversation(true);
    }

    const handleCloseConversation = () => {
        setOpenConversation(false);
        setSelectedUser(null);
    }

    // Sample data cho các cuộc hội thoại
    const conversations = [
        {
            id: 1,
            name: 'Thái Tuấn',
            lastMessage: 'Bạn: xin chào',
            date: '7h',
            avatar: DefaultProfilePic,
            isUnread: false,
        },
        {
            id: 3,
            name: 'KO',
            lastMessage: 'KO: xin chào',
            date: '7h',
            avatar: DefaultProfilePic,
            isUnread: true,
        },
        {
            id: 4,
            name: 'Danny',
            lastMessage: 'Danny: xin chào',
            date: '7h',
            avatar: DefaultProfilePic,
            isUnread: false,
        },
    ];

    return (
        <div className={`messages flex flex-col gap-[10px] p-[5px] cursor-pointer`}>
            {conversations.map((conv) => (
                <div
                    key={conv.id}
                    className="message flex justify-between items-center p-2 cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-700"
                    onClick={() => handleOpenConversation(conv)}
                >
                    <div className="flex gap-[15px]">
                        <img
                            src={conv.avatar}
                            alt=""
                            className="w-[60px] h-[60px] rounded-full object-cover object-center"
                        />
                        <div className="content flex flex-col">
                            <p className="name font-medium text-[20px] dark:text-white">{conv.name}</p>
                            <div className="message-info flex items-center gap-[10px]">
                                <p
                                    className={`lastest-msg font-medium text-[20px] ${conv.isUnread
                                        ? 'text-black dark:text-white'
                                        : 'text-gray-500 dark:text-gray-400'
                                    }`}
                                >
                                    {conv.lastMessage}
                                </p>
                                <p
                                    className={`date font-medium text-[20px] ${conv.isUnread
                                        ? 'text-black dark:text-white'
                                        : 'text-gray-500 dark:text-gray-400'
                                    }`}
                                >
                                    {conv.date}
                                </p>
                            </div>
                        </div>
                    </div>
                    {conv.isUnread && (
                        <div className="blue-dot w-[20px] h-[20px] bg-blue-500 rounded-full"></div>
                    )}
                </div>
            ))}

            {/* Hiển thị khung chat khi click vào một cuộc hội thoại */}
            {openConversation && selectedUser && (
                <Conversation user={selectedUser} onClose={handleCloseConversation}/>
            )}
        </div>
    );
}

export default Message