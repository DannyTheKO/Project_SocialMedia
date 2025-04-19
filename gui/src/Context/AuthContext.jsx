// Context Login/Register User

import { createContext, useState } from "react";
import { useNavigate } from "react-router";
import { useEffect } from "react";
import { authApi } from "../Services/Auth/authApi";

export const AuthContext = createContext();

export const AuthContextProvider = ({ children }) => {
    const [currentUser, setCurrentUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    // Call whoAmI when loading
    useEffect(() => {
        authApi.whoAmI()
            .then((response) => {
                setCurrentUser(response.data); // response.data = UserDTO
                setLoading(false);
            })
            .catch(() => {
                setCurrentUser(null);
                setLoading(false);
            });
    }, []);


    // login function
    const login = async (username, password) => {
        try {
            await authApi.login(username, password); // send HttpOnly cookie
            const response = await authApi.whoAmI();
            setCurrentUser(response.data);
            navigate("/");
        } catch (error) {
            throw new Error(error.response?.data?.message || "Login failed");
        }

    }

    // register function
    const register = async (username, firstname, lastname, email, password, birthday) => {
        try {
            await authApi.register(username, firstname, lastname, email, password, birthday);
            const response = await authApi.whoAmI();
            setCurrentUser(response.data);
            navigate("/");
        } catch (error) {
            throw new Error(error.response?.data?.message || "Registration failed");
        }
    };

    // Hàm logout function
    const logout = async () => {
        try {
            await authApi.logout(); // TODO: build logout controller
            setCurrentUser(null);
            navigate("/login");
        } catch (error) {
            console.error("Logout failed:", error);
        }
    };

    return (
        <AuthContext.Provider value={{ currentUser, login, register, logout, loading }}>
            {children}
        </AuthContext.Provider>
    );
};