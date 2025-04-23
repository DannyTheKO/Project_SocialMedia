import React, { useEffect, useState } from 'react'
import './Posts.css'
import Post from '../Post/Post'
import { postApi } from '../../Services/PostService/postService'
import EditPostModal from '../Modal/EditPostModal/EditPostModal'

const Posts = ({ userID }) => {

    const [posts, setPosts] = useState([])

    const [editPost, setEditPost] = useState(null);

    useEffect(() => {
        loadAllPosts(userID)
    }, [userID])

    const handleDeletePost = (postId) => {
        setPosts(posts.filter((post) => post.postId !== postId));
    };

    const handleHidePost = (postId) => {
        setPosts(posts.filter((post) => post.postId !== postId));
    };

    const handleEditPost = (editedPost) => {
        setEditPost(editedPost);
    };

    const onPostUpdated = (editedPost) => {
        setPosts(
            posts.map((p) => (p.postId === editedPost.postId ? editedPost : p))
        );
        setEditPost(null);
    }

    const loadAllPosts = async (userID) => {
        try {
            let response;

            // Check if userID exists and is not empty (if it's a string)
            if (userID && (typeof userID === 'string' ? userID.trim() !== '' : true)) {
                response = await postApi.getAllPostsByUserId(userID);
            } else {
                response = await postApi.getAllPosts();
            }

            // Since Axios interceptor returns response.data directly,
            // and backend returns ApiResponse with "message" and "data" fields
            if (response && response.message === "Success") {
                setPosts(response.data);
            } else {
                console.warn('Invalid API response:', response);
                setPosts([]);
            }
        } catch (error) {
            console.error('Error fetching posts:', error.response?.data || error.message);
            setPosts([]);
        }
    };



    return <div className="posts">
        {/*Danny: Anh chỉnh từ "posts" sang "posts?", có tí xiu à ^^*/}
        {posts?.length > 0 ? (
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
                    onDeletePost={handleDeletePost}
                    onEditPost={handleEditPost}
                />
            )

        ) : (<p>Không có bài viết nào !</p>)
        }

        {editPost && (
            <EditPostModal
                isOpen={!!editPost}
                onClose={() => setEditPost(null)}
                post={editPost}
                onPostUpdated={onPostUpdated}
            />
        )}


    </div>
}

export default Posts