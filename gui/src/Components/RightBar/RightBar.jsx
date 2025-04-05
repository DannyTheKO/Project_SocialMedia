import React from 'react'
import './RightBar.scss'
import PlaceHolderImage from '../../assets/login-image.jpg'

const RightBar = () => {
    return (
        <div className='rightbar flex-[3]'>
            <div className="container p-[20px]">

                <div className="item p-[20px] mb-[20px]">
                    <span className='text-gray-500'>Latest Activities</span>
                    <div className="user flex items-center justify-between my-[20px]">
                        <div className="userInfo flex items-center gap-[15px]">
                            <img src={PlaceHolderImage} alt="" className='w-[40px] h-[40px] rounded-full object-cover' />
                            <span className='text-[18px] font-medium'>Tuan Thai </span>
                        </div>
                        <div className="buttons flex gap-[10px]">
                            <button className='text-white bg-blue-500 p-[8px]'>Follow</button>
                            <button className='text-white bg-red-500 p-[8px]'>Dismiss</button>
                        </div>
                    </div>
                    <div className="user flex items-center justify-between my-[20px]">
                        <div className="userInfo flex items-center gap-[15px]">
                            <img src={PlaceHolderImage} alt="" className='w-[40px] h-[40px] rounded-full object-cover' />
                            <span className='text-[18px] font-medium'>Tuan Thai </span>
                        </div>
                        <div className="buttons flex gap-[10px]">
                            <button className='text-white bg-blue-500 p-[8px]'>Follow</button>
                            <button className='text-white bg-red-500 p-[8px]'>Dismiss</button>
                        </div>
                    </div>
                </div>

                <div className="item p-[20px] mb-[20px]">
                    <span className='text-gray-500'>Latest Activities</span>
                    <div className="user flex items-center justify-between my-[20px]">
                        <div className="userInfo flex items-center gap-[15px]">
                            <img src={PlaceHolderImage} alt="" className='w-[40px] h-[40px] rounded-full object-cover' />
                            <p>
                                <span className='text-[18px] font-medium'>Tuan Thai </span>
                                changed their cover picture
                            </p>
                        </div>
                        <span className='text-gray-500'> 1 min ago </span>
                    </div>
                    <div className="user flex items-center justify-between my-[20px]">
                        <div className="userInfo flex items-center gap-[15px]">
                            <img src={PlaceHolderImage} alt="" className='w-[40px] h-[40px] rounded-full object-cover' />
                            <p>
                                <span className='text-[18px] font-medium'>Tuan Thai </span>
                                liked a post
                            </p>
                        </div>
                        <span className='text-gray-500'> 1 min ago </span>
                    </div>
                    <div className="user flex items-center justify-between my-[20px]">
                        <div className="userInfo flex items-center gap-[15px]">
                            <img src={PlaceHolderImage} alt="" className='w-[40px] h-[40px] rounded-full object-cover' />
                            <p>
                                <span className='text-[18px] font-medium'>Tuan Thai </span>
                                posted
                            </p>
                        </div>
                        <span className='text-gray-500'> 1 min ago </span>
                    </div>
                </div>

                <div className="item p-[20px] mb-[20px]">
                    <span className='text-gray-500'>Online Friends</span>
                    <div className="user flex items-center justify-between my-[20px]">
                        <div className="userInfo flex items-center gap-[15px] relative">
                            <img src={PlaceHolderImage} alt="" className='w-[40px] h-[40px] rounded-full object-cover' />
                            <div className='w-[12px] h-[12px] rounded-full bg-lime-400 absolute top-0 left-[30px]' />
                            <span className='text-[18px] font-medium'>Tuan Thai </span>
                        </div>
                    </div>
                    <div className="user flex items-center justify-between my-[20px]">
                        <div className="userInfo flex items-center gap-[15px] relative">
                            <img src={PlaceHolderImage} alt="" className='w-[40px] h-[40px] rounded-full object-cover' />
                            <div className='w-[12px] h-[12px] rounded-full bg-lime-400 absolute top-0 left-[30px]' />
                            <span className='text-[18px] font-medium'>Tuan Thai </span>
                        </div>
                    </div>
                    <div className="user flex items-center justify-between my-[20px]">
                        <div className="userInfo flex items-center gap-[15px] relative">
                            <img src={PlaceHolderImage} alt="" className='w-[40px] h-[40px] rounded-full object-cover' />
                            <div className='w-[12px] h-[12px] rounded-full bg-lime-400 absolute top-0 left-[30px]' />
                            <span className='text-[18px] font-medium'>Tuan Thai </span>
                        </div>
                    </div>
                    <div className="user flex items-center justify-between my-[20px]">
                        <div className="userInfo flex items-center gap-[15px] relative">
                            <img src={PlaceHolderImage} alt="" className='w-[40px] h-[40px] rounded-full object-cover' />
                            <div className='w-[12px] h-[12px] rounded-full bg-lime-400 absolute top-0 left-[30px]' />
                            <span className='text-[18px] font-medium'>Tuan Thai </span>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default RightBar