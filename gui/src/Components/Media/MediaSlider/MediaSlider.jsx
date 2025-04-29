import {isAudio, isVideo} from "../../../Utils/Media/checkFileType.js";
import {getMediaUrl} from "../../../Utils/Media/getMediaUrl.js";
import React, {useState} from "react";
import "./MediaSlider.css"

const MediaSlider = ({mediaFileObjects}) => {
    // State slider if comments has multiple media files
    const [currentIndex, setCurrentIndex] = useState(0);

    // Handler của nút prevSlider
    const handlePrev = () => {
        setCurrentIndex((prevIndex) => (prevIndex === 0 ? mediaFileObjects.media.length - 1 : prevIndex - 1));
    };

    // Handler của nút nextSlider
    const handleNext = () => {
        setCurrentIndex((prevIndex) => (prevIndex === mediaFileObjects.media.length - 1 ? 0 : prevIndex + 1));
    };

    return (
        <>
            {mediaFileObjects.media && mediaFileObjects.media.length > 0 && (
                // Media Slider Container
                <div className="row-1">
                    <div className="mediaSlider-container">
                        {mediaFileObjects.media.length === 1 ? (
                            <div className="mediaSlider-single">
                                {/* Single media files */}
                                {isAudio(mediaFileObjects.media[0].filePath) ? (
                                    // If it's audio
                                    <audio
                                        src={getMediaUrl(mediaFileObjects.media[0].filePath)}
                                        controls
                                        className="mediaSlider-audioFile"
                                    />
                                ) : isVideo(mediaFileObjects.media[0].filePath) ? (
                                    // If it's video
                                    <video
                                        src={getMediaUrl(mediaFileObjects.media[0].filePath)}
                                        controls
                                        className="mediaSlider-videoFile"
                                    />
                                ) : (
                                    // Otherwise it's an image
                                    <img
                                        src={getMediaUrl(mediaFileObjects.media[0].filePath)}
                                        alt=""
                                        className="mediaSlider-imageFile"
                                    />
                                )}
                            </div>
                        ) : (
                            // Multiple media files
                            <div className="mediaSlider-multiple">
                                <div
                                    className="mediaSlider-control"
                                    style={{transform: `translateX(-${currentIndex * 100}%)`}}
                                >
                                    {mediaFileObjects.media.map((item, index) => (
                                        // TODO: Audio ?
                                        <div key={index} className="w-full flex flex-shrink-0">
                                            {isVideo(item.filePath) ? (
                                                <video // If Video
                                                    src={getMediaUrl(item.filePath)}
                                                    controls
                                                    className="w-full object-cover object-center max-h-[500px] rounded-md"
                                                />
                                            ) : isAudio(item.filePath) ? (
                                                <audio // If Audio
                                                    src={getMediaUrl(item.filePath)}
                                                    controls
                                                    className="w-full"
                                                />
                                            ) : (
                                                <img // Else Image
                                                    src={getMediaUrl(item.filePath)}
                                                    alt={`Post media ${index + 1}`}
                                                    className="w-full object-cover object-center max-h-[500px] rounded-md"
                                                />
                                            )}
                                        </div>
                                    ))}
                                </div>

                                {/*Button Group*/}
                                <button className="mediaSlider-btnPrev" onClick={handlePrev}> ← </button>
                                <button className="mediaSlider-btnNext" onClick={handleNext}> → </button>
                                <div className="mediaSlider-length"> {currentIndex + 1} / {mediaFileObjects.media.length} </div>
                            </div>
                        )}
                    </div>
                </div>
            )}
        </>
    )
}

export default MediaSlider;