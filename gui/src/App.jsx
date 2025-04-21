import { BrowserRouter, Routes, Route, Outlet } from "react-router-dom";
import { AuthContextProvider } from "./Context/AuthContext";
import ProtectedRoute from "./Services/Auth/ProtectedRoute";
import Home from "./Pages/Home/Home";
import Profile from "./Pages/Profile/Profile";
import Friends from "./Pages/Friends/Friends";
import Login from "./Pages/Login/Login";
import Register from "./Pages/Register/Register";
import ForgotPassword from "./Pages/ForgotPassword/ForgotPassword";
import NavBar from "./Components/Navbar/NavBar";
import LeftBar from "./Components/LeftBar/LeftBar";
import RightBar from "./Components/RightBar/RightBar";
import { ToastContainer } from "react-toastify";

const Layout = () => {
    return (
        <div>
            <NavBar />
            <div className="flex">
                <LeftBar />
                <div className="flex-[6]">
                    <Outlet />
                </div>
                <RightBar />
            </div>
        </div>
    );
};

function App() {
    return (
        <BrowserRouter>
            <AuthContextProvider>
                <ToastContainer
                    position="top-right"
                    autoClose={3000}
                    hideProgressBar={false}
                    newestOnTop={true}
                    closeOnClick
                    pauseOnHover
                    draggable
                    theme="dark"
                    toastClassName="custom-toast"
                    bodyClassName="custom-toast-body"
                />
                <Routes>
                    <Route
                        path="/"
                        element={
                            <ProtectedRoute>
                                <Layout />
                            </ProtectedRoute>
                        }
                    >
                        <Route index element={<Home />} />
                        <Route path="/profile/:id" element={<Profile />} />
                        <Route path="/friends" element={<Friends />} />
                    </Route>
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/forgotPassword" element={<ForgotPassword />} />
                </Routes>
            </AuthContextProvider>
        </BrowserRouter>
    );
}

export default App;