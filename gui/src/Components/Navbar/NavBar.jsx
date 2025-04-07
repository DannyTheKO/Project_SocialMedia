import React, { useContext } from 'react'
import './NavBar.css'
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import WbSunnyOutlinedIcon from '@mui/icons-material/WbSunnyOutlined';
import DarkModeOutlinedIcon from '@mui/icons-material/DarkModeOutlined';
import GridViewOutlinedIcon from '@mui/icons-material/GridViewOutlined';
import NotificationsOutlinedIcon from '@mui/icons-material/NotificationsOutlined';
import EmailOutlinedIcon from '@mui/icons-material/EmailOutlined';
import PersonOutlineOutlinedIcon from '@mui/icons-material/PersonOutlineOutlined';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import ListIcon from '@mui/icons-material/List';
import { Link } from 'react-router'
import PlaceHolderImage from '../../assets/login-image.jpg'
import { ThemeContext } from '../../Context/ThemeContext';

const NavBar = () => {

    const { darkMode, toggleDarkMode } = useContext(ThemeContext) // truy cập Context


    return (
        <div className='navbar'>
            <div className="left">
                <Link to="/">
                    <span className='logo'>Social Media</span>
                </Link>
                <HomeOutlinedIcon className='icon text-color' style={{ fontSize: '32px' }} />
                <button onClick={toggleDarkMode}>
                    {darkMode ? (
                        <WbSunnyOutlinedIcon className='icon text-color' style={{ fontSize: '32px' }} />
                    ) : (
                        <DarkModeOutlinedIcon className='icon text-color' style={{ fontSize: '32px' }} />
                    )}
                </button>
                <GridViewOutlinedIcon className='icon text-color' style={{ fontSize: '32px' }} />

                <div className="search">
                    <SearchOutlinedIcon className='icon text-color' />
                    <input type="text"
                        placeholder='Search...'
                        className='search-input text-color' />
                </div>
            </div>

            <div className="right">
                <div className='listIcon'>
                    <ListIcon className='text-color' style={{ fontSize: '36px' }} />
                </div>
                <PersonOutlineOutlinedIcon className='icon text-color' style={{ fontSize: '32px' }} />
                <EmailOutlinedIcon className='icon text-color' style={{ fontSize: '32px' }} />
                <NotificationsOutlinedIcon className='icon text-color' style={{ fontSize: '32px' }} />
                <div className="user">
                    <img src={PlaceHolderImage} alt="" className='avatar' />
                    <span className='text-[20px] text-color'>Tuan Thai</span>
                </div>
            </div>
        </div>
    )
}

export default NavBar