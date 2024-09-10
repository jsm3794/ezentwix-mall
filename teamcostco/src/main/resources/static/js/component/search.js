const search_btn = $('#search_btn');
const productList = $('.product_list');
const size_select = $('#size');

search_btn.on('click', (e) => {
    e.preventDefault();
    const currentUrl = new URL(window.location.href);
    const params = new URLSearchParams(currentUrl.search);
    const newQuery = $('#query').val();
    params.set('query', newQuery);
    const newUrl = `${currentUrl.origin}${currentUrl.pathname}?${params.toString()}`;
    window.location.href = newUrl;
});

function clearCurrentPage() {
    $('#page').val(1);
}


function clearProductList() {
    $('.list_box').empty(); // .list_box 요소의 모든 자식 요소를 제거합니다.
}

function fetchProduct() {
    let product_list = [];

    $.ajax({
        url: '/search',
        method: 'POST',
        data: getQueryParams(), // 웹 uri 상 파라메터 전부 가져와 적용하기
        dataType: 'json',
        success: function (data, status, xhr) {
            const pagedata = data;

            pagedata.data.forEach(item => {
                const productDiv = $('<div>', {
                    class: 'product_item',
                    html: `<div class='thumbnail_wrap'>
                    <img class='product_thumbnail' src='https://sitem.ssgcdn.com/23/84/49/item/1000060498423_i1_290.jpg'></div>
                    <span class='product_name'><b>${item.brand + ' '}</b>${item.product_name}</span>
                    ${item.discount > 0.00 ? `<span class='before_discount_price'>${item.selling_price.toLocaleString()}원</span>` : ''}
                    <div class='product_price'>
                    ${item.discount > 0.00 ? `<span class='discount'>${item.discount * 100}%</span>` : ''}
                    <span class='product_price'>${(Math.floor(item.selling_price * (1 - item.discount) / 10) * 10).toLocaleString()}원</span>
                    </div>`
                })

                productDiv.on('click', (e) => {
                    location.href = `/productdetailview/${item.product_code}`;
                });

                $('.list_box').append(productDiv);
            });

            $('#totalpage').val(pagedata.pageDetails.totalPages);
            console.dir(pagedata);
        },
        error: function (data, status, err) {
        },
        complete: function () {
            showList();
        }
    });
}

const hideList = () => {
    if (productList.hasClass('show')) {
        productList.removeClass('show');
    }
}

const showList = () => {
    if (!productList.hasClass('show')) {
        productList.addClass('show');
    }
}

size_select.on('change', (e) => {
    clearProductList();
    hideList();
    fetchProduct();
});

$('#show_more_btn').on('click', () => {
    const currentPage = parseInt($('#page').val(), 10);
    const totalpage = $('#totalpage').val();

    if (currentPage < totalpage) {
        $('#page').val(currentPage + 1);
        fetchProduct();
    }

});

$('.brand_checkbox').on('change', () => {
    const checkedCheckboxes = $('.brand_checkbox:checked');
    const checkedIds = checkedCheckboxes.map((_, checkbox) => $(checkbox).attr('id')).get();
    const joinedIds = checkedIds.join(' | ');
    $('#brands').val(joinedIds);
    hideList();
    clearCurrentPage();
    clearProductList();
    fetchProduct();
});


function getQueryParams() {
    const queryParams = {};
    queryParams['query'] = getQueryParam('query');
    queryParams['page'] = $('#page').val();
    queryParams['size'] = $('#size').val();
    queryParams['brands'] = $('#brands').val();
    return queryParams;
}

function getQueryParam(param) {
    var urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

fetchProduct();