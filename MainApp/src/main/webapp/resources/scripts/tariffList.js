const btnTariff = $("#btn-tariffs");
const tariffModal = document.getElementById('tariffModal');
const tariffSpan = document.getElementById('tariffSpan');
const tariffTable = $("#tariffTable");

$(function () {
    btnTariff.click(function () {
        tariffModal.style.display = "block";
        showAll(appendTable);
    });
});

function showAll(action) {
    tariffTable.empty();
    let currentUrl = contextPath + "/client/tariffs";
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

function handleError(result, action) {
    if (result.code == 200) {
        action(result.body);
    }
}

function appendTable(row) {
    let chosenTariffId = $("#chosenTariff").val();
    if (row.id == chosenTariffId) {
        tariffTable.append(
            "<tr>" +
            "<td>" + row.id + "</td>" +
            "<td>" + row.name + "</td>" +
            "<td>" + row.sms + "</td>" +
            "<td>" + row.minutes + "</td>" +
            "<td>" + row.internet + "</td>" +
            "<td>" + row.price + "</td>" +
            "<td>" +
            "<p> This tariff is yours!</p>" +
            "</td>" +
            "</tr>"
        );
    } else {
        tariffTable.append(
            "<tr>" +
            "<td>" + row.id + "</td>" +
            "<td>" + row.name + "</td>" +
            "<td>" + row.sms + "</td>" +
            "<td>" + row.minutes + "</td>" +
            "<td>" + row.internet + "</td>" +
            "<td>" + row.price + "</td>" +
            "<td>" +
            "<button id='chooseTariff_" + row.id + "' type='button'>Add to basket</button>" +
            "</td>" +
            "</tr>"
        );
    }

    $("#chooseTariff_" + row.id).click(function () {
        let curUrl = contextPath + "/client/tariffs";
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
    });
}

function buildBasket(data) {
    if(data.chosenTariff != null){
        $("#basketTBody").append(
            "<tr>" +
            "<td>" +
            "<button id='btnBasketRemoveTariff' type='button'>X</button>" +
            "</td>" +
            "<td id='chosenTariffName'>" + "<p>" + "<b>" + "Tariff: " + "</b>" +
            data.chosenTariff.name + "</p>" + "</td>" +
            "<td id='chosenTariffPrice'>" + data.chosenTariff.price + "</td>" +
            "<td>" + 0 + "</td>" +
            "</tr>"
        );
    }

    $.each(data.chosenAdditions, function (index, value) {
        $("#basketTBody").append(
            "<tr>" +
            "<td>" +
            "<button id='btnBasketRemoveOption_" + value.id + "' type = 'button'>X</button>" +
            "</td>" +
            "<td id='chosenOptionName'>" + value.name + "</td>" +
            "<td id='chosenOptionPrice'>" + value.price + "</td>" +
            "<td id='chosenOptionActCost'>" + value.additionActivationCost + "</td>" +
            "</tr>"
        );

        $("#btnBasketRemoveOption_" + value.id).click(function () {
            let curUrl = contextPath + "/client/removeOptionFromBasket";
            $
                .ajax({
                    type: "POST",
                    contentType: "application/json; charset=UTF-8",
                    url: curUrl,
                    data: JSON.stringify(value.id)
                })
                .done(function (data) {
                    $("#btnBasketRemoveOption_" + value.id).parents('tr').first().remove();
                    price.text(data.body.totalPrice + " ₽");
                    actCost.text(data.body.totalActivationCost + " ₽");
                    $("#basketTotal").text(data.body.totalSum + " ₽");
                    btnCheck();
                });
        })
    });


    $("#btnBasketRemoveTariff").click(function () {
        let curUrl = contextPath + "/client/removeTariffFromBasket";
        $.getJSON(curUrl, function (data) {
            clearTable(data);
            price.text(data.body.totalPrice + " ₽");
            actCost.text(data.body.totalActivationCost + " ₽");
            $("#basketTotal").text(data.body.totalSum + " ₽");
            btnCheck();
        })
    });
}

function clearTable(data) {
    if (data.body.chosenAdditions == null) {
        $("#basketTable").empty();
    } else {
        $("#btnBasketRemoveTariff").parents('tr').first().remove();
    }
}

tariffSpan.onclick = function () {
    tariffModal.style.display = "none";
};

window.onclick = function (event) {
    if (event.target == tariffModal) {
        tariffModal.style.display = "none";
    }
};