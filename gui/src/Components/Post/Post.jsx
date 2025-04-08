import React, { useState } from 'react';
import './Post.css';
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import FavoriteOutlinedIcon from "@mui/icons-material/FavoriteOutlined";
import TextsmsOutlinedIcon from "@mui/icons-material/TextsmsOutlined";
import ShareOutlinedIcon from "@mui/icons-material/ShareOutlined";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import { Link } from "react-router-dom";
import Comments from '../Comments/Comments';
import DefaultProfilePic from '../../assets/defaultProfilePic.jpg';
import moment from 'moment/moment';
import 'moment/locale/vi';

moment.locale('vi');    

const Post = ({ user, content, comments, likes, media, createdPost, modifiedPost }) => {
    const [commentOpen, setCommentOpen] = useState(false);
    const liked = false;

    return (
        <div className="post">
            <div className="container">
                <div className="user">
                    <div className="userInfo">
                        <img
                            src={user?.profileImageUrl || DefaultProfilePic}
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
                    {media && media.length > 0 && <img src={media[0]} alt="Post media" />}
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
                {commentOpen && <Comments />}
            </div>
        </div>
    );
};

export default Post;