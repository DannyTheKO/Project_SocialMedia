import React, {useState} from 'react'
import './Stories.css'
import AddCircleOutlinedIcon from '@mui/icons-material/AddCircleOutlined';
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
            name: "John Doe",
            img: "https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load",
        },
        {
            id: 2,
            name: "John Doe",
            img: "https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load",
        },
        {
            id: 3,
            name: "John Doe",
            img: "https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load",
        },
        {
            id: 4,
            name: "John Doe",
            img: "https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load",
        }
    ];


    return (
        <>
            <div className='stories'>
                {/* Nút tạo story của người dùng */}
                <div className="story">
                    <img
                        src='https://images.pexels.com/photos/13916254/pexels-photo-13916254.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load'
                        alt=""/>
                    <span>User</span>
                    <button><AddCircleOutlinedIcon style={{fontSize: "40px"}}/></button>
                </div>

                {/* Story fetch được */}
                {stories.map((story) => (
                    <div className="story" key={story.id} onClick={() => handleStoryClick(story)}>
                        <img src={story.img} alt=""/>
                        <span>{story.name}</span>
                    </div>
                ))}
            </div>


            {/* Story Viewer Overlay */}
            {
                selectedStory && (
                    <div className="story-viewer" onClick={handleCloseStory}>
                        <button className='back-button' onClick={handleCloseStory}>
                            <ArrowBackIcon style={{fontSize: '32px', color: 'white'}}/>
                        </button>

                        <div
                            className="story-content"
                            onClick={e => e.stopPropagation()} // Ngăn click xuyên qua
                        >
                            <img src={selectedStory.img} alt={selectedStory.name}/>
                            <span>{selectedStory.name} <MoreHorizIcon style={{fontSize: '32px'}}/></span>
                            <button
                                className="close-button"
                                onClick={handleCloseStory}
                            >
                                <CloseIcon/>
                            </button>
                        </div>
                    </div>
                )
            }

        </>
    )
}

export default Stories