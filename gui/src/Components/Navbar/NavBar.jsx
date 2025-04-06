import React from 'react'
import './NavBar.scss'
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import WbSunnyOutlinedIcon from '@mui/icons-material/WbSunnyOutlined';
import DarkModeOutlinedIcon from '@mui/icons-material/DarkModeOutlined';
import GridViewOutlinedIcon from '@mui/icons-material/GridViewOutlined';
import NotificationsOutlinedIcon from '@mui/icons-material/NotificationsOutlined';
import EmailOutlinedIcon from '@mui/icons-material/EmailOutlined';
import PersonOutlineOutlinedIcon from '@mui/icons-material/PersonOutlineOutlined';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import { Link } from 'react-router'
import PlaceHolderImage from '../../assets/login-image.jpg'

const NavBar = () => {
    return (
        <div className='navbar flex items-center justify-between py-[20px] px-[20px] h-[70px] border-b border-gray-400 sticky top-0 bg-white'>
            <div className="left flex items-center gap-[30px]">
                <Link to="/">
                    <span className='font-bold text-[22px] text-blue-900'>TTuan Social</span>
                </Link>
                <HomeOutlinedIcon />
                <DarkModeOutlinedIcon />
                <GridViewOutlinedIcon />

                <div className="search flex items-center gap-[10px] border-1 border-gray-400 rounded-sm p-[8px]">
                    <SearchOutlinedIcon />
                    <input type="text"
                        placeholder='Search...'
                        className='border-none w-[500px]' />
                </div>
            </div>

            <div className="right flex items-center gap-[25px]">
                <PersonOutlineOutlinedIcon />
                <EmailOutlinedIcon />
                <NotificationsOutlinedIcon />
                <div className="user flex items-center gap-[10px] font-medium">
                    <img src={PlaceHolderImage} alt="" className='w-[30px] h-[30px] rounded-full object-cover' />
                    <span className='text-[18px]'>Tuan Thai</span>
                </div>
            </div>
        </div>
    )
}

export default NavBar