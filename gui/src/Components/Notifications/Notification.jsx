import React from 'react'
import MoreHorizOutlinedIcon from '@mui/icons-material/MoreHorizOutlined';
import ZoomOutMapOutlinedIcon from '@mui/icons-material/ZoomOutMapOutlined';
import BorderColorOutlinedIcon from '@mui/icons-material/BorderColorOutlined';
import DefaultProfilePic from '../../assets/defaultProfilePic.jpg';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import './Notification.css'

const Notification = () => {
    return (
        <div className='chat'>
            <div className="header flex justify-between items-center p-2">
                <div className="left text-[32px] font-bold text-black dark:text-white">
                    Notifications
                </div>
                <div className="right flex gap-[15px] items-center dark:text-white">
                    <MoreHorizOutlinedIcon />
                </div>
            </div>
            <div className="flex gap-[15px] items-center mx-[10px]">
                <button className='bg-gray-300 text-black dark:bg-[#4a4a4a] hover:bg-gray-500 dark:text-white px-4 py-2 rounded-3xl font-medium text-[18px] cursor-pointer'>Tất cả</button>
                <button className='bg-gray-300 text-black dark:bg-[#4a4a4a] hover:bg-gray-500 dark:text-white px-4 py-2 rounded-3xl font-medium text-[18px] cursor-pointer'>Chưa đọc</button>
            </div>
            {/*  notifications component */}
            <div className="notifications flex flex-col gap-[8px] p-[5px] cursor-pointer">
                {/* Unread notification */}
                <div className="notification flex justify-between items-center">
                    <div className='flex gap-[15px] mr-[10px]'>
                        <img src={DefaultProfilePic} alt="" className='w-[60px] h-[60px] rounded-full object-cover object-center' />
                        <div className="content flex flex-col">
                            <div className='notification-info items-center'>
                                <span className="lastest-msg font-medium text-[20px] text-gray-700 dark:text-[lightgray]">
                                    <b className='text-black dark:text-gray-300'>Danny</b> đã bày tỏ cảm xúc về bài viết của bạn
                                </span>
                            </div>
                            <p className="date font-medium text-[20px] text-blue-500">7h</p>
                        </div>
                    </div>
                    <div className="blue-dot w-[20px] h-[20px] bg-blue-500 rounded-full"></div>
                </div>


                {/* Seen notification */}
                <div className="notification flex gap-[15px]">
                    <img src={DefaultProfilePic} alt="" className='w-[60px] h-[60px] rounded-full object-cover object-center' />
                    <div className="content flex flex-col">
                        <div className='notification-info items-center'>
                            <span className="lastest-msg font-medium text-[20px] text-gray-700 dark:text-[gray]">
                                <b className='text-black dark:text-gray-300'>Danny</b> đã bày tỏ cảm xúc về bài viết của bạn
                            </span>
                        </div>
                        <p className="date font-medium text-[20px] text-gray-700 dark:text-[gray]">7h</p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Notification