import React, {useContext, useEffect, useState} from "react";
import NotificationWebSocketService from "../../../Services/NotificationService/notificationWebSocketService.jsx";
import "./RecentActivities.css"
import {notificationApi} from "../../../Services/NotificationService/notificationService.jsx";
import {AuthContext} from "../../../Context/AuthContext.jsx";
import {toast} from "react-toastify";
import {getMediaUrl} from "../../../Utils/Media/getMediaUrl.js";
import moment from "moment/moment.js";
moment.locale("vi");

const RecentActivities = () => {

    const recentActivitiesWebSocket = new NotificationWebSocketService()
    const [notifications, setNotifications] = useState([]);
    const [unreadNotifications, setUnreadNotifications] = useState(0);

    const {currentUser} = useContext(AuthContext)


    // Fetch initial notifications
    useEffect(() => {
        if (currentUser) {
            recentActivitiesWebSocket.connect(currentUser.userId, handleNewNotification)
            fetchNewNotifications().catch(r => console.log(r))
        }

        return () => {
            recentActivitiesWebSocket.disconnect()
        }
    }, [currentUser]);

    const fetchNewNotifications = async () => {
        const response = await notificationApi.getUserNotification();
        if (response.message === "Success") {
            setNotifications([...response.data] || []);
        } else {
            setNotifications([]);
            console.error("Cannot Fetch Notification...")
        }
    }

    const handleNewNotification = (notification) => {
        try {
            // DEBUG LOG
            // console.log(typeof currentUser.userId)
            // console.log(typeof notification.senderId)

            // Check if the current user is the sender of the notification
            if (notification.senderId !== parseInt(currentUser.userId)) {
                // console.log("Received notification", notification.content);
                // Add the notification to the list
                setNotifications(prev => [...prev, notification]);
                // Display the notification content
                toast.info( `${notification.senderName} ${notification.content}`);
            }
        } catch (error) {
            console.error("Error handling notification:", error);
        }
    }

    const handleDeleteNotification = async (notificationId) => {
        await notificationApi.deleteNotification(notificationId)
        await fetchNewNotifications()
    }

    const getTopThreeRecentNotifications = (notifications) => {
        const sortedNotifications = notifications.sort((a, b) => {
            return new Date(b.createdAt) - new Date(a.createdAt);
        });
        return sortedNotifications.slice(0, 3);
    };

    const topThreeNotifications = getTopThreeRecentNotifications(notifications);


    if (notifications.length > 0) {
        return (
            <div className="recentActivities-container">
                <span className="recentActivities-display-title">Hoạt động gần đây</span>
                {topThreeNotifications.map((notification, index) => (
                    <div key={index} className="recentActivities-display-user" onClick={() => handleDeleteNotification(notification.notificationId)}>
                        <div className="recentActivities-display-userInfo">
                            <img src={getMediaUrl(notification.senderImageUrl)} alt="" className='avatar' />
                            <p>
                                <span className="recentActivities-display-username">{notification.senderName}</span>
                                <span className="recentActivities-display-content">{notification.content}</span>
                            </p>
                        </div>
                        <span className='time-color'>{moment(notification.createdAt).fromNow()}</span>
                    </div>
                ))}
            </div>
        )

    } else {
        return (
            <div className="recentActivities-container">
                <span className="recentActivities-display-title">Không có hoạt động</span>
            </div>
        );
    }

}

export default RecentActivities