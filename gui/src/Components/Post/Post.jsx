import React, { useEffect, useState } from 'react';
import './Post.css';
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import FavoriteOutlinedIcon from "@mui/icons-material/FavoriteOutlined";
import TextsmsOutlinedIcon from "@mui/icons-material/TextsmsOutlined";
import ShareOutlinedIcon from "@mui/icons-material/ShareOutlined";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import { Link } from "react-router-dom";
import Comments from '../Comments/Comments';
import DefaultProfilePic from '../../assets/defaultProfilePic.jpg';
import moment from 'moment/moment';
import 'moment/locale/vi';

moment.locale('vi');

const Post = ({ user, postId, content, comments, likes, media, createdPost, modifiedPost }) => {
    // Đóng mở khung bình luận
    const [commentOpen, setCommentOpen] = useState(false);

    // Handle slider nếu post có nhiều ảnh
    const [currentIndex, setCurrentIndex] = useState(0);

    // Viết hàm xác định người dùng đã like post này hay chưa
    const liked = false;

    // Hàm kiểm tra file ảnh hay là video
    const isVideo = (filePath) => {
        return filePath && filePath.toLowerCase().endsWith('.mp4');
    };

    // Hàm get image từ url public
    const getImageUrl = (filePath) => {
        console.log("a")
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
            console.log("Generated Image URL:", fullUrl);
            return fullUrl;
        } catch (error) {
            console.error("Lỗi khi tạo URL ảnh:", error, "FilePath:", filePath);
            return DefaultProfilePic;
        }
    };

    // Handler của nút prevSlider
    const handlePrev = () => {
        setCurrentIndex((prevIndex) => (prevIndex === 0 ? media.length - 1 : prevIndex - 1));
    };

    // Handler của nút nextSlider
    const handleNext = () => {
        setCurrentIndex((prevIndex) => (prevIndex === media.length - 1 ? 0 : prevIndex + 1));
    };

    return (
        <div className="post">
            <div className="container">
                <div className="user">
                    <div className="userInfo">
                        <img
                            src={getImageUrl(user?.profileImageUrl) || DefaultProfilePic}
                            alt="Profile"
                        />
                        <div className="details">
                            <Link to={`/profile/${user?.userId}`}>
                                <span className="name">{user?.username || 'Unknown'}</span>
                            </Link>
                            <span className="date">
                                {moment(createdPost).fromNow()}
                            </span>
                        </div>
                    </div>
                    <MoreHorizIcon style={{ cursor: 'pointer', fontSize: '32px' }} />
                </div>
                <div className="content">
                    <p>{content}</p>
                    {media && media.length > 0 && (
                        <div className="media-gallery relative">
                            {media.length === 1 ? (
                                // Nếu chỉ có 1 media, hiển thị tĩnh
                                isVideo(media[0].filePath) ? (
                                    <video
                                        src={getImageUrl(media[0].filePath)}
                                        controls
                                        className="w-full"
                                    />
                                ) : (
                                    <img
                                        src={getImageUrl(media[0].filePath)}
                                        alt="Post media"
                                        className="w-full"
                                    />
                                )
                            ) : (
                                // Nếu có nhiều hơn 1 media, hiển thị slider
                                <div className="relative overflow-hidden">
                                    {/* Container chứa tất cả media */}
                                    <div
                                        className="flex transition-transform duration-500 ease-in-out"
                                        style={{
                                            transform: `translateX(-${currentIndex * 100}%)`,
                                        }}
                                    >
                                        {media.map((item, index) => (
                                            <div
                                                key={index}
                                                className="w-full flex-shrink-0"
                                            >
                                                {isVideo(item.filePath) ? (
                                                    <video
                                                        src={getImageUrl(item.filePath)}
                                                        controls
                                                        className="w-full"
                                                    />
                                                ) : (
                                                    <img
                                                        src={getImageUrl(item.filePath)}
                                                        alt={`Post media ${index + 1}`}
                                                        className="w-full"
                                                    />
                                                )}
                                            </div>
                                        ))}
                                    </div>
                                    {/* Nút Prev */}
                                    <button
                                        onClick={handlePrev}
                                        className="absolute left-2 top-1/2 transform -translate-y-1/2 bg-gray-800 text-white p-4 rounded-full hover:opacity-50 text-[36px] cursor-pointer"
                                    >
                                        ←
                                    </button>
                                    {/* Nút Next */}
                                    <button
                                        onClick={handleNext}
                                        className="absolute right-2 top-1/2 transform -translate-y-1/2 bg-gray-800 text-white p-4 rounded-full hover:opacity-50 text-[36px] cursor-pointer"
                                    >
                                        →
                                    </button>
                                    {/* Hiển thị số thứ tự media */}
                                    <div className="absolute top-6 right-1 transform -translate-y-[-1/2] bg-gray-800 text-white px-5 py-3 rounded-full">
                                        {currentIndex + 1} / {media.length}
                                    </div>
                                </div>
                            )}
                        </div>
                    )}
                </div>
                <div className="info">
                    <div className="item">
                        {liked ? (
                            <FavoriteOutlinedIcon style={{ color: "red" }} />
                        ) : (
                            <FavoriteBorderOutlinedIcon />
                        )}
                        <p>{likes?.length || 0} Likes</p>
                    </div>
                    <div className="item" onClick={() => setCommentOpen(!commentOpen)}>
                        <TextsmsOutlinedIcon />
                        {comments?.length || 0} Comments
                    </div>
                    <div className="item">
                        <ShareOutlinedIcon />
                        Share
                    </div>
                </div>
                {commentOpen && <Comments postId={postId} isVideo={isVideo} getImageUrl={getImageUrl} />}
            </div>
        </div>
    );
};

export default Post;