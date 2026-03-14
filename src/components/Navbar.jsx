// import React from "react";
// import { useNavigate } from "react-router-dom";
// import "../comp_css/Navbar.css";
// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
// import {
//   faCartShopping,
//   faSearch,
//   faUser,
// } from "@fortawesome/free-solid-svg-icons";

// const Navbar = () => {
//   const iconstyle = {
//     display: "flex",
//     alignItems: "center",
//     justifyContent: "space-between",
//   };
//   const navigate = useNavigate();

//   let userId = localStorage.getItem("userId");
//   let name = localStorage.getItem("name");

//   const handleLoginClick = () => {
//     navigate("/login");
//   };

//   const handleLogoutClick = () => {
//     localStorage.removeItem("userid");
//     localStorage.removeItem("jwtToken");
//     localStorage.removeItem("cartid")
//     localStorage.removeItem("name")

//     alert("Logout Successfully.....");
//     navigate("/");
//   };

//   return (
//     <nav className="navbar">
//       <div className="logo">
//         <h3
//           onClick={() => {
//             navigate("/");
//           }}
//         >
//           E-Commerce
//         </h3>
//       </div>

//       <div className="search-bar">
//         <input
//           type="text"
//           placeholder="Search..."
//           onClick={() => {
//             navigate("/product");
//           }}
//         />
//         <FontAwesomeIcon icon={faSearch} className="search-icon" />
//       </div>

//       <div className="iconbutton">
//         <div
//           style={iconstyle}
//           onClick={() => {
//             navigate("/user/cart");
//           }}
//           className="cart-button"
//         >
//           <FontAwesomeIcon icon={faCartShopping} className="cart-icon" />

//           <p style={{ margin: "4px" }}>Cart</p>
//         </div>
//         {userId ? (
//           <>
//             <div
//               style={iconstyle}
//               className="login-button"
//               onClick={() => {
//                 navigate("/user/order-details");
//               }}
//             >
//               <FontAwesomeIcon icon={faUser} className="cart-icon" />
//               {name}
//             </div>
//             <div onClick={handleLogoutClick}>Logout</div>
//           </>
//         ) : (
//           <>
//             <div
//               style={iconstyle}
//               className="login-button"
//               onClick={handleLoginClick}
//             >
//               <FontAwesomeIcon icon={faUser} className="cart-icon" />
//               Login
//             </div>
//             <div
//               className="iconbutton"
//               onClick={() => {
//                 navigate("/register-user");
//               }}
//             >
//               SignIn
//             </div>
//           </>
//         )}
//       </div>
//     </nav>
//   );
// };

// export default Navbar;


import React from "react";
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

  const userId = localStorage.getItem("userId");
  const name = localStorage.getItem("name");
  const token = localStorage.getItem("jwtToken");

  // ✅ Handle Cart Click with Auth Check
  const handleCartClick = () => {
    console.log("token => ", token);
    
    if (token) {
      navigate("/user/cart");
      
    } else {
      alert("Please log in to access your cart");
      navigate("/login", { state: { from: location.pathname } });
    }
  };

  // ✅ Handle Login Click
  const handleLoginClick = () => {
    navigate("/login", { state: { from: location.pathname } });
  };

  const handleProductClick = () => {
    navigate("/product", { state: { from: location.pathname } });
  }

  // ✅ Handle Logout
  const handleLogoutClick = () => {
    localStorage.removeItem("userid");
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("cartid");
    localStorage.removeItem("name");

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

      <div style={iconStyle} onClick={handleProductClick} className="cart-button">
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

        {/* Conditional Rendering for Login / Logout */}
        {userId ? (
          <>
            {/* User Profile */}
            <div
              style={iconStyle}
              className="login-button"
              onClick={() => navigate("/user/order-details")}
            >
              <FontAwesomeIcon icon={faUser} className="cart-icon" />
              {name}
            </div>
            {/* Logout */}
            <div onClick={handleLogoutClick} className="logout-button">
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