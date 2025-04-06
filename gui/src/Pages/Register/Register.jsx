import React from 'react'
import { Link } from 'react-router-dom'
import RegisterImage from '../../assets/register-image.jpg'

const Register = () => {
    return (
        <div className='h-screen bg-[rgb(193,190,255)] flex items-center justify-center'>
            <div className="w-[60%] min-h-[600px] flex flex-row-reverse bg-white rounded-[10px] overflow-hidden">

                <div
                    className="flex-1 p-[50px] flex flex-col gap-[65px] text-white"
                    style={{
                        backgroundImage: `linear-gradient(rgba(39,11,96,0.4), rgba(39,11,96,0.4)), url(${RegisterImage})`,
                        backgroundSize: 'cover',
                        backgroundPosition: 'center',
                    }}
                >
                    <h1 className='text-[65px] leading-[60px] font-bold'>Social Media</h1>
                    <p className='text-[20px]'>
                        Lorem ipsum dolor, sit amet consectetur adipisicing elit.
                        Nobis repellendus, modi, est sit, autem officiis obcaecati
                        asperiores dolorem minus ut veritatis?
                    </p>
                    <span className='text-[20px]'>Do you have an account ?</span>
                    <Link to="/login">
                        <button className='w-1/2 py-[8px] text-[18px] bg-white text-purple-700 font-bold cursor-pointer'>
                            Login
                        </button>
                    </Link>
                </div>

                <div className="flex-1 p-[50px] flex flex-col gap-[35px]">
                    <h1 className='font-bold text-[30px] text-[#555] leading-[20px]'>Register</h1>
                    <form className='flex flex-col gap-[18px]'>
                        <input
                            type="text"
                            placeholder='Username'
                            className='border-b border-gray-300 px-[10px] py-[20px] outline-none text-[18px]'
                        />
                        <input
                            type="email"
                            placeholder='Email'
                            className='border-b border-gray-300 px-[10px] py-[20px] outline-none text-[18px]'
                        />
                        <input
                            type="password"
                            placeholder='Password'
                            className='border-b border-gray-300 px-[10px] py-[20px] outline-none text-[18px]'
                        />
                        <input
                            type="text"
                            placeholder='Name'
                            className='border-b border-gray-300 px-[10px] py-[20px] outline-none text-[18px]'
                        />
                        <button className='w-1/2 py-[10px] mt-[20px] text-[18px] bg-[#a532f3] text-white font-bold cursor-pointer'>
                            Register
                        </button>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Register
