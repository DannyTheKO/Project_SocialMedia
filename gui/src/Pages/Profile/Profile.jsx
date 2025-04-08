import React, { use, useEffect, useState } from 'react'
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
import { useParams } from 'react-router';
import { getUser } from '../../Services/UserService/userService'
import DefaultProfilePic from '../../assets/defaultProfilePic.jpg';

const Profile = () => {

    const { id } = useParams();
    const [userName, setUserName] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [bio, setBio] = useState('');
    const [profileImageUrl, setProfileImageUrl] = useState('');
    const [bannerImageUrl, setBannerImageUrl] = useState('');

    useEffect(() => {
        loadUserInfo()
    }, [id])

    const loadUserInfo = async () => {
        try {
            const response = await getUser(id);

            if (response && response.data) {
                const userData = response.data.data;
                setUserName(userData.username || '');
                setFirstName(userData.firstName || '');
                setLastName(userData.lastName || '');
                setEmail(userData.email || '');
                setBio(userData.bio || '');
                setProfileImageUrl(userData.profileImageUrl || '');
                setBannerImageUrl(userData.bannerImageUrl || '');
            } else {
                console.warn('Invalid data format:', response.data);
                setUserName('');
                setFirstName('');
                setLastName('');
                setEmail('');
                setBio('');
                setProfileImageUrl('');
                setBannerImageUrl('');
            }

        } catch (error) {
            console.error("Lỗi khi lấy dữ liệu người dùng: ", error);
            setUserName('');
            setFirstName('');
            setLastName('');
            setEmail('');
            setBio('');
            setProfileImageUrl('');
            setBannerImageUrl('');
        }
    }

    return (
        <div className='profile'>
            <div className='images'>
                <img
                    src={bannerImageUrl || DefaultProfilePic}
                    alt="Cover"
                    className='cover h-full w-full object-cover'
                />
                <img
                    src={profileImageUrl || DefaultProfilePic}
                    alt="Profile"
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
                        <span>{userName || 'User'}</span>
                        <div className="info">
                            <div className="item">
                                <PlaceIcon />
                                <span>Viet Nam</span>
                            </div>
                            <div className="item">
                                <LanguageIcon />
                                <span>Tiếng Việt</span>
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