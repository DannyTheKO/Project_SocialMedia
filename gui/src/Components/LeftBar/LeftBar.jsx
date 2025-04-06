import React from 'react'
import './LeftBar.scss'
import Friends from "../../assets/1.png";
import Groups from "../../assets/2.png";
import Gallery from "../../assets/8.png";
import Videos from "../../assets/9.png";
import Messages from "../../assets/10.png";
import PlaceHolderImage from '../../assets/login-image.jpg'

const LeftBar = () => {
    return (
        <div className='leftbar flex-[2] sticky top-[70px] overflow-auto'>
            <div className="container p-[20px]">
                <div className="menu flex flex-col gap-[30px]">
                    <div className="user flex items-center gap-[10px] font-medium">
                        <img src={PlaceHolderImage} alt="" className='w-[35px] h-[35px] rounded-full object-cover' />
                        <span className='text-[18px]'>Tuan Thai</span>
                    </div>
                    <div className="item flex items-center gap-[10px]">
                        <img src={Friends} alt="" className='w-[35px] h-[35px]' />
                        <span className='text-[18px]'>Friends</span>
                    </div>
                    <div className="item flex items-center gap-[10px]">
                        <img src={Groups} alt="" className='w-[35px] h-[35px]' />
                        <span className='text-[18px]'>Groups</span>
                    </div>
                </div>
                <hr className='my-[20px] border-none h-[0.5px] bg-gray-300' />
                <div className="menu flex flex-col gap-[35px]">
                    <span className='text-[18px]'>Your shortcuts</span>
                    <div className="item flex items-center gap-[10px]">
                        <img src={Gallery} alt="" className='w-[35px] h-[35px]' />
                        <span className='text-[18px]'>Gallery</span>
                    </div>
                    <div className="item flex items-center gap-[10px]">
                        <img src={Videos} alt="" className='w-[35px] h-[35px]' />
                        <span className='text-[18px]'>Videos</span>
                    </div>
                    <div className="item flex items-center gap-[10px]">
                        <img src={Messages} alt="" className='w-[35px] h-[35px]' />
                        <span className='text-[18px]'>Messages</span>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LeftBar