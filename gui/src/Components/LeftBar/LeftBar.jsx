import React from 'react'
import './LeftBar.css'
import Friends from "../../assets/1.png";
import Groups from "../../assets/2.png";
import Gallery from "../../assets/8.png";
import Videos from "../../assets/9.png";
import Messages from "../../assets/10.png";
import PlaceHolderImage from '../../assets/login-image.jpg'

const LeftBar = () => {
    return (
        <div className='leftbar'>
            <div className="container p-[20px]">
                <div className="menu">
                    <div className='item font-medium'>
                        <img src={PlaceHolderImage} alt="" className='image rounded-full object-cover'/>
                        <span className='text-[20px] text-color font-normal'>Tuan Thai</span>
                    </div>
                    <div className='item'>
                        <img src={Friends} alt="" className='image'/>
                        <span className='text-[20px] text-color font-normal'>Bạn bè</span>
                    </div>
                    <div className='item'>
                        <img src={Groups} alt="" className='image'/>
                        <span className='text-[20px] text-color font-normal'>Nhóm</span>
                    </div>
                </div>
                <hr/>
                <div className="menu flex flex-col gap-[20px]">
                    <span className='text-[20px] text-color font-normal'>Lối tắt</span>
                    <div className='item'>
                        <img src={Gallery} alt="" className='image'/>
                        <span className='text-[20px] text-color font-normal'>Thư viện</span>
                    </div>
                    <div className='item'>
                        <img src={Videos} alt="" className='image'/>
                        <span className='text-[20px] text-color font-normal'>Videos</span>
                    </div>
                    <div className='item'>
                        <img src={Messages} alt="" className='image'/>
                        <span className='text-[20px] text-color font-normal'>Tin nhắn</span>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LeftBar