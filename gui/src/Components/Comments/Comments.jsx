import React, { useContext, useEffect, useRef, useState } from 'react'
import './Comments.css'
import { commentApi } from '../../Services/CommentService/commentService'
import { AuthContext } from '../../Context/AuthContext'
import { toast } from 'react-toastify';
import CommentEditForm from "./EditForm/CommentEditForm.jsx";
import DisplayComment from "./DisplayComment/DisplayComment.jsx";
import MediaSelectedPreview from "../Media/MediaPreview/MediaSelectedPreview.jsx";
import CommentCreateForm from "./CreateForm/CommentCreateForm.jsx";

// const Comments = ({ postId, isVideo, getMediaUrl, onPostComment }) => {

const Comments = ({ postId }) => {
    // Authentication
    const { currentUser } = useContext(AuthContext);

    const [comments, setComments] = useState([]);
    const [commentCreateText, setCommentCreateText] = useState('');
    const [commentEditText, setCommentEditText] = useState(null);
    const [files, setFiles] = useState([]);

    useEffect(() => {
        // Fetch comments
        fetchComments(postId)
            .catch(error => {
                console.error("Error loading comment", error)
            });
    }, [postId]);


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
            setComments([]);
            5
        }
    }

    const handleSubmitComment = async (postId) => {
        try {
            if (!commentCreateText.trim() && files.length === 0) {
                // Don't submit if there's no content and no files
                return;
            }

            // Create FormData to handle file uploads
            const formData = new FormData();

            // Add text content
            formData.append("content", commentCreateText);

            // Add media files if any
            if (files && files.length > 0) {
                for (let i = 0; i < files.length; i++) {
                    formData.append("mediaFileRequest", files[i]);
                }
            }

            // DEBUG: Log the actual contents of FormData
            // console.log("FormData contents:");
            // for (let pair of formData.entries()) {
            //     console.log(pair[0] + ': ' + pair[1]);
            // }

            const response = await commentApi.createComment(
                currentUser.userId,
                postId,
                formData
            );

            if (response && response.message === "Success") {
                // Clear the input field and reset files
                setCommentCreateText('');
                setFiles([]);

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
            formData.append("content", updatedContent.trim() || "");

            // Add media files if any
            if (updatedFiles && updatedFiles.length > 0) {
                for (let i = 0; i < updatedFiles.length; i++) {
                    formData.append("mediaFileRequest", updatedFiles[i]);
                }
            }

            // Debug Print
            // console.log("FormData contents:");
            // for (let pair of formData.entries()) {
            //     console.log(pair[0] + ': ' + pair[1]);
            // }

            // Call the API to update the comment
            const response = await commentApi.updateComment(commentId, formData);

            if (response && response.message === "Success") {
                // Refresh comments list to show the update
                const updatedComments = await commentApi.getPostComments(postId);
                if (updatedComments && updatedComments.message === "Success") {
                    setComments(updatedComments.data || []);
                }

                // Reset edit mode state if you're tracking it
                setCommentEditText(null);
            } else {
                toast.error("Failed to update comment");
            }
        } catch (error) {
            console.error("Error updating comment:", error);
            toast.error("Error updating comment");
        }
    };

    const handleDeleteComment = async (commentId) => {
        try {
            const response = await commentApi.deleteComment(commentId);

            if (response && response.message === "Success") {
                // Refresh comments list to show the update
                const updatedComments = await commentApi.getPostComments(postId);
                if (updatedComments && updatedComments.message === "Success") {
                    setComments(updatedComments.data || []);
                }

                toast.success("Successfully deleted");
            } else {
                toast.warn("Invalid Permission")
            }

        } catch (error) {
            console.error("Error deleting comment:", error);
            toast.error("Error deleting comment");
        }
    }

    return (
        <div className="commentWrapper">
            {/*Create Form*/}
            <CommentCreateForm
                currentUser={currentUser}
                commentInputText={commentCreateText}
                setCommentInputText={setCommentCreateText}
                setFiles={setFiles}
                handleSubmitComment={handleSubmitComment}
                postId={postId}
            />

            {/* File Preview */}
            {/* Dev note: This section could be a separate component?*/}
            {files.length > 0 && (
                <MediaSelectedPreview
                    styleContainer={"flex bg-[whitesmoke] dark:bg-[#343434] rounded-md flex-wrap gap-2 py-4 pl-[16px] ml-15 mr-[10px] mb-10"}
                    mediaFileObjects={files}
                    onRemove={(index) => {
                        const newFiles = [...files];
                        newFiles.splice(index, 1);
                        setFiles(newFiles);
                    }}
                />
            )}

            {/* Add null check before mapping */}
            <div className="comment-list">
                {comments && comments.length > 0 ? (
                    comments.map((comment, index) => (
                        <div className='singleComment' key={comment.commentId}>
                            {commentEditText === comment.commentId ? (
                                // Edit Form Action
                                <CommentEditForm
                                    comment={comment}
                                    onCancel={() => setCommentEditText(null)}
                                    onSave={(updatedContent, updatedFiles) => handleEditComment(comment.commentId, updatedContent, updatedFiles)}
                                />
                            ) : (
                                // Display Comment
                                <DisplayComment
                                    comment={comment}
                                    onStartEdit={() => setCommentEditText(comment.commentId)}
                                    onDelete={() => handleDeleteComment(comment.commentId)}
                                />
                            )}
                        </div>
                    ))
                ) : (
                    <div>No comments yet</div>
                )}
            </div>
        </div>
    )
}

export default Comments