import React, {useContext, useState} from 'react';
import moment from 'moment';
import 'moment/locale/vi';
import './SingleCommentComponent.css';

import {isAudio, isVideo} from "../../../Utils/Media/checkFileType.js"
import {getImageUrl} from '../../../Utils/Media/getImageUrl.js';
import {AuthContext} from "../../../Context/AuthContext.jsx";

import CommentEditForm from "./EditForm/CommentEditForm.jsx";

import EditIcon from '@mui/icons-material/Edit';


moment.locale('vi');

const SingleCommentComponent = ({
                                    comment,

                                    // This is for Flag switch between Edit and Display
                                    isEditing,      // Boolean flag that determines whether the comment is currently being edited

                                    // This Variable for Display Section
                                    onStartEdit,    // Callback function triggered when the user clicks the edit button. It sets up the editing state in the parent component.

                                    // This Variable Edit Form Section
                                    onCancelEdit,   // Callback function triggered when the user clicks the Cancel button while editing. Resets the editing state.
                                    onSaveEdit,     // Callback function that handles saving the edited comment. It receives the updated text and files and makes the API call to update the comment.
                                }) => {
    // State slider if comments has multiple media files
    const [currentIndex, setCurrentIndex] = useState(0);

    // Authentication
    const {currentUser} = useContext(AuthContext)

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
                // Edit Form Action
                <CommentEditForm
                    comment={comment}
                    onCancel={onCancelEdit}
                    onSave={onSaveEdit}
                />
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

export default SingleCommentComponent