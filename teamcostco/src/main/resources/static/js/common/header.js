function checkLoginAndNavigate(element, loggedInUrl) { 

    console.log($(element).closest('.icon-box').data('is-logged-in'));
    var isLoggedIn = $(element).closest('.icon-box').data('is-logged-in') == true; 
    if (isLoggedIn) {
        window.location.href = loggedInUrl;
    } else {
        window.open('/login', '팀코스트코몰 - 로그인', 'width=600,height=600');
    }
}

$('#person').click(function (event) {
    checkLoginAndNavigate(this, '/customer');
});

$('#favorite').click(function (event) {
    checkLoginAndNavigate(this, '/customer/wishlist');
});

$('#cart').click(function (event) {
    checkLoginAndNavigate(this, '/customer/cart');
});

$('.header-left > h1').click(function (event){
    window.location.href = '/';
});