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

    function updateWishlistUI(productCodes) {
        // 현재 제품이 위시리스트에 있는지 확인
        const currentProductCode = $('#product_code').val(); // 제품 코드를 저장하는 hidden input 필요
        if (productCodes.includes(currentProductCode)) {
            $('.wishListBtn').addClass('active');
        } else {
            $('.wishListBtn').removeClass('active');
        }
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
                        location.href = '/login';
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
                    streamWishlist(social_id);
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
    

     // 페이지 로드 시 위시리스트 스트리밍 시작
     $.ajax({
        url: '/login/check',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            if (data.loggedIn) {
                streamWishlist(data.customerId);
            }
        }
    });

    function applyPriceByAmount() {
        const totalPrice = $amountEl.val() * $('#product_price').val();
        $('.product_total_price').val(totalPrice);
        $('.total_price').text(totalPrice.toLocaleString());
    }

});
