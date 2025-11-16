import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../Router/api";
import "../comp_css/Cart.css";
 
const Cart = () => {
  const navigate = useNavigate();
  const [cartData, setCartData] = useState();
  const [totalAmount, setTotalAmount] = useState(0);
 
  const cartId = localStorage.getItem("cartid");
  const userId = localStorage.getItem("userid");
  
  console.log("See cartid is Here: "+cartId);
  // ✅ Fetch Cart Data
  const fetchCartData = () => {
    api
      .get(`/ecom/cart/products/1`)
      .then((response) => {
        setCartData(response.data);
        setTotalAmount(response.data.totalAmount);
      })
      .catch((error) => {
        console.error("Error fetching data from the API:", error);
        alert("Your cart is empty!");
      });
  };


 
  // ✅ Place Order
  const apiCallOrderPlaced = () => {
    api
      .post(`/ecom/orders/placed/${userId}`)
      .then(() => {
        alert("Order Placed Successfully!");
        navigate("/user/order-details");
      })
      .catch((error) => {
        console.error("Error placing order:", error);
      });
  };
 
  const orderPlaced = () => {
    apiCallOrderPlaced();
  };
 
  // ✅ Remove Product
  const removeProductFromCart = (productId) => {
    api
      .delete(`/ecom/cart/remove-product/${cartId}/${productId}`)
      .then(() => {
        alert("Product removed from cart");
        fetchCartData();
      })
      .catch((error) => {
        console.error("Error removing product:", error);
      });
  };
 
  // ✅ Increase Product Quantity
  const increaseCount = (productId) => {
    api
      .put(`/ecom/cart/increase-productQty/${cartId}/${productId}`)
      .then((response) => {
        setTotalAmount(response.data.totalAmount);
        fetchCartData();
      })
      .catch((error) => {
        console.error("Error increasing quantity:", error);
      });
  };
 
  // ✅ Decrease Product Quantity
  const decreaseCount = (productId) => {
    api
      .put(`/ecom/cart/decrease-productQty/${cartId}/${productId}`)
      .then((response) => {
        setTotalAmount(response.data.totalAmount);
        fetchCartData();
      })
      .catch((error) => {
        alert("Product cannot be decreased further!");
        console.error("Error decreasing quantity:", error);
      });
  };
 
  // ✅ Empty Cart
  const emptyCart = (cartId) => {
    api
      .delete(`/ecom/cart/empty/${cartId}`)
      .then(() => {
        alert("Cart emptied successfully!");
        setCartData(null);
        setTotalAmount(0);
      })
      .catch((error) => {
        console.error("Error emptying cart:", error);
      });
  };
 
  useEffect(() => {
    document.title = "Ecommerce | Cart";
    fetchCartData();
  }, []);
 
  return (
    <div className="cart-page">
      {cartData && cartData.cartItems?.length > 0 ? (
        <>
          <h2 style={{ textAlign: "center" }}>Your Shopping Cart</h2>
 
          {cartData.cartItems.map((item) => (
            <div key={item.product.productId} className="cart-product">
              <div className="cart-product-img">
                <img
                  src={item.product.imageUrl}
                  alt={item.product.name}
                  width="150"
                  height="150"
                />
              </div>
 
              <div className="cart-product-info">
                <h2>{item.product.name}</h2>
                <p>Category: {item.product.category}</p>
                <p>Description: {item.product.description}</p>
                <h3>Price: ₹{item.product.price}</h3>
 
                <div className="quantity-controls">
                  <button onClick={() => increaseCount(item.product.productId)}>
                    +
                  </button>
                  <span style={{ fontSize: "20px", margin: "0 10px" }}>
                    {item.quantity}
                  </span>
                  <button onClick={() => decreaseCount(item.product.productId)}>
                    -
                  </button>
                </div>
 
                <div className="cart-actions">
                  <button
                    onClick={() =>
                      removeProductFromCart(item.product.productId)
                    }
                    className="remove-btn"
                  >
                    Remove
                  </button>
                </div>
              </div>
            </div>
          ))}
 
          <div className="cart-details">
            <h2>Total Cart Amount: ₹{totalAmount}</h2>
 
            <div className="cart-buttons">
              <button onClick={orderPlaced}>Place Order</button>
 
              <button
                onClick={() => emptyCart(cartId)}
                style={{ backgroundColor: "red" }}
              >
                Empty Cart
              </button>
 
              <button onClick={() => navigate("/user/order-details")}>
                Order Details
              </button>
            </div>
          </div>
        </>
      ) : (
        <div className="empty-cart-message">
          <h1>
            Your cart is empty. <Link to="/">Shop Now</Link>
          </h1>
        </div>
      )}
    </div>
  );
};
 
export default Cart;