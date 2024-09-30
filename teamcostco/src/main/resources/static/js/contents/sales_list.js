$('.description_area').click(function(event){
    var pCode = $(event.target.parentElement).data('product-code');
    window.open('/product/product_detail?product_code=' + pCode);
});

$('.view_detail').click(function(event){
    var sId = $(event.target).data('sales-id');
    window.open('/customer/sales/detail/' + sId);
});


$('.shipping_trace').click(function(event){
    var sId = $(event.target.parentElement).data('sales-id');
    window.open('/customer/sales/shippingtrace/' + sId);
});
