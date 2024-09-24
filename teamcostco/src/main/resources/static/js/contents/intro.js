let slideIndex = 0;
showSlides();

function showSlides() {
  const slides = document.getElementsByClassName("slide-item");
  const dots = document.getElementsByClassName("pagination-dot");
  
  for (let i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
    dots[i].className = dots[i].className.replace(" active", "");
  }
  
  slideIndex++;
  if (slideIndex > slides.length) { slideIndex = 1; }
  
  slides[slideIndex - 1].style.display = "block";
  dots[slideIndex - 1].className += " active";
  
  setTimeout(showSlides, 4000); // 슬라이드 자동 전환 시간 (3초)
}

function changeSlide(n) {
  slideIndex += n;
  const slides = document.getElementsByClassName("slide-item");
  const dots = document.getElementsByClassName("pagination-dot");
  
  if (slideIndex > slides.length) { slideIndex = 1; }
  if (slideIndex < 1) { slideIndex = slides.length; }
  
  for (let i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
    dots[i].className = dots[i].className.replace(" active", "");
  }
  
  slides[slideIndex - 1].style.display = "block";
  dots[slideIndex - 1].className += " active";
}

function currentSlide(n) {
  slideIndex = n;
  changeSlide(0);
}
