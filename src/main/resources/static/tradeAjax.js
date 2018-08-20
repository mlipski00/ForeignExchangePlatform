function buyTrade() {
    stopInterval();
    console.log('processing buy trade');
    var buyTrade = {};


    buyTrade.instrument = document.getElementById("buyModalInstrument").innerHTML;
    buyTrade.price = document.getElementById("buyModalPrice").innerHTML;
    buyTrade.amount = document.getElementById("buyAmount").value;

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
        location.reload();
    }).fail(function () {
        console.log('ajax failed');
    });
};

function sellTrade() {
    stopInterval();
    console.log('processing buy trade');
    var sellTrade = {};


    sellTrade.instrument = document.getElementById("sellModalInstrument").innerHTML;
    sellTrade.price = document.getElementById("sellModalPrice").innerHTML;
    sellTrade.amount = document.getElementById("sellAmount").value;

    $.ajax({
        url: "http://localhost:8080/transaction/sell",
        type: "POST",
        headers: {
            'Accept': 'application/json',
            "Content-Type": "application/json"
        },
        data: JSON.stringify(sellTrade)
    }).done(function () {
        console.log('buy AJAX done');
        location.reload();
    }).fail(function () {
        console.log('ajax failed');
    });
};