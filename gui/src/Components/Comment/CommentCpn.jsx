import React, {useContext, useState} from 'react'
import './CommentCpn.css'
import moment from 'moment'
import 'moment/locale/vi';
import {isAudio, isVideo} from "../../Utils/Media/checkFileType.js"
import {getImageUrl} from '../../Utils/Media/getImageUrl.js';
import {AuthContext} from "../../Context/AuthContext.jsx";
import EditIcon from '@mui/icons-material/Edit';
import AttachFileIcon from '@mui/icons-material/AttachFile';


moment.locale('vi');

const CommentCpn = ({
                        comment,
                        isEditing,      // Boolean flag that determines whether the comment is currently being edited
                        editText,       // Holds the current text content in the edit form.
                        setEditText,    // Function to update the editText state when the user types in the edit textarea.
                        editFiles,      // Array that stores file objects selected during comment editing (for attaching new media).
                        setEditFiles,   // Function to update the editFiles state when the user selects new files to attach to the comment.
                        onStartEdit,    // Callback function triggered when the user clicks the edit button. It sets up the editing state in the parent component.
                        onCancelEdit,   // Callback function triggered when the user clicks the Cancel button while editing. Resets the editing state.
                        onSaveEdit      // Callback function that handles saving the edited comment. It receives the updated text and files and makes the API call to update the comment.
                    }) => {
    // State slider if comments has multiple media files
    const [currentIndex, setCurrentIndex] = useState(0);

    // Authentication
    const {currentUser} = useContext(AuthContext)

    // console.log(`Comment From UserID: ${typeof comment.userId}`)
    // console.log(`UserID: ${typeof currentUser.userId}`)
    // console.log("====")

    // Handler của nút prevSlider
    const handlePrev = () => {
        setCurrentIndex((prevIndex) => (prevIndex === 0 ? comment.media.length - 1 : prevIndex - 1));
    };

    // Handler của nút nextSlider
    const handleNext = () => {
        setCurrentIndex((prevIndex) => (prevIndex === comment.media.length - 1 ? 0 : prevIndex + 1));
    };

    // TODO: Clean up this mess, create more components for Edit Form, Display Comment
    return (
        <div className="comment" key={comment.commentId}>
            {isEditing ? (
                // Edit Form
                <div className="p-3 bg-white dark:bg-[#343434] rounded-md shadow">
                    <textarea
                        value={editText}
                        onChange={(e) => setEditText(e.target.value)}
                        className="w-full p-2 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent resize-y min-h-[80px] bg-white dark:bg-gray-700 text-gray-900 dark:text-gray-100"
                        placeholder="Edit your comment..."
                    />
                    <div className="mt-2">
                        <label
                            className="inline-flex items-center px-3 py-2 text-sm font-medium text-gray-700 dark:text-gray-300 bg-gray-100 dark:bg-gray-700 rounded-md cursor-pointer hover:bg-gray-200 dark:hover:bg-gray-600"
                            htmlFor={`edit-file-input-${comment.commentId}`}>
                            <AttachFileIcon/> Add media
                        </label>
                        <input
                            id={`edit-file-input-${comment.commentId}`}
                            type="file"
                            accept="image/*,video/*"
                            multiple
                            style={{display: 'none'}}
                            onChange={(e) => setEditFiles(Array.from(e.target.files))}
                        />
                    </div>

                    {editFiles.length > 0 && (
                        <div className="edit-file-preview">
                            {editFiles.length} file(s) selected
                        </div>
                    )}
                    <div className="flex justify-end mt-2 gap-2">
                        <button
                            className="px-3 py-1.5 rounded bg-blue-500 hover:bg-blue-600 text-white border-none cursor-pointer text-sm"
                            onClick={() => onSaveEdit(editText, editFiles)}
                        >
                            Save
                        </button>
                        <button
                            className="px-3 py-1.5 rounded bg-gray-200 dark:bg-gray-600 text-gray-900 dark:text-gray-200 border-none cursor-pointer text-sm hover:bg-gray-300 dark:hover:bg-gray-500"
                            onClick={onCancelEdit}
                        >
                            Cancel
                        </button>
                    </div>
                </div>

            ) : (
                // Display Comment
                <>
                    <div className="row-1">
                        <img src={getImageUrl(comment.profileImageUrl)} className="avatar" alt=""/>
                        <div className="content">
                            <div className="info">
                                <div className='left'>
                                    <span>{comment.username}</span>
                                    <p>{comment.content}</p>
                                </div>
                                <div className='right'>
                                    <span className="date">{moment(comment.createdAt).fromNow()}</span>
                                    {/* Add edit button if the comment belongs to current user */}
                                    {comment.userId === Number(currentUser.userId) && (
                                        // Edit Button
                                        <button
                                            className="bg-transparent border-none cursor-pointer text-gray-500 ml-2 hover:text-blue-500"
                                            onClick={onStartEdit}
                                        >
                                            <EditIcon fontSize="small"/>
                                        </button>
                                    )}
                                </div>
                            </div>

                            <div className="row-1">
                                {comment.media && comment.media.length > 0 && (
                                    <div className="row-2 w-full p-0">
                                        {comment.media.length === 1 ? (
                                            // 1 media
                                            // nếu là Audio
                                            isAudio(comment.media[0].filePath) ? (
                                                    <audio
                                                        src={getImageUrl(comment.media[0].filePath)}
                                                        controls
                                                        className="w-full rounded-md"
                                                    />
                                                ) :
                                                // nếu là Video
                                                isVideo(comment.media[0].filePath) ? (
                                                    <video
                                                        src={getImageUrl(comment.media[0].filePath)}
                                                        controls
                                                        className="w-full object-center object-cover max-h-[500px] rounded-md"
                                                    />
                                                ) : (
                                                    <img
                                                        src={getImageUrl(comment.media[0].filePath)}
                                                        alt="Post media"
                                                        className="w-full object-center object-cover max-h-[500px] rounded-md"
                                                    />
                                                )
                                        ) : (
                                            // Nhiều media – slider
                                            <div className="relative w-full max-h-[500px] overflow-hidden">
                                                <div
                                                    className="flex w-full transition-transform duration-250 ease-in-out"
                                                    style={{
                                                        transform: `translateX(-${currentIndex * 100}%)`,
                                                    }}
                                                >
                                                    {comment.media.map((item, index) => (
                                                        <div key={index} className="w-full flex flex-shrink-0">
                                                            {isVideo(item.filePath) ? (
                                                                <video
                                                                    src={getImageUrl(item.filePath)}
                                                                    controls
                                                                    className="w-full h-full object-center object-cover max-h-[500px] rounded-md"
                                                                />
                                                            ) : (
                                                                <img
                                                                    src={getImageUrl(item.filePath)}
                                                                    alt={`Post media ${index + 1}`}
                                                                    className="w-full h-full object-center object-cover max-h-[500px] rounded-md"
                                                                />
                                                            )}
                                                        </div>
                                                    ))}
                                                </div>

                                                <button
                                                    onClick={handlePrev}
                                                    className="absolute left-2 top-1/2 transform -translate-y-1/2 bg-gray-800 text-white p-4 rounded-full hover:opacity-50 text-[36px] cursor-pointer"
                                                >
                                                    ←
                                                </button>
                                                <button
                                                    onClick={handleNext}
                                                    className="absolute right-2 top-1/2 transform -translate-y-1/2 bg-gray-800 text-white p-4 rounded-full hover:opacity-50 text-[36px] cursor-pointer"
                                                >
                                                    →
                                                </button>

                                                <div
                                                    className="absolute top-12 right-3 transform -translate-y-1/2 bg-gray-800 text-white px-5 py-3 rounded-full">
                                                    {currentIndex + 1} / {comment.media.length}
                                                </div>
                                            </div>
                                        )}
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>
                </>
            )}
        </div>
    )
}

export default CommentCpn