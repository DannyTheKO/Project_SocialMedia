import React from 'react'
import './Home.css'
import Stories from '../../Components/Stories/Stories'
import Posts from '../../Components/Posts/Posts'
import CreatePost from '../../Components/CreatePost/CreatePost'

const Home = () => {
    return (
        <div className='home'>
            <CreatePost />
            {/* <Stories /> */}
            <Posts />
        </div>
    )
}

export default Home