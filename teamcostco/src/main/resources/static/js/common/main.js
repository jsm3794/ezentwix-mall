document.addEventListener("DOMContentLoaded", function () {
    if (document.fonts) {
        document.body.classList.add('loaded');
    }
});

function checkLoginAndExecute(callback) {
    $.ajax({
        url: '/login/check',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            if (data.loggedIn) {
                callback(data);
            } else {
                if (confirm('로그인이 필요한 서비스입니다. 로그인 하시겠습니까?')) {
                    window.open('/login', '팀코스트코몰 - 로그인', 'width=600,height=600');
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error('로그인 상태 확인 중 오류 발생:', textStatus, errorThrown);
        }
    });
}
