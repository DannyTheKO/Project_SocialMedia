import React from 'react'
import './Register.scss'
import { Link } from 'react-router'

const Register = () => {
    return (
        <div className='register'>
            <div className="card">
                <div className="left font-medium">
                    <h1>Ttuan Social.</h1>
                    <p>
                        Lorem ipsum dolor, sit amet consectetur adipisicing elit.
                        Nobis repellendus, modi, est sit, autem officiis obcaecati
                        asperiores dolorem minus ut veritatis?
                    </p>
                    <span>Do you have an account ?</span>
                    <Link to="/login">
                        <button>Login</button>
                    </Link>
                </div>

                <div className="right">
                    <h1>Register</h1>
                    <form action="">
                        <input type="text" placeholder='Username' />
                        <input type="email" placeholder='Email' />
                        <input type="password" placeholder='Password' />
                        <input type="text" placeholder='Name' />
                        <button>Register</button>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Register