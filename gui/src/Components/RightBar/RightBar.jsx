import React, { useEffect, useState } from 'react'
import './RightBar.css'
import PlaceHolderImage from '../../Assets/login-image.jpg'
import DefaultProfilePic from '../../Assets/defaultProfilePic.jpg';
import { relationshipsApi } from '../../Services/RelationshipsService/relationshipsService';
import { Link } from 'react-router';

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
            console.log(data);
            setFriends(data);
            setError(null);
        } catch (err) {
            setError('Không thể tải danh sách bạn bè.');
            console.error(err);
        }
    };

    useEffect(() => {
        fetchFriends();

        console.log("Friends đã cập nhật:", friends);
    }, [])

    return (
        <div className='rightbar'>
            <div className="container p-[20px]">
                <div className="item">
                    <span className='time-color'>Hoạt động gần đây</span>
                    <div className='user'>
                        <div className="userInfo max-xl:text-[16px]">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <p>
                                <span className='text-[20px] text-color font-medium'>Tuan Thai </span>
                                changed their cover picture
                            </p>
                        </div>
                        <span className='time-color'> 1 min ago </span>
                    </div>
                    <div className='user'>
                        <div className="userInfo">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <p>
                                <span className='text-[20px] text-color font-medium'>Tuan Thai </span>
                                liked a post
                            </p>
                        </div>
                        <span className='time-color'> 1 min ago </span>
                    </div>
                    <div className='user'>
                        <div className="userInfo">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <p>
                                <span className='text-[20px] text-color font-medium'>Tuan Thai </span>
                                posted
                            </p>
                        </div>
                        <span className='time-color'> 1 min ago </span>
                    </div>
                </div>

                <div className="item">
                    <span className='time-color'>Bạn bè online</span>
                    {friends.map((friend) => (
                        <Link to={`profile/${friend.user2.userId}`}>
                            <div className='user'>
                                <div className="userInfo relative">
                                    <img src={friend.user2.profileImageUrl || DefaultProfilePic} alt="" className='avatar' />
                                    <div className='green-dot' />
                                    <span className='text-[20px] text-color font-medium'>{friend.user2.username} </span>
                                </div>
                            </div>
                        </Link>
                    ))}
                </div>

            </div>
        </div>
    )
}

export default RightBar