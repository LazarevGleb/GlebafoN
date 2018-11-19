const btnBasket = $("#btn-basket");
const basket = document.getElementById('basketModal');
const basketSpan = document.getElementById('basketSpan');
const price = $("#basketPrice");
const actCost = $("#basketActCost");
const total = document.getElementById('basketTotal');
const buyBtn = $("#buyButton");
const buyStatus = $("#basketStatus");


$(function () {
    btnBasket.click(function () {
        basket.style.display = "block";
    });
    btnCheck();
    buyBtn.click(function () {
        let curUrl = contextPath + "/client/submitBasket";
        location.href = curUrl;
    })
});

function btnCheck() {

    let money = parseInt(document.getElementById('balanceField').textContent);
    let moneyTotal = parseInt(total.textContent);
    if (isEmptyBasket()) {
        buyBtn.prop("disabled", true);
        buyStatus.prop("textContent", "");
    }
    else if (moneyTotal > money) {
        buyBtn.prop("disabled", true);
        buyStatus.prop("textContent", "Please top up your balance");
        buyStatus.css("color", "red");
    }
    else {
        buyBtn.prop("disabled", false);
        buyStatus.prop("textContent", "");
    }
}

function isEmptyBasket() {
    return $("#basketTBody").children().length == 0 &&
        $("#doTableTBody").children().length == 0;
}

basketSpan.onclick = function () {
    basket.style.display = "none";
};

