var stompClient = null;
function connect() {
    const socket = new SockJS('/ws');//http://localhost:8080
    stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/newRequest', function(msgput){
            console.log("Заебись! " + msgput);
        }//{    //messageOutput
            // showMessageOutput(JSON.parse(messageOutput.body));
        //}
        );
    });
}

function sendRequest() {
    stompClient.send("/app/requestsNumber");
    // stompClient.send("/app/requestsNumber", {}, {});    //JSON.stringify({'from':from, 'text':text})
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
        console.log("Разсоединение");
    }
}

// function showMessageOutput(messageOutput) {
//     var response = document.getElementById('response');
//     var p = document.createElement('p');
//     p.style.wordWrap = 'break-word';
//     p.appendChild(document.createTextNode(messageOutput.from + ": "
//         + messageOutput.text + " (" + messageOutput.time + ")"));
//     response.appendChild(p);
// }