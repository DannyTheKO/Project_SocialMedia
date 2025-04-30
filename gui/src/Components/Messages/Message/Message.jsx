import React, { useEffect, useState } from 'react'
import DefaultProfilePic from '../../../Assets/defaultProfilePic.jpg';
import './Message.css'
import Conversation from '../../Conversations/Conversation';
import { relationshipsApi } from '../../../Services/RelationshipsService/relationshipsService';

const Message = () => {

    const [openConversation, setOpenConversation] = useState(false)
    const [selectedUser, setSelectedUser] = useState(null)
    const [friends, setFriends] = useState([]);

    const handleOpenConversation = (user) => {
        setSelectedUser(user);
        setOpenConversation(true);
    }

    const handleCloseConversation = () => {
        setOpenConversation(false);
        setSelectedUser(null);
    }

    // Sample data cho các cuộc hội thoại
    // const conversations = [
    //     {
    //         id: 2,
    //         name: 'KO',
    //         lastMessage: 'KO: xin chào',
    //         date: '7h',
    //         avatar: DefaultProfilePic,
    //         isUnread: true,
    //     },
    //     {
    //         id: 1,
    //         name: 'Tuan',
    //         lastMessage: 'KO: xin chào',
    //         date: '7h',
    //         avatar: DefaultProfilePic,
    //         isUnread: true,
    //     },
    // ];

    const fetchFriends = async () => {
        try {
            const response = await relationshipsApi.getFriends();
            const data = response.data; // ApiResponse { data: List<RelationshipsDTO> }
            // Debug log
            console.log(data);
            setFriends(data);
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchFriends()
    }, [])

    return (
        <div className={`messages flex flex-col gap-[10px] p-[5px] cursor-pointer`}>
            {friends.map((friend) => (
                <div
                    key={friend.relationshipsId}
                    className="message flex justify-between items-center p-2 cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-700"
                    onClick={() => handleOpenConversation(friend.user2)}
                >
                    <div className="flex gap-[15px]">
                        <img
                            src={friend.user2.profileImageUrl || DefaultProfilePic}
                            alt=""
                            className="w-[60px] h-[60px] rounded-full object-cover object-center"
                        />
                        <div className="content flex flex-col">
                            <p className="name font-medium text-[20px] dark:text-white">{friend.user2.username}</p>
                            <div className="message-info flex items-center gap-[10px]">
                                {/* <p
                                    className={`lastest-msg font-medium text-[20px] ${conv.isUnread
                                        ? 'text-black dark:text-white'
                                        : 'text-gray-500 dark:text-gray-400'
                                        }`}
                                >
                                    {conv.lastMessage}
                                </p> */}
                                <p
                                    className='lastest-msg font-medium text-[20px] text-gray-500 dark:text-gray-400'
                                >
                                    Cùng trò chuyện nào !
                                </p>

                                {/* <p
                                    className={`date font-medium text-[20px] ${conv.isUnread
                                        ? 'text-black dark:text-white'
                                        : 'text-gray-500 dark:text-gray-400'
                                        }`}
                                >
                                    {conv.date}
                                </p> */}

                                <p className='text-gray-500 dark:text-gray-400'></p>
                            </div>
                        </div>
                    </div>
                    {/* {conv.isUnread && (
                        <div className="blue-dot w-[20px] h-[20px] bg-blue-500 rounded-full"></div>
                    )} */}
                    <div className="blue-dot w-[20px] h-[20px] bg-blue-500 rounded-full"></div>
                </div>
            ))}

            {/* Hiển thị khung chat khi click vào một cuộc hội thoại */}
            {openConversation && selectedUser && (
                <Conversation user={selectedUser} onClose={handleCloseConversation} />
            )}
        </div>
    );
}

export default Message