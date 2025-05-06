import React, { useContext, useEffect, useState } from 'react'
import './RightBar.css'
import PlaceHolderImage from '../../Assets/login-image.jpg'
import DefaultProfilePic from '../../Assets/defaultProfilePic.jpg';
import { relationshipsApi } from '../../Services/RelationshipsService/relationshipsService';
import { Link } from 'react-router';
import RecentActivities from "./RecentActivities/RecentActivities.jsx"
import { getMediaUrl } from '../../Utils/Media/getMediaUrl.js';
import moment from 'moment/moment.js';
import 'moment/locale/vi'

moment.locale('vi');

const RightBar = () => {

    const [friends, setFriends] = useState([]);
    const [error, setError] = useState('')
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const fetchFriends = async () => {
        try {
            const response = await relationshipsApi.getFriends();
            const data = response.data; // ApiResponse { data: List<RelationshipsDTO> }
            // Debug log
            // console.log(data);
            setFriends(data);
            setError(null);
        } catch (err) {
            setError('Không thể tải danh sách bạn bè.');
            console.error(err);
        }
    };

    useEffect(() => {
        fetchFriends();

        // console.log("Friends đã cập nhật:", friends);
    }, [])

    return (
        <div className='rightBar'>
            <div className="container p-[20px]">
                <RecentActivities />

                <div className="item">
                    <span className='time-color'>Bạn bè</span>
                    {friends.map((friend) => (
                        <Link to={`profile/${friend.user2.userId}`}>
                            <div className='user'>
                                <div className="userInfo relative">
                                    <img src={getMediaUrl(friend.user2.profileImageUrl) || DefaultProfilePic} alt="" className='avatar' />
                                    {moment(friend.user2.lastLogin).fromNow() == 'vài giây trước' && (<div className='green-dot' />)}
                                    <span className='text-[20px] text-color font-medium'>{friend.user2.username} </span>
                                </div>
                                <p>{moment(friend.user2.lastLogin).fromNow() == 'vài giây trước' ? 'Đang hoạt động' : moment(friend.user2.lastLogin).fromNow()}</p>
                            </div>
                        </Link>
                    ))}
                </div>

            </div>
        </div>
    )
}

export default RightBar