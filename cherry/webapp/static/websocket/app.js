var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/api/async-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/subscribe_vm_items', function (greeting) {
            showGreeting(JSON.stringify(greeting));
        });
        stompClient.subscribe('/topic/subscribe_host_items', function (greeting) {
            showGreeting(JSON.stringify(greeting));
        });
        stompClient.subscribe('/topic/subscribe_switch_items', function (greeting) {
            showGreeting(JSON.stringify(greeting));
        });
        stompClient.subscribe('/topic/subscribe_env_items', function (greeting) {
            showGreeting(JSON.stringify(greeting));
        });
        stompClient.subscribe('/topic/subscribe_water_items', function (greeting) {
            showGreeting(JSON.stringify(greeting));
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function send_host_items() {
    stompClient.send("/app/send_host_items", {}, JSON.stringify({'name': $("#name").val()}));
}
function send_env_items() {
    stompClient.send("/app/send_env_items", {}, JSON.stringify({'name': $("#name").val()}));
}
function send_vm_items() {
    stompClient.send("/app/send_vm_items", {}, JSON.stringify({'name': $("#name").val()}));
}
function send_switch_items() {
    stompClient.send("/app/send_switch_items", {}, JSON.stringify({'name': $("#name").val()}));
}
function send_water_items() {
    stompClient.send("/app/send_water_items", {}, JSON.stringify({'name': $("#name").val()}));
}


function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { send_host_items(); send_vm_items();send_switch_items();send_env_items();send_water_items();});
});

