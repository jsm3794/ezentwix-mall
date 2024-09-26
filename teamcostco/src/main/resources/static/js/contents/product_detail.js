$(document).ready(function () {

    var $tabButtons = $('.tab_button');
    var $sections = $('.tab_content');
    var $amountEl = $('#amount');
    var $buyButton = $('.buy_button');

    const mainImage = document.querySelector('.main_image');
    const img = mainImage.querySelector('img');


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

    $amountEl.on('focus', function () {
        previousValue = $amountEl.val();
    });

    $amountEl.on('change', function () {
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
    $('.wishListBtn').click(function (e) {
        checkLoginAndExecute(function (data) {
            toggleWishlist(data.customerId);
        });
    });


    $buyButton.click(function (event) {
        event.preventDefault(); // 기본 동작 방지

        checkLoginAndExecute(function (data) {
            var product_code = $buyButton.data('productCode');
            var amount = $amountEl.val();
            window.location.href = '/purchase?product_list=' + encodeURIComponent(product_code + ':' + amount);
        });
    });



    function toggleWishlist(social_id) {
        const product_code = $('#product_code').val();
        const wishListBtn = $('.wishListBtn');
        let iconElement = wishListBtn.find('.material-symbols-outlined, .material-symbols-outlined-fill'); // 두 가지 클래스를 모두 탐색

        // 아이콘 요소가 없을 경우를 대비한 확인 (실제로 존재하는지 확인만 함)
        if (iconElement.length === 0) {
            console.error('아이콘 요소를 찾을 수 없습니다.');
            return;
        }

        // 위시리스트에 이미 있는지 확인 (filled 클래스가 있는지 확인)
        const isInWishlist = iconElement.hasClass('material-symbols-outlined-fill');
        const url = isInWishlist ? '/api/wishlist/delete' : '/api/wishlist/add';

        $.ajax({
            url: url,
            method: 'POST',
            data: { product_code: product_code },
            success: function (response) {
                if (response.success) {
                    if (isInWishlist) {
                        // filled 클래스 제거하고 outlined 클래스로 변경
                        iconElement.removeClass('material-symbols-outlined-fill').addClass('material-symbols-outlined');
                        alert('찜 목록에서 제거되었습니다.');
                    } else {
                        // outlined 클래스 제거하고 filled 클래스로 변경
                        iconElement.removeClass('material-symbols-outlined').addClass('material-symbols-outlined-fill');
                        alert('찜 목록에 추가되었습니다.');
                    }
                } else {
                    alert('위시리스트 업데이트에 실패했습니다.');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
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
        var productCode = $('#product_code').val();
        var productCount = $('#amount').val();
        var $thisButton = $(this);

        checkLoginAndExecute(function (data) {
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
            window.location.href = '/cart'; // 장바구니 페이지의 URL로 변경하세요.
        });
    }


    mainImage.addEventListener('mousemove', function (e) {
        const rect = mainImage.getBoundingClientRect();
        const x = e.clientX - rect.left; // 요소 내에서의 X 좌표
        const y = e.clientY - rect.top;  // 요소 내에서의 Y 좌표

        // 이미지 이동 비율 계산
        const xPercent = (x / rect.width) * 100;
        const yPercent = (y / rect.height) * 100;

        img.style.transformOrigin = `${xPercent}% ${yPercent}%`;
    });

    mainImage.addEventListener('mouseenter', function () {
        img.style.transform = 'scale(1.5)';
    });

    mainImage.addEventListener('mouseleave', function () {
        img.style.transform = 'scale(1)';
    });


});
