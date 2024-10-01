$(document).ready(function () {
    // 제품 이름 클릭 시 상세 페이지 열기
    $('.product_name').click(function (event) {
        var product_code = event.target.id;
        window.open('/product/product_detail?product_code=' + product_code);
    });

    // 배송 요청 메시지 변경 시 값 설정
    $('#request_msg_list').change(function (event) {
        $('#shipping_request_message').val(event.target.value);
    });

    // 주소 찾기 버튼 클릭 시 주소 찾기 실행
    $('.find_address').click(function (event) {
        execDaumPostcode();
    });

    // Daum 우편번호 서비스 실행 함수
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function (data) {
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수
                if (data.userSelectedType === 'R') { // 도로명 주소 선택 시
                    addr = data.roadAddress;
                    if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                        extraAddr += data.bname;
                    }
                    if (data.buildingName !== '' && data.apartment === 'Y') {
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    if (extraAddr !== '') {
                        extraAddr = ' (' + extraAddr + ')';
                    }
                } else { // 지번 주소 선택 시
                    addr = data.jibunAddress;
                }
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                $('#postal_code').val(data.zonecode);
                $('#road_name_address').val(addr);
                $('#detail_address').val('');
                // 커서를 상세주소 필드로 이동한다.
                $('#detail_address').focus();
            }
        }).open();
    }

    // 주소 변경 버튼 클릭 시 배송 주소 페이지로 이동
    $('.change_address_btn').click(function(event) {
        window.location.href = '/customer/shipping_address';
    });

    // 주문 처리 중 오버레이 생성 및 표시 함수
    function showOrderProcessingOverlay() {
        // 오버레이가 이미 존재하는지 확인
        if ($('#order-processing-overlay').length === 0) {
            var overlayHtml = `
                <div id="order-processing-overlay">
                    <div class="overlay-content">
                        <div class="spinner"></div>
                        <span>주문 처리 중...</span>
                    </div>
                </div>
            `;
            $('body').append(overlayHtml);

            // 오버레이 스타일 적용
            $('#order-processing-overlay').css({
                'position': 'fixed',
                'top': '0',
                'left': '0',
                'width': '100%',
                'height': '100%',
                'background-color': 'rgba(0, 0, 0, 0.5)',
                'display': 'flex',
                'align-items': 'center',
                'justify-content': 'center',
                'z-index': '10000'
            });

            $('.overlay-content').css({
                'background-color': '#fff',
                'padding': '20px 40px',
                'border-radius': '8px',
                'text-align': 'center',
                'font-size': '20px',
                'color': '#333',
                'display': 'flex',
                'align-items': 'center'
            });

            // 스피너 스타일
            $('.spinner').css({
                'margin-right': '10px',
                'border': '6px solid #f3f3f3',
                'border-top': '6px solid #3498db',
                'border-radius': '50%',
                'width': '24px',
                'height': '24px',
                'animation': 'spin 2s linear infinite'
            });

            // 스피너 애니메이션
            var spinnerStyle = `
                @keyframes spin {
                    0% { transform: rotate(0deg); }
                    100% { transform: rotate(360deg); }
                }
            `;
            $('head').append(`<style>${spinnerStyle}</style>`);
        }
        // 오버레이 표시
        $('#order-processing-overlay').fadeIn(300);
        // 페이지 상호작용 차단
        $('body').css('pointer-events', 'none');
    }

    // 주문 처리 중 오버레이 숨기기 함수
    function hideOrderProcessingOverlay() {
        $('#order-processing-overlay').fadeOut(300, function() {
            // 오버레이 제거
            $('#order-processing-overlay').remove();
            // 페이지 상호작용 허용
            $('body').css('pointer-events', 'auto');
        });
    }

    // Checkout 버튼 클릭 이벤트 핸들러
    $('.checkout-button').click(function (event) {
        event.preventDefault(); // 기본 폼 제출 방지

        // 필수 필드 검증 (예시)
        var requiredFields = [
            '#sender_name',
            '#sender_phone_number',
            '#recipient_name',
            '#postal_code',
            '#road_name_address',
            '#detail_address',
            '#recipient_phone_number',
            '#payment_method'
        ];

        // 주문 처리 중 오버레이 표시
        showOrderProcessingOverlay();

        // 주문 데이터 수집
        var formData = {
            sender_name: $('#sender_name').val(),
            sender_phone_number: $('#sender_phone_number').val(),
            recipient_name: $('#recipient_name').val(),
            postal_code: $('#postal_code').val(),
            road_name_address: $('#road_name_address').val(),
            detail_address: $('#detail_address').val(),
            recipient_phone_number: $('#recipient_phone_number').val(),
            shipping_request_message: $('#shipping_request_message').val(),
            payment_method: $('input[name="payment-method"]:checked').val(),

            items: productList.map(function (item) {
                return {
                    product_code: item.product.product_code,
                    qty: item.count
                };
            })
        };

        var payload = {
            shipping_address: {
                sender_name: formData.sender_name,
                sender_phone_number: formData.sender_phone_number,
                recipient_name: formData.recipient_name,
                postal_code: formData.postal_code,
                road_name_address: formData.road_name_address,
                detail_address: formData.detail_address,
                recipient_phone_number: formData.recipient_phone_number,
                shipping_request_message: formData.shipping_request_message
            },
            payment_method: formData.payment_method,
            items: formData.items
        };

        // AJAX 요청 보내기
        $.ajax({
            url: '/api/sales', // 백엔드 엔드포인트
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(payload),
            success: function (response) {
                location.href = '/customer/sales/detail/' + response;
            },
            error: function (xhr, status, error) {
                var errorMessage = "주문 처리 중 오류가 발생했습니다.";
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    errorMessage += "\n" + xhr.responseJSON.message;
                }
                alert(errorMessage);
                hideOrderProcessingOverlay();
            }
        });
    });
});
