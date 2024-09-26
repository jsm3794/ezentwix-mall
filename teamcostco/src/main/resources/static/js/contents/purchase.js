$('.product_name').click(function (event) {
    var product_code = event.target.id;
    window.open('/product/product_detail?product_code=' + product_code);
});

$('#request_msg_list').change(function (event) {
    $('#shipping_request_message').val(event.target.value);
});

$('.find_address').click(function (event) {
    execDaumPostcode();
});

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수
            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }
            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                // document.getElementById("sample6_extraAddress").value = extraAddr;
            } else {
                // document.getElementById("sample6_extraAddress").value = '';
            }
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postal_code').value = data.zonecode;
            document.getElementById("road_name_address").value = addr;
            document.getElementById("detail_address").value = '';
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detail_address").focus();
        }
    }).open();
}

$('.checkout-button').click(function (event) {
    event.preventDefault(); // 기본 폼 제출 방지

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

        items: productList.map(function(item) {
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

    $.ajax({
        url: '/api/sales', // 백엔드 엔드포인트
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(payload),
        success: function (response) {
            alert("주문이 성공적으로 완료되었습니다!");
        },
        error: function (xhr, status, error) {
            alert("주문 처리 중 오류가 발생했습니다: " + xhr.responseText);
        }
    });
});