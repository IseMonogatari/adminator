// TODO функция добавления нового пользователя
function appendUser() {
    $.ajax({
        url: '/users',
        type: 'POST',
        cache: false,
        async: false,
        contentType: 'application/json',
        data: JSON.stringify({
            lastName: $("#lastName_append").val(),
            name: $("#name_append").val(),
            email: $("#email_append").val(),
            birthday: $("#birthday_append").val(),
            password: $("#password_append").val(),
            role: $("#role_append").val()
        }),
        success: function(result) {
            console.log(result);
            set_message('Вы успешно зарегистрированы!');
            showAllUsers();
        },
        error: function (error) {
            console.log("какая-то ошибка добавления пользователя" + error);
            set_message_error('#mistake_append',
                'Данный пользователь, вероятно, уже существует. Зарегестрируйтесь под новым именем.' +
                '\nЕсли же ранее этот пользователь уже был зарегестрирован с роль \"ПОЛЬЗОВАТЕЛЬ\", ' +
                'то он не может иметь роль \"АДМИНИСТРАТОР\"!')
        }
    });
    //Удаляем введённый текст после добавления пользователя
    $("#lastName_append").val('');
    $("#name_append").val('');
    $("#email_append").val('');
    $("#birthday_append").val('');
    $("#password_append").val('');
}

// TODO Выводит сообщение об успешном добавлении пользователя
function set_message(text) {
    let $item = $('<div  class="alert alert-success">' + text + '</div>');
    $item.appendTo($('#success_append')).delay(3000).slideUp(200, function(){
        $item.remove();
    });
}

// TODO Выводит сообщение об неудачном добавлении пользователя
function set_message_error(id_div, text) {
    let $item = $('<div  class="alert alert-danger">' + text + '</div>');
    $item.appendTo($(id_div)).delay(10000).slideUp(200, function(){
        $item.remove();
    });
}
// TODO Выводит сообщение "НЕ ПРОВЕРИЛИ ТЕБЯ ,ГАД"
function set_message_info(id_div, text) {
    let $item = $('<div  class="alert alert-info">' + text + '</div>');
    $item.appendTo($(id_div)).delay(3000).slideUp(200, function(){
        $item.remove();
    });
}

// TODO Выводит сообщение об успешном добавлении пользователя
function set_message_success(id_div, text) {
    let $item = $('<div  class="alert alert-success">' + text + '</div>');
    $item.appendTo($(id_div)).delay(5000).slideUp(200, function(){
        $item.remove();
    });
}