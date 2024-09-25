document.addEventListener("DOMContentLoaded", function () {
    const openPopupBtn = document.getElementById("openPopupBtn");
    const closePopupBtn = document.getElementById("closePopupBtn");
    const popup = document.getElementById("popup");
    const addressForm = document.getElementById("addressForm");

    // 팝업 열기
    openPopupBtn.addEventListener("click", function () {
        popup.style.display = "block";
    });

    // 팝업 닫기
    closePopupBtn.addEventListener("click", function () {
        popup.style.display = "none";
    });

    // 폼 제출 처리
    addressForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const formData = new FormData(addressForm);
        const data = {
            alias: formData.get("alias"),
            recipient: formData.get("recipient"),
            address: formData.get("address"),
            phone: formData.get("phone")
        };

        // AJAX 요청으로 서버에 데이터 저장
        fetch('/customer/address/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    alert('배송지가 성공적으로 추가되었습니다.');
                    location.reload(); // 페이지 새로고침으로 테이블 업데이트
                } else {
                    alert('추가하는 중 오류가 발생했습니다.');
                }
            });
    });
});