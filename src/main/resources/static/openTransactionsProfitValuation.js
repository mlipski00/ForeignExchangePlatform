var d = new Date();

var buyModalArgument = []
var sellModalArgument = []

window.onload = function () {
    var stompClient = null;
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
        for (var i = 0; i < json.length; i++) {
            buyModalArgument[i] = '\"' + json[i].instrument.description + '\"' + ", " +  json[i].askPriceBucket.price;
            sellModalArgument[i] = '\"' + json[i].instrument.description + '\"' + ", " + json[i].bidPriceBucket.price;
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
