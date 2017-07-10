var client_subscribe_vm_items = null;
var client_subscribe_host_items = null;
var client_subscribe_switch_items = null;
var client_subscribe_env_items = null;
var client_subscribe_water_items = null;

var websocket_clients=[];

var websockt_url_root="ws://localhost/api/async-websocket"

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

function connect(url) {
    url =websockt_url_root+url;
   var socket=null;
    if ('WebSocket' in window) {
        socket = new WebSocket(url);
    } else if ('MozWebSocket' in window) {
        socket = new MozWebSocket(url);
    } else {
        console.log(url+'Error: WebSocket is not supported by this browser.');
        return;
    }
     socket.onopen = function () {
        console.log(url+'Info: WebSocket connection opened.');
         websocket_clients.push(socket);
         if(websocket_clients.length==5){setConnected(true)}

    };
      socket.onclose = function () {
          console.log(url+'Info: WebSocket closed.');
          var index = websocket_clients.indexOf(socket);
          websocket_clients.splice(index, 1);
    };
      socket.onmessage = function (message) {
          showGreeting(JSON.stringify(message.data));
    };

}

function disconnect() {
    if (websocket_clients != null) {
        websocket_clients.forEach(function (socket,i) {
            socket.close() ;
        })
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendmessage() {
    if (websocket_clients != null) {
        websocket_clients.forEach(function (socket,i) {
            socket.send(JSON.stringify({'name': $("#name").val()}));
        })
    }

}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() {
        connect("/subscribe_host_items");
        connect("/subscribe_vm_items");
        connect("/subscribe_switch_items");
        connect("/subscribe_water_items");
        connect("/subscribe_env_items");
    });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendmessage();});
});

