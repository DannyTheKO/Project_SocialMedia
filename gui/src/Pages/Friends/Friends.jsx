import React, { useEffect, useState } from 'react';
import './Friends.css';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import CancelPresentationIcon from '@mui/icons-material/CancelPresentation';
import CheckOutlinedIcon from '@mui/icons-material/CheckOutlined';
import VisibilityIcon from '@mui/icons-material/Visibility';
import DefaultProfilePic from '../../Assets/defaultProfilePic.jpg';
import moment from 'moment';
import 'moment/locale/vi';
import { toast } from 'react-toastify';
import { relationshipsApi } from '../../Services/RelationshipsService/relationshipsService';
import { Link } from 'react-router';
moment.locale('vi');

const Friends = () => {
    const [friendRequests, setFriendRequests] = useState([]);
    const [friends, setFriends] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [error, setError] = useState(null);
    const [viewMode, setViewMode] = useState('requests');

    // Lấy danh sách yêu cầu kết bạn (PENDING)
    const fetchFriendRequests = async (pageNum = 0, size = 10) => {

        try {
            const response = await relationshipsApi.getPendingRequests(pageNum, size);
            const data = response.data; // ApiResponse { data: Page<Relationships> }
            setFriendRequests(data); // List<Relationships>
            setError(null);
        } catch (err) {
            setError('Không thể tải danh sách lời mời kết bạn.');
            console.error(err);
        }
    };

    // Lấy danh sách bạn bè (FRIENDS)
    const fetchFriends = async () => {
        try {
            const response = await relationshipsApi.getFriends();
            const data = response.data; // ApiResponse { data: List<RelationshipsDTO> }
            setFriends(data);
            setError(null);
        } catch (err) {
            setError('Không thể tải danh sách bạn bè.');
            console.error(err);
        }
    };

    // Xử lý chấp nhận yêu cầu kết bạn
    const handleAccept = async (relationshipId) => {
        try {
            await relationshipsApi.acceptFriendRequest(relationshipId);
            toast.success("Đã thêm người dùng vào danh sách bạn bè!");
            // Cập nhật danh sách yêu cầu
            setFriendRequests(friendRequests.filter((req) => req.relationshipId !== relationshipId));
            setError(null);
        } catch (err) {
            toast.error("Lỗi khi chấp nhận lời mời kết bạn!");
            setError('Không thể chấp nhận lời mời.');
            console.error(err);
        }
    };

    // Xử lý từ chối yêu cầu kết bạn
    const handleReject = async (relationshipId) => {
        try {
            await relationshipsApi.rejectFriendRequest(relationshipId);
            // Cập nhật danh sách
            setFriendRequests(friendRequests.filter((req) => req.relationshipId !== relationshipId));
        } catch (err) {
            setError('Không thể gỡ lời mời.');
            console.error(err);
        }
    };

    const handleViewModeChange = (mode) => {
        setViewMode(mode);
        if (mode === 'requests') {
            fetchFriendRequests(page);
        } else if (mode === 'friends') {
            fetchFriends();
        }
    };

    // Gọi API khi component mount hoặc "page"/"viewMode" thay đổi
    useEffect(() => {
        if (viewMode === 'requests') {
            fetchFriendRequests(page);
        }
    }, [page, viewMode]);

    // Xử lý chuyển trang
    const handlePageChange = (newPage) => {
        if (newPage >= 0 && newPage < totalPages) {
            setPage(newPage);
        }
    };

    return (
        <div className='friends'>
            {/* Row 1: Title and search input */}
            <div className='row-1 flex flex-col gap-[15px]'>
                <div className="flex justify-between items-center dark:text-white">
                    <p className="text-[24px] font-semibold dark:text-white">Bạn bè</p>
                    <div className="w-2/5 border-1 border-gray-400 dark:border-gray-200 flex p-2 rounded-md">
                        <SearchOutlinedIcon style={{ fontSize: '36px', cursor: 'pointer' }} />
                        <input
                            type="text"
                            placeholder="Tìm ..."
                            className="search-input text-color"
                        />
                    </div>
                </div>
                <div className="flex gap-[10px]">
                    <button
                        onClick={() => handleViewModeChange('requests')}
                        className={`hover:bg-gray-500 px-4 py-2 rounded-3xl font-medium text-[18px] cursor-pointer ${viewMode === 'requests' ? 'bg-blue-600 text-white' : 'bg-gray-300 dark:bg-[#4a4a4a] text-black dark:text-white'}`}
                    >
                        Yêu cầu
                    </button>
                    <button
                        onClick={() => handleViewModeChange('friends')}
                        className={`hover:bg-gray-500 px-4 py-2 rounded-3xl font-medium text-[18px] cursor-pointer ${viewMode === 'friends' ? 'bg-blue-600 text-white' : 'bg-gray-300 dark:bg-[#4a4a4a] text-black dark:text-white'}`}
                    >
                        Bạn bè
                    </button>
                </div>
            </div>
            <hr className='text-gray-300 dark:text-gray-500' />

            {/* Row 2: Friend Request */}
            <div className="row-2 flex flex-col gap-[15px]">
                <p className='text-[24px] font-semibold text-black dark:text-white flex gap-[10px] items-center'>
                    {viewMode === 'requests' ? 'Lời mời kết bạn' : 'Danh sách bạn bè'}
                    {viewMode === 'requests' && (
                        <span className="text-[24px] request-count font-semibold text-red-600">
                            {friendRequests.length}
                        </span>
                    )}
                </p>
                {error && <p className="text-red-600">{error}</p>}
                {viewMode === 'requests' && friendRequests.length === 0 && <p>Không có lời mời kết bạn.</p>}
                {viewMode === 'friends' && friends.length === 0 && <p>Không có bạn bè.</p>}

                {/* Hiển thị danh sách yêu cầu kết bạn */}
                {viewMode === 'requests' && friendRequests.map((request) => (
                    <div key={request.relationshipId} className="flex gap-[15px] items-center">
                        <img
                            src={request.user2.profileImageUrl || DefaultProfilePic} // user1 là người gửi yêu cầu
                            className="w-[120px] h-[120px] rounded-full object-cover object-center"
                            alt={`${request.user2.firstName} ${request.user2.lastName}'s avatar`}
                        />
                        <div className="user-info flex flex-col gap-[5px] w-1/2 max-2xl:w-3/4">
                            <p className="text-[22px] font-semibold text-black dark:text-white">
                                {request.user2.firstName} {request.user2.lastName}
                            </p>
                            <p className="date text-[22px] font-normal text-gray-600 dark:text-gray-400">
                                {moment(request.createdAt).fromNow()}
                            </p>
                            <div className="interact flex gap-[10px]">
                                <button
                                    onClick={() => handleAccept(request.relationshipId)}
                                    className="bg-blue-600 hover:bg-gray-200 dark:hover:bg-gray-400 w-3/4 px-6 py-2 text-[22px] font-medium text-white rounded-lg cursor-pointer flex gap-[10px] items-center justify-center"
                                >
                                    <CheckOutlinedIcon />
                                    Chấp nhận
                                </button>
                                <button
                                    onClick={() => handleReject(request.relationshipId)}
                                    className="bg-[#4a4a4a] hover:bg-gray-200 dark:hover:bg-gray-400 w-3/4 px-6 py-2 text-[22px] font-medium text-white rounded-lg cursor-pointer flex gap-[10px] items-center justify-center"
                                >
                                    <CancelPresentationIcon />
                                    Gỡ
                                </button>
                            </div>
                        </div>
                    </div>
                ))}

                {/* Hiển thị danh sách bạn bè */}
                {viewMode === 'friends' && friends.map((friend) => (
                    <div key={friend.relationshipId} className="flex gap-[15px] items-center">
                        <img
                            src={friend.user2.profileImageUrl || DefaultProfilePic}
                            className="w-[120px] h-[120px] rounded-full object-cover object-center"
                            alt={`${friend.user2.firstName} ${friend.user2.lastName}'s avatar`}
                        />
                        <div className="user-info flex flex-col gap-[5px] w-1/2 max-2xl:w-3/4">
                            <p className="text-[22px] font-semibold text-black dark:text-white">
                                {friend.user2.firstName} {friend.user2.lastName}
                            </p>
                            <div className="interact flex gap-[10px]">
                                <Link to={`/profile/${friend.user2.userId}`}>
                                    <button className="bg-blue-600 hover:bg-gray-200 dark:hover:bg-gray-400 w-full px-6 py-2 text-[22px] font-medium text-white rounded-lg cursor-pointer flex gap-[10px] items-center justify-center">
                                        <VisibilityIcon />
                                        Xem thông tin
                                    </button>
                                </Link>
                            </div>
                        </div>
                    </div>
                ))}

                {/* Phân trang
                {totalPages > 1 && viewMode === 'requests' && (
                    <div className="pagination flex gap-[10px] justify-center">
                        <button
                            onClick={() => handlePageChange(page - 1)}
                            disabled={page === 0}
                            className="px-4 py-2 bg-gray-300 rounded disabled:opacity-50"
                        >
                            Trước
                        </button>
                        <span>
                            Trang {page + 1} / {totalPages}
                        </span>
                        <button
                            onClick={() => handlePageChange(page + 1)}
                            disabled={page === totalPages - 1}
                            className="px-4 py-2 bg-gray-300 rounded disabled:opacity-50"
                        >
                            Sau
                        </button>
                    </div>
                )} */}
            </div>
            <hr className='text-gray-300 dark:text-gray-500' />
        </div>
    );
};

export default Friends;