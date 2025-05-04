import React, { useContext } from "react";
import { AuthContext } from "../../../Context/AuthContext.jsx";
import { getMediaUrl } from "../../../Utils/Media/getMediaUrl.js";
import EditIcon from "@mui/icons-material/Edit";
import MediaSlider from "../../Media/MediaSlider/MediaSlider.jsx"
import DeleteIcon from "@mui/icons-material/Delete";
import ThumbUpOffAltIcon from '@mui/icons-material/ThumbUpOffAlt';

import "./DisplayComment.css"

// Moment Config
import moment from "moment/moment.js";
moment.locale('vi');

const DisplayComment = ({ comment, onStartEdit, onDelete }) => {
    // Authentication
    const { currentUser } = useContext(AuthContext);

    return (
        // Display Comment
        <div className="comment-display-wrapper">
            <img src={getMediaUrl(comment.profileImageUrl)} className="avatar" alt="" />
            <div className="comment-display-content">
                <div className="comment-display-info">
                    {/*Left*/}
                    <div className="comment-display-left">
                        <span>{comment.username}</span>
                        <p>{comment.content}</p>
                    </div>

                    {/*Right*/}
                    <div className="comment-display-right">
                        {/* Add edit button if the comment belongs to current user */}
                        {comment.userId === Number(currentUser.userId) && (
                            <>
                                {/*Edit Button*/}
                                <button
                                    className="bg-blue-500 hover:bg-blue-800 px-[6px] py-[4px] flex justify-center rounded-md border-none cursor-pointer"
                                    onClick={onStartEdit}
                                >
                                    <EditIcon fontSize="small" />
                                </button>

                                {/*Delete Button*/}
                                <button
                                    className="bg-red-500 hover:bg-red-800 px-[6px] py-[4px] flex justify-center rounded-md border-none cursor-pointer"
                                    onClick={() => onDelete(comment.commentId)}
                                >
                                    <DeleteIcon fontSize="small" />
                                </button>
                            </>
                        )}
                        <span className="date">{moment(comment.createdAt).fromNow()}</span>
                    </div>
                </div>

                <div className="comment-display-interaction">
                    {/*Likes*/}
                    <button>
                        <ThumbUpOffAltIcon style={{ cursor: "pointer", fontSize: "24px" }} /> Like
                    </button>
                </div>

                {/*If Single comment has multiple media files*/}
                <MediaSlider mediaFileObjects={comment} />
            </div>
        </div>
    )
}

export default DisplayComment;