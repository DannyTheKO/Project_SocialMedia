import React, { useEffect, useState } from 'react'
import './Posts.css'
import Post from '../Post/Post'
import { getAllPosts } from '../../Services/PostsService/postService'

const Posts = () => {

    const [posts, setPosts] = useState([])

    useEffect(() => {
        loadAllPosts()
    }, [])

    const loadAllPosts = async () => {
        try {
            const response = await getAllPosts();

            if (response && response.data) {
                setPosts(response.data.data);
            } else {
                console.warn('Invalid data format:', response.data);
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