const relModal = document.getElementById('optionsWindow');
const ospan = document.getElementById('optionSpan');
const tBody = $("#optionTBody");
const relationBtn = $("#relBtn");
const context = "http://127.0.0.1:8080/MainApp";
const btnDiv = document.getElementById('buttonDiv');
const initId = $("#optionId").val();
const nameVal = $("#nameIn");
const parameterVal = $("#parameterIn");
const valueVal = $("#valueIn");
const priceVal = $("#priceIn");
const actVal = $("#activationIn");
const addButton = $("#addBtn");

let mandStr = "";
let incomStr = "";

$(function () {
    if (initId != 0) {
        fillUpdateForm(initId);
        addButton.css("value", "Update");
        addButton.innerText = "Update";
    }
    else {
        fillNewForm();
    }

    relationBtn.click(function () {
        relModal.style.display = "block";
        showStatusTable(buildTBody);
    });

    addButton.click(function () {
        let reqName = nameVal.val();
        let reqId = initId;
        let reqParameter = parameterVal.val();
        let reqValue = valueVal.val();
        let reqPrice = priceVal.val();
        let reqActiCost = actVal.val();
        if (checkInput(reqValue)) {
            if (checkInput(reqPrice)) {
                if (checkInput(reqActiCost)) {
                    let reqTariffs = $("#tariffSelector").val() + "," + $("#inTariffTd").text();
                    let request = {
                        "id": reqId, "name": reqName, "parameter": reqParameter, "value": reqValue,
                        "price": reqPrice, "additionActivationCost": reqActiCost, "tariffs": reqTariffs,
                        "mandatory": mandStr, "incompatible": incomStr
                    };
                    let reqUrl = context + "/employee/submitRelations";
                    $
                        .ajax({
                            type: "POST",
                            contentType: "application/json; charset=UTF-8",
                            url: reqUrl,
                            data: JSON.stringify(request)
                        })
                        .done(function () {
                            location.href = context + "/employee/additions";
                        })
                } else {
                    $("#costStatus").prop("disabled", false);
                    addButton.prop("disabled", "disabled");
                }
            } else {
                $("#priceStatus").prop("disabled", false);
                addButton.prop("disabled", "disabled");
            }
        } else {
            addButton.prop("disabled", "disabled");
            $("#valueStatus").prop("disabled", false);
        }
    });
});

function checkInput(str) {

    let reg = new RegExp('^[0-9]+$');
    return reg.exec(str);
}

function fillNewForm() {
    let inTh = $("#incomTh");
    inTh.remove();
    let startUrl = context + "/employee/getTariffs";
    $
        .ajax({url: startUrl})
        .done(function (result) {
            drawNewChooseTariffTable(result.body);
        })
}

function drawNewChooseTariffTable(data) {
    $.each(data, function (index, dataVal) {
        $("#tariffSelector").append(
            "<option id='opt_" + dataVal.id + "' value='" + dataVal.name + "(" +
            dataVal.description + ")" + "'>" + dataVal.name + " (" + dataVal.description + ")" +
            "</option>"
        )
    });
}

function fillUpdateForm(initId) {
    let startUrl = context + "/employee/optionForm";
    $
        .ajax({
            type: "POST",
            contentType: "application/json; charset=UTF-8",
            url: startUrl,
            data: JSON.stringify(initId)
        })
        .done(function (result) {
            nameVal.val(result.body.name);
            parameterVal.val(result.body.parameter);
            valueVal.val(result.body.value);
            priceVal.val(result.body.price);
            actVal.val(result.body.additionActivationCost);
            drawTariffTable(result.body.packages);
            drawChooseTariffTable(result.body.remainedTariffs);
        })
}

function drawChooseTariffTable(data) {
    $.each(data, function (index, dataVal) {
        $("#tariffSelector").append(
            "<option id='opt_" + index + "' value='" + dataVal + "'>" + dataVal + "</option>"
        )
    });
}

function drawTariffTable(data) {
    $.each(data, function (index, value) {
        $("#inTariffTd").append(
            value + "<button id='removeTarBtn_" + index + "'>X</button><br>"
        );
        $("#removeTarBtn_" + index).click(function () {
            let curUrl = context + "/employee/removeIncompatibleTariff";
            let req = initId + "#" + value;
            $
                .ajax({
                    type: "POST",
                    contentType: "application/json; charset=UTF-8",
                    url: curUrl,
                    data: JSON.stringify(req)
                })
                .done(function (result) {
                    $("#inTariffTd").empty();
                    fillUpdateForm(result.body.id);
                })
        })
    })
}

function buildTBody(row) {
    tBody.append(
        "<tr>" +
        "<td>" + row.name + "</td>" +
        "<td><select id='select_" + row.id + "'><option id='opt1' value='NONE'>NONE</option>" +
        "<option id='opt2' value='MANDATORY'>MANDATORY</option>" +
        "<option id='opt3' value='INCOMPATIBLE'>INCOMPATIBLE</option></select></td>" +
        "</tr>"
    );
}

function writeStatus(data) {
    if (data.toString().length == 0) {
        $("#checkStatus").prop("textContent", "Some of mandatory options are incompatible to each other");
        $("#checkStatus").css("color", "red");
        $("#applyButton").prop("disabled", "disabled");
        addButton.prop("disabled", "disabled");
    }
    else {
        $("#checkStatus").prop("textContent", "Check passed!");
        $("#checkStatus").css("color", "green");
        $("#applyButton").prop("disabled", false);
        addButton.prop("disabled", false);
    }
    let apBtn = document.getElementById('applyButton');
    apBtn.onclick = function () {
        relModal.style.display = "none";
        $.each(data, function (index, val) {
            if (val.status == "MANDATORY") {
                mandStr += val.id + ";";
            } else if (val.status == "INCOMPATIBLE") {
                incomStr += val.id + ";";
            }
        });
    }
}

function showStatusTable(action) {
    if (mandStr.length == 0 && incomStr.length == 0) {
        tBody.empty();
        while (btnDiv.firstChild) {
            btnDiv.removeChild(btnDiv.firstChild);
        }
        let curUrl = context + "/employee/getAddStatusTable";
        $
            .ajax({
                type: "POST",
                contentType: "application/json; charset=UTF-8",
                url: curUrl,
                data: JSON.stringify(initId)
            })
            .done(function (result) {
                btnDiv.innerHTML += "<button id='checkButton'>Check</button>" +
                    "<button id='applyButton' disabled='disabled'>Apply</button>";
                $("#checkStatus").prop("textContent", "");
                let chBtn = document.getElementById('checkButton');
                let applyUrl = context + "/employee/checkRelations";
                let str = "";
                chBtn.onclick = function () {
                    incomStr = "";
                    mandStr = "";
                    str = "";
                    $.each(result.body, function (index, value) {
                        str += value.id + "#" + $("#select_" + value.id + " option:selected").text() + ";";
                    });
                    str += initId;
                    $
                        .ajax({
                            type: "POST",
                            contentType: "application/json; charset=UTF-8",
                            url: applyUrl,
                            data: JSON.stringify(str)
                        })
                        .done(function (result) {
                            writeStatus(result.body);
                        })
                };
                $.each(result.body, function (index, value) {
                    action(value);
                });
            });
    }
    else {
        $("#applyButton").prop("disabled", "disabled");
        $("#checkStatus").prop("textContent", "");
        $("#addBtn").prop("disabled", "disabled");
    }

}

ospan.onclick = function () {
    relModal.style.display = "none";
};

window.onclick = function (event) {
    if (event.target == relModal) {
        relModal.style.display = "none";
    }
};
