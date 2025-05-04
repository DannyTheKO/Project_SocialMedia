import React from "react";
import PlaceHolderImage from "../../../Assets/login-image.jpg";
import "./RecentActivities.css"

const RecentActivities = () => {

    return (
        <div className="recentActivities-container">
            <span className="recentActivities-display-title">Hoạt động gần đây</span>
            <div className="recentActivities-display-user">
                <div className="recentActivities-display-userInfo">
                    <img src={PlaceHolderImage} alt="" className='avatar' />
                    <p>
                        <span className="recentActivities-display-username">Tuan Thai</span>
                        <span className="recentActivities-display-content">changed their cover picture</span>
                    </p>
                </div>
                <span className='time-color'> 1 min ago </span>
            </div>
            <div className='recentActivities-display-user'>
                <div className="recentActivities-display-userInfo">
                    <img src={PlaceHolderImage} alt="" className='avatar' />
                    <p>
                        <span className="recentActivities-display-username">Tuan Thai</span>
                        <span className="recentActivities-display-content">changed their cover picture</span>
                    </p>
                </div>
                <span className='time-color'> 1 min ago </span>
            </div>
            <div className='recentActivities-display-user'>
                <div className="recentActivities-display-userInfo">
                    <img src={PlaceHolderImage} alt="" className='avatar' />
                    <p>
                        <span className="recentActivities-display-username">Tuan Thai</span>
                        <span className="recentActivities-display-content">changed their cover picture</span>
                    </p>
                </div>
                <span className='time-color'> 1 min ago </span>
            </div>
        </div>
    )
}

export default RecentActivities