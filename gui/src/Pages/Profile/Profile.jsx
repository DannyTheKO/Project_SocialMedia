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

const Profile = () => {
    return (
        <div className='profile bg-white'>
            <div className='images w-full h-[300px] relative'>
                <img src="https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load" alt="" className='cover h-full w-full object-cover' />
                <img src="https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load" alt="" className='profilePic w-[200px] h-[200px] rounded-full object-cover absolute left-0 right-0 m-auto top-[200px]' />
            </div>
            <div className="profileContainer py-[20px] px-[70px]">
                <div className="userInfo h-[180px] rounded-lg bg-white text-black p-[50px] flex items-center justify-between mb-[20px]">
                    <div className="left flex-[1] flex gap-[10px]">
                        <a href="http://facebook.com" class='text-gray-500'>
                            <FacebookTwoToneIcon fontSize="large" />
                        </a>
                        <a href="http://facebook.com" class='text-gray-500'>
                            <InstagramIcon fontSize="large" />
                        </a>
                        <a href="http://facebook.com" class='text-gray-500'>
                            <TwitterIcon fontSize="large" />
                        </a>
                        <a href="http://facebook.com" class='text-gray-500'>
                            <LinkedInIcon fontSize="large" />
                        </a>
                        <a href="http://facebook.com" class='text-gray-500'>
                            <PinterestIcon fontSize="large" />
                        </a>
                    </div>
                    <div className="center flex-[1] flex flex-col items-center gap-[10px] mt-[100px]">
                        <span class='font-medium text-[30px]'>User</span>
                        <div className="info w-full flex items-center justify-around">
                            <div className="item flex items-center gap-[10px] text-gray-500">
                                <PlaceIcon />
                                <span>Viet Nam</span>
                            </div>
                            <div className="item flex items-center gap-[10px] text-gray-500">
                                <LanguageIcon />
                                <span>Java Spring Boot</span>
                            </div>
                        </div>
                        <button className='border-none bg-[#5271FF] tetx-white py-[10px] px-[20px] rounded-md cursor-pointer'>Follow</button>
                    </div>
                    <div className="right flex-[1] flex items-center justify-end gap-[10px]">
                        <EmailOutlinedIcon />
                        <MoreVertIcon />
                    </div>
                </div>
            </div>
        </div>

    )
}

export default Profile