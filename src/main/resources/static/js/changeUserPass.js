function sendEmailForChangePassword() {
    $.ajax({
        url: '/reset_passwords',
        type: 'POST',
        cache: false,
        async: false,
        contentType: 'application/json',
        data: JSON.stringify({
            email: $("#email_password_reset").val()
        }),
        success: function(result) {
            console.log("Выполнелась херня");
            console.log($("#email_password_reset").val());
        },
        error: function (error) {
            console.log("какая-то ошибка обновления" + error);
        }
    });
}

function changeUserPassword() {
    $.ajax({
        url: '/reset_passwords',
        type: 'PUT',
        cache: false,
        async: false,
        contentType: 'application/json',
        data: JSON.stringify({
            email: $("#email_reset").val(),
            password: $("#password_reset").val()
        }),
        success: function(result) {
            console.log(result);
        },
        error: function (error) {
            console.log("какая-то ошибка сохранения нового пароля\n" + error);
        }
    });
}