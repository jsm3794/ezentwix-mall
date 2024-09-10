document.addEventListener("DOMContentLoaded", (e)=>{

  
});

let product_list = [];

$.ajax({
    url: '/search',
    method: 'POST',
    data : getQueryParams(), // 웹 uri 상 파라메터 전부 가져와 적용하기
    dataType : 'json',
    success: function (data, status, xhr) {
        if(data.length > 0){
            $('#search_state').text('입니다.');
        } else {
            $('#search_state').text('가 없습니다.');
        }
        product_list = data;
    },
    error: function (data, status, err) {
    },
    complete: function () {
        product_list.forEach(item => {
            console.log(item.discount);
            const productDiv = $('<div>', {
                class:'product_item',
                html:`<div class='thumbnail_wrap'>
                <img class='product_thumbnail' src='https://sitem.ssgcdn.com/23/84/49/item/1000060498423_i1_290.jpg'></div>
                <span class='product_name'><b>${item.brand + ' '}</b>${item.product_name}</span>
                ${item.discount > 0.00 ? `<span class='before_discount_price'>${item.selling_price.toLocaleString()}원</span>` : ''}
                <div class='product_price'>
                ${item.discount > 0.00 ? `<span class='discount'>${item.discount * 100}%</span>` : ''}
                <span class='product_price'>${(Math.floor(item.selling_price * (1 - item.discount) / 10) * 10).toLocaleString()}원</span>
                </div>`
            })

            productDiv.on('click',(e) =>{
                location.href=`/product/${item.product_code}`;
            });

            $('.list_box').append(productDiv);
        });
    }
});

function getQueryParams() {
    const params = new URLSearchParams(location.search);
    const queryParams = {};
    queryParams['query'] = $('#query').val();
    queryParams['page'] = $('#page').val();
    queryParams['size'] = $('#size').val();
    return queryParams;
}