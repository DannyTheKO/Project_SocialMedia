import React, {useEffect, useState} from 'react'
import './Comments.css'
import {commentApi} from '../../Services/CommentService/commentService'
import DefaultProfilePic from '../../Assets/defaultProfilePic.jpg'
import SendIcon from '@mui/icons-material/Send';
import CommentCpn from '../Comment/CommentCpn';

const Comments = ({postId}) => {

    const [comments, setComments] = useState([])

    useEffect(() => {
        loadPostComments(postId)
    }, [postId])

    const loadPostComments = async (postId) => {
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
        }
    }

    return (
        <div className='comments'>
            <div className="write">
                <img src={DefaultProfilePic} alt=""/>
                <input type="text" placeholder='Write a comment'/>
                <button><SendIcon/></button>
            </div>

            {/* Add null check before mapping */}
            {comments && comments.length > 0 ? (
                comments.map((comment, index) => (
                    <CommentCpn comment={comment} key={index}/>
                ))
            ) : (
                <div>No comments yet</div>
            )}
        </div>
    )
}

export default Comments