import React, {useState} from 'react'
import DefaultProfilePic from '../../assets/defaultProfilePic.jpg'
import './CommentCpn.css'
import moment from 'moment'
import 'moment/locale/vi';

moment.locale('vi');

const CommentCpn = ({comment}) => {
    // State slider nếu comment có nhiều ảnh
    const [currentIndex, setCurrentIndex] = useState(0);

    // Hàm kiểm tra file ảnh hay là video
    const isVideo = (filePath) => {
        return filePath && filePath.toLowerCase().endsWith('.mp4');
    };

    const isAudio = (filePath) => {
        return filePath && filePath.toLowerCase().endsWith('.mp3');
    };

    // Hàm get image từ url public
    const getImageUrl = (filePath) => {
        console.log("a")
        if (!filePath) return DefaultProfilePic;

        const baseUrl = "http://localhost:8080";

        try {
            // Thử split với "uploads\", nếu không được thì thử với "uploads\\"
            let relativePath = filePath.split("uploads\\")[1] || filePath.split("uploads\\\\")[1];

            // Nếu không split được, trả về ảnh mặc định
            if (!relativePath) {
                console.warn("Không thể parse đường dẫn ảnh:", filePath);
                return DefaultProfilePic;
            }

            // Thay tất cả dấu \ thành / để tạo URL hợp lệ
            const cleanPath = relativePath.replace(/\\/g, "/");

            // Tạo URL public
            const fullUrl = `${baseUrl}/uploads/${cleanPath}`;
            console.log("Generated Image URL:", fullUrl);
            return fullUrl;
        } catch (error) {
            console.error("Lỗi khi tạo URL ảnh:", error, "FilePath:", filePath);
            return DefaultProfilePic;
        }
    };

    // Handler của nút prevSlider
    const handlePrev = () => {
        setCurrentIndex((prevIndex) => (prevIndex === 0 ? comment.media.length - 1 : prevIndex - 1));
    };

    // Handler của nút nextSlider
    const handleNext = () => {
        setCurrentIndex((prevIndex) => (prevIndex === comment.media.length - 1 ? 0 : prevIndex + 1));
    };

    return (
        <div className="comment" key={comment.commentId}>
            <div className="row-1">
                <img src={DefaultProfilePic} className="avatar" alt=""/>
                <div className="content">
                    <div className="info">
                        <div className='left'>
                            <span>{comment.username}</span>
                            <p>{comment.content}</p>
                        </div>
                        <div className='right'>
                            <span className="date">{moment(comment.createdAt).fromNow()}</span>
                        </div>
                    </div>


                    {comment.media && comment.media.length > 0 && (
                        <div className="row-2 w-full">
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
                                            className="w-full rounded-md"
                                        />
                                    ) : (
                                        <img
                                            src={getImageUrl(comment.media[0].filePath)}
                                            alt="Post media"
                                            className="w-full rounded-md"
                                        />
                                    )
                            ) : (
                                // Nhiều media – slider
                                <div className="relative w-full max-h-[500px] overflow-hidden">
                                    <div
                                        className="flex w-full transition-transform duration-500 ease-in-out"
                                        style={{
                                            transform: `translateX(-${currentIndex * 100}%)`,
                                        }}
                                    >
                                        {comment.media.map((item, index) => (
                                            <div key={index} className="w-full flex-shrink-0">
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
                                        className="absolute top-14 right-1 transform -translate-y-1/2 bg-gray-800 text-white px-5 py-3 rounded-full">
                                        {currentIndex + 1} / {comment.media.length}
                                    </div>
                                </div>
                            )}
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}

export default CommentCpn