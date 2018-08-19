
// $('#buyConfirmation').bind('click', buyTrade, false);
// $('#buyConfirmation').click()
    function buyTrade() {
    stopInterval();
    console.log('processing buy trade');
    var buyTrade = {};

    // buyTrade.instrument = $('#buyInstrument').val();
    // buyTrade.price = $('#buyPrice').val();

    buyTrade.instrument = "EURPLN";
    buyTrade.price = 4;

    $.ajax({
        url: "http://localhost:8080/transaction/buy",
        type: "POST",
        headers: {
            'Accept': 'application/json',
            "Content-Type": "application/json"
        },
        data: JSON.stringify(buyTrade)
    }).done(function () {
        console.log('buy AJAX done');
        // refreshBooks($('#root'));
        location.reload();
    }).fail(function () {
        console.log('ajax failed');
    });
};