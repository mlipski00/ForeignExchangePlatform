function getHostUrl() {
    const HOST_URL = "http://localhost:8080"
    return HOST_URL;
}
function buyTrade() {
    const HOST_URL = getHostUrl();
    stopInterval();
    console.log('processing buy trade');
    var buyTrade = {};


    buyTrade.instrument = document.getElementById("buyModalInstrument").innerHTML;
    buyTrade.price = document.getElementById("buyModalPrice").innerHTML;
    buyTrade.amount = document.getElementById("buyAmount").value;

    $.ajax({
        url: HOST_URL + "/transaction/buy",
        type: "POST",
        headers: {
            'Accept': 'application/json',
            "Content-Type": "application/json"
        },
        data: JSON.stringify(buyTrade)
    }).done(function (data) {
        console.log('buy AJAX done' + data.toString());
        var transaction = jQuery.parseJSON(JSON.stringify(data));
        console.log(transaction);
        $("#tradeResultModal").modal();
        if (transaction.executed === true) {
            document.getElementById("tradeResultTRX").innerHTML =
                "<p>Date time: " + transaction.tradeDateTime + "</p>"
                + "<p>Trade side: " + transaction.buySell + "</p>"
                + "<p>instrument: " + transaction.instrument + "</p>"
                + "<p>Amount: " + transaction.amount + "</p>"
                + "<p>Amount in PLN: " + transaction.amountPLN + "</p>"
                + "<p>Trade price: " + transaction.price + "</p>";
        } else {
            document.getElementById("tradeResultTRX").innerHTML = "Transaction not executed. Reason: " + transaction.executionFailReason;
        }
    }).fail(function () {
        console.log('ajax failed');
    });
};

function sellTrade() {
    const HOST_URL = getHostUrl();
    stopInterval();
    console.log('processing sell trade');
    var sellTrade = {};


    sellTrade.instrument = document.getElementById("sellModalInstrument").innerHTML;
    sellTrade.price = document.getElementById("sellModalPrice").innerHTML;
    sellTrade.amount = document.getElementById("sellAmount").value;

    $.ajax({
        url: HOST_URL + "/transaction/sell",
        type: "POST",
        headers: {
            'Accept': 'application/json',
            "Content-Type": "application/json"
        },
        data: JSON.stringify(sellTrade)
    }).done(function (data) {
        console.log('sell AJAX done' + data.toString());
        var transaction = jQuery.parseJSON(JSON.stringify(data));
        console.log(transaction);
        $("#tradeResultModal").modal();
        if (transaction.executed === true) {
            document.getElementById("tradeResultTRX").innerHTML =
                "<p>Date time: " + transaction.tradeDateTime + "</p>"
                + "<p>Trade side: " + transaction.buySell + "</p>"
                + "<p>instrument: " + transaction.instrument + "</p>"
                + "<p>Amount: " + transaction.amount + "</p>"
                + "<p>Amount in PLN: " + transaction.amountPLN + "</p>"
                + "<p>Trade price: " + transaction.price + "</p>";
        } else {
            document.getElementById("tradeResultTRX").innerHTML = "Transaction not executed. Reason: " + transaction.executionFailReason;
        }
    }).fail(function () {
        console.log('ajax failed');
    });
};

function reloadPage() {
    location.reload();
}