$('#person').click(function (event) {
<<<<<<< HEAD
    if ($(this).attr('data-is-logged-in') == 'true') {
        window.location.href = '/customer';
    } else {
        window.open('/login', '팀코스트코몰 - 로그인', 'width=600,height=600');
    }
});


$('#cart').click(function (event) {
    window.location.href = '/cart';
});
=======
    window.open('/login', '팀코스트코몰 - 로그인', 'width=600,height=600');
});
>>>>>>> origin/wishlist
