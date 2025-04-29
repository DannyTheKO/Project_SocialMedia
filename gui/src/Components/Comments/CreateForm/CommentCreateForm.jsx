import React, { useRef } from 'react';
import AttachFileIcon from "@mui/icons-material/AttachFile";
import SendIcon from "@mui/icons-material/Send";
import { getMediaUrl } from "../../../Utils/Media/getMediaUrl.js";
import "./CommentCreateForm.css";

const CommentCreateForm = ({
                               currentUser,
                               commentInputText,
                               setCommentInputText,
                               setFiles,
                               handleSubmitComment,
                               postId
                           }) => {

    const fileInputRef = useRef(null);

    return (
        <div className="comment-create-wrapper">
            <img src={getMediaUrl(currentUser.profileImageUrl)} alt=""/>
            {/*Create Form*/}
            <div className="comment-create-input">
                <input
                    type="text"
                    placeholder='Write a comment'
                    value={commentInputText}
                    onChange={(e) => setCommentInputText(e.target.value)}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' && !e.shiftKey) {
                            e.preventDefault();
                            handleSubmitComment(postId)
                                .catch(error => {
                                    console.error(`Error submit comment: ${error}`);
                                });
                        }
                    }}
                />

                {/* File attachment button */}
                <label htmlFor="comment-file-input" className="file-upload-label">
                    <AttachFileIcon/>
                </label>
                <input
                    id="comment-file-input"
                    type="file"
                    accept="image/*,video/*"
                    multiple
                    ref={fileInputRef}
                    style={{display: 'none'}}
                    onChange={(e) => setFiles(Array.from(e.target.files))}
                />
            </div>
            <button className="submit-comment" onClick={() => handleSubmitComment(postId)}>
                <SendIcon/>
            </button>
        </div>
    );
};

export default CommentCreateForm;
