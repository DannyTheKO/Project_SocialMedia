import React from 'react'
import './Friends.css'
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import CancelPresentationIcon from '@mui/icons-material/CancelPresentation';
import CheckOutlinedIcon from '@mui/icons-material/CheckOutlined';

const Friends = () => {
    return (
        <div className='friends'>
            <div className='row-1 flex flex-col gap-[15px]'>
                <div className="flex justify-between items-center dark:text-white">
                    <p className='text-[24px] font-semibold dark:text-white'>Bạn bè</p>
                    <div className='w-2/5 border-1 border-gray-400 dark:border-gray-200 flex p-2 rounded-md'>
                        <SearchOutlinedIcon style={{fontSize: "36px", cursor: 'pointer'}}/>
                        <input type="text"
                               placeholder='Tìm ...'
                               className='search-input text-color'/>
                    </div>

                </div>
                <div className='flex gap-[10px]'>
                    <button
                        className='bg-gray-300 text-black dark:bg-[#4a4a4a] hover:bg-gray-500 dark:text-white px-4 py-2 rounded-3xl font-medium text-[18px] cursor-pointer'>Gợi
                        ý
                    </button>
                    <button
                        className='bg-gray-300 text-black dark:bg-[#4a4a4a] hover:bg-gray-500 dark:text-white px-4 py-2 rounded-3xl font-medium text-[18px] cursor-pointer'>Bạn
                        bè
                    </button>
                </div>
            </div>
            <hr className='text-gray-300 dark:text-gray-500'/>
            <div className="row-2 flex flex-col gap-[15px]">
                <p className='text-[24px] font-semibold text-black dark:text-white flex gap-[10px] items-center'>
                    Lời mời kết bạn
                    <span className='text-[24px] request-count font-semibold text-red-600'>2</span>
                </p>
                <div className='flex gap-[15px] items-center'>
                    <img
                        src="https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load"
                        className='w-[120px] h-[120px] rounded-full object-cover object-center'/>
                    <div className='user-info flex flex-col gap-[5px] w-1/2 max-2xl:w-3/4'>
                        <p className='text-[22px] font-semibold text-black dark:text-white'>Jane Doe</p>
                        <p className='date text-[22px] font-normal text-gray-600 dark:text-gray-400'>2 giờ trước</p>
                        <div className='interact flex gap-[10px]'>
                            <button
                                className='bg-blue-600 hover:bg-gray-200 dark:hover:bg-gray-400 w-3/4 px-6 py-2 text-[22px] font-medium text-white rounded-lg hover:bg- cursor-pointer flex gap-[10px] items-center justify-center'>
                                <CheckOutlinedIcon/>Chấp nhận
                            </button>
                            <button
                                className='bg-[#4a4a4a]  hover:bg-gray-200 dark:hover:bg-gray-400 w-3/4 px-6 py-2 text-[22px] font-medium text-white rounded-lg cursor-pointer flex gap-[10px] items-center justify-center'>
                                <CancelPresentationIcon/>Gỡ
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <hr className='text-gray-300 dark:text-gray-500'/>
            <div className="row-3 flex flex-col gap-[15px]">
                <p className='text-[24px] font-semibold dark:text-white'>Những người bạn có thể biết</p>
                <div className='flex gap-[15px] items-center'>
                    <img
                        src="https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load"
                        className='w-[120px] h-[120px] rounded-full object-cover object-center'/>
                    <div className='user-info flex flex-col gap-[15px] w-1/2 max-2xl:w-3/4'>
                        <p className='text-[22px] font-semibold text-black dark:text-white'>Jane Doe</p>
                        <div className='interact flex gap-[10px]'>
                            <button
                                className='bg-blue-600 hover:bg-gray-200 dark:hover:bg-gray-400 w-3/4 px-6 py-2 text-[22px] font-medium text-white rounded-lg hover:bg- cursor-pointer flex gap-[10px] items-center justify-center'>
                                <PersonAddIcon/>Thêm bạn bè
                            </button>
                            <button
                                className='bg-[#4a4a4a] hover:bg-gray-200 dark:hover:bg-gray-400 w-3/4 px-6 py-2 text-[22px] font-medium text-white rounded-lg cursor-pointer flex gap-[10px] items-center justify-center'>
                                <CancelPresentationIcon/>Gỡ
                            </button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    )
}

export default Friends