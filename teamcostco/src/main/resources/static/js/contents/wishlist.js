$(document).ready(function() {
    // 전체 선택 체크박스 이벤트
    $('#select-all-checkbox').change(function() {
        $('.item-checkbox').prop('checked', $(this).is(':checked'));
    });

    // 개별 체크박스 이벤트
    $('.item-checkbox').change(function() {
        if ($('.item-checkbox:checked').length === $('.item-checkbox').length) {
            $('#select-all-checkbox').prop('checked', true);
        } else {
            $('#select-all-checkbox').prop('checked', false);
        }
    });

    // 개별 삭제 버튼 클릭 이벤트
    $('.delete_btn').on('click', function(e) {
        e.stopPropagation();
        const product_code = $(this).closest('.wishlist-item').find('.product_code').val();
        deleteWishlistItem(product_code);
    });

    // 장바구니에 추가 버튼 클릭 이벤트
    $('.cart_btn').click(function(e) {
        e.stopPropagation();
        const product_code = $(this).closest('.wishlist-item').find('.product_code').val();
        addToCart(product_code, $(this));
    });

    // 상품명 클릭 시 상세페이지로 이동
    $('.product-link').click(function(e) {
        e.stopPropagation();
        const product_code = $(this).closest('.wishlist-item').find('.product_code').val();
        location.href = '/product/product_detail?product_code=' + product_code;
    });

    // 선택 항목 삭제 버튼 클릭 이벤트
    $('#delete_selected').click(function() {
        const selectedItems = $('.item-checkbox:checked');
        if (selectedItems.length === 0) {
            alert('삭제할 항목을 선택해주세요.');
            return;
        }
        if (confirm('선택한 항목을 위시리스트에서 삭제하시겠습니까?')) {
            selectedItems.each(function() {
                deleteWishlistItem($(this).val());
            });
        }
    });

    // 선택 항목 장바구니 추가 버튼 클릭 이벤트
    $('#add_to_cart_selected').click(function() {
        const selectedItems = $('.item-checkbox:checked');
        if (selectedItems.length === 0) {
            alert('장바구니에 추가할 항목을 선택해주세요.');
            return;
        }
        selectedItems.each(function() {
            addToCart($(this).val());
        });
    });

    // 위시리스트 아이템 삭제 함수
    function deleteWishlistItem(product_code) {
        $.ajax({
            url: '/api/wishlist/delete',
            type: 'POST',
            data: { product_code: product_code },
            success: function(response) {
                console.log('삭제 성공:', response);
                if (response.success) {
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

    // 장바구니에 추가 함수
    function addToCart(product_code, $button) {
        $.ajax({
            url: '/api/cart/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                product_code: product_code,
                product_count: 1
            }),
            success: function(response) {
                console.log('장바구니에 상품이 추가되었습니다:', response);
                showCartModal($button);
            },
            error: function(xhr, status, error) {
                console.error('장바구니 추가 실패:', error);
                alert('장바구니 추가에 실패했습니다. 다시 시도해주세요.');
            }
        });
    }

    // 장바구니 모달 표시 함수
    function showCartModal($button) {
        var modalHtml = `
            <div id="cart_modal" class="cart_modal">
                <div class="modal_content">
                    <p>상품이 장바구니에 추가되었습니다.</p>
                    <button id="go_to_cart">장바구니로 이동</button>
                    <button id="continue_shopping">쇼핑 계속하기</button>
                </div>
            </div>
        `;

        $('body').append(modalHtml);
    }

    // 모달 창 버튼 클릭 이벤트
    $(document).on('click', '#continue_shopping', function() {
        $('#cart_modal').remove();
    });

    $(document).on('click', '#go_to_cart', function() {
        window.location.href = '/customer/cart';
    });
});