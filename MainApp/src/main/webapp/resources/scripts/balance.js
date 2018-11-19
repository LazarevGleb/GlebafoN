const balanceModal = document.getElementById('balanceModal');
const btnBalance = $("#btn-balance");
const btnBalanceSet = $("#btnBalanceSet");
const balanceSpan = document.getElementById('balanceSpan');
$(function () {
    btnBalance.click(function () {
        balanceModal.style.display = "block";
    });

    btnBalanceSet.click(function () {
        let balance = $("#balanceInput").val();
        let url = contextPath + "/client/balance";
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=UTF-8",
            url: url,
            data: JSON.stringify(balance)
        }).done(function (data) {
            balanceModal.style.display = "none";
            let curBal = data.body.balance;
            $("#balanceField").prop("textContent", curBal + " ₽");
            btnCheck();
        });
    });
});

balanceSpan.onclick = function () {
    balanceModal.style.display = "none";
};

window.onclick = function (event) {
    if (event.target == balanceModal) {
        balanceModal.style.display = "none";
    }
};