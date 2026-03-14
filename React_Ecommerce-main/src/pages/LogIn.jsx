import React, { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import "../comp_css/Login.css";
import loginbg from "../picture/loginbg1.webp";
 
const Login = () => {
  const navigate = useNavigate();
 
  // Default form values
  const [form, setForm] = useState({
    username: "",
    password: "",
  });
 
  // Change page title when component mounts
  useEffect(() => {
    document.title = "Ecommerce | Login";
    return () => {
      document.title = "Ecommerce App";
    };
  }, []);
 
  // Handle input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };
 
  // Handle form submit
  const handleSubmit = async (e) => {
    e.preventDefault();
 
    try {
      const response = await axios.post(
        "http://localhost:8080/auth/login",
        {
          username: form.username,
          password: form.password,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
 
      const token = response.data.token;
      console.log("See User ID "+response.data.userid);
      
      if (token) {
        // Save token & user info to localStorage
        localStorage.setItem("jwtToken", token);
        localStorage.setItem("username", response.data.username);
        localStorage.setItem("roles", JSON.stringify(response.data.roles));
        localStorage.setItem("userId", response.data.userId);
        localStorage.setItem("cartId", response.data.cartId);
 
        alert("Login successful!");
        navigate("/"); // redirect to home
      } else {
        alert("Invalid credentials, please try again.");
      }
    } catch (error) {
      if (error.response && error.response.status === 401) {
        alert("Invalid username or password.");
      } else {
        alert("Error during login. Please try again later.");
        console.error("Login Error:", error);
      }
    }
  };
 
  // Inline background style
  const bgStyle = {
    backgroundImage: `url(${loginbg})`,
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundPosition: "center center",
    height: "100vh",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  };
 
  return (
    <div style={bgStyle}>
      <div className="loginContainer">
        <div className="login-form">
          <h2 style={{ textAlign: "center", color: "white", marginBottom: "20px" }}>
            Welcome to User Login
          </h2>
 
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="username">Username:</label>
              <input
                id="username"
                type="text"
                name="username"
                value={form.username}
                onChange={handleChange}
                placeholder="Enter your username"
                required
              />
            </div>
 
            <div className="form-group">
              <label htmlFor="password">Password:</label>
              <input
                id="password"
                type="password"
                name="password"
                value={form.password}
                onChange={handleChange}
                placeholder="Enter your password"
                required
              />
            </div>
 
            <div className="form-group">
              <input type="submit" value="Login" className="login-btn" />
            </div>
 
            <p style={{ color: "white", textAlign: "center" }}>
              Don't have an account?{" "}
              <Link to="/register-user" style={{ color: "#00ffff" }}>
                Register here
              </Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
};
 
export default Login;