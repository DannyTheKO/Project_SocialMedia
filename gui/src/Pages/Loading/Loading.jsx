import React from 'react'
import FacebookIcon from '@mui/icons-material/Facebook';
import './Loading.css'



const Loading = () => {
    return (
        <div className='m-0 p-0 box-border text-[24px] loading'>
            <div className="container">
                <p>Welcome user !</p>
            </div>

            <div className="loading-page">
                <FacebookIcon />

                <div className="name-container">
                    <div className="logo-name">
                        nullx
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Loading