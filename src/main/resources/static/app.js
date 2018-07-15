//value will be fetch via API
var timeToConfirmTransaction;
var floatTimeToConfirmTransaction;
var d = new Date();
var counter = 0;


window.onload = function () {
    $("#requoteBuyButton").hide();
    $("#requoteSellButton").hide();
    console.log("decision time: " + $("#main-content").attr("data-decisiontime"));
    timeToConfirmTransaction = parseInt($("#main-content").attr("data-decisiontime"));
    var stompClient = null;
    var appendedQuotation = document.getElementById("quotationRow");


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
        var socket = new SockJS('websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/user', function (greeting) {
                showGreeting(greeting.body);
                $("#quotationRow").hide();
                $("#quotationRow").fadeIn("1000");
            });
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }


    function showGreeting(quotation) {
        var json = JSON.parse(quotation);
        // var startTimer = setInterval(myTimer, 1000);
        console.log(json);
        console.log(json[0].time);
        appendedQuotation.innerHTML = "";
        for (var i = 0; i < json.length; i++) {
            appendedQuotation.innerHTML += "<tr><td>" + json[i].instrument.description
                + "</td><td>" + json[i].time
                + "</td><td><button type='button' class='btn btn-outline-primary' data-toggle='modal' data-target='#buyModal' onclick='startModalTimer()'> Buy: "
                + json[i].askPriceBucket.price
                + "</button></td><td><button type='button' class='btn btn-outline-primary'  data-toggle='modal' data-target='#sellModal'  onclick='startModalTimer()'> Sell: "
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

function startModalTimer() {
    modalTimer = setInterval(myTimer, 1000);
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

function requote() {
    stopInterval();
    startModalTimer();
}