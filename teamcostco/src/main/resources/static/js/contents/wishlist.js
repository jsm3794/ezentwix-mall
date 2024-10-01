$(document).ready(function () {
    // 전체 선택 체크박스 이벤트
    $('#select-all-checkbox').change(function () {
        $('.item-checkbox').prop('checked', $(this).is(':checked'));
    });

    // 개별 체크박스 이벤트
    $('.item-checkbox').change(function () {
        if ($('.item-checkbox:checked').length === $('.item-checkbox').length) {
            $('#select-all-checkbox').prop('checked', true);
        } else {
            $('#select-all-checkbox').prop('checked', false);
        }
    });

    // 개별 삭제 버튼 클릭 이벤트
    $('.delete_btn').on('click', function (e) {
        e.stopPropagation();
        const product_code = $(this).closest('.wishlist-item').find('.product_code').val();
        deleteWishlistItem(product_code);
    });

    // 장바구니에 추가 버튼 클릭 이벤트 (개별)
    $('.cart_btn').click(function (e) {
        e.stopPropagation();
        const product_code = $(this).closest('.wishlist-item').find('.product_code').val();
        addToCart(product_code, true, $(this));
    });

    // 상품명 클릭 시 상세페이지로 이동
    $('.product-link').click(function (e) {
        e.stopPropagation();
        const product_code = $(this).closest('.wishlist-item').find('.product_code').val();
        location.href = '/product/product_detail?product_code=' + product_code;
    });

    // 선택 항목 삭제 버튼 클릭 이벤트
    $('#delete_selected').click(function () {
        const selectedItems = $('.item-checkbox:checked');
        if (selectedItems.length === 0) {
            alert('삭제할 항목을 선택해주세요.');
            return;
        }
        if (confirm('선택한 항목을 위시리스트에서 삭제하시겠습니까?')) {
            const deletePromises = [];
            const deletedItems = [];
            const failedItems = [];

            selectedItems.each(function () {
                const product_code = $(this).val();
                deletePromises.push(deleteWishlistItemPromise(product_code).then(function (response) {
                    if (response.success) {
                        $('#delete_' + product_code).closest('.wishlist-item').remove();
                        deletedItems.push(product_code);
                    } else {
                        failedItems.push(product_code);
                    }
                }).catch(function (error) {
                    console.error('삭제 실패:', error);
                    failedItems.push(product_code);
                }));
            });

            // 모든 삭제 요청이 완료된 후 결과를 처리
            Promise.all(deletePromises).then(function () {
                let message = '';
                if (deletedItems.length > 0) {
                    message += `${deletedItems.length}개의 항목이 성공적으로 삭제되었습니다.\n`;
                }
                if (failedItems.length > 0) {
                    message += `${failedItems.length}개의 항목 삭제에 실패했습니다. 다시 시도해주세요.`;
                }
                alert(message);
            });
        }
    });

    // 선택 항목 장바구니 추가 버튼 클릭 이벤트
    $('#add_to_cart_selected').click(function () {
        const selectedItems = $('.item-checkbox:checked');
        if (selectedItems.length === 0) {
            alert('장바구니에 추가할 항목을 선택해주세요.');
            return;
        }

        const addPromises = [];

        selectedItems.each(function () {
            const product_code = $(this).val();
            addPromises.push(addToCartPromise(product_code));
        });

        // 모든 AJAX 호출이 완료된 후 모달을 한 번만 표시
        Promise.all(addPromises).then(function (responses) {
            showCartModal();
        }).catch(function () {
            alert('장바구니 추가에 실패했습니다. 일부 항목은 추가되지 않았을 수 있습니다.');
        });
    });

    // 위시리스트 아이템 삭제 함수 (개별)
    function deleteWishlistItem(product_code) {
        let result = confirm('해당 상품을 위시리스트에서 삭제할까요?');
        if (result) {
            $.ajax({
                url: '/api/wishlist/delete',
                type: 'POST',
                data: { product_code: product_code },
                success: function (response) {
                    if (response.success) {
                        $('#delete_' + product_code).closest('.wishlist-item').remove();
                        alert(response.message);
                    } else {
                        alert('위시리스트 삭제에 실패했습니다.');
                    }
                },
                error: function (xhr, status, error) {
                    console.error('삭제 실패:', error);
                    alert('삭제 중 오류가 발생했습니다. 다시 시도해주세요.');
                }
            });
        }
    }

    // 위시리스트 아이템 삭제 함수 (Promise 반환)
    function deleteWishlistItemPromise(product_code) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: '/api/wishlist/delete',
                type: 'POST',
                data: { product_code: product_code },
                success: function (response) {
                    if (response.success) {
                        resolve(response);
                    } else {
                        reject(response.message);
                    }
                },
                error: function (xhr, status, error) {
                    console.error('삭제 실패:', error);
                    reject(error);
                }
            });
        });
    }

    // 장바구니에 추가 함수 (개별)
    function addToCart(product_code, showModal = true, $button = null) {
        return $.ajax({
            url: '/api/cart/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                product_code: product_code,
                product_count: 1
            }),
            success: function (response) {
                console.log('장바구니에 상품이 추가되었습니다:', response);
                if (showModal && $button) {
                    showCartModal($button);
                }
            },
            error: function (xhr, status, error) {
                console.error('장바구니 추가 실패:', error);
                alert('장바구니 추가에 실패했습니다. 다시 시도해주세요.');
            }
        });
    }

    // 장바구니에 추가 함수 (Promise 반환)
    function addToCartPromise(product_code) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: '/api/cart/add',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    product_code: product_code,
                    product_count: 1
                }),
                success: function (response) {
                    console.log('장바구니에 상품이 추가되었습니다:', response);
                    resolve(response);
                },
                error: function (xhr, status, error) {
                    console.error('장바구니 추가 실패:', error);
                    reject(error);
                }
            });
        });
    }

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
});
