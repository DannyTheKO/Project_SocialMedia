import React from 'react'
import './Login.scss'
import { Link } from 'react-router'

const Login = () => {
    return (
        <div className='login'>
            <div className="card">

                <div className="left font-medium">
                    <h1>Hello World.</h1>
                    <p>
                        Lorem ipsum dolor, sit amet consectetur adipisicing elit.
                        Nobis repellendus, modi, est sit, autem officiis obcaecati
                        asperiores dolorem minus ut veritatis?
                    </p>
                    <span>Don't you have an account ?</span>
                    <Link to="/register">
                        <button>Register</button>
                    </Link>
                </div>

                <div className="right">
                    <h1>Login</h1>
                    <form action="">
                        <input type="text" placeholder='Username' />
                        <input type="password" placeholder='Password' />
                        <button>Login</button>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Login