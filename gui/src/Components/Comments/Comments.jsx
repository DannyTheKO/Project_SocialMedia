import React, { useState, useEffect } from 'react'
import './Comments.css'
import { getPostComment } from '../../Services/CommentService/commentService'
import DefaultProfilePic from '../../assets/defaultProfilePic.jpg'
import SendIcon from '@mui/icons-material/Send';
import CommentCpn from '../Comment/CommentCpn';

const Comments = ({ postId }) => {

    const [comments, setComments] = useState([])

    useEffect(() => {
        loadPostComments(postId)
    }, [postId])

    const loadPostComments = async (postId) => {
        try {
            let response = await getPostComment(postId)

            if (response && response.data) {
                setComments(response.data.data)
            } else {
                console.warn("Không nhận được dữ liệu API comments!", response.data.data)
                setComments([])
            }

        } catch (Error) {
            Console.error('Lỗi khi lấy dữ liệu comments: ', Error)
            setComments([])
        }
    }

    return <div className='comments'>
        <div className="write">
            <img src={DefaultProfilePic} alt="" />
            <input type="text" placeholder='Write a comment' />
            <button><SendIcon /> </button>
        </div>

        {comments.map((comment, index) => (
            <CommentCpn comment={comment} key={index} />
        ))}
    </div>
}

export default Comments