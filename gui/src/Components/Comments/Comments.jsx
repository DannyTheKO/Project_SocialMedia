import React, {useContext, useEffect, useMemo, useRef, useState} from 'react'
import './Comments.css'
import {commentApi} from '../../Services/CommentService/commentService'
import {AuthContext} from '../../Context/AuthContext'
import SendIcon from '@mui/icons-material/Send';
import AttachFileIcon from '@mui/icons-material/AttachFile';
import CommentCpn from '../Comment/CommentCpn';
import {toast} from 'react-toastify';

const Comments = ({postId, isVideo, getImageUrl}) => {

    // Authentication
    const {currentUser} = useContext(AuthContext);

    // Fetch Comment
    const [comments, setComments] = useState([]);

    // Create Comment
    const [commentInputText, setCommentInputText] = useState('');
    const [files, setFiles] = useState([]);
    const fileInputRef = useRef(null);

    // Edit Comment
    const [editingCommentId, setEditingCommentId] = useState(null);
    const [editCommentText, setEditCommentText] = useState('');
    const [editCommentFiles, setEditCommentFiles] = useState([]);


    // Create stable file preview URLs that don't change on every re-render
    const filePreviewUrls = useMemo(() => {
        return Array.from(files).map(file => ({
            url: URL.createObjectURL(file),
            type: file.type,
            name: file.name
        }));
    }, [files]); // Only recreate URLs when files array changes

    useEffect(() => {
        // Fetch comments
        fetchComments(postId)
            .catch(error => {
                console.error("Error loading comment", error)
            });

        // Return cleanup function
        return () => {
            filePreviewUrls.forEach(filePreview => {
                URL.revokeObjectURL(filePreview.url);
            });
        };
    }, [postId, filePreviewUrls]);


    const fetchComments = async (postId) => {
        try {
            const response = await commentApi.getPostComments(postId);

            // Since Axios interceptor returns response.data directly,
            // and backend returns ApiResponse with "message" and "data" fields
            if (response && response.message === "Success") {
                setComments(response.data || []); // Ensure we set an empty array if data is null/undefined
            } else {
                console.warn("Invalid API response:", response);
                setComments([]);
            }
        } catch (error) {
            console.error('Error fetching comments:', error.response?.data || error.message);
            setComments([]);5
        }
    }

    const handleSubmitComment = async (postId) => {
        try {
            if (!commentInputText.trim() && files.length === 0) {
                // Don't submit if there's no content and no files
                return;
            }

            // Create FormData to handle file uploads
            const formData = new FormData();

            // Add text content
            formData.append("content", commentInputText);

            // Add media files if any
            if (files && files.length > 0) {
                for (let i = 0; i < files.length; i++) {
                    formData.append("mediaFileRequest", files[i]);
                }
            }

            // DEBUG: Log the actual contents of FormData
            console.log("FormData contents:");
            for (let pair of formData.entries()) {
                console.log(pair[0] + ': ' + pair[1]);
            }

            const response = await commentApi.createComment(
                currentUser.userId,
                postId,
                formData
            );

            if (response && response.message === "Success") {
                // Clear the input field and reset files
                setCommentInputText('');
                setFiles([]);

                // Reset file input
                if (fileInputRef.current) {
                    fileInputRef.current.value = '';
                }

                // Refresh comments list
                const updatedComments = await commentApi.getPostComments(postId);
                if (updatedComments && updatedComments.message === "Success") {
                    setComments(updatedComments.data || []);
                }

                // Optional: Show success notification
                // toast.success("Comment posted successfully!");
            } else {
                console.warn("Failed to post comment:", response);
                // Optional: Show error notification
                // toast.error("Failed to post comment");
            }
        } catch (error) {
            console.error("Error submitting comment:", error.response?.data || error.message);
            // Optional: Show error notification
            // toast.error("Error posting comment");
        }
    };

    const handleEditComment = async (commentId, updatedContent, updatedFiles) => {
        try {
            // Create FormData for the updated comment
            const formData = new FormData();

            // Add content (with fallback for empty content)
            formData.append("content", updatedContent.trim() || "Shared media");

            // Add media files if any
            if (updatedFiles && updatedFiles.length > 0) {
                for (let i = 0; i < updatedFiles.length; i++) {
                    formData.append("mediaFileRequest", updatedFiles[i]);
                }
            }

            // Call the API to update the comment
            const response = await commentApi.updateComment(commentId, formData);

            if (response && response.message === "Success") {
                // Refresh comments list to show the update
                const updatedComments = await commentApi.getPostComments(postId);
                if (updatedComments && updatedComments.message === "Success") {
                    setComments(updatedComments.data || []);
                }

                // Reset edit mode state if you're tracking it
                setEditingCommentId(null);
            } else {
                toast.error("Failed to update comment");
            }
        } catch (error) {
            console.error("Error updating comment:", error);
            toast.error("Error updating comment");
        }
    };

    return (
        <div className='comments'>
            <div className="write">
                <img src={getImageUrl(currentUser.profileImageUrl)} alt=""/>
                <div className="comment-input-container">
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

            {/* Show file preview if files are selected */}
            {files.length > 0 && (
                <div className="flex flex-wrap gap-2 mt-2 mb-4 mx-15">
                    {filePreviewUrls.map((filePreview, index) => (
                        <div key={index} className="relative w-20 h-20 overflow-hidden rounded-md">
                            {filePreview.type.startsWith('image/') ? (
                                // If image exist
                                <img className="w-full h-full object-cover rounded-md" src={filePreview.url}
                                     alt={`Preview ${index}`}/>

                            ) : filePreview.type.startsWith('video/') ? (
                                // Else if video exist
                                <video controls>
                                    <source className="w-full h-full object-cover rounded-md"
                                            src={URL.createObjectURL(filePreview)} type={filePreview.type}/>
                                    Your browser does not support the video tag.
                                </video>

                            ) : (
                                // Else unknown file ?
                                <div>{filePreview.name}</div>

                            )}
                            <button
                                className="absolute top-1 right-1 w-6 h-6 flex items-center justify-center
                                bg-red-500 text-white rounded-full text-xs cursor-pointer
                                hover:bg-red-600 transition-colors"
                                onClick={() => {
                                    const newFiles = [...files];
                                    newFiles.splice(index, 1);
                                    setFiles(newFiles);
                                }}
                            >
                                ×
                            </button>
                        </div>
                    ))}
                </div>
            )}

            {/* Add null check before mapping */}
            {comments && comments.length > 0 ? (
                comments.map((comment, index) => (
                    <CommentCpn
                        comment={comment}
                        key={index}
                        isEditing={editingCommentId === comment.commentId}
                        editText={editCommentText}
                        setEditText={setEditCommentText}
                        editFiles={editCommentFiles}
                        setEditFiles={setEditCommentFiles}
                        onStartEdit={() => {
                            setEditingCommentId(comment.commentId);
                            setEditCommentText(comment.content || "");
                            setEditCommentFiles([]);
                        }}
                        onCancelEdit={() => setEditingCommentId(null)}
                        onSaveEdit={(updatedContent, updatedFiles) =>
                            handleEditComment(comment.commentId, updatedContent, updatedFiles)
                        }
                    />
                ))
            ) : (
                <div>No comments yet</div>
            )}
        </div>
    )
}

export default Comments