import React, { useState, useEffect } from "react";
import "../comp_css/Slider.css"; //
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/bundle";
import { Pagination, Navigation, Autoplay } from "swiper/modules";

const Slider = ({ images, swiperConfig }) => {
  const configModules = [];
  if (swiperConfig?.pagination) {
    configModules.push(Pagination);
  }
  if (swiperConfig?.navigation) {
    configModules.push(Navigation);
  }
  if (swiperConfig?.autoplay) {
    configModules.push(Autoplay);
  }

  return (
    <Swiper
      pagination={{ dynamicBullets: true, dynamicMainBullets: 3, clickable: true }}
      navigation={swiperConfig?.navigation || false}
      loop={swiperConfig?.loop || false}
      autoplay={{ delay: 4000 }}
      speed={1000}
      modules={configModules}
      spaceBetween={swiperConfig?.spaceBetween}
      className={swiperConfig?.className || "slider-container"}
    >
      {images.map((image, index) => (
        <SwiperSlide style={{...swiperConfig?.styles}} className="swiper-slide" key={index}>
          <img src={image} alt={`Slide ${index}`} />
        </SwiperSlide>
      ))}
    </Swiper>
  );

  // return (
  //   <div className="hero-banner">
  //     <img
  //       key={currentIndex}
  //       src={images[currentIndex]}
  //       alt={`Slide ${currentIndex}`}

  //     />
  //   </div>
  // );
};

export default Slider;
