import React, { useEffect, useState } from 'react'
import './Posts.css'
import Post from '../Post/Post'
import { getAllPosts, getUserPosts } from '../../Services/PostService/postService'

const Posts = ({ userID }) => {

    const [posts, setPosts] = useState([])

    useEffect(() => {
        loadAllPosts(userID)
    }, [userID])

    const loadAllPosts = async (userID) => {
        try {
            let response;

            if (userID && userID.trim() !== '') {
                response = await getUserPosts(userID);
            } else {
                response = await getAllPosts();
            }
            if (response && response.data) {
                setPosts(response.data.data);
            } else {
                console.warn('Không nhận được dữ liệu API posts!', response.data);
                setPosts([]);
            }
        } catch (error) {
            console.error('Lỗi khi lấy dữ liệu:', error);
            setPosts([]);
        }
    };


    return <div className="posts">
        {posts.length > 0 ? (
            posts.map((post, index) =>
                <Post
                    key={index}
                    user={post.user}
                    postId={post.postId}
                    content={post.content}
                    comments={post.comments}
                    likes={post.likes}
                    media={post.media}
                    createdPost={post.createdPost}
                    modifiedPost={post.modifiedPost}
                />
            )

        ) : (<p>Không có bài viết nào !</p>)
        }
    </div>
}

export default Posts