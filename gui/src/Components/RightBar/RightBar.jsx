import React from 'react'
import './RightBar.css'
import PlaceHolderImage from '../../assets/login-image.jpg'

const RightBar = () => {

    return (
        <div className='rightbar'>
            <div className="container p-[20px]">
                <div className="item">
                    <span className='time-color'>Suggestions For You</span>
                    <div className='user'>
                        <div className="userInfo">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <span className='text-[18px] text-color font-medium'>Tuan Thai </span>
                        </div>
                        <div className="buttons">
                            <button className='blue-button'>Follow</button>
                            <button className='red-button'>Dismiss</button>
                        </div>
                    </div>
                    <div className='user'>
                        <div className="userInfo">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <span className='text-[18px] text-color font-medium'>Tuan Thai </span>
                        </div>
                        <div className="buttons">
                            <button className='blue-button'>Follow</button>
                            <button className='red-button'>Dismiss</button>
                        </div>
                    </div>
                </div>

                <div className="item">
                    <span className='time-color'>Latest Activities</span>
                    <div className='user'>
                        <div className="userInfo">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <p>
                                <span className='text-[18px] text-color font-medium'>Tuan Thai </span>
                                changed their cover picture
                            </p>
                        </div>
                        <span className='time-color'> 1 min ago </span>
                    </div>
                    <div className='user'>
                        <div className="userInfo">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <p>
                                <span className='text-[18px] text-color font-medium'>Tuan Thai </span>
                                liked a post
                            </p>
                        </div>
                        <span className='time-color'> 1 min ago </span>
                    </div>
                    <div className='user'>
                        <div className="userInfo">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <p>
                                <span className='text-[18px] text-color font-medium'>Tuan Thai </span>
                                posted
                            </p>
                        </div>
                        <span className='time-color'> 1 min ago </span>
                    </div>
                </div>

                <div className="item">
                    <span className='time-color'>Online Friends</span>
                    <div className='user'>
                        <div className="userInfo relative">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <div className='green-dot' />
                            <span className='text-[18px] text-color font-medium'>Tuan Thai </span>
                        </div>
                    </div>
                    <div className='user'>
                        <div className="userInfo relative">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <div className='green-dot' />
                            <span className='text-[18px] text-color font-medium'>Tuan Thai </span>
                        </div>
                    </div>
                    <div className='user'>
                        <div className="userInfo relative">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <div className='green-dot' />
                            <span className='text-[18px] text-color font-medium'>Tuan Thai </span>
                        </div>
                    </div>
                    <div className='user'>
                        <div className="userInfo relative">
                            <img src={PlaceHolderImage} alt="" className='avatar' />
                            <div className='green-dot' />
                            <span className='text-[18px] text-color font-medium'>Tuan Thai </span>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default RightBar