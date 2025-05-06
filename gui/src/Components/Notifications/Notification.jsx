import React, {useContext, useEffect, useState} from 'react'
import MoreHorizOutlinedIcon from '@mui/icons-material/MoreHorizOutlined';
import './Notification.css'
import {AuthContext} from "../../Context/AuthContext.jsx";
import {notificationApi} from "../../Services/NotificationService/notificationService.jsx";
import NotificationWebSocketService from "../../Services/NotificationService/notificationWebSocketService.jsx";
import {getMediaUrl} from "../../Utils/Media/getMediaUrl.js";
import moment from "moment/moment.js";

moment.locale('vi');

const Notification = () => {

    const notificationWebSocket = new NotificationWebSocketService();
    const {currentUser} = useContext(AuthContext);
    const [unreadNotifications, setUnreadNotifications] = useState([]);
    const [seenNotifications, setSeenNotifications] = useState([]);
    const [unReadFilter, setUnreadFilter] = useState(false);

    useEffect(() => {
        if (currentUser) {
            fetchNewNotification().catch(r => console.error(r));
            notificationWebSocket.connect(currentUser.userId, handleNewNotification);
        }

        return () => {
            notificationWebSocket.disconnect();
        }
    }, [currentUser]);

    // TODO:
    //  - Filter unread notification
    //  - Show all notification

    const fetchNewNotification = async () => {
        const response = await notificationApi.getUserNotification();
        console.log(response);
        if (response.message === "Success") {
            const unread = response.data.filter((data) => !data.isRead);
            const seen = response.data.filter((data) => data.isRead);

            setUnreadNotifications(unread || []);
            setSeenNotifications(seen || []);
        } else {
            setUnreadNotifications([])
            console.error("Cannot Fetch Notification...")
        }
    }

    const handleNewNotification = (notification) => {
        try {
            if (notification.senderId !== parseInt(currentUser.userId)) {
                setUnreadNotifications(prevState => [...prevState, notification]);
            }
        } catch (error) {
            console.error(error);
            setUnreadNotifications([])
        }
    }

    const sortUnreadNotificationsByDate = unreadNotifications.sort((a, b) => {
        return new Date(b.createdAt) - new Date(a.createdAt);
    })

    const sortSeenNotificationsByDate = seenNotifications.sort((a, b) => {
        return new Date(b.createdAt) - new Date(b.createdAt);
    })

    const markAsRead = async (notificationId) => {
        await notificationApi.markAsRead(notificationId)
        await fetchNewNotification()
    }
    const markAsReadAll = async () => {
        await notificationApi.markAllAsRead()
        await fetchNewNotification()
    }

    const deleteNotification = async (notificationId) => {
        await notificationApi.deleteNotification(notificationId)
        await fetchNewNotification()
    }

    const deleteAllNotifications = async () => {
        await notificationApi.deleteAllNotifications()
        await fetchNewNotification()
    }


    return (
        <div className='chat'>
            <div className="header flex justify-between items-center p-2">
                <div className="left text-[32px] font-bold text-black dark:text-white">
                    Notifications
                </div>
                <div className="right flex gap-[15px] items-center dark:text-white">
                    <MoreHorizOutlinedIcon/>
                </div>
            </div>
            <div className="flex gap-[15px] items-center mx-[10px]">
                <button
                    onClick={() => markAsReadAll()}
                    className='bg-gray-300 text-black dark:bg-[#4a4a4a] hover:bg-gray-500 dark:text-white px-4 py-2 rounded-3xl font-medium text-[18px] cursor-pointer'>
                    Đọc Tất Cả
                </button>
                <button
                    onClick={() => deleteAllNotifications()}
                    className='bg-gray-300 text-black dark:bg-[#4a4a4a] hover:bg-gray-500 dark:text-white px-4 py-2 rounded-3xl font-medium text-[18px] cursor-pointer'>
                    Xoá Tất Cả
                </button>
            </div>
            {/*  Notifications Component */}
            {/* Sort By Date */}

            {/*Unread Notification*/}
            {sortUnreadNotificationsByDate.map((notification) => (
                <div className="notifications flex flex-col gap-[8px] p-[5px] cursor-pointer"
                     onClick={() => markAsRead(notification.notificationId)}>
                    <div className="notification flex justify-between items-center">
                        <div className='flex gap-[15px] mr-[10px]'>
                            <img src={getMediaUrl(notification.senderImageUrl)} alt=""
                                 className='w-[60px] h-[60px] rounded-full object-cover object-center'/>
                            <div className="content flex flex-col">
                                <div className='notification-info items-center'>
                                <span
                                    className="lastest-msg font-medium text-[20px] text-gray-700 dark:text-[lightgray]">
                                    <b className='text-black dark:text-gray-300'>{notification.senderName}</b> {notification.content}
                                </span>
                                </div>
                                <p className="date font-medium text-[20px] text-blue-500">{moment(notification.createdAt).fromNow()}</p>
                            </div>
                        </div>
                        <div className="blue-dot w-[20px] h-[20px] bg-blue-500 rounded-full"></div>
                    </div>
                </div>
            ))}

            {/*Seen Notification*/}
            {sortSeenNotificationsByDate.map((notification) => (
                <div className="notification flex justify-between items-center"
                     onClick={() => deleteNotification(notification.notificationId)}>
                    <div className='flex gap-[15px] mr-[10px]'>
                        <img src={getMediaUrl(notification.senderImageUrl)} alt=""
                             className='w-[60px] h-[60px] rounded-full object-cover object-center'/>
                        <div className="content flex flex-col">
                            <div className='notification-info items-center'>
                                <span
                                    className="lastest-msg font-medium text-[20px] text-gray-700 dark:text-[lightgray]">
                                    <b className='text-black dark:text-gray-300'>{notification.senderName}</b> {notification.content}
                                </span>
                            </div>
                            <p className="date font-medium text-[20px] text-gray-500">{moment(notification.createdAt).fromNow()}</p>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    )
}

export default Notification