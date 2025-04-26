import DefaultProfilePic from '../../assets/defaultProfilePic.jpg';

export const getMediaUrl = (filePath) => {
    // If the file path is empty, switch to default profile picture
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
        // Debug Print
        // console.log(`${baseUrl}/uploads/${cleanPath}`)

        return `${baseUrl}/uploads/${cleanPath}`;
    } catch (error) {
        console.error("Lỗi khi tạo URL ảnh:", error, "FilePath:", filePath);
        return DefaultProfilePic;
    }
};