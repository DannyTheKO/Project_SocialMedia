import React from 'react'
import { Link } from 'react-router-dom'
import RegisterImage from '../../assets/register-image-2.jpg'

const Register = () => {
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
                        Tạo tài khoản để bắt đầu hành trình kết nối, chia sẻ và khám phá nội dung chất lượng trong cộng đồng hiện đại, năng động và luôn sẵn sàng đồng hành cùng bạn.
                    </p>
                    <span className='text-[20px] mt-[30px] font-medium'>Đã có tài khoản ?</span>
                    <Link to="/login">
                        <button className='w-1/2 py-[12px] text-[24px] bg-white text-blue-700 font-bold cursor-pointer rounded-xl hover:bg-blue-500 hover:text-white'>
                            Đăng nhập
                        </button>
                    </Link>
                </div>

                <div className="flex-1 p-[50px] flex flex-col gap-[40px]">
                    <h1 className='font-bold text-[40px] text-[#555]'>Đăng ký</h1>
                    <form className='flex flex-col gap-[30px]'>
                        <div className='private-info flex gap-[50px] justify-between px-[10px]'>
                            <input
                                type="Firstname"
                                placeholder='Họ'
                                className='border-b border-gray-300 py-[20px] outline-none text-[20px]'
                            /><input
                                type="Lastname"
                                placeholder='Tên'
                                className='border-b border-gray-300 py-[20px] outline-none text-[20px]'
                            />
                        </div>
                        <div className='private-info items-center flex justify-between px-[10px]'>
                            <label className='flex-[1] text-[20px]'>Ngày sinh: </label>
                            <input
                                type="date"
                                className='flex-[3] border-b border-gray-300 py-[20px] outline-none text-[20px]'
                            />
                        </div>
                        <input
                            type="text"
                            placeholder='Username'
                            className='border-b border-gray-300 px-[10px] py-[20px] outline-none text-[20px]'
                        />
                        <input
                            type="password"
                            placeholder='Password'
                            className='border-b border-gray-300 px-[10px] py-[20px] outline-none text-[20px]'
                        />
                        <button className='w-1/2 py-[12px] text-[20px] bg-blue-500 hover:bg-[lightgray] hover:text-[#222] mt-[20px] text-white font-bold rounded-xl cursor-pointer'>
                            Đăng ký
                        </button>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Register
