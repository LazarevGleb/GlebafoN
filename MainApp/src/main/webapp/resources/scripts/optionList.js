const coTable = $("#conOptionsTable");
const avTable = $("#avOptionsTable");
const optModal = document.getElementById('optionModal');
const optSpan = document.getElementById('optionSpan');
const optBtn = $("#btn-options");

$(function () {
    optBtn.click(function () {
        optModal.style.display = "block";
        coTable.empty();
        showOptions(buildCOTable, contextPath + "/client/options");
        avTable.empty();
        showOptions(buildAVTable, contextPath + "/client/avOptions");
    })
});

function showOptions(action, currentUrl) {
    $
        .ajax({url: currentUrl})
        .done(function (result) {
            handleError(result, function (result) {
                for (let i = 0; i < result.length; i++) {
                    action(result[i]);
                }
            })
        })
}

function buildAVTable(row) {
    avTable.append(
        "<tr>" +
        "<td>" + row.id + "</td>" +
        "<td>" + row.name + "</td>" +
        "<td>" + row.parameter + "</td>" +
        "<td>" + row.value + "</td>" +
        "<td>" + row.price + "</td>" +
        "<td>" + row.additionActivationCost + "</td>" +
        "<td id='tdReq_" + row.id + "'>" + "</td>" +
        "<td>" +
        "<button id='chooseOption_" + row.id + "' type='button'>+</button>" +
        "</td>" +
        "</tr>"
    );

    $.each(row.mandatoryOptions, function (index, value) {
        let additionField = $("#contractAdditions");
        if(additionField.val().indexOf(value) < 0){//if contract does not have mandatory options
            console.log(true);
            let curBtn = $("#chooseOption_" + row.id);
            curBtn.prop("disabled", true);
            curBtn.attr("dataToggle", "tooltip");
            curBtn.attr("title", "Please connect requirements!");

        }
        $("#tdReq_" + row.id).append(
            value + "<br>"
        );
    });

    $("#chooseOption_" + row.id).click(function () {
        let curUrl = contextPath + "/client/chooseOption";
        $
            .ajax({
                type: "POST",
                contentType: "application/json; charset=UTF-8",
                url: curUrl,
                data: JSON.stringify(row.id)
            })
            .done(function (result) {
                $("#basketTBody").empty();
                buildBasket(result.body);
                document.getElementById('basketModal').style.display = "block";
                price.text(result.body.totalPrice + " ₽");
                actCost.text(result.body.totalActivationCost + " ₽");
                $("#basketTotal").text(result.body.totalSum + " ₽");
                btnCheck();
            });
    })
}

function buildCOTable(row) {
    coTable.append(
        "<tr>" +
        "<td>" + row.name + "</td>" +
        "<td>" +
        "<button id='removeOption_" + row.id + "' type='button'>X</button>" +
        "</td>" +
        "</tr>"
    );

    $("#removeOption_" + row.id).click(function () {
        let curUrl = contextPath + "/client/options";
        $
            .ajax({
                type: "POST",
                contentType: "application/json; charset=UTF-8",
                url: curUrl,
                data: JSON.stringify(row.id)
            })
            .done(function (result) {
                $("#doTableTBody").empty();
                $.each(result.body.deletedAdditions, function (index, value) {
                    buildDOTAble(value);
                });
                document.getElementById('basketModal').style.display = "block";
                btnCheck();
            });
    });
}

function buildDOTAble(data) {
    $("#doTableTBody").append(
        "<tr>" +
        "<td>" +
        "<button id='btnBasketRemoveOption_" + data.id + "' type = 'button'> X </button>" +
        "</td>" +
        "<td id='deletedOptionName'>" + data.name + "</td>" +
        "</tr>"
    );

    $("#btnBasketRemoveOption_" + data.id).click(function () {
        let curUrl = contextPath + "/client/removeOptionFromBasket";
        $
            .ajax({
                type: "POST",
                contentType: "application/json; charset=UTF-8",
                url: curUrl,
                data: JSON.stringify(data.id)
            })
            .done(function (result) {
                $("#btnBasketRemoveOption_" + data.id).parents('tr').first().remove();
                price.text(result.body.totalPrice + " ₽");
                actCost.text(result.body.totalActivationCost + " ₽");
                $("#basketTotal").text(result.body.totalSum + " ₽");
            });
    })
}

optSpan.onclick = function () {
    optModal.style.display = "none";
};

window.onclick = function (event) {
    if (event.target == optModal) {
        optModal.style.display = "none";
    }

    if (event.target == balanceModal) {
        balanceModal.style.display = "none";
    }

    if (event.target == tariffModal) {
        tariffModal.style.display = "none";
    }

    if (event.target == basket) {
        basket.style.display = "none";
    }
};