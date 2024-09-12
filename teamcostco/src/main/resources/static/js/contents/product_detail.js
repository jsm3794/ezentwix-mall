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

    function applyPriceByAmount() {
        const totalPrice = $amountEl.val() * $('#product_price').val();
        $('.product_total_price').val(totalPrice);
        $('.total_price').text(totalPrice.toLocaleString());
    }

});
