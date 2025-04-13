import {createContext, useEffect, useState} from "react";

export const ThemeContext = createContext();

export const ThemeProvider = ({children}) => {
    const [darkMode, setDarkMode] = useState(false)
    // Mặc định là light mode


    // Dùng localStorage để giữ theme của người dùng thay vì mặc định luôn là Light Mode
    // Tham khảo docs của Tailwind CSS
    useEffect(() => {
        const savedMode = localStorage.getItem('theme')
        if (savedMode === 'dark') {
            // Nghĩa là người dùng click sang Dark Mode
            document.documentElement.classList.add('dark')
            setDarkMode(true)
        } else {
            document.documentElement.classList.remove('dark')
            setDarkMode(false)
        }
    }, [])

    // Hàm toggle theme
    const toggleDarkMode = () => {
        const newMode = !darkMode;
        setDarkMode(newMode);
        if (newMode) {
            document.documentElement.classList.add("dark");
            localStorage.setItem("theme", "dark");
        } else {
            document.documentElement.classList.remove("dark");
            localStorage.setItem("theme", "light");
        }
    }

    return (
        // Bọc các Component con (children) và truyền giá trị cho context
        <ThemeContext.Provider value={{darkMode, toggleDarkMode}}>
            {children}
        </ThemeContext.Provider>
    )

}