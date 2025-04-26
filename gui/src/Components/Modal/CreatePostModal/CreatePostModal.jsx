import React, {useContext, useState} from 'react'
import './CreatePostModal.css'
import CloseIcon from '@mui/icons-material/Close';
import CollectionsIcon from '@mui/icons-material/Collections';
import { postApi } from '../../../Services/PostService/postService';
import { toast } from 'react-toastify';
import {getMediaUrl} from "../../../Utils/Media/getMediaUrl.js";
import {AuthContext} from "../../../Context/AuthContext.jsx";

const CreatePostModal = ({ isOpen, onClose }) => {

    const [content, setContent] = useState("");
    const [files, setFiles] = useState([]);
    const [privacy, setPrivacy] = useState("Công khai");
    const [error, setError] = useState(null);

    const {currentUser} = useContext(AuthContext)

    if (!isOpen) return null;

    const handleFileChange = (e) => {
        console.log(e.target.files)
        setFiles((prevFiles) => [...prevFiles, ...Array.from(e.target.files)]);
    };

    const removeFile = (index) => {
        setFiles((prevFiles) => prevFiles.filter((_, i) => i !== index));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Debug log 
        console.log("Post content:", content, "Privacy:", privacy);

        if (!content.trim() && files.length === 0) {
            setError("Vui lòng nhập nội dung hoặc thêm ảnh/video!");
            toast.error("Vui lòng nhập nội dung hoặc thêm ảnh/video!");
            return;
        }

        const postData = {
            content: content
        }

        // add media files to postData
        files.forEach((file, index) => {
            postData[`files[${index}]`] = file;
        });

        try {
            const response = await postApi.createPost(postData);
            const result = response.data;

            // Debug data log
            // console.log(result)

            if (response.message === "Success") {
                // console.log("Post created successfully: ", result.data); 
                // result data is PostDTO

                toast.success("Đăng bài viết thành công !");
                setContent("");
                setPrivacy("Công khai");
                setError(null);
                onClose();
            }
            else {
                toast.error(result.message || "Đã có lỗi xảy ra khi đăng bài!");
            }
        } catch (error) {
            console.error("Error creating post: ", error);
            toast.error("Đã có lỗi xảy ra khi đăng bài. Vui lòng thử lại!");
        }
    }

    return (
        <>
            {/* Overlay */}
            <div className='fixed inset-0 bg-black opacity-80 flex items-center justify-center z-40'></div>

            {/* Modal Content */}
            <div className="fixed inset-0 flex items-center justify-center z-50">
                <div className="bg-[whitesmoke] dark:bg-neutral-800 w-[50%] max-w-[70%] rounded-lg p-4 relative text-[28px]">
                    {/* Header and close button */}
                    <div className="border-b border-gray-600 py-4 mb-4">
                        <h2 className="text-black dark:text-white font-bold text-center text-[32px]">CREATE POST</h2>
                        <button onClick={onClose} className="absolute right-4 top-4 text-gray-500 hover:text-black dark:hover:text-gray-200">
                            <CloseIcon style={{ fontSize: "36px", cursor: "pointer" }} />
                        </button>
                    </div>

                    {/* User Information */}
                    <div className="flex gap-[10px] items-center mb-4">
                        <img src={getMediaUrl(currentUser.profileImageUrl)}
                            alt="User avatar" className="w-[60px] h-[60px] rounded-full mr-3" />
                        <div>
                            <p className="text-black dark:text-white font-medium">{currentUser.username}</p>
                            <select value={privacy} onChange={(e) => setPrivacy(e.target.value)} className="bg-neutral-300 hover:bg-neutral-400 dark:bg-neutral-600 dark:text-white text-[18px] rounded p-2 cursor-pointer">
                                <option value="Công khai">Công khai</option>
                                <option value="Bạn bè">Bạn bè</option>
                                <option value="Chỉ mình tôi">Chỉ mình tôi</option>
                            </select>
                        </div>
                    </div>

                    {/* Content textarea */}
                    <textarea
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        placeholder={`What's on your mind, ${currentUser.username}?`}
                        className="w-full bg-transparent dark:text-white placeholder-gray-400 border-none outline-none resize-none h-24 mb-4"
                    />

                    {/* Preview selected files */}
                    {files.length > 0 && (
                        <div className="grid grid-cols-3 gap-2 mb-4">
                            {files.map((file, index) => (
                                <div key={index} className="relative">
                                    {file.type.startsWith("image/") ? (
                                        <img
                                            src={URL.createObjectURL(file)}
                                            alt={file.name}
                                            className="w-full max-h-[200px] object-cover rounded-lg"
                                        />
                                    ) : (
                                        <video
                                            src={URL.createObjectURL(file)}
                                            className="w-full max-h-[200px] object-cover rounded-lg"
                                            controls
                                        />
                                    )}
                                    <button
                                        onClick={() => removeFile(index)}
                                        className="absolute top-2 right-2 bg-black text-white rounded-full w-[30px] h-[30px] p-[10px] flex items-center justify-center cursor-pointer"
                                    >
                                        <CloseIcon />
                                    </button>
                                </div>
                            ))}
                        </div>
                    )}


                    {/* Drag/drop media files */}
                    <div className="rounded-lg p-3 mb-4 flex items-center justify-center cursor-pointer outline-none bg-neutral-300 hover:bg-neutral-400 dark:bg-neutral-700 dark:hover:bg-neutral-600">
                        <label className="flex flex-col items-center gap-2 dark:text-gray-300 font-normal cursor-pointer text-[20px]">
                            <CollectionsIcon style={{ fontSize: "36px", color: "green" }} />
                            <span className="text-[28px] font-semibold">Add photos/videos</span>
                            or drag and drop
                            <input
                                type="file"
                                multiple
                                onChange={handleFileChange}
                                className="hidden"
                                accept="image/*,video/*"
                            />
                        </label>
                    </div>

                    {/* Display Error */}
                    {error && (
                        <div className="text-red-500 text-center mb-4">{error}</div>
                    )}

                    {/* Post button */}
                    <button onClick={handleSubmit} className="w-full bg-[#1b74e4] text-white font-semibold py-2 rounded-lg hover:bg-blue-700 cursor-pointer">
                        Post
                    </button>
                </div>
            </div>

        </>
    )
}

export default CreatePostModal