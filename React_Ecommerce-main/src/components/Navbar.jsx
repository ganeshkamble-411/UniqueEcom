import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "../comp_css/Navbar.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCartShopping,
  faSearch,
  faUser,
} from "@fortawesome/free-solid-svg-icons";

const Navbar = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const iconStyle = {
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
    cursor: "pointer",
  };

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [name, setName] = useState("");

  // ✅ Sync state with localStorage
  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    const storedName = localStorage.getItem("name");

    setIsLoggedIn(!!token);
    setName(storedName || "");
  }, []);

  // ✅ Handle Cart Click
  const handleCartClick = () => {
    const token = localStorage.getItem("jwtToken");

    if (token) {
      navigate("/user/cart");
    } else {
      alert("Please log in to access your cart");
      navigate("/login", { state: { from: location.pathname } });
    }
  };

  // ✅ Handle Login
  const handleLoginClick = () => {
    navigate("/login", { state: { from: location.pathname } });
  };

  const handleProductClick = () => {
    navigate("/product");
  };

  // ✅ Handle Logout (FIXED)
  const handleLogoutClick = () => {
    localStorage.removeItem("userId");   // fixed key
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("cartid");
    localStorage.removeItem("name");

    setIsLoggedIn(false);   // 👈 IMPORTANT
    setName("");

    alert("Logout Successfully");
    navigate("/");
  };

  return (
    <nav className="navbar">
      {/* Logo */}
      <div className="logo" onClick={() => navigate("/")}>
        <h3>E-Commerce</h3>
      </div>

      {/* Search Bar */}
      <div className="search-bar">
        <input
          type="text"
          placeholder="Search..."
          onClick={() => navigate("/product")}
        />
        <FontAwesomeIcon icon={faSearch} className="search-icon" />
      </div>

      {/* Products */}
      <div
        style={iconStyle}
        onClick={handleProductClick}
        className="cart-button"
      >
        <FontAwesomeIcon icon={faCartShopping} className="cart-icon" />
        <p style={{ margin: "4px" }}>Products</p>
      </div>

      {/* Right Icons */}
      <div className="iconbutton">
        {/* Cart */}
        <div
          style={iconStyle}
          onClick={handleCartClick}
          className="cart-button"
        >
          <FontAwesomeIcon icon={faCartShopping} className="cart-icon" />
          <p style={{ margin: "4px" }}>Cart</p>
        </div>

        {/* ✅ Clean Conditional Rendering */}
        {isLoggedIn ? (
          <>
            {/* Profile */}
            <div
              style={iconStyle}
              className="login-button"
              onClick={() => navigate("/user/order-details")}
            >
              <FontAwesomeIcon icon={faUser} className="cart-icon" />
              {name}
            </div>

            {/* Logout */}
            <div
              style={iconStyle}
              onClick={handleLogoutClick}
              className="logout-button"
            >
              Logout
            </div>
          </>
        ) : (
          <>
            {/* Login */}
            <div
              style={iconStyle}
              className="login-button"
              onClick={handleLoginClick}
            >
              <FontAwesomeIcon icon={faUser} className="cart-icon" />
              Login
            </div>

            {/* SignUp */}
            <div
              className="signin-button"
              onClick={() => navigate("/register-user")}
            >
              SignIn
            </div>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;