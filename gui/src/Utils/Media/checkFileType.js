// Hàm kiểm tra file ảnh hay là video
export function isVideo(filePath) {
    return filePath.toLowerCase().endsWith('.mp4') ||
        filePath.toLowerCase().endsWith('.webm') ||
        filePath.toLowerCase().endsWith('.mov');
}

export function isAudio (filePath) {
    return filePath && filePath.toLowerCase().endsWith('.mp3');
}