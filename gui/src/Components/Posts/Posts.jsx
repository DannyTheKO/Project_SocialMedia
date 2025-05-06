import React, { useEffect, useState } from 'react'
import './Posts.css'
import Post from '../Post/Post'
import SharedPost from '../SharedPost/SharedPost'
import { postApi } from '../../Services/PostService/postService'
import EditPostModal from '../Modal/EditPostModal/EditPostModal'
import { timelineApi } from '../../Services/TimelineService/timelineService'

const Posts = ({ userID }) => {

    const [posts, setPosts] = useState([])

    const [editItem, setEditItem] = useState(null)

    useEffect(() => {
        loadTimeline(userID)
    }, [userID])

    const handleDeletePost = (postId) => {
        setPosts(posts.filter((item) =>
            !(item.hasOwnProperty('postId') && item.postId === postId) &&
            !(item.hasOwnProperty('sharedPostId') && item.sharedPostId === postId)
        ));
    };

    const handleHidePost = (postId) => {
        setPosts(posts.filter((post) => post.postId !== postId));
    };

    const handleEditPost = (editedItem) => {
        setEditItem(editedItem);
    };

    const onPostUpdated = (editedPost) => {
        setPosts(
            posts.map((p) =>
                p.postId === editedPost.postId ? editedPost : p
            )
        );
        setEditItem(null);
    };

    const onSharedPostUpdated = (editedSharedPost) => {
        setPosts(
            posts.map((p) =>
                p.sharedPostId === editedSharedPost.sharedPostId ? editedSharedPost : p
            )
        );
        setEditItem(null);
    };

    const loadTimeline = async (userID) => {
        try {
            let response;
            if (userID && (typeof userID === 'string' ? userID.trim() !== '' : true)) {
                response = await timelineApi.getTimeline(userID);
            } else {
                response = await timelineApi.getTimeline();
            }

            if (response && response.message === "Success") {
                setPosts(response.data);
            } else {
                console.warn('Invalid API response:', response);
                setPosts([]);
            }
        } catch (error) {
            console.error('Error fetching timeline:', error.response?.data || error.message);
            setPosts([]);
        }
    };

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
            posts.map((item, index) =>
                item.postId ? (
                    <Post
                        key={index}
                        user={item.user}
                        postId={item.postId}
                        content={item.content}
                        comments={item.comments}
                        likes={item.likes}
                        shareCount={item.shareCount}
                        media={item.media}
                        createdPost={item.createdPost}
                        modifiedPost={item.modifiedPost}
                        onDeletePost={handleDeletePost}
                        onEditPost={handleEditPost}
                    />
                ) : item.sharedPostId && (
                    <SharedPost
                        key={index}
                        user={item.user}
                        sharedPostId={item.sharedPostId}
                        originalPost={item.originalPost}
                        sharedContent={item.sharedContent}
                        comments={item.comments}
                        likes={item.likes}
                        createdAt={item.sharedAt}
                        onHidePost={handleDeletePost}
                        onEditPost={handleEditPost}
                    />
                )
            )
        ) : (
            <p>Không có bài viết nào !</p>
        )}

        {editItem && (
            <EditPostModal
                isOpen={!!editItem}
                onClose={() => setEditItem(null)}
                post={editItem.postId ? editItem : null}
                sharedPost={editItem.sharedPostId ? editItem : null}
                onPostUpdated={onPostUpdated}
                onSharedPostUpdated={onSharedPostUpdated}
            />
        )}


    </div>
}

export default Posts