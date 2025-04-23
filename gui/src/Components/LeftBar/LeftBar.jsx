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

const LeftBar = () => {

    const { currentUser, setCurrentUser } = useContext(AuthContext)

    const getImageUrl = (filePath) => {
        if (!filePath) return DefaultProfilePic;

        const baseUrl = "http://localhost:8080";

        try {
            // Thử split với "uploads\", nếu không được thì thử với "uploads\\"
            let relativePath = filePath.split("uploads\\")[1] || filePath.split("uploads\\\\")[1];

            // Nếu không split được, trả về ảnh mặc định
            if (!relativePath) {
                console.warn("Không thể parse đường dẫn ảnh:", filePath);
                return DefaultProfilePic;
            }

            // Thay tất cả dấu \ thành / để tạo URL hợp lệ
            const cleanPath = relativePath.replace(/\\/g, "/");

            // Tạo URL public
            const fullUrl = `${baseUrl}/uploads/${cleanPath}`;
            return fullUrl;
        } catch (error) {
            console.error("Lỗi khi tạo URL ảnh:", error, "FilePath:", filePath);
            return DefaultProfilePic;
        }
    };


    return (
        <div className='leftbar'>
            <div className="container p-[20px]">
                <div className="menu">
                    <Link to={`/profile/${currentUser.userId}`}>
                        <div className='item font-medium'>
                            <img src={getImageUrl(currentUser.profileImageUrl) || DefaultProfilePic} alt="" className='image rounded-full object-cover' />
                            <span className='text-[20px] text-color font-normal'>{currentUser.username}</span>
                        </div>
                    </Link>
                    <div className='item'>
                        <img src={Friends} alt="" className='image' />
                        <span className='text-[20px] text-color font-normal'>Bạn bè</span>
                    </div>
                    <div className='item'>
                        <img src={Groups} alt="" className='image' />
                        <span className='text-[20px] text-color font-normal'>Nhóm</span>
                    </div>
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
                    <div className='item'>
                        <img src={Messages} alt="" className='image' />
                        <span className='text-[20px] text-color font-normal'>Tin nhắn</span>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LeftBar