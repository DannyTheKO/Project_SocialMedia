import React, { useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import RegisterImage from '../../Assets/register-image-2.jpg';
import { AuthContext } from '../../Context/AuthContext';

const Register = () => {
    const { register } = useContext(AuthContext);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [birthday, setBirthday] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(''); // Reset error before call API
        try {
            await register(username, firstName, lastName, '', password, birthday); // Call Register function from AuthContext
        } catch (err) {
            const errorMessage = err.response?.data?.message || err.message || 'Đăng ký thất bại';
            setError(errorMessage);
        }
    };

    return (
        <div className='h-screen bg-black flex items-center justify-center'>
            <div className="w-[60%] min-h-[600px] flex bg-white rounded-[10px] overflow-hidden flex-row-reverse">
                <div
                    className="flex-1 p-[50px] flex flex-col gap-[55px] text-white"
                    style={{
                        backgroundImage: `linear-gradient(rgba(22, 131, 213,0.4), rgba(22, 131, 213,0.4)), url(${RegisterImage})`,
                        backgroundSize: 'cover',
                        backgroundPosition: 'center',
                    }}
                >
                    <h1 className='text-[65px] leading-[60px] font-bold'>Social Media</h1>
                    <p className='text-[20px] font-medium'>
                        Tạo tài khoản để bắt đầu hành trình kết nối, chia sẻ và khám phá nội dung chất lượng trong cộng
                        đồng hiện đại, năng động và luôn sẵn sàng đồng hành cùng bạn.
                    </p>
                    <span className='text-[20px] mt-[30px] font-medium'>Đã có tài khoản ?</span>
                    <Link to="/login">
                        <button
                            className='w-1/2 py-[12px] text-[24px] bg-white text-blue-700 font-bold cursor-pointer rounded-xl hover:bg-blue-500 hover:text-white'>
                            Đăng nhập
                        </button>
                    </Link>
                </div>

                <div className="flex-1 p-[50px] flex flex-col gap-[40px]">
                    <h1 className='font-bold text-[40px] text-[#555]'>Đăng ký</h1>
                    <form className='flex flex-col gap-[30px]' onSubmit={handleSubmit}>
                        <div className='private-info flex gap-[50px] justify-between px-[10px]'>
                            <input
                                type="text"
                                placeholder='Họ'
                                className='border-b border-gray-300 py-[20px] outline-none text-[20px]'
                                value={firstName}
                                onChange={(e) => setFirstName(e.target.value)}
                                required
                            />
                            <input
                                type="text"
                                placeholder='Tên'
                                className='border-b border-gray-300 py-[20px] outline-none text-[20px]'
                                value={lastName}
                                onChange={(e) => setLastName(e.target.value)}
                                required
                            />
                        </div>
                        <div className='private-info items-center flex justify-between px-[10px]'>
                            <label className='flex-[1] text-[20px]'>Ngày sinh: </label>
                            <input
                                type="date"
                                className='flex-[3] border-b border-gray-300 py-[20px] outline-none text-[20px]'
                                value={birthday}
                                onChange={(e) => setBirthday(e.target.value)}
                                required
                            />
                        </div>
                        <input
                            type="text"
                            placeholder='Username'
                            className='border-b border-gray-300 px-[10px] py-[20px] outline-none text-[20px]'
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                        <input
                            type="password"
                            placeholder='Password'
                            className='border-b border-gray-300 px-[10px] py-[20px] outline-none text-[20px]'
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                        {error && <p className='text-red-500 text-[16px] text-center'>{error}</p>}
                        <button
                            type="submit"
                            className='w-1/2 py-[12px] text-[20px] bg-blue-500 hover:bg-[lightgray] hover:text-[#222] mt-[20px] text-white font-bold rounded-xl cursor-pointer'>
                            Đăng ký
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default Register;