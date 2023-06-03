ws = new WebSocket("ws://" + location.host + "/ws"); //"ws://localhost:8080/ws"

ws.onopen = function () {
    console.log("Соединение по websocket произошло.");
}

//TODO Получаем сообщение от сервера
ws.onmessage = function (ev) {
    console.log(ev);
    action(ev.data);
}

ws.onerror = function (ev) {
}
ws.onclose = function (ev) {
}

function sendRequestToServer() {
    ws.send("Hi!");
    getUsersWhichWantToBeAAdminForTable();
}

function action(message) {
    console.log("зашли в action");
    let output = document.getElementById("request_number");
    if (message !== '0') {
        output.style.display = '';
        console.log("зашли в if");
        output.innerHTML = message;
        if (getUsersWhichWantToBeAAdmin().length !== message) {
            console.log("зашли во второй if");
            set_message_info('#update_table', 'Обновите таблицу.');
        }
        // setTimeout(function () {
        //     }
        // }, 1000);   //TODO Делаю задержку проверки на 1 сек.
    } else {
        console.log("зашли else");
        output.style.display = "none";
    }
}