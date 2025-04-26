import React, {useContext} from "react";
import {AuthContext} from "../../../Context/AuthContext.jsx";
import {getMediaUrl} from "../../../Utils/Media/getMediaUrl.js";
import EditIcon from "@mui/icons-material/Edit";
import MediaSlider from "../../Media/MediaSlider/MediaSlider.jsx"

// Moment Config
import moment from "moment/moment.js";
import DeleteIcon from "@mui/icons-material/Delete";
moment.locale('vi');

const DisplayComment = ({comment, onStartEdit, onDelete}) => {
    // Authentication
    const {currentUser} = useContext(AuthContext);

    return (
        // Display Comment
        <div className="row-1">
            <img src={getMediaUrl(comment.profileImageUrl)} className="avatar" alt=""/>
            <div className="content">
                <div className="info">

                    {/*Left*/}
                    <div className='left'>
                        <span>{comment.username}</span>
                        <p>{comment.content}</p>
                    </div>

                    {/*Right*/}
                    <div className="flex content-center gap-[10px]">
                        {/* Add edit button if the comment belongs to current user */}
                        {comment.userId === Number(currentUser.userId) && (
                            <>
                                {/*Edit Button*/}
                                <button
                                    className="bg-blue-500 hover:bg-blue-800 px-[6px] py-[4px] rounded-md flex justify-center border-none cursor-pointer"
                                    onClick={onStartEdit}
                                >
                                    <EditIcon fontSize="small"/>
                                </button>

                                {/*Delete Button*/}
                                <button
                                    className="bg-red-500 hover:bg-red-800 px-[6px] py-[4px] rounded-md flex justify-center border-none cursor-pointer"
                                    onClick={() => onDelete(comment.commentId)}
                                >
                                    <DeleteIcon fontSize="small"/>
                                </button>
                            </>
                        )}
                        <span className="date">{moment(comment.createdAt).fromNow()}</span>
                    </div>
                </div>

                {/*If Single comment has multiple media files*/}
                <MediaSlider mediaFileObjects={comment}/>
            </div>
        </div>
    )
}

export default DisplayComment;