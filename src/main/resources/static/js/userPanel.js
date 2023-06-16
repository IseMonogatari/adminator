// TODO Показываем все данные зарегистрированного пользователя
function tableAuthorizedUser() {
    let user = showAuthorizedUser();
    console.log(user);

    let table =
        "<tr>" +
        "   <td>" + "<img src=" + user.duckUrl + " width=\"120\" height=\"120\" alt=\"duck\">" + "</td>" +
        "   <td>" + user.id + "</td>" +
        "   <td>" + user.lastName + "</td>" +
        "   <td>" + user.name + "</td>" +
        "   <td>" + user.email + "</td>" +
        "   <td>" + user.age + "</td>" +
        "   <td>" + user.role + "</td>" +
        "</tr>";
    $("#oneUserTableBody").html(table);
}