import React, { useContext } from 'react'
import './LeftBar.css'
import Friends from "../../Assets/1.png";
import Groups from "../../Assets/2.png";
import Gallery from "../../Assets/8.png";
import Videos from "../../Assets/9.png";
import Messages from "../../Assets/10.png";
import DefaultProfilePic from '../../assets/defaultProfilePic.jpg';
import { AuthContext } from '../../Context/AuthContext';
import { Link } from 'react-router';
import { getMediaUrl } from '../../Utils/Media/getMediaUrl.js'

const LeftBar = () => {

    const { currentUser, setCurrentUser } = useContext(AuthContext)


    return (
        <div className='leftBar'>
            <div className="container p-[20px]">
                <div className="menu">
                    <Link to={`/profile/${currentUser.userId}`}>
                        <div className='item font-medium'>
                            <img src={getMediaUrl(currentUser.profileImageUrl) || DefaultProfilePic} alt="" className='image rounded-full object-cover' />
                            <span className='text-[20px] text-color font-normal'>{currentUser.username}</span>
                        </div>
                    </Link>
                    <Link to={`/friends`}>
                        <div className='item'>
                            <img src={Friends} alt="" className='image' />
                            <span className='text-[20px] text-color font-normal'>Bạn bè</span>
                        </div>
                    </Link>
                    {/* <div className='item'>
                        <img src={Groups} alt="" className='image' />
                        <span className='text-[20px] text-color font-normal'>Nhóm</span>
                    </div> */}
                </div>
                <hr />
                <div className="menu flex flex-col gap-[20px]">
                    <span className='text-[20px] text-color font-normal'>Lối tắt</span>
                    <div className='item'>
                        <img src={Gallery} alt="" className='image' />
                        <span className='text-[20px] text-color font-normal'>Thư viện</span>
                    </div>
                    <div className='item'>
                        <img src={Videos} alt="" className='image' />
                        <span className='text-[20px] text-color font-normal'>Videos</span>
                    </div>
                    {/* <div className='item'>
                        <img src={Messages} alt="" className='image' />
                        <span className='text-[20px] text-color font-normal'>Tin nhắn</span>
                    </div> */}
                </div>
            </div>
        </div>
    )
}

export default LeftBar