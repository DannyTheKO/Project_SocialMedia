import React, { useContext, useEffect, useState } from 'react'
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
import PhotoCameraIcon from '@mui/icons-material/PhotoCamera';
import FileUploadIcon from '@mui/icons-material/FileUpload';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import PersonRemoveIcon from '@mui/icons-material/PersonRemove';
import EditSquareIcon from '@mui/icons-material/EditSquare';
import BlockIcon from '@mui/icons-material/Block';
import Posts from '../../Components/Posts/Posts'
import { useParams } from 'react-router';
import { userApi } from '../../Services/UserService/userService'
import DefaultProfilePic from '../../Assets/defaultProfilePic.jpg';
import { ApiResponse } from "../../Model/ApiResponse.jsx";
import { User } from "../../Model/User.jsx";
import { AuthContext } from '../../Context/AuthContext.jsx';
import { toast } from 'react-toastify';
import { getMediaUrl } from '../../Utils/Media/getMediaUrl.js';
import Chat from '../../Components/Chat/Chat.jsx';
import { relationshipsApi } from '../../Services/RelationshipsService/relationshipsService.jsx';
import Conversation from '../../Components/Conversations/Conversation.jsx';
import EditProfileModal from '../../Components/Modal/EditProfileModal/EditProfileModal.jsx';

const Profile = () => {

    const { currentUser, setCurrentUser } = useContext(AuthContext);
    const { id } = useParams();
    const [selectedUser, setSelectedUser] = useState(null)
    const [userName, setUserName] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [bio, setBio] = useState('');
    const [profileImageUrl, setProfileImageUrl] = useState('');
    const [bannerImageUrl, setBannerImageUrl] = useState('');
    const [openChat, setOpenChat] = useState(false);
    const [isFriend, setIsFriend] = useState(false);
    const [isPending, setIsPending] = useState(false);
    const [isBlocked, setIsBlocked] = useState(false);
    const [relationshipId, setRelationshipId] = useState(null);
    // State dropdown
    const [showDropDown, setShowDropDown] = useState(false);

    // State update profile modal
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [updatedUser, setUpdatedUser] = useState(currentUser);


    // state for change profilePic and bannerPic
    const [selectedProfileFile, setSelectedProfileFile] = useState(null);
    const [selectedBannerFile, setSelectedBannerFile] = useState(null);
    const [profilePreview, setProfilePreview] = useState(null);
    const [bannerPreview, setBannerPreview] = useState(null);
    const [showConfirmModal, setShowConfirmModal] = useState(false);
    const [isProfileImage, setIsProfileImage] = useState(false); // indentify is profilePic or bannerPic

    /*
    * Anh clean lại cái này lun nha
    *
     */

    const checkFriendShip = async () => {
        try {
            const response = await relationshipsApi.checkFriendShip(id);
            // Debug log
            // console.log(response.data)
            setIsFriend(response.data.friend);
            setIsPending(response.data.pending);
            setIsBlocked(response.data.blocked);
            setRelationshipId(isFriend || isPending ? relationshipId : response.data.relationshipId);

            // check BLOCKED status 
            const relResponse = await relationshipsApi.findRelationshipId(id);
            if (relResponse.message === 'Success') {
                const relId = relResponse.data;
                const relDetails = await relationshipsApi.checkFriendShip(relId);
                // Debug log
                // console.log(relId)
                // console.log(relDetails.data)
                if (relDetails.data.blocked) {
                    setIsBlocked(true);
                }
            }
        } catch (error) {
            console.error('Error when checking is friend:', error);
        }
    }

    useEffect(() => {
        loadUserInfo();
        checkFriendShip();
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
                setSelectedUser(userData)
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

    const handleFileChange = (e, type) => {
        const file = e.target.files[0];

        if (!file) return;

        if (type === "profile") {
            setSelectedProfileFile(file)
            setProfilePreview(URL.createObjectURL(file))
            setIsProfileImage(true)
        } else {
            setSelectedBannerFile(file)
            setBannerPreview(URL.createObjectURL(file))
            setIsProfileImage(false)
        }
        setShowConfirmModal(true)
    };

    const handleConfirmUpdate = async () => {
        const fileToUpdate = isProfileImage ? selectedProfileFile : selectedBannerFile;
        const fieldToUpdate = isProfileImage ? "profileImage" : "bannerImage";

        if (!fileToUpdate) {
            toast.error("Không có file nào được chọn!");
            setShowConfirmModal(false);
            return;
        }

        // console.log("File to update:", fileToUpdate);

        const userData = new FormData();
        userData.append(fieldToUpdate, fileToUpdate);

        try {
            const response = await userApi.updateUser(userData);
            if (response.message === "Success" && response.data) {
                // Debug log
                // console.log(response.data)

                const updatedUser = new User(response.data);
                if (isProfileImage) {
                    setProfileImageUrl(updatedUser.profileImageUrl);
                    setSelectedProfileFile(null);
                    setProfilePreview(null);
                } else {
                    setBannerImageUrl(updatedUser.bannerImageUrl);
                    setSelectedBannerFile(null);
                    setBannerPreview(null);
                }
                toast.success(`Cập nhật ${isProfileImage ? "ảnh đại diện" : "ảnh bìa"} thành công!`);
            } else {
                toast.error("Cập nhật thất bại!");
                console.error(response.message)
            }
        } catch (error) {
            console.error("Error updating user: ", error.response?.data || error.message);
            toast.error(`Đã có lỗi xảy ra khi cập nhật ${isProfileImage ? "ảnh đại diện" : "ảnh bìa"} !`);
        }
        setShowConfirmModal(false);
    };

    const handleCancelUpdate = () => {
        setSelectedProfileFile(null);
        setSelectedBannerFile(null);
        setProfilePreview(null);
        setBannerPreview(null);
        setShowConfirmModal(false);
    };

    const handleOpenChat = () => {
        setOpenChat(!openChat);
    }

    const handleAddFriend = async () => {
        try {
            const response = await relationshipsApi.createRelationship(id, 'PENDING')
            if (response.message === 'Success') {
                toast.success('Gửi yêu cầu kết bạn thành công!');
                setIsPending(true)
                setRelationshipId(response.data.relationshipId)
                await checkFriendShip();
            } else {
                toast.error('Gửi yêu cầu kết bạn thất bại!');
            }
        } catch (error) {
            console.error('Error sending friend request:', error.response?.data || error.message);
            toast.error('Đã có lỗi xảy ra khi gửi yêu cầu kết bạn!');
        }
    };

    const handleRemoveFriend = async () => {
        if (!relationshipId) {
            toast.error('Không tìm thấy mối quan hệ để hủy!');
            return;
        }
        try {
            const response = await relationshipsApi.deleteRelationship(relationshipId);
            if (response.message === 'Success') {
                setIsFriend(false);
                setIsPending(false);
                setRelationshipId(null);
                toast.success('Hủy kết bạn thành công!');
            } else {
                toast.error('Hủy kết bạn thất bại!');
            }
        } catch (error) {
            console.error('Error removing friend:', error.response?.data || error.message);
            toast.error('Đã có lỗi xảy ra khi hủy kết bạn!');
        }
    };

    const handleCancelRequest = async () => {
        if (!relationshipId) {
            toast.error('Không tìm thấy yêu cầu để hủy!');
            return;
        }
        try {
            const response = await relationshipsApi.deleteRelationship(relationshipId);
            if (response.message === 'Success') {
                setRelationshipId(null)
                setIsPending(false)
                toast.success('Hủy yêu cầu kết bạn thành công!');
            } else {
                toast.error('Hủy yêu cầu kết bạn thất bại!');
            }
        } catch (error) {
            console.error('Error canceling request:', error.response?.data || error.message);
            toast.error(error.response?.data?.message || 'Đã có lỗi xảy ra khi hủy yêu cầu kết bạn!');
        }
    }

    const handleBlockUser = async () => {
        try {
            const response = await relationshipsApi.blockUser(id);
            if (response.message === "Success") {
                setIsBlocked(true);
                setIsPending(false);
                setIsFriend(false);
                setRelationshipId(null);
                toast.success("Chặn người dùng thành công !");
            } else {
                toast.error("Chặn người dùng thất bại !");
            }
        } catch (error) {
            console.error('Error blocking user:', error.response?.data || error.message);
            toast.error(error.response?.data?.message || 'Đã có lỗi xảy ra khi chặn người dùng!');
        }
    }

    const handleUpdateProfile = async () => {
        setIsModalOpen(true)
        setShowDropDown(false)
    }

    const handleProfileUpdated = (updatedData) => {
        setUpdatedUser(updatedData)
    }

    return (
        <div className='profile'>
            {isBlocked ? (
                <div className="blocked-message">
                    <h2>Người dùng đã bị chặn hoặc đã chặn bạn!</h2>
                </div>
            ) : (
                <>
                    <div className="images">
                        <img
                            src={getMediaUrl(bannerImageUrl) || DefaultProfilePic}
                            alt="Cover"
                            className="cover h-full w-full object-center object-cover rounded-md"
                        />
                        {currentUser?.userId === id && (
                            <label className="upload-banner">
                                <PhotoCameraIcon />
                                <span className="ml-2">Chỉnh sửa ảnh bìa</span>
                                <input
                                    type="file"
                                    onChange={(e) => handleFileChange(e, "banner")}
                                    className="hidden"
                                    accept="image/*"
                                />
                            </label>
                        )}

                        <div className="group relative">
                            <img
                                src={getMediaUrl(profileImageUrl) || DefaultProfilePic}
                                alt="Profile"
                                className="profilePic w-full h-full object-cover rounded-full"
                            />
                            <div
                                className="absolute inset-0 bg-black opacity-0 group-hover:opacity-50 rounded-full transition-opacity duration-300 ease-in-out"
                            ></div>
                            {currentUser?.userId === id && (
                                <label className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-300 ease-in-out cursor-pointer">
                                    <FileUploadIcon />
                                    <span className="ml-2">Tải lên ảnh đại diện mới</span>
                                    <input
                                        type="file"
                                        onChange={(e) => handleFileChange(e, "profile")}
                                        className="hidden"
                                        accept="image/*"
                                    />
                                </label>
                            )}
                        </div>


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
                                {currentUser?.userId !== id && (
                                    <div className={`${isFriend ? 'remove-friend-btn' : isPending ? 'pending-btn' : 'add-friend-btn'} cursor-pointer`}>
                                        {isFriend ? (
                                            <>
                                                <PersonRemoveIcon />
                                                <button onClick={handleRemoveFriend}>
                                                    Hủy kết bạn
                                                </button>
                                            </>
                                        ) : isPending ? (
                                            <>
                                                <PersonRemoveIcon />
                                                <button onClick={handleCancelRequest}>
                                                    Hủy yêu cầu
                                                </button>
                                            </>
                                        ) : (
                                            <>
                                                <div>
                                                    <PersonAddIcon />
                                                    <button onClick={handleAddFriend}>
                                                        Kết bạn
                                                    </button>
                                                </div>
                                                <div>
                                                    <BlockIcon />
                                                    <button onClick={handleBlockUser}>
                                                        Chặn
                                                    </button>
                                                </div>
                                            </>
                                        )}
                                    </div>
                                )}
                            </div>
                            <div className="right relative">
                                {currentUser?.userId != id && (
                                    <EmailOutlinedIcon style={{ cursor: 'pointer' }} onClick={handleOpenChat} />
                                )}
                                <MoreVertIcon style={{ cursor: 'pointer' }} onClick={() => setShowDropDown(!showDropDown)} />
                                {showDropDown && (
                                    <div className="absolute top-[30px] mt-1 w-96 bg-neutral-800 text-white rounded-lg shadow-lg z-50 text-[20px]">
                                        {currentUser.userId == id && (
                                            <>
                                                <button
                                                    onClick={handleUpdateProfile}
                                                    className="w-full text-left px-4 py-2 hover:bg-neutral-700 rounded-t-lg flex gap-[10px] items-center cursor-pointer"
                                                >
                                                    <EditSquareIcon style={{ fontSize: "24px" }} />
                                                    Chỉnh sửa thông tin cá nhân
                                                </button>
                                            </>

                                        )}
                                    </div>
                                )}
                            </div>
                        </div>
                        {openChat && selectedUser && (
                            <Conversation user={selectedUser} onClose={handleOpenChat} />
                        )}
                        <Posts userID={id} />
                    </div>

                    {showConfirmModal && (
                        <>
                            <div className="fixed inset-0 bg-black opacity-80 z-40" onClick={handleCancelUpdate}></div>
                            <div className="fixed inset-0 flex items-center justify-center z-50">
                                <div className="bg-[whitesmoke] dark:bg-neutral-800 w-[50%] max-w-[70%] rounded-lg p-4 relative">
                                    <h2 className="text-black dark:text-white font-bold text-center text-[24px] mb-4">
                                        Xác nhận thay đổi {isProfileImage ? "ảnh đại diện" : "ảnh bìa"}
                                    </h2>
                                    <div className="flex justify-center mb-[20px]">
                                        <img
                                            src={isProfileImage ? profilePreview : bannerPreview}
                                            alt="Preview"
                                            className="max-w-full max-h-[80vh] object-cover"
                                        />
                                    </div>
                                    <div className="flex justify-center gap-[10px]">
                                        <button
                                            onClick={handleConfirmUpdate}
                                            className="bg-[#1b74e4] text-white font-semibold px-[15px] py-[10px] rounded-lg hover:bg-blue-700 cursor-pointer"
                                        >
                                            Xác nhận
                                        </button>
                                        <button
                                            onClick={handleCancelUpdate}
                                            className="bg-gray-500 text-white font-semibold px-[15px] py-[10px] rounded-lg hover:bg-gray-600 cursor-pointer"
                                        >
                                            Hủy
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </>
                    )}
                    <EditProfileModal
                        isOpen={isModalOpen}
                        onClose={() => setIsModalOpen(false)}
                        user={updatedUser}
                        onProfileUpdated={handleProfileUpdated}
                    />
                </>
            )}
        </div>
    )
}

export default Profile