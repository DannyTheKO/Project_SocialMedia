import React, { useContext, useEffect, useState } from 'react';
import './SharedPost.css';
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
import { sharedPostApi } from '../../Services/SharedPostService/sharedPostService';
import { likeApi } from '../../Services/LikeService/likeService'; // Đảm bảo import likeApi
import { AuthContext } from '../../Context/AuthContext';
import { toast } from 'react-toastify';
import { getMediaUrl } from '../../Utils/Media/getMediaUrl.js';
import { isVideo } from '../../Utils/Media/checkFileType.js';

moment.locale('vi');

const SharedPost = ({ user, sharedPostId, originalPost, sharedContent, comments, likes, createdAt, onHidePost, onEditPost }) => {
    const { currentUser } = useContext(AuthContext);
    const [commentOpen, setCommentOpen] = useState(false);
    const [commentAmount, setCommentAmount] = useState(comments?.length || 0);
    const [liked, setLiked] = useState(false);
    const [likeCount, setLikeCount] = useState(likes?.length || 0);
    const [showDropDown, setShowDropDown] = useState(false);
    const [currentIndex, setCurrentIndex] = useState(0);

    useEffect(() => {
        const fetchLikeData = async () => {
            try {
                const countResponse = await likeApi.getLikesCountBySharedPostId(sharedPostId);
                if (countResponse.message === 'Success') {
                    setLikeCount(countResponse.data || 0); // Đảm bảo không bị undefined
                }

                const likesResponse = await likeApi.getAllLikesBySharedPostId(sharedPostId);
                if (likesResponse.message === 'Success') {
                    const likesData = likesResponse.data || [];
                    // // Debug log
                    // console.log(likesData[0].userId);
                    const userHasLiked = likesData.some(like => like.userId == currentUser.userId);
                    setLiked(userHasLiked);
                }
            } catch (error) {
                console.error('Error fetching like data:', error);
                toast.error('Không thể tải dữ liệu lượt thích!');
            }
        };
        fetchLikeData();
    }, [sharedPostId, currentUser.userId]);

    const handleToggleLike = async () => {
        try {
            const response = await likeApi.toggleLike(null, null, sharedPostId); // Sử dụng sharedPostId
            if (response.message === 'Success') {
                setLiked(!liked);
                setLikeCount(prev => liked ? prev - 1 : prev + 1);
                toast.success(liked ? 'Đã bỏ thích bài viết!' : 'Đã thích bài viết!');
            } else {
                toast.error('Thao tác thất bại!');
            }
        } catch (error) {
            console.error('Error toggling like:', error);
            toast.error('Đã có lỗi xảy ra khi thích bài viết!');
        }
    };

    const handlePrev = () => {
        setCurrentIndex((prevIndex) => (prevIndex === 0 ? (originalPost.media?.length || 0) - 1 : prevIndex - 1));
    };

    const handleNext = () => {
        setCurrentIndex((prevIndex) => (prevIndex === (originalPost.media?.length || 0) - 1 ? 0 : prevIndex + 1));
    };

    const handleEditPost = () => {
        onEditPost({ sharedPostId, sharedContent });
        setShowDropDown(false);
    };

    const handleDeletePost = async () => {
        try {
            const response = await sharedPostApi.deleteSharedPost(sharedPostId);
            if (response.message === 'Success') {
                onHidePost(sharedPostId);
                toast.success('Bài chia sẻ đã được xóa thành công!');
            } else {
                toast.error('Xóa bài chia sẻ thất bại');
            }
        } catch (error) {
            console.error('Error deleting shared post:', error);
            toast.error('Đã có lỗi xảy ra khi xóa bài chia sẻ!');
        }
        setShowDropDown(false);
    };

    const handleSharePost = async () => {
        try {
            const response = await sharedPostApi.createSharedPost({
                originalPostId: originalPost.postId,
                sharedContent: '',
            });
            if (response.message === 'Success') {
                toast.success('Đã chia sẻ bài viết!');
            } else {
                toast.error('Chia sẻ thất bại!');
            }
        } catch (error) {
            console.error('Error sharing post:', error);
            toast.error('Đã có lỗi xảy ra khi chia sẻ!');
        }
    };

    const onPostComment = () => {
        setCommentAmount(prev => prev + 1);
    };

    return (
        <div className="shared-post">
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
                            <span className="date">{moment(createdAt).fromNow()}</span>
                        </div>
                    </div>
                    <div className="relative" onClick={() => setShowDropDown(!showDropDown)}>
                        <MoreHorizIcon style={{ cursor: 'pointer', fontSize: '32px' }} />
                        {showDropDown && (
                            <div className="absolute right-0 mt-1 w-96 bg-neutral-800 text-white rounded-lg shadow-lg z-50 text-[20px]">
                                {user.userId === currentUser.userId && (
                                    <>
                                        <button
                                            onClick={handleEditPost}
                                            className="w-full text-left px-4 py-2 hover:bg-neutral-700 rounded-t-lg flex gap-[10px] items-center cursor-pointer"
                                        >
                                            <EditSquareIcon style={{ fontSize: '24px' }} />
                                            Chỉnh sửa bài chia sẻ
                                        </button>
                                        <button
                                            onClick={handleDeletePost}
                                            className="w-full text-left px-4 py-2 hover:bg-neutral-700 rounded-b-lg flex gap-[10px] items-center text-red-500 cursor-pointer"
                                        >
                                            <DeleteIcon style={{ fontSize: '24px' }} />
                                            Xóa bài chia sẻ
                                        </button>
                                    </>
                                )}
                            </div>
                        )}
                    </div>
                </div>
                <div className="content">
                    <p>{sharedContent && (sharedContent)}</p>
                    <div className='p-[20px] border-1 border-gray-400 rounded-2xl mt-[10px]'>
                        <p>{originalPost.content}</p>
                        {originalPost.media && originalPost.media.length > 0 && (
                            <div className="media-gallery relative my-[20px]">
                                {originalPost.media.length === 1 ? (
                                    isVideo(originalPost.media[0].filePath) ? (
                                        <video
                                            src={getMediaUrl(originalPost.media[0].filePath)}
                                            controls
                                            className="w-full max-h-[500px] object-cover object-center rounded-md"
                                        />
                                    ) : (
                                        <img
                                            src={getMediaUrl(originalPost.media[0].filePath)}
                                            alt="/"
                                            className="w-full max-h-[500px] object-cover object-center rounded-md"
                                        />
                                    )
                                ) : (
                                    <div className="relative overflow-hidden">
                                        <div
                                            className="flex transition-transform duration-250 ease-in-out"
                                            style={{ transform: `translateX(-${currentIndex * 100}%)` }}
                                        >
                                            {originalPost.media.map((item, index) => (
                                                <div key={index} className="w-full flex-shrink-0">
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
                                        <button
                                            onClick={handlePrev}
                                            className="absolute left-2 top-1/2 transform -translate-y-1/2 bg-gray-800 text-white p-4 rounded-full hover:opacity-50 text-[36px] cursor-pointer"
                                        >
                                            ←
                                        </button>
                                        <button
                                            onClick={handleNext}
                                            className="absolute right-2 top-1/2 transform -translate-y-1/2 bg-gray-800 text-white p-4 rounded-full hover:opacity-50 text-[36px] cursor-pointer"
                                        >
                                            →
                                        </button>
                                        <div className="absolute top-3 right-2 transform -translate-y-[-1/2] bg-gray-800 text-white px-5 py-3 rounded-full">
                                            {currentIndex + 1} / {originalPost.media.length}
                                        </div>
                                    </div>
                                )}
                            </div>
                        )}

                    </div>
                    <div className='flex gap-[10px] items-center mt-[25px]'>
                        <p className="text-gray-500 text-md ">Được chia sẻ từ bài viết của {originalPost.user.username}</p>
                        <img className='original-user-img'
                            src={getMediaUrl(originalPost.user?.profileImageUrl) || DefaultProfilePic} alt="" />
                    </div>

                </div>
                <div className="info">
                    <div className="item" onClick={handleToggleLike}>
                        {liked ? (
                            <FavoriteOutlinedIcon style={{ color: 'red' }} className="likeIcon" />
                        ) : (
                            <FavoriteBorderOutlinedIcon />
                        )}
                        <p>{likeCount} Likes</p>
                    </div>
                    <div className="item" onClick={() => setCommentOpen(!commentOpen)}>
                        <TextsmsOutlinedIcon />
                        {commentAmount} Comments
                    </div>
                    <div className="item" onClick={handleSharePost}>
                        <ShareOutlinedIcon />
                        Share
                    </div>
                </div>
                {commentOpen && <Comments sharedPostId={sharedPostId} onPostComment={onPostComment} />}
            </div>
        </div>
    );
};

export default SharedPost;