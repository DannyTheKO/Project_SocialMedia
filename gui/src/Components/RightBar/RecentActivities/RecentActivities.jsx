import React, {useContext, useEffect, useState} from "react";
import PlaceHolderImage from "../../../Assets/login-image.jpg";
import NotificationWebSocketService from "../../../Services/NotificationService/notificationWebSocketService.jsx";
import "./RecentActivities.css"
import {notificationApi} from "../../../Services/NotificationService/notificationService.jsx";
import {AuthContext} from "../../../Context/AuthContext.jsx";
import {toast} from "react-toastify";
import {getMediaUrl} from "../../../Utils/Media/getMediaUrl.js";
import moment from "moment/moment.js";
moment.locale("vi");

const RecentActivities = () => {

    const [notifications, setNotifications] = useState([]);
    const [unreadNotifications, setUnreadNotifications] = useState(0);

    const {currentUser} = useContext(AuthContext)


    // Fetch initial notifications
    useEffect(() => {
        if (currentUser) {
            NotificationWebSocketService.connect(currentUser.userId, handleNewNotification)
            fetchNewNotifications().catch(r => console.log(r))
        }

        return () => {
            NotificationWebSocketService.disconnect()
        }
    }, [currentUser]);

    const fetchNewNotifications = async () => {
        const response = await notificationApi.getUserNotification();
        if (response.message === "Success") {
            setNotifications([...response.data] || []);
        } else {
            setNotifications([]);
            toast.error("Cannot Fetch Notification...")
        }
    }

    const handleNewNotification = (notification) => {
        try {
            console.log(typeof currentUser.userId)
            console.log(typeof notification.senderId)
            // Check if the current user is the sender of the notification
            if (notification.senderId !== parseInt(currentUser.userId)) {
                console.log("Received notification", notification.content);
                // Add the notification to the list
                setNotifications(prev => [...prev, notification]);
                // Display the notification content
                toast.info( `${notification.senderName} ${notification.content}`);
            }
        } catch (error) {
            console.error("Error handling notification:", error);
        }
    }



    return (
        <div className="recentActivities-container">
            <span className="recentActivities-display-title">Hoạt động gần đây</span>
            {notifications.map((notification, index) => (
                <div key={index} className="recentActivities-display-user">
                    <div className="recentActivities-display-userInfo">
                        <img src={getMediaUrl(notification.senderImageUrl)} alt="" className='avatar' />
                        <p>
                            <span className="recentActivities-display-username">{notification.senderName}</span>
                            <span className="recentActivities-display-content">{notification.content}</span>
                        </p>
                    </div>
                    {console.log(notification.createdAt)}
                    <span className='time-color'>{moment(notification.createdAt).fromNow()}</span>
                </div>
            ))}
        </div>
    )
}

export default RecentActivities