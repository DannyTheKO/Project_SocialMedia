import React, {useEffect, useState} from 'react'
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
import {useParams} from 'react-router';
import {userApi} from '../../Services/UserService/userService'
import DefaultProfilePic from '../../assets/defaultProfilePic.jpg';
import {ApiResponse} from "../../Model/ApiResponse.jsx";
import {User} from "../../Model/User.jsx";

const Profile = () => {

    const {id} = useParams();
    const [userName, setUserName] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [bio, setBio] = useState('');
    const [profileImageUrl, setProfileImageUrl] = useState('');
    const [bannerImageUrl, setBannerImageUrl] = useState('');

    const getImageUrl = (filePath) => {
        if (!filePath) return DefaultProfilePic;

        const baseUrl = "http://localhost:8080";

        try {
            // Thử split với "uploads\", nếu không được thì thử với "uploads\\"
            let relativePath = filePath.split("uploads\\")[1] || filePath.split("uploads\\\\")[1];

            // Nếu không split được, trả về ảnh mặc định
            if (!relativePath) {
                console.warn("Không thể parse đường dẫn ảnh:", filePath);
                return DefaultProfilePic;
            }

            // Thay tất cả dấu \ thành / để tạo URL hợp lệ
            const cleanPath = relativePath.replace(/\\/g, "/");

            // Tạo URL public
            const fullUrl = `${baseUrl}/uploads/${cleanPath}`;
            console.log("Generated Image URL:", fullUrl);
            return fullUrl;
        } catch (error) {
            console.error("Lỗi khi tạo URL ảnh:", error, "FilePath:", filePath);
            return DefaultProfilePic;
        }
    };

    /*
    * Anh clean lại cái này lun nha
    *
     */

    useEffect(() => {
        // In case of ignored Promise
        loadUserInfo().then(user => {
            return user
        })
    }, [id])

    const loadUserInfo = async () => {
        try {
            const response = await userApi.getUserById(id);
            // console.log(response);

            // Since Axios interceptor automatically returns response.data
            // and backend returns ApiResponse with "message" and "data" fields
            if (response.message === "Success" && response.data) {
                const userData = new User(response.data); // Create User instance
                setUserName(userData.username);
                setFirstName(userData.firstName);
                setLastName(userData.lastName);
                setEmail(userData.email);
                setBio(userData.bio);
                setProfileImageUrl(userData.profileImageUrl);
                setBannerImageUrl(userData.bannerImageUrl);
            } else {
                console.warn('Invalid response:', response);
                clearUserData();
            }
        } catch (error) {
            console.error("Error fetching user data: ", error.response?.data || error.message);
            clearUserData();
        }
    }

// Helper function to clear user data
    const clearUserData = () => {
        setUserName('');
        setFirstName('');
        setLastName('');
        setEmail('');
        setBio('');
        setProfileImageUrl('');
        setBannerImageUrl('');
    }


    return (
        <div className='profile'>
            <div className='images'>
                <img
                    src={getImageUrl(bannerImageUrl) || DefaultProfilePic}
                    alt="Cover"
                    className='cover h-full w-full object-cover'
                />
                <img
                    src={getImageUrl(profileImageUrl) || DefaultProfilePic}
                    alt="Profile"
                    className='profilePic'
                />
            </div>
            <div className="profileContainer">
                <div className="profileUserInfo">
                    <div className="left">
                        <a href="http://facebook.com" className='text-gray-500'>
                            <FacebookTwoToneIcon fontSize="large"/>
                        </a>
                        <a href="http://facebook.com" className='text-gray-500'>
                            <InstagramIcon fontSize="large"/>
                        </a>
                        <a href="http://facebook.com" className='text-gray-500'>
                            <TwitterIcon fontSize="large"/>
                        </a>
                        <a href="http://facebook.com" className='text-gray-500'>
                            <LinkedInIcon fontSize="large"/>
                        </a>
                        <a href="http://facebook.com" className='text-gray-500'>
                            <PinterestIcon fontSize="large"/>
                        </a>
                    </div>
                    <div className="center">
                        <span>{userName || 'User'}</span>
                        <div className="info">
                            <div className="item">
                                <PlaceIcon/>
                                <span>Viet Nam</span>
                            </div>
                            <div className="item">
                                <LanguageIcon/>
                                <span>Tiếng Việt</span>
                            </div>
                        </div>
                        <button>Follow</button>
                    </div>
                    <div className="right">
                        <EmailOutlinedIcon style={{cursor: 'pointer'}}/>
                        <MoreVertIcon style={{cursor: 'pointer'}}/>
                    </div>
                </div>
                <Posts userID={id}/>
            </div>
        </div>
    )
}

export default Profile