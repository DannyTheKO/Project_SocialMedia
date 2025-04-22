import React, { useState } from 'react'
import './CreatePost.css'
import CollectionsIcon from '@mui/icons-material/Collections';
import HistoryToggleOffIcon from '@mui/icons-material/HistoryToggleOff';
import CreatePostModal from './CreatePostModal/CreatePostModal';

const CreatePost = () => {

    const [isModalOpen, setIsModalOpen] = useState(false);

    return (
        <div className='create-post h-[200px] mb-[20px] bg-[whitesmoke] dark:bg-[#222] p-[10px] rounded-lg'>
            <div className="row-1 p-[20px] flex gap-[10px]">
                <img src="https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load" className='h-[70px] w-[70px] object-cover rounded-full' />
                <input type="text" placeholder=" Bạn đang nghĩ gì ? "
                    className='text-[24px] bg-gray-300 hover:bg-gray-200 dark:bg-neutral-700 dark:hover:bg-neutral-500 dark:text-gray-300 w-full px-[15px] py-[10px] rounded-4xl outline-none'
                    onClick={() => setIsModalOpen(true)} />
            </div>
            <hr className='h-[0.5px] mx-[20px] text-gray-300 dark:text-gray-600 bg-gray-300 dark:bg-gray-600' />
            <div className="row-2 flex justify-evenly items-center my-[10px] font-medium">
                <div className='p-[20px] px-[40px] text-[24px] dark:text-neutral-300 flex items-center gap-[10px]'>
                    <HistoryToggleOffIcon style={{ color: "red", fontSize: "30px" }} /> Story mới
                </div>
                <div className='p-[20px] px-[40px] text-[24px] dark:text-neutral-300 flex items-center gap-[10px]'
                    onClick={() => setIsModalOpen(true)}>
                    <CollectionsIcon style={{ color: "green", fontSize: "30px" }} /> Ảnh/Video
                </div>
            </div>

            <CreatePostModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
            />
        </div>
    )
}

export default CreatePost