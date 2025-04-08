import { createBrowserRouter, Navigate, Outlet, RouterProvider } from 'react-router-dom'
import './App.css'
import Login from './Pages/Login/Login'
import Register from './Pages/Register/Register'
import NavBar from './Components/Navbar/NavBar'
import LeftBar from './Components/LeftBar/LeftBar'
import RightBar from './Components/RightBar/RightBar'
import Home from './Pages/Home/Home'
import Profile from './Pages/Profile/Profile'
import ForgotPassword from './Pages/ForgotPassword/ForgotPassword'

function App() {

  const currentUser = true;

  const ProtectedRoute = ({ children }) => {
    if (!currentUser) {
      return <Navigate to="/login" />
    }

    // else
    return children
  }

  const Layout = () => {
    return (
      <div>
        <NavBar />
        <div className='flex'>
          <LeftBar />
          <div className='flex-[6]'>
            <Outlet />
          </div>
          <RightBar />
        </div>
      </div>
    )
  }

  const router = createBrowserRouter([
    {
      path: "/",
      element: (
        <ProtectedRoute>
          <Layout />
        </ProtectedRoute>
      ),
      children: [
        {
          path: "/",
          element: <Home />
        },
        {
          path: "/profile/:id",
          element: <Profile />
        },
      ]
    },
    {
      path: "/login",
      element: <Login />
    },
    {
      path: "/register",
      element: <Register />
    },
    {
      path: "/forgotPassword",
      element: <ForgotPassword />
    },
  ]);

  return (
    <div>
      <RouterProvider router={router} />
    </div>
  )

}

export default App
