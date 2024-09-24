let slideIndex = 0;
showSlides();

function showSlides() {
  const slides = document.getElementsByClassName("slide");
  const dots = document.getElementsByClassName("dot");
  
  // 모든 슬라이드와 도트를 숨기고 활성화 클래스 제거
  for (let i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
    dots[i].className = dots[i].className.replace(" active", "");
  }
  
  slideIndex++;
  if (slideIndex > slides.length) { slideIndex = 1; }
  
  // 현재 슬라이드와 도트를 표시 및 활성화
  slides[slideIndex - 1].style.display = "block";
  dots[slideIndex - 1].className += " active";
  
  // 슬라이드 자동 전환 시간 (4초)
  setTimeout(showSlides, 4000);
}

function changeSlide(n) {
  slideIndex += n;
  const slides = document.getElementsByClassName("slide");
  const dots = document.getElementsByClassName("dot");
  
  if (slideIndex > slides.length) { slideIndex = 1; }
  if (slideIndex < 1) { slideIndex = slides.length; }
  
  // 모든 슬라이드와 도트를 숨기고 활성화 클래스 제거
  for (let i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
    dots[i].className = dots[i].className.replace(" active", "");
  }
  
  // 현재 슬라이드와 도트를 표시 및 활성화
  slides[slideIndex - 1].style.display = "block";
  dots[slideIndex - 1].className += " active";
}

function currentSlide(n) {
  slideIndex = n;
  showCurrentSlide();
}

function showCurrentSlide() {
  const slides = document.getElementsByClassName("slide");
  const dots = document.getElementsByClassName("dot");
  
  // 모든 슬라이드와 도트를 숨기고 활성화 클래스 제거
  for (let i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
    dots[i].className = dots[i].className.replace(" active", "");
  }
  
  // 현재 슬라이드와 도트를 표시 및 활성화
  slides[slideIndex - 1].style.display = "block";
  dots[slideIndex - 1].className += " active";
  
  // 자동 전환 타이머 리셋
  clearTimeout(autoSlide);
  autoSlide = setTimeout(showSlides, 4000);
}

// 자동 전환 타이머를 전역 변수로 선언하여 리셋 가능하게 함
let autoSlide = setTimeout(showSlides, 4000);
