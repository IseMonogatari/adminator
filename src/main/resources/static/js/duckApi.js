function getDuck() {
    let ppic;
    $.ajax({
        url: '/ducks',
        type: 'GET',
        cache: false,
        async: false,
        contentType: 'image/jpeg',
        success: function (data) {
            // alert(data);

            // const img = document.createElement('img');
            // img.src = data;
            // document.getElementById('image-container').append(img);
            // $("#myDuck").append(img);
            // 'editModalById(" + data[i].id + ")'
            // '<img src=" + data + " class="img-thumbnail">'
            ppic = "<img src=" + data + " width=\"120\" height=\"120\" alt=\"duck\">";
            // let ppic = "<img src=" + data + " class=\"img-thumbnail\">";
            // $("#myDuck").append(ppic);
        },
        error: function (error) {
            console.log("Ошибка в получении URL " + error);
        }
    });
    return ppic;
}

function getAllDucks() {
    let ducks;
    $.ajax({
        url: '/ducks/all',
        type: 'GET',
        cache: false,
        async: false,
        contentType: 'application/json',
        success: function (data) {
            alert(data);

            const img = document.createElement('img');
            img.src = data;
            // document.getElementById('image-container').append(img);
            $("#myDuck").append(img);
            // ducks = data.
        },
        error: function (error) {
            console.log("Ошибка в получении URL " + error);
        }
    });
    return ducks;
}