// Context Login/Register User

import {createContext, useState} from "react";

export const AuthContext = createContext();

export const AuthContextProvider = ({children}) => {
    const [currentUser, setCurrentUser] = useState(true);

    const login = () => {
        //TODO: Create Login function here
    };


    // TODO: Get JWT if still validate

    return (
        <AuthContext.Provider value={{currentUser, login}}>
            {children}
        </AuthContext.Provider>
    );
};