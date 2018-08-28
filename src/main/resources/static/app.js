var timeToConfirmTransaction;
var floatTimeToConfirmTransaction;
var d = new Date();
var counter = 0;

var buyModalArgument = []
var sellModalArgument = []

var decisionTime;
var minimumTradeAmount;
var maximumTradeAmount;

window.onload = function () {
    checkNewMessages();
    fetch();
    $("#requoteBuyButton").hide();
    $("#requoteSellButton").hide();
    console.log("decision time: " + $("#main-content").attr("data-decisiontime"));
    timeToConfirmTransaction = parseInt($("#main-content").attr("data-decisiontime"));
    var stompClient = null;
    var appendedQuotation = document.getElementById("quotationRow");
    connect();

    function setConnected(connected) {
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) {
            $("#conversation").show();
        }
        else {
            $("#conversation").hide();
        }
        $("#userinfo").html("");
    }

    function connect() {
        document.getElementById("pricingConnectionStatus").innerText = "Pricing connection status: connecting...";
        var socket = new SockJS('websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/user', function (greeting) {
                showGreeting(greeting.body);
                $("#quotationRow").hide();
                $("#quotationRow").fadeIn("1000");
                document.getElementById("pricingConnectionStatus").innerText = "Pricing connection status: connected";
            });
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
        document.getElementById("pricingConnectionStatus").innerText = "Pricing connection status: disconnected";
    }


    function showGreeting(quotation) {
        valuateProfits();
        var json = JSON.parse(quotation);
        console.log(json);
        console.log(json[0].time);
        appendedQuotation.innerHTML = "";
        for (var i = 0; i < json.length; i++) {
            buyModalArgument[i] = '\"' + json[i].instrument.description + '\"' + ", " +  json[i].askPriceBucket.price;
            sellModalArgument[i] = '\"' + json[i].instrument.description + '\"' + ", " + json[i].bidPriceBucket.price;
            appendedQuotation.innerHTML += "<tr><td>" + json[i].instrument.description
                + "</td><td>" + json[i].time
                + "</td><td><button type='button' class='btn btn-outline-primary' data-toggle='modal' data-target='#buyModal' onClick='startModalTimer("+ buyModalArgument[i] + ")'> Buy: "
                + json[i].askPriceBucket.price
                + "</button></td><td><button type='button' class='btn btn-outline-primary'  data-toggle='modal' data-target='#sellModal'  onClick='startModalTimer("+ sellModalArgument[i] + ")'> Sell: "
                + json[i].bidPriceBucket.price + "</button></td></tr>";
        }
    }



    //$('#btn btn-outline-primary').click(function(){$('#buyModal').modal('show');})
    $(function () {
        $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $("#connect").click(function () {
            connect();
        });
        $("#disconnect").click(function () {
            disconnect();
        });
    });
}
var modalTimer;

function startModalTimer(instrument, price) {
    modalTimer = setInterval(myTimer, 1000);
    document.getElementById("buyModalPrice").innerHTML = price;
    document.getElementById("buyModalInstrument").innerHTML = instrument;
    document.getElementById("sellModalPrice").innerHTML = price;
    document.getElementById("sellModalInstrument").innerHTML = instrument;
}

function myTimer() {
    floatTimeToConfirmTransaction = timeToConfirmTransaction - counter;
    console.log(d.toLocaleTimeString());
    var modalTimers = document.querySelectorAll(".modalTimer");
    modalTimers.forEach(function(element) {
        if (floatTimeToConfirmTransaction <= 0 ) {
            element.innerHTML = "Please requote"
            $("#buyConfirmation").hide();
            $("#sellConfirmation").hide();
            $("#requoteBuyButton").show();
            $("#requoteSellButton").show();
        } else {
            element.innerHTML = "Time to confirm transaction: " + floatTimeToConfirmTransaction;
        }
    });
    counter = counter + 1;
}

function stopInterval() {
    clearInterval(modalTimer);
    floatTimeToConfirmTransaction = timeToConfirmTransaction;
    counter = 0;

    $("#buyConfirmation").show();
    $("#sellConfirmation").show();
    $("#requoteBuyButton").hide();
    $("#requoteSellButton").hide();


}

function buyRequote() {
    stopInterval();
    for (i = 0; i < buyModalArgument.length; i++) {
        if(document.getElementById("buyModalInstrument").innerHTML.toString() === buyModalArgument[i].substr(1,7).toString()){
            var buyArgumentToSplit = buyModalArgument[i].split(",");
            startModalTimer(buyArgumentToSplit[0].substr(1,7), buyArgumentToSplit[1]);
        }
    }

}

function sellRequote() {
    stopInterval();
    for (i = 0; i < sellModalArgument.length; i++) {
        console.log("sell modal: " + document.getElementById("sellModalInstrument").innerHTML.toString())
        console.log(sellModalArgument[i].substr(1,7).toString())
        if(document.getElementById("sellModalInstrument").innerHTML.toString() === sellModalArgument[i].substr(1,7).toString()){
            var sellArgumentToSplit = sellModalArgument[i].split(",");
            console.log("first part of split: " + sellArgumentToSplit[0]);
            startModalTimer(sellArgumentToSplit[0].substr(1,7), sellArgumentToSplit[1]);
        }
    }

}

function buyTradeWithValidation() {
    if (document.getElementById("buyAmount").value < minimumTradeAmount) {
        alert("Minimal amount is " + minimumTradeAmount);
        stopInterval();
        return false;
    }
    if (document.getElementById("buyAmount").value > maximumTradeAmount) {
        alert("Maximal amount is " + maximumTradeAmount);
        stopInterval();
        return false;
    }
    buyTrade();
}

function sellTradeWithValidation() {
    if (document.getElementById("sellAmount").value < minimumTradeAmount) {
        alert("Minimal amount is " + minimumTradeAmount);
        stopInterval();
        return false;
    }
    if (document.getElementById("sellAmount").value > maximumTradeAmount) {
        alert("Maximal amount is " + maximumTradeAmount);
        stopInterval();
        return false;
    }
    sellTrade();
}

function fetch() {
}
$.ajax({
    url: "http://localhost:8080/platformsettings/allsettings",
    type: "GET"
}).done(function(data) {
    decisionTime = data.decisionTime;
    minimumTradeAmount = data.minimumTradeAmount;
    maximumTradeAmount = data.maximumTradeAmount;
})

function checkNewMessages() {
    $.ajax({
        url: "http://localhost:8080/checkNewMessages",
        type: "GET"
    }).done(function(data) {
        if (data.length > 0) {
            var messages = jQuery.parseJSON(JSON.stringify(data))
            $('#newMessageModal').modal('show');
            document.getElementById("messageModalSender").innerText = "From: " + messages[0].sender.username
            document.getElementById("messageModalLinkToMessage").innerHTML = "<a href=http://localhost:8080/messages/Inbox/" + messages[0].id + " class='btn btn-primary'>Open Message</a>";
        }
    })

}