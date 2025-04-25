// Hàm kiểm tra file ảnh hay là video
export const isVideo = (filePath) => {
    return filePath && filePath.toLowerCase().endsWith('.mp4');
};

export const isAudio = (filePath) => {
    return filePath && filePath.toLowerCase().endsWith('.mp3');
};