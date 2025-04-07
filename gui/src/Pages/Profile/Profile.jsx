import React from 'react'
import './Profile.css'
import FacebookTwoToneIcon from "@mui/icons-material/FacebookTwoTone";
import LinkedInIcon from "@mui/icons-material/LinkedIn";
import InstagramIcon from "@mui/icons-material/Instagram";
import PinterestIcon from "@mui/icons-material/Pinterest";
import TwitterIcon from "@mui/icons-material/Twitter";
import PlaceIcon from "@mui/icons-material/Place";
import LanguageIcon from "@mui/icons-material/Language";
import EmailOutlinedIcon from "@mui/icons-material/EmailOutlined";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import Posts from '../../Components/Posts/Posts'

const Profile = () => {
    return (
        <div className='profile'>
            <div className='images'>
                <img src="https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load"
                    alt=""
                    className='cover h-full w-full object-cover'
                />
                <img src="https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load"
                    alt=""
                    className='profilePic'
                />
            </div>
            <div className="profileContainer">
                <div className="profileUserInfo">
                    <div className="left">
                        <a href="http://facebook.com" className='text-gray-500'>
                            <FacebookTwoToneIcon fontSize="large" />
                        </a>
                        <a href="http://facebook.com" className='text-gray-500'>
                            <InstagramIcon fontSize="large" />
                        </a>
                        <a href="http://facebook.com" className='text-gray-500'>
                            <TwitterIcon fontSize="large" />
                        </a>
                        <a href="http://facebook.com" className='text-gray-500'>
                            <LinkedInIcon fontSize="large" />
                        </a>
                        <a href="http://facebook.com" className='text-gray-500'>
                            <PinterestIcon fontSize="large" />
                        </a>
                    </div>
                    <div className="center">
                        <span>User</span>
                        <div className="info">
                            <div className="item">
                                <PlaceIcon />
                                <span>Viet Nam</span>
                            </div>
                            <div className="item">
                                <LanguageIcon />
                                <span>Java Spring Boot</span>
                            </div>
                        </div>
                        <button>Follow</button>
                    </div>
                    <div className="right">
                        <EmailOutlinedIcon style={{ cursor: 'pointer' }} />
                        <MoreVertIcon style={{ cursor: 'pointer' }} />
                    </div>
                </div>
                <Posts />
            </div>
        </div>

    )
}

export default Profile