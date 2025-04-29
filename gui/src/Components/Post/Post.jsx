import React, { useContext, useState } from 'react';
import './Post.css';
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import FavoriteOutlinedIcon from "@mui/icons-material/FavoriteOutlined";
import TextsmsOutlinedIcon from "@mui/icons-material/TextsmsOutlined";
import ShareOutlinedIcon from "@mui/icons-material/ShareOutlined";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import EditSquareIcon from '@mui/icons-material/EditSquare';
import DeleteIcon from '@mui/icons-material/Delete';
import { Link } from "react-router-dom";
import Comments from '../Comments/Comments';
import DefaultProfilePic from '../../Assets/defaultProfilePic.jpg';
import moment from 'moment/moment.js';
import 'moment/locale/vi';
import { postApi } from '../../Services/PostService/postService';
import { AuthContext } from '../../Context/AuthContext';
import { toast } from 'react-toastify';
import { getMediaUrl } from '../../Utils/Media/getMediaUrl.js';
import { isVideo } from '../../Utils/Media/checkFileType.js';

moment.locale('vi');

const Post = ({ user, postId, content, comments, likes, media, createdPost, modifiedPost, onHidePost, onEditPost }) => {
    // FIXME: Fix the damn CSS on this Post Component

    const { currentUser, setCurrentUser } = useContext(AuthContext)

    // Đóng mở khung bình luận
    const [commentOpen, setCommentOpen] = useState(false);

    const [commentAmount, setCommentAmount] = useState(comments?.length || 0);

    // Handle slider nếu post có nhiều ảnh
    const [currentIndex, setCurrentIndex] = useState(0);

    // Hàm xác định người dùng đã like post này hay chưa
    const liked = false;

    // State dropdown
    const [showDropDown, setShowDropDown] = useState(false);

    // Handler của nút prevSlider
    const handlePrev = () => {
        setCurrentIndex((prevIndex) => (prevIndex === 0 ? media.length - 1 : prevIndex - 1));
    };

    // Handler của nút nextSlider
    const handleNext = () => {
        setCurrentIndex((prevIndex) => (prevIndex === media.length - 1 ? 0 : prevIndex + 1));
    };

    const handleEditPost = () => {
        // Debug log
        console.log("Edit post: ", { postId, content, media })
        onEditPost({ postId, content, media }) // callback to parent
        setShowDropDown(false); // hide dropdown after select "Edit Post"
    }

    const handleDeletePost = async () => {
        try {
            const response = await postApi.deletePost(postId);
            if (response.message === "Success") {
                onHidePost(postId);
                toast.success("Bài viết đã được xóa thành công!");
            } else {
                toast.error("Xóa bài viết thất bại");
                console.log(response.message)
            }
        } catch (error) {
            console.error("Error deleting post:", error);
            toast.error("Đã có lỗi xảy ra khi xóa bài viết!");
        }
        setShowDropDown(false); // Ẩn dropdown sau khi ẩn
    };

    const handleHidePost = async () => {
        try {
            const response = await postApi.hidePost(postId);
            if (response.message === "Success") {
                onHidePost(postId);
                toast.success("Bài viết đã được ẩn thành công!");
            } else {
                toast.error("Ẩn bài viết thất bại");
                console.log(response.message)
            }
        } catch (error) {
            console.error("Error deleting post:", error);
            toast.error("Đã có lỗi xảy ra khi ẩn bài viết!");
        }
        setShowDropDown(false); // Ẩn dropdown sau khi ẩn
    };

    const onPostComment = () => {
        setCommentAmount((prev) => prev + 1);
    };

    return (
        <div className="post">
            <div className="container">
                <div className="user">
                    <div className="userInfo">
                        <img
                            src={getMediaUrl(user?.profileImageUrl) || DefaultProfilePic}
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
                    <div className="relative"
                        onClick={() => setShowDropDown(!showDropDown)}>
                        <MoreHorizIcon style={{ cursor: 'pointer', fontSize: '32px' }} />
                        {/* Dropdown menu */}
                        {showDropDown && (
                            <div className="absolute right-0 mt-1 w-96 bg-neutral-800 text-white rounded-lg shadow-lg z-50 text-[20px]">
                                {user.userId === currentUser.userId && (
                                    <button
                                        onClick={handleEditPost}
                                        className="w-full text-left px-4 py-2 hover:bg-neutral-700 rounded-t-lg flex gap-[10px] items-center cursor-pointer"
                                    >
                                        <EditSquareIcon style={{ fontSize: "24px" }} />
                                        Chỉnh sửa bài viết
                                    </button>
                                )}
                                <button
                                    onClick={handleDeletePost}
                                    className="w-full text-left px-4 py-2 hover:bg-neutral-700 rounded-b-lg flex gap-[10px] items-center text-red-500 cursor-pointer"
                                >
                                    <DeleteIcon style={{ fontSize: "24px" }} />
                                    Xóa bài viết
                                </button>
                            </div>
                        )}
                    </div>
                </div>
                <div className="content">
                    <p>{content}</p>
                    {media && media.length > 0 && (
                        <div className="media-gallery relative my-[20px]">
                            {media.length === 1 ? (
                                // Nếu chỉ có 1 media, hiển thị tĩnh
                                isVideo(media[0].filePath) ? (
                                    <video
                                        src={getMediaUrl(media[0].filePath)}
                                        controls
                                        className="w-full max-h-[500px] object-cover object-center rounded-md"
                                    />
                                ) : (
                                    <img
                                        src={getMediaUrl(media[0].filePath)}
                                        alt="/"
                                        className="w-full max-h-[500px] object-cover object-center rounded-md"
                                    />
                                )
                            ) : (
                                // Nếu có nhiều hơn 1 media, hiển thị slider
                                <div className="relative overflow-hidden">
                                    {/* Container chứa tất cả media */}
                                    <div
                                        className="flex transition-transform duration-250 ease-in-out"
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
                                                        src={getMediaUrl(item.filePath)}
                                                        controls
                                                        className="w-full max-h-[500px] object-cover object-center rounded-md"
                                                    />
                                                ) : (
                                                    <img
                                                        src={getMediaUrl(item.filePath)}
                                                        alt={`Image: ${index + 1}`}
                                                        className="w-full max-h-[500px] object-cover object-center rounded-md"
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
                                    <div
                                        className="absolute top-3 right-2 transform -translate-y-[-1/2] bg-gray-800 text-white px-5 py-3 rounded-full">
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
                        {commentAmount} Comments
                    </div>
                    <div className="item">
                        <ShareOutlinedIcon />
                        Share
                    </div>
                </div>
                {commentOpen && <Comments postId={postId} isVideo={isVideo} getMediaUrl={getMediaUrl} onPostComment={onPostComment} />}
            </div>
        </div>
    );
};

export default Post;