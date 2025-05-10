# 🌐 ĐỒ ÁN MẠNG XÃ HỘI FULLSTACK

Đây là đồ án môn học được phát triển bởi nhóm sinh viên với mục tiêu xây dựng một nền tảng mạng xã hội cơ bản. Hệ thống cho phép người dùng đăng ký, đăng nhập, đăng bài viết, bình luận, like, kết bạn, nhắn tin thời gian thực và nhận thông báo.

- **Frontend**: React + Vite  
- **Backend**: Java Spring Boot  
- **Cơ sở dữ liệu**: MySQL và MongoDB  
- **Realtime**: WebSocket  
- **Xác thực**: JWT + Refresh Token  

## 🛠 HƯỚNG DẪN CÀI ĐẶT

### 1. Backend
- Mở file `application.properties` và chỉnh sửa thông tin kết nối MySQL và MongoDB.
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/social_media
spring.datasource.username=root
spring.datasource.password=root

# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/social_media

- Chạy lệnh:

./mvnw clean install

Sau đó chạy file Start_Server.java bằng IDE hoặc dòng lệnh.

    mvn spring-boot:run

### 2. Frontend
Mở terminal:

cd gui
npm install     # Cài đặt các thư viện cần thiết
npm run dev     # Chạy server frontend tại http://localhost:3000