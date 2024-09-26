

$('.delete_btn').on('click', function() {
    const product_code = $('#product_code').val();
    console.log(product_code);
    if (confirm('해당 상품을 위시리스트에서 삭제하시겠습니까?')) {
        // 서버로 AJAX 요청 보내기
        $.ajax({
            url: '/api/wishlist/delete/',
            type: 'POST',
            data: { product_code: product_code },
            success: function(response) {
                console.log('삭제 성공:', response);
                if (response.success) {
                    // 아이템을 화면에서 제거
                    $('#delete_' + product_code).closest('.wishlist-item').remove();
                    alert(response.message);
                } else {
                    alert('위시리스트 삭제에 실패했습니다.');
                }
            },
            error: function(xhr, status, error) {
                console.error('삭제 실패:', error);
                alert('삭제 중 오류가 발생했습니다. 다시 시도해주세요.');
            }
        });
    }
});

// 상세페이지
// $('.wishlist-item').click((e) => {
//     const product_code = $('#product_code').val();
//     location.href = '/product/product_detail?product_code=' + product_code;
// });

// 장바구니에 추가 버튼 클릭 이벤트 리스너
$('.cart_btn').click(function () {
    const product_code = $('#product_code').val(); // 상품 코드를 가져옵니다.
    var product_count = 1; // 구매 수량을 가져옵니다. (초기값 1)

    var $thisButton = $(this); // 현재 클릭된 버튼을 참조

    // 서버로 AJAX 요청 보내기
    $.ajax({
        url: '/api/cart/add',
         type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            product_code: product_code,
            product_count: product_count
        }),
        success: function (response) {
            console.log('장바구니에 상품이 추가되었습니다:', response);
            // 모달 창 표시
            showCartModal($thisButton);
        },
        error: function (xhr, status, error) {
            console.error('장바구니 추가 실패:', error);
            alert('장바구니 추가에 실패했습니다. 다시 시도해주세요.');
        }
    });
});


// 모달 창에서 버튼 클릭 이벤트 리스너
$(document).on('click', '#continue_shopping', function () {
    // 모달 창 숨기기
    $('#cart_modal').remove();
});

$(document).on('click', '#go_to_cart', function () {
    // 장바구니 페이지로 이동
    window.location.href = '/customer/cart';
});

function showCartModal($button) {
    // 모달 창 HTML 생성
    var modalHtml = `
        <div id="cart_modal" class="cart_modal">
            <div class="modal_content">
                <p>상품이 장바구니에 추가되었습니다.</p>
                <button id="go_to_cart">장바구니로 이동</button>
                <button id="continue_shopping">쇼핑 계속하기</button>
            </div>
        </div>
    `;


    // 모달 창을 body 대신 버튼의 부모 요소에 추가
    $button.parent().append(modalHtml);

    // 모달 창 위치 설정
    var buttonOffset = $button.offset();
    var modalWidth = 300; // 모달 창의 너비
    var modalHeight = $('.modal_content').outerHeight();

    // 모달 창을 버튼 아래에 위치시키기
    $('#cart_modal').css({
        'position': 'absolute',
        'top': buttonOffset.top + $button.outerHeight() + 10 + 'px',  // 버튼 아래로 10px 띄우기
        'left': buttonOffset.left - (modalWidth - $button.outerWidth()) / 2 + 'px',
        'width': modalWidth + 'px',
        'z-index': 1000
    });
}

