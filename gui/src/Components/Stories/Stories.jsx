import React, { useState } from 'react'
import './Stories.css'
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import CloseIcon from '@mui/icons-material/Close';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';

const Stories = () => {

    const [selectedStory, setSelectedStory] = useState(null);

    const handleStoryClick = (story) => {
        setSelectedStory(story)
    }

    const handleCloseStory = () => {
        setSelectedStory(null)
    }

    //TEMPORARY
    const stories = [
        {
            id: 1,
            name: "John Doe1",
            img: "https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load",
        },
        {
            id: 2,
            name: "John Doe2",
            img: "https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load",
        },
        {
            id: 3,
            name: "John Doe3",
            img: "https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load",
        },
        {
            id: 4,
            name: "John Doe4",
            img: "https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load",
        },
        {
            id: 5,
            name: "John Doe5",
            img: "https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load",
        },
        {
            id: 6,
            name: "John Doe6",
            img: "https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load",
        }
    ];

    const [currentIndex, setCurrentIndex] = useState(0);
    const storiesPerPage = 5

    // handler Next Button
    const handleNext = () => {
        if (currentIndex + storiesPerPage < stories.length) {
            setCurrentIndex(currentIndex + 1);
        }
    };

    // handler Prev Button
    const handlePrev = () => {
        if (currentIndex > 0) {
            setCurrentIndex(currentIndex - 1);
        }
    };

    const visibleStories = stories.slice(currentIndex, currentIndex + storiesPerPage);

    // const translateX = -(currentIndex * (100 / storiesPerPage) + 1);

    return (
        <>
            <div className='stories relative'>
                <div className="stories-container">
                    {/* Story fetch được */}
                    {visibleStories.map((story) => (
                        <div className="story" key={story.id} onClick={() => handleStoryClick(story)}>
                            <img src={story.img} alt="" className="w-full h-full object-cover object-center place-items-center" />
                            <span className="absolute bottom-[10px] left-[10px] text-white text-[18px] font-medium max-md:hidden">
                                {story.name}
                            </span>
                        </div>
                    ))}
                </div>


                {/*  Prev Button */}
                {currentIndex > 0 && (
                    <button
                        onClick={handlePrev}
                        className="prev-button"
                    >
                        <ArrowBackIosIcon />
                    </button>
                )}

                {/* Next Button */}
                {currentIndex + storiesPerPage < stories.length && (
                    <button
                        onClick={handleNext}
                        className="next-button"
                    >
                        <ArrowForwardIosIcon />
                    </button>
                )}
            </div>


            {/* Story Viewer Overlay */}
            {
                selectedStory && (
                    <div className="story-viewer" onClick={handleCloseStory}>
                        <button className='back-button' onClick={handleCloseStory}>
                            <ArrowBackIcon style={{ fontSize: '32px', color: 'white' }} />
                        </button>

                        <div
                            className="story-content"
                            onClick={e => e.stopPropagation()} // Ngăn click xuyên qua
                        >
                            <img src={selectedStory.img} alt={selectedStory.name} />
                            <span>{selectedStory.name} <MoreHorizIcon style={{ fontSize: '32px' }} /></span>
                            <button
                                className="close-button"
                                onClick={handleCloseStory}
                            >
                                <CloseIcon />
                            </button>
                        </div>
                    </div>
                )
            }

        </>
    )
}

export default Stories