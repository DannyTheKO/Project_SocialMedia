import React, { useState, useEffect } from 'react';
import DefaultProfilePic from '../../../assets/defaultProfilePic.jpg';
import { getMediaUrl } from '../../../Utils/Media/getMediaUrl';
import { userApi } from '../../../Services/UserService/userService';

const ConversationAvatar = ({ msg }) => {
    const [avatarUrl, setAvatarUrl] = useState(DefaultProfilePic);

    const getUserAvatar = async (userId) => {
        try {
            const response = await userApi.getUserById(userId);
            if (response.message === 'Success') {
                console.log(response.data.profileImageUrl);
                return response.data.profileImageUrl;
            }
            return null;
        } catch (error) {
            console.error('Failed loading user avatar: ', error);
            return null;
        }
    };

    useEffect(() => {
        if (msg.sender !== 'User') {
            getUserAvatar(msg.senderId).then((url) => {
                setAvatarUrl(url ? getMediaUrl(url) : DefaultProfilePic);
            });
        }
    }, [msg.sender, msg.senderId]);

    return (
        <img src={avatarUrl} alt="" className="message-avatar" />
    );
};

export default ConversationAvatar;