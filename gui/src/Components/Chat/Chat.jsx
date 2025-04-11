import React from 'react'
import './Chat.css'
import MoreHorizOutlinedIcon from '@mui/icons-material/MoreHorizOutlined';
import ZoomOutMapOutlinedIcon from '@mui/icons-material/ZoomOutMapOutlined';
import BorderColorOutlinedIcon from '@mui/icons-material/BorderColorOutlined';
import DefaultProfilePic from '../../assets/defaultProfilePic.jpg';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';

const Chat = () => {
    return (
        <div className='chat'>
            <div className="header flex justify-between items-center p-2">
                <div className="left text-[32px] font-bold text-black dark:text-white">
                    Chats
                </div>
                <div className="right flex gap-[15px] items-center dark:text-white">
                    <MoreHorizOutlinedIcon />
                    <ZoomOutMapOutlinedIcon />
                    <BorderColorOutlinedIcon />
                </div>
            </div>
            <div className="search-messages flex gap-[15px] items-center mx-[10px] p-[10px] rounded-3xl bg-gray-300 dark:bg-[#434343]">
                <SearchOutlinedIcon className='text-dark dark:text-white' style={{ fontSize: "36px", cursor: 'pointer' }} />
                <input type="text"
                    placeholder='Tìm tin nhắn'
                    className='search-input text-dark dark:text-white' />
            </div>
            {/*  Messages component */}
            <div className="messages flex flex-col gap-[8px] p-[5px] cursor-pointer">
                {/* Self message */}
                <div className="message flex gap-[15px]">
                    <img src={DefaultProfilePic} alt="" className='w-[60px] h-[60px] rounded-full object-cover object-center' />
                    <div className="content flex flex-col">
                        <p className="name font-medium text-[20px] dark:text-white">Thái Tuấn</p>
                        <div className='message-info flex items-center gap-[10px]'>
                            <p className="lastest-msg font-medium text-[20px] text-gray-500 dark:text-[gray]">Bạn: xin chào</p>
                            <p className="date font-medium text-[20px] text-gray-500 dark:text-[gray]">7h</p>
                        </div>
                    </div>
                </div>

                {/* Unread message */}
                <div className="message flex justify-between items-center">
                    <div className='flex gap-[15px]'>
                        <img src={DefaultProfilePic} alt="" className='w-[60px] h-[60px] rounded-full object-cover object-center' />
                        <div className="content flex flex-col">
                            <p className="name font-medium text-[20px] dark:text-white">KO</p>
                            <div className='message-info flex items-center gap-[10px]'>
                                <p className="lastest-msg font-medium text-[20px] dark:text-white">KO: xin chào</p>
                                <p className="date font-medium text-[20px] dark:text-white">7h</p>
                            </div>
                        </div>
                    </div>
                    <div className="blue-dot w-[20px] h-[20px] bg-blue-500 rounded-full"></div>
                </div>


                {/* Seen message */}
                <div className="message flex gap-[15px]">
                    <img src={DefaultProfilePic} alt="" className='w-[60px] h-[60px] rounded-full object-cover object-center' />
                    <div className="content flex flex-col">
                        <p className="name font-medium text-[20px] dark:text-white">Danny</p>
                        <div className='message-info flex items-center gap-[10px]'>
                            <p className="lastest-msg font-medium text-[20px] text-gray-500 dark:text-[gray]">Danny: xin chào</p>
                            <p className="date font-medium text-[20px] text-gray-500 dark:text-[gray]">7h</p>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    )
}

export default Chat