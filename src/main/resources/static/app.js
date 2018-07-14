window.onload = function () {
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
        console.log(json);
        console.log(json[0].time);
        appendedQuotation.innerHTML = "";
        for (var i = 0; i < json.length; i++) {
            appendedQuotation.innerHTML += "<tr><td>" + json[i].instrument.description
                + "</td><td>" + json[i].time + "</td><td><button type='button' class='btn btn-outline-primary'> Buy: " + json[i].askPriceBucket.price
            +"</button></td><td><button type='button' class='btn btn-outline-primary'> Sell: "+ json[i].bidPriceBucket.price + "</button></td></tr>";
        }
    }

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