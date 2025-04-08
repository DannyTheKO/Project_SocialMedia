import React from 'react'
import { Link } from 'react-router-dom'

const ForgotPassword = () => {
    return (
        <div className='h-screen bg-black flex items-center justify-center'>
            <div className="w-[50%] min-h-[400px] flex flex-col items-center pt-[20px] bg-white rounded-[10px] overflow-hidden">
                <h1 className='text-[45px] leading-[60px] font-bold text-blue-500 mb-[40px]'>Quên mật khẩu</h1>
                <div className='w-[80%]'>
                    <form className='flex flex-col gap-[30px]'>
                        <input
                            type="text"
                            placeholder='Nhập địa chỉ email tài khoản'
                            className='border-2 border-gray-300 rounded-xl px-[10px] py-[20px] text-[20px]'
                        />
                        <button className='w-full py-[12px] text-[20px] bg-blue-500 hover:bg-[lightgray] hover:text-[#222] text-white font-bold rounded-xl cursor-pointer'>
                            Xác nhận
                        </button>
                        <Link to="/login">
                            <span className='hover:text-blue-500 cursor-pointer text-gray-500 text-[18px]'>Quay lại đăng nhập</span>
                        </Link>
                    </form>
                </div>

            </div>
        </div>
    )
}

export default ForgotPassword