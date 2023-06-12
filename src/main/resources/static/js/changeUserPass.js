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
            set_message_success('#success_send_password_reset',
                'Перейдите по ссылке, указанной в отправленном письме');
        },
        error: function (error) {
            set_message_error('#mistake_send_password_reset',
                'Пользователя с данной почтой не существует!')
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
            set_message_success('#success_reset_password',
                'Ваши данные были обновлены!');
        },
        error: function (error) {
            set_message_error('#mistake_reset_password',
                'Ошибка обновления данных.')
        }
    });
}