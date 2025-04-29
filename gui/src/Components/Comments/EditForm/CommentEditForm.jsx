import AttachFileIcon from "@mui/icons-material/AttachFile";
import React, {useState} from "react";

import "./CommentEditForm.css";
import {getMediaUrl} from "../../../Utils/Media/getMediaUrl.js";
import MediaSelectedPreview from "../../Media/MediaPreview/MediaSelectedPreview.jsx";


const CommentEditForm = ({comment, onSave, onCancel}) => {
    const [editText, setEditText] = useState( comment.content || "");
    const [editFiles, setEditFiles] = useState([]);

    return (
        <div className="comment-edit-container">
            {/*Left Container*/}
            <div className="comment-edit-containerAvatar">
                <img src={getMediaUrl(comment.profileImageUrl)} className="avatar" alt=""/>
            </div>

            {/*Right Container*/}
            <div className="comment-edit-containerForm">
                {/*Text Area for Edit Form*/}
                <textarea
                    value={editText}
                    onChange={(e) => setEditText(e.target.value)}
                    className="comment-edit-textArea"
                    placeholder="Edit your comment..."
                />

                {/*Attach File Button*/}
                <div className="mt-2">
                    <label
                        className="comment-edit-attachFileButton"
                        htmlFor={`edit-file-input-${comment.commentId}`}>
                        <AttachFileIcon/> Add media
                    </label>
                    <input
                        id={`edit-file-input-${comment.commentId}`}
                        type="file"
                        accept="image/*,video/*"
                        multiple
                        style={{display: 'none'}}
                        onChange={(e) => setEditFiles(Array.from(e.target.files))}
                    />
                </div>

                {/*File Preview*/}
                {editFiles.length > 0 && (
                    <MediaSelectedPreview
                        styleContainer={"flex bg-[whitesmoke] dark:bg-[#343434] rounded-md flex-wrap gap-2 py-4"}
                        mediaFileObjects={editFiles}
                        onRemove={(index) => {
                            const newFiles = [...editFiles];
                            newFiles.splice(index, 1);
                            setEditFiles(newFiles);
                        }}
                    />
                )}

                {/*Button Group*/}
                <div className="comment-edit-btnGroup">
                    <button
                        className="comment-edit-btnSave"
                        onClick={() => onSave(editText, editFiles)}
                    >
                        Save
                    </button>
                    <button
                        className="comment-edit-btnCancel"
                        onClick={() => onCancel()}
                    >
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    )
}

export default CommentEditForm;