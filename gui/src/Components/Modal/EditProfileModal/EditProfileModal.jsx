// EditProfileModal.js
import React, { useState, useEffect } from 'react';
import './EditProfileModal.css';
import { userApi } from '../../../Services/UserService/userService';
import { toast } from 'react-toastify';

const EditProfileModal = ({ isOpen, onClose, user, onProfileUpdated }) => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: user?.lastName || '',
        bio: user?.bio || '',
        birthDate: user?.birthDate ? user.birthDate.split('T')[0] : '',
        bannerImage: null,
        email: user?.email || ''
    });

    useEffect(() => {
        if (user) {
            setFormData({
                firstName: '',
                lastName: user.lastName || '',
                bio: user.bio || '',
                birthDate: user.birthDate ? user.birthDate.split('T')[0] : '',
                profileImage: null,
                bannerImage: null,
                email: user.email
            });
        }
    }, [user]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleFileChange = (e) => {
        const { name, files } = e.target;
        setFormData({ ...formData, [name]: files[0] });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const updatedData = new FormData();
            updatedData.append('firstName', formData?.firstName || null);
            updatedData.append('lastName', formData?.lastName || null);
            updatedData.append('bio', formData?.bio || null);
            updatedData.append('email', formData?.email || null);
            if (formData.profileImage) {
                updatedData.append('profileImage', formData.profileImage || null);
            }
            if (formData.bannerImage) {
                updatedData.append('bannerImage', formData.bannerImage || null);
            }
            updatedData.append('birthDate', formData?.birthDate || null);


            const response = await userApi.updateUser(updatedData);
            if (response.message === 'Success') {
                toast.success('Cập nhật thông tin cá nhân thành công!');
                onProfileUpdated(response.data);
                onClose();
            } else {
                toast.error('Cập nhật thông tin thất bại!');
            }
        } catch (error) {
            console.error('Error updating profile:', error);
            toast.error('Đã có lỗi xảy ra khi cập nhật thông tin!');
        }
    };

    if (!isOpen) return null;

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h2>Chỉnh sửa thông tin cá nhân</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Họ:</label>
                        <input
                            type="text"
                            name="firstName"
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Tên:</label>
                        <input
                            type="text"
                            name="lastName"
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Email:</label>
                        <input
                            type="text"
                            name="email"
                            value={formData.email}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Tiểu sử:</label>
                        <textarea
                            name="bio"
                            value={formData.bio}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Ngày sinh:</label>
                        <input
                            type="date"
                            name="birthDate"
                            value={formData.birthDate}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Ảnh đại diện:</label>
                        <input
                            type="file"
                            name="profileImage"
                            onChange={handleFileChange}
                            accept="image/*"
                        />
                    </div>
                    <div className="form-group">
                        <label>Ảnh bìa:</label>
                        <input
                            type="file"
                            name="bannerImage"
                            onChange={handleFileChange}
                            accept="image/*"
                        />
                    </div>
                    <div className="modal-actions">
                        <button type="submit">Lưu</button>
                        <button type="button" onClick={onClose}>Hủy</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default EditProfileModal;