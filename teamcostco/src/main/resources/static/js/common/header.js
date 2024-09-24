$('#person').click(function (event) {
    if ($(this).attr('data-is-logged-in') == 'true') {
        window.location.href = '/customer';
    } else {
        window.open('/login', '팀코스트코몰 - 로그인', 'width=600,height=600');
    }
});

$('#favorite').click(function (event) {
    if ($(this).attr('data-is-logged-in') == 'true') {
        window.location.href = '/customer/wishlist';
    } else {
        window.open('/login', '팀코스트코몰 - 로그인', 'width=600,height=600');
    }
});

$('#cart').click(function (event) {
    if ($(this).attr('data-is-logged-in') == 'true') {
        window.location.href = '/customer/cart';
    } else {
        window.open('/login', '팀코스트코몰 - 로그인', 'width=600,height=600');
    }
});

$('.header-left>h1').click(function (event){
    window.location.href = '/';
});