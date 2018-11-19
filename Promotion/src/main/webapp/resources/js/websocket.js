const promoTable = $("#promoTable");
const output = document.getElementById("output");
const contextPath = "http://127.0.0.1:8080/MainApp";
const wsUri = "ws://" + document.location.host + document.location.pathname + "socket";
const socket = new WebSocket(wsUri);

const optModal = document.getElementById('optionsModal');
const optTBody = $("#optionsTBody");
const optSpan = document.getElementById('optionsSpan');

socket.onerror = function (evt) {
    onError(evt);
};

$(function () {
    showAll(appendTable);
});

socket.onmessage = onMessage;

function onMessage() {
    console.log("Inside onMessage");
    showAll(appendTable);
}

function appendTable(row) {
    console.log("Inside is appendTable method");
    promoTable.append(
        "<tr>" +
        "<td>" + row.name + "</td>" +
        "<td>" + row.sms + "</td>" +
        "<td>" + row.minutes + "</td>" +
        "<td>" + row.internet + "</td>" +
        "<td>" + row.price + "</td>" +
        "<td>" +
        "<p id='optionBtn_" + row.id + "'>" + "<u>" + "Options" + "</u>" + "</p>" +
        "</td>" +
        "</tr>"
    )
    ;
    $("#optionBtn_" + row.id).click(function () {
        optTBody.empty();
        showOptions(row.additions);
        optModal.style.display = "block";
    })
}

function showOptions(data) {
    $.each(data, function (index, value) {
        optTBody.append(
            "<tr>" +
            "<td>" + value.name + "</td>" +
            "<td>" + value.price + " ₽" + "</td>" +
            "<td>" + value.additionActivationCost + " ₽" + "</td>" +
            "</tr>"
        )
    })

}

function showAll(action) {
    clearPromo();
    let curUrl = contextPath + "/tariffs";
    $
        .ajax({url: curUrl})
        .done(function (result) {
            handleError(result, function (result) {
                for (let i = 0; i < result.length; i++) {
                    action(result[i]);
                }
            })
        })
}

function clearPromo() {
    promoTable.empty();
}

function handleError(result, action) {
    if (result.code == 200) {
        action(result.body);
    } else {
        onError(result.body);
    }
}

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data +
        "<br>" + "on URI: " + wsUri);
}

socket.onopen = function () {
    onOpen();
};

function writeToScreen(message) {
    output.innerHTML += message + "<br>";
}

function onOpen() {
    writeToScreen("Connected to " + wsUri);
}

optSpan.onclick = function () {
    optModal.style.display = "none";
};

window.onclick = function (event) {
    if (event.target == optModal) {
        optModal.style.display = "none";
    }
};