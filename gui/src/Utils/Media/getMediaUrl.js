import DefaultProfilePic from '../../assets/defaultProfilePic.jpg';

export const getMediaUrl = (filePath) => {
    if (!filePath) return DefaultProfilePic;

    const baseUrl = "http://localhost:8080";

    try {
        // bỏ "gui\\src\\Assets\\uploads\\" hoặc "gui/src/Assets/uploads/"
        let relativePath = filePath.replace(/gui[/\\]src[/\\]Assets[/\\]uploads[/\\]/i, "");

        if (!relativePath) {
            console.warn("Không thể parse đường dẫn ảnh:", filePath);
            return DefaultProfilePic;
        }

        const cleanPath = relativePath.replace(/\\/g, "/");
        return `${baseUrl}/uploads/${cleanPath}`;
    } catch (error) {
        console.error("Lỗi khi tạo URL ảnh:", error, "FilePath:", filePath);
        return DefaultProfilePic;
    }
};