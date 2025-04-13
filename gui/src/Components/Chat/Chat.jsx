import React, { useState } from 'react'
import './Chat.css'
import MoreHorizOutlinedIcon from '@mui/icons-material/MoreHorizOutlined';
import ZoomOutMapOutlinedIcon from '@mui/icons-material/ZoomOutMapOutlined';
import BorderColorOutlinedIcon from '@mui/icons-material/BorderColorOutlined';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import Messages from '../Messages/Messages';

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
            <Messages />
        </div >
    )
}

export default Chat