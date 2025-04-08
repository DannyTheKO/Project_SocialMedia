import React from 'react'
import { Link } from 'react-router-dom'
import PlaceHolderImage from '../../assets/login-image-2.jpg'

const Login = () => {
    return (
        <div className='h-screen bg-black flex items-center justify-center'>
            <div className="w-[60%] min-h-[600px] flex bg-white rounded-[10px] overflow-hidden">

                <div
                    className="flex-1 p-[50px] flex flex-col gap-[55px] text-white"
                    style={{
                        backgroundImage: `linear-gradient(rgba(22, 131, 213,0.4), rgba(22, 131, 213,0.4)), url(${PlaceHolderImage})`,
                        backgroundSize: 'cover',
                        backgroundPosition: 'center',
                    }}
                >
                    <h1 className='text-[65px] leading-[60px] font-bold'>Social Media</h1>
                    <p className='text-[20px] font-medium'>
                        Chào mừng bạn đến với nền tảng kết nối hiện đại, đăng nhập để tiếp tục khám phá, chia sẻ thông tin và xây dựng mối quan hệ ý nghĩa cùng cộng đồng của chúng tôi.
                    </p>
                    <span className='text-[20px] mt-[30px] font-medium'>Chưa có tài khoản Social Media ?</span>
                    <Link to="/register">
                        <button className='w-1/2 py-[12px] text-[24px] bg-white text-blue-700 font-bold cursor-pointer rounded-xl hover:bg-blue-500 hover:text-white'>
                            Đăng ký
                        </button>
                    </Link>
                </div>

                <div className="flex-1 p-[50px] flex flex-col gap-[40px]">
                    <h1 className='font-bold text-[40px] text-[#555]'>Đăng nhập</h1>
                    <form className='flex flex-col gap-[30px]'>
                        <input
                            type="text"
                            placeholder='Tên đăng nhập'
                            className='border-b border-gray-300 px-[10px] py-[20px] outline-none text-[20px]'
                        />
                        <input
                            type="password"
                            placeholder='Mật khẩu'
                            className='border-b border-gray-300 px-[10px] py-[20px] outline-none text-[20px]'
                        />
                        <div className='login flex flex-col gap-[10px] justify-center items-center'>
                            <button className='w-1/2 py-[12px] text-[20px] bg-blue-500 hover:bg-[lightgray] hover:text-[#222] mt-[20px] text-white font-bold rounded-xl cursor-pointer'>
                                Đăng nhập
                            </button>
                            <Link to="/forgotPassword">
                                <span className='text-blue-500 cursor-pointer hover:text-gray-500 text-[16px]'>Quên mật khẩu ?</span>
                            </Link>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    )
}

export default Login
