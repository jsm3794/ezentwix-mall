let slideIndex = 0;
let autoSlide;

// 슬라이드를 표시하는 공통 함수
function displaySlide(index) {
  const slides = document.getElementsByClassName("slide");
  const dots = document.getElementsByClassName("dot");
  
  // 인덱스가 슬라이드 수를 초과하거나 미만일 경우 순환
  if (index >= slides.length) { 
    slideIndex = 0; 
  }
  if (index < 0) { 
    slideIndex = slides.length - 1; 
  }
  
  // 모든 슬라이드와 도트를 숨기고 활성화 클래스 제거
  for (let i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
    dots[i].classList.remove("active");
  }
  
  // 현재 슬라이드와 도트 표시 및 활성화
  slides[slideIndex].style.display = "block";
  dots[slideIndex].classList.add("active");
}

// 자동 전환을 담당하는 함수
function showSlides() {
  slideIndex++;
  displaySlide(slideIndex);
  // 다음 자동 전환을 설정
  autoSlide = setTimeout(showSlides, 4000);
}

// 슬라이드 이동 버튼을 클릭했을 때 호출되는 함수
function changeSlide(n) {
  slideIndex += n;
  displaySlide(slideIndex);
  resetAutoSlide();
}

// 특정 슬라이드로 이동할 때 호출되는 함수
function currentSlide(n) {
  slideIndex = n - 1; // 슬라이드 인덱스는 0부터 시작
  displaySlide(slideIndex);
  resetAutoSlide();
}

// 자동 전환 타이머를 초기화하는 함수
function resetAutoSlide() {
  clearTimeout(autoSlide);
  autoSlide = setTimeout(showSlides, 3000);
}

// 초기 슬라이드 표시 및 자동 전환 시작
displaySlide(slideIndex);
autoSlide = setTimeout(showSlides, 3000);

// 추천 상품 캐러셀
$(document).ready(function () {
  const $carousel = $('.product-carousel');
  const itemWidth = $('.product-item').outerWidth(true); // 상품의 너비
  const itemsPerView = 4; // 한번에 4개의 상품을 보여줍니다.
  const totalItems = $carousel.children().length; // 전체 상품 개수
  let currentPosition = 0;

  // 슬라이드 위치 업데이트
  function updateCarouselPosition() {
      const translateX = -currentPosition * itemWidth;
      $carousel.css('transform', 'translateX(' + translateX + 'px)');
  }

  // next 버튼 클릭 시
  $('.carousel-button.next').on('click', function () {
      if (currentPosition < totalItems - itemsPerView) {
          currentPosition += itemsPerView - 1; // 4개 중 1개는 중복되게 보여줌
          if (currentPosition >= totalItems) {
              currentPosition = totalItems - itemsPerView; // 마지막 상품까지만
          }
          updateCarouselPosition();
      }
  });

  // prev 버튼 클릭 시
  $('.carousel-button.prev').on('click', function () {
      if (currentPosition > 0) {
          currentPosition -= itemsPerView - 1; // 4개 중 1개는 중복되게 보여줌
          if (currentPosition < 0) {
              currentPosition = 0; // 첫 상품까지만
          }
          updateCarouselPosition();
      }
  });
});
