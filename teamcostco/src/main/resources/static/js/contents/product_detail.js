$(document).ready(function () {

    var $tabButtons = $('.tab_button');
    var $sections = $('.tab_content');
    var $amountEl = $('#amount');

    function activateTabBasedOnScroll() {
        var scrollTop = $(window).scrollTop();

        $sections.each(function (index, section) {
            var $section = $(section);
            var sectionTop = $section.offset().top;

            // Check if the top of the section is exactly at the top of the viewport
            if (scrollTop >= sectionTop - 50) {
                $tabButtons.removeClass('active');
                $tabButtons.eq(index).addClass('active');
            }
        });
    }

    $(window).on('scroll', function () {
        activateTabBasedOnScroll();
    });

    // Handle tab button clicks
    $tabButtons.click(function () {
        var index = $(this).index();
        var $section = $sections.eq(index);

        $('html, body').animate({
            scrollTop: $section.offset().top - 30
        }, 300);
    });

    // Scroll to top button
    $('.go_top').click(function () {
        $('html, body').animate({
            scrollTop: 0
        }, 300);
    });

    $('#amount_decrease').click(function (event) {
        if ($amountEl.val() > 1) {
            $amountEl.val(parseInt($amountEl.val()) - 1);
            applyPriceByAmount();
        }
    });

    $('#amount_increase').click(function (event) {
        $amountEl.val(parseInt($amountEl.val()) + 1);
        applyPriceByAmount();
    });

    var previousValue = $amountEl.val();

    $amountEl.on('focus', function() {
        previousValue = $amountEl.val();
    });

    $amountEl.on('change', function() {
        var currentValue = $amountEl.val();
        var numericValue = currentValue.replace(/[^0-9]/g, '');

        if (numericValue === '' || parseInt(numericValue, 10) < 1) {
            $amountEl.val(previousValue);
        } else {
            previousValue = $amountEl.val();
        }
        applyPriceByAmount();
    });

    // 찜 목록
     // 위시리스트 스트리밍 기능 추가
     function streamWishlist(social_id) {
        const url = `/api/wishlist/stream/${social_id}`;
        
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.body.getReader();
            })
            .then(reader => new ReadableStream({
                start(controller) {
                    return pump();
                    function pump() {
                        return reader.read().then(({ done, value }) => {
                            if (done) {
                                controller.close();
                                return;
                            }
                            controller.enqueue(value);
                            return pump();
                        });
                    }
                }
            }))
            .then(stream => new Response(stream))
            .then(response => response.text())
            .then(text => {
                const productCodes = text.trim().split('\n');
                updateWishlistUI(productCodes);
            })
            .catch(err => console.error(err));
    }

    // 찜 목록 관련 코드 수정
    $('.wishListBtn').click((e) => {
        $.ajax({
            url: '/login/check',  
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                if (data.loggedIn) {
                    toggleWishlist(data.customerId);
                } else {
                    if (confirm('로그인이 필요한 서비스입니다. 로그인 하시겠습니까?')) {
                        window.open('/login', '팀코스트코몰 - 로그인', 'width=600,height=600');
                    }
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('로그인 상태 확인 중 오류 발생:', textStatus, errorThrown);
            }
        });
    });

    function toggleWishlist(social_id) {
        const product_code = $('#product_code').val();
        const wishListBtn = $('.wishListBtn');
        const iconElement = wishListBtn.find('.material-symbols-outlined');
        
        const isInWishlist = iconElement.text().trim() === 'delete';
        const url = isInWishlist ? '/api/wishlist/delete' : '/api/wishlist/add';
        
        $.ajax({
            url: url,
            method: 'POST',
            data: { product_code: product_code },
            success: function(response) {
                if (response.success) {
                    if (isInWishlist) {
                        iconElement.text('favorite');
                        alert('찜 목록에서 제거되었습니다.');
                    } else {
                        iconElement.text('delete');
                        alert('찜 목록에 추가되었습니다.');
                    }
                } else {
                    alert('위시리스트 업데이트에 실패했습니다: ');
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('위시리스트 업데이트 중 오류 발생:', textStatus, errorThrown);
                alert('위시리스트 업데이트에 실패했습니다. 다시 시도해주세요.');
            }   
        });
    }

    function applyPriceByAmount() {
        const totalPrice = $amountEl.val() * $('#product_price').val();
        $('.product_total_price').val(totalPrice);
        $('.total_price').text(totalPrice.toLocaleString());
    }

      // 장바구니에 추가 버튼 클릭 이벤트 리스너
      $('.cart_button').click(function () {
        var productCode = $('#product_code').val(); // 상품 코드를 가져옵니다.
        var productCount = $('#amount').val(); // 구매 수량을 가져옵니다.

        var $thisButton = $(this); // 현재 클릭된 버튼을 참조

        // 서버로 AJAX 요청 보내기
        $.ajax({
            url: '/api/cart/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                product_code: productCode,
                product_count: productCount
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

    // 모달 창 표시 함수 수정
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

        // 모달 창을 버튼 위에 위치시키기
        $('#cart_modal').css({
            'position': 'absolute',
            'top': buttonOffset.top - modalHeight - 10 + 'px', // 버튼 위로 약간 띄우기
            'left': buttonOffset.left - (modalWidth - $button.outerWidth()) / 2 + 'px',
            'width': modalWidth + 'px',
            'z-index': 1000
        });
    }

});
