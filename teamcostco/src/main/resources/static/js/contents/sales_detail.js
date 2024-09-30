document.addEventListener('DOMContentLoaded', function (event) {
    // 장바구니에 추가 버튼 클릭 이벤트 리스너
    $('.cart_button').click(function (event) {
        var $thisButton = $(this);
        var productCode = $thisButton.data('product-code');
        var productCount = 1;



        $.ajax({
            url: '/api/cart/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                product_code: productCode,
                product_count: productCount
            }),
            success: function (response) {
                showCartModal($thisButton);
            },
            error: function (xhr, status, error) {
                alert('장바구니 추가에 실패했습니다. 다시 시도해주세요.');
            }
        });

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

        // 모달 창을 body에 추가
        $('body').append(modalHtml);

        // 모달 창 요소 선택
        var $modal = $('#cart_modal');
        var $modalContent = $modal.find('.modal_content');

        // 모달 창 스타일 설정
        $modal.css({
            'position': 'fixed',
            'top': 0,
            'left': 0,
            'width': '100%',
            'height': '100%',
            'background': 'rgba(0, 0, 0, 0.5)', // 반투명 검은색 배경
            'z-index': 1000,
            'display': 'flex',
            'justify-content': 'center',
            'align-items': 'center'
        });

        $modalContent.css({
            'background-color': '#fff',
            'padding': '20px',
            'border-radius': '8px',
            'text-align': 'center',
            'position': 'relative'
        });

        // 모달 창 외부를 클릭하면 모달 창 닫기
        $modal.on('click', function (e) {
            if ($(e.target).is('#cart_modal')) {
                $modal.remove();
            }
        });

        // "쇼핑 계속하기" 버튼 클릭 시 모달 창 닫기
        $('#continue_shopping').on('click', function () {
            $modal.remove();
        });

        // "장바구니로 이동" 버튼 클릭 시 장바구니 페이지로 이동
        $('#go_to_cart').on('click', function () {
            window.location.href = '/customer/cart'; // 장바구니 페이지의 URL로 변경하세요.
        });
    }

    $('.description_area').click(function (event) {
        var pCode = $(event.target.parentElement).data('product-code');
        window.open('/product/product_detail?product_code=' + pCode);
    });
});