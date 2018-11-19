const contextPath = "http://127.0.0.1:8080/MainApp";

$(function () {
    let block = $("#block").val();
    disableButtons(block);

    $("#btn-block").click(function () {
        $.getJSON(contextPath + "/client/blockSelf", function (data) {
            let lock = data.body.block;
            disableButtons(lock);
        })
    });
});


function disableButtons(lock) {
    if (lock == "CLIENT_BLOCKED") {
        $("#btn-block").prop("textContent", "Unblock number");
        $("#btn-tariffs").prop("disabled", true);
        $("#btn-options").prop("disabled", true);
        $("#blockStatus").prop("textContent", "Your number is blocked by yourself");
    }
    else if (lock == "UNBLOCKED") {
        $("#btn-block").prop("textContent", "Block number");
        $("#btn-tariffs").prop("disabled", false);
        $("#btn-options").prop("disabled", false);
        $("#blockStatus").prop("textContent", "");
    }
    if (lock == "MANAGER_BLOCKED") {
        $("#btn-block").css("display", "none");
        $("#btn-tariffs").prop("disabled", true);
        $("#btn-options").prop("disabled", true);
        $("#blockStatus").html( "Your number is blocked by manager!" + "<br>" +
            "You cannot unblock it yourself :(");
    }
}
