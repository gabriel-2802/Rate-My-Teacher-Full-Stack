'use client'
import { Swiper, SwiperSlide } from 'swiper/react'
import { Autoplay, Pagination, Navigation } from 'swiper/modules'
import 'swiper/css'
import 'swiper/css/pagination'
import 'swiper/css/navigation'
import { slidingDoorImages } from '@/data'

export default function HeroSwiper() {
  return (
    <div className="relative bg-gray-100 dark:bg-gray-800">
      <Swiper
        modules={[Autoplay, Pagination, Navigation]}
        autoplay={{ delay: 5000, disableOnInteraction: false }}
        pagination={{ clickable: true }}
        navigation
        loop
        className="w-full h-[70vh] md:h-[90vh]"
      >
        {slidingDoorImages.map((imageSrc, index) => (
          <SwiperSlide key={index}>
            <div
              className="relative h-full flex items-center justify-center bg-cover bg-center"
              style={{ backgroundImage: `url(${imageSrc})` }}
            >
              {/* Subtle gradient overlay */}
              <div className="absolute inset-0 bg-gradient-to-r from-gray-800/40 to-gray-600/40" />
              <div className="relative z-10 max-w-4xl px-4 text-center">
                <h1 className="text-4xl md:text-6xl font-bold text-gray-50 mb-6 tracking-tight">
                  Architectural Aluminum Excellence
                </h1>
                <p className="text-lg md:text-xl text-gray-200 mb-8">
                  Precision-engineered solutions for modern architecture
                </p>
                <div className="flex flex-col sm:flex-row justify-center gap-4">
                  <button className="bg-amber-600 text-white px-8 py-4 rounded-lg font-semibold text-lg hover:bg-amber-700 transition-colors shadow-lg">
                    View Collections
                  </button>
                  <button className="bg-white text-gray-900 px-8 py-4 rounded-lg font-semibold text-lg hover:bg-gray-100 transition-colors shadow-lg">
                    Request Consultation
                  </button>
                </div>
              </div>
            </div>
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  )
}
