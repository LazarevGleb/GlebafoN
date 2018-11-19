<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="/struts-tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>Client page</title>
    <jsp:include page="header.jsp"/>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/core/css/modal.css"/>">
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/core/css/mainPage.css"/>">
    <c:set value="${pageContext.request.contextPath}" var="contextPath"/>

</head>
<body>

<input type="hidden" id="number" value="${contract.number}"/>
<input type="hidden" id="block" value="${contract.block}"/>
<input type="hidden" id="chosenTariff" value="${contract.tariff.id}"/>
<input type="hidden" id="contractAdditions" value="${contract.additions}"/>
<input type="hidden" id="basketTariff" value="${basket.chosenTariff}"/>
<input type="hidden" id="basketChAdds" value="${basket.chosenAdditions}"/>
<input type="hidden" id="basketDelAdds" value="${basket.deletedAdditions}"/>

<div style="margin-left: 15px">
    <ul class="row">
        <li>
            <button type="submit" id="btn-block" class="btn-lg myBtn"></button>
        </li>
        <li>
            <button type="submit" id="btn-balance" class="btn-lg myBtn">Balance</button>
        </li>
        <li>
            <button type="submit" id="btn-tariffs" class="btn-lg myBtn">Tariffs</button>
        </li>
        <li>
            <button type="submit" id="btn-options" class="btn-lg myBtn">Options</button>
        </li>
        <li>
            <button type="submit" id="btn-basket" class="btn-lg myBtn">Basket</button>
        </li>
        <li>
            <form action="${contextPath}/login" method="get">
                <button type="submit" id="btn-quit" class="btn-lg myBtn">Quit</button>
            </form>
        </li>
    </ul>
</div>

<div style="margin-left: 10px; font-size: 70px">
    <div class="row" style="background: #fcf8e3">
        <label class="col-sm-2">Client:</label>
        <div class="col-sm-10" id="client">
            <p>${contract.client.name} ${contract.client.surname}</p>
        </div>
    </div>

    <div class="row" style="background: #9fcdff">
        <label class="col-sm-2">Number:</label>
        <div class="col-sm-10" id="contractNumber">${contract.number}</div>
    </div>

    <div class="row" style="background: #fcf8e3">
        <label class="col-sm-2">Tariff:</label>
        <div class="col-sm-10" id="tariffName">${contract.tariff.name}</div>
    </div>

    <div class="row" style="background: #9fcdff">
        <label class="col-sm-2">Options:</label>
        <div class="col-sm-10" id="options">
            <c:if test="${empty contract.additions}">
                <c:out value="No options"/>
            </c:if>
            <c:forEach var="addition" items="${contract.additions}" varStatus="loop">
                <c:out value="${addition.name}"/>
                <c:if test="${not loop.last}">,</c:if>
            </c:forEach>
        </div>
    </div>

    <div class="row" style="background: #fcf8e3">
        <label class="col-sm-2">Balance:</label>
        <div class="col-sm-10" id="balanceField">${contract.balance} &#8381</div>
    </div>

    <div class="row" style="background: #fcf8e3">
        <div class="col-sm-10" id="blockStatus" style="color: #b21f2d"></div>
    </div>
</div>

<div id="balanceModal" class="modal" style="font-size: 40px">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Balance</h3>
            <span id="balanceSpan" class="close">&times;</span>
        </div>
        <div class="modal-body">
            <h3>Top up your balance</h3>
            <input autofocus="autofocus" type="text" id="balanceInput"/>
            <button style="float: right" type="submit" id="btnBalanceSet">Apply</button>
        </div>
    </div>
</div>

<div id="basketModal" class="modal">
    <div class="modal-content" style="height: auto; top: 50px; left: 1300px">
        <div class="modal-header">
            <h5>Basket</h5>
            <span id="basketSpan" class="close">&times;</span>
        </div>
        <div class="modal-body">
            <div id="divBasket">
                <table class="table table-bordered table-striped text-center"
                       style="font-size: small" id="basketTable">
                    <thead>
                    <tr class="thead-dark">
                        <th style="background: #FFFFFF; border: 0"></th>
                        <th>Product</th>
                        <th>Price</th>
                        <th>Act-cost</th>
                    </tr>
                    </thead>
                    <tbody id="basketTBody">
                    </tbody>
                </table>

                <table id="DOTable" style="font-size: medium">
                    <h5>Removed options:</h5>
                    <tbody id="doTableTBody">
                    </tbody>
                </table>

                <div>
                    <b>Price:</b>
                    <p id="basketPrice">0.0 ₽</p>
                </div>
                <div>
                    <b>Total activation cost:</b>
                    <p id="basketActCost">0.0 ₽</p>
                </div>
                <div>
                    <b>TOTAL:</b>
                    <p id="basketTotal">0.0 ₽</p>
                </div>
                <button id="buyButton" disabled="disabled">Buy it!</button>
                <p id="basketStatus"></p>
            </div>
        </div>
    </div>
</div>

<div id="tariffModal" class="modal">
    <div class="modal-content" style="height: auto; width: auto; top: 70px;
left: 50px">
        <div class="modal-header">
            <h4>Tariffs</h4>
            <span id="tariffSpan" class="close">&times;</span>
        </div>
        <div class="modal-body">
            <table class="table table-striped" style="font-size: medium">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>SMS</th>
                    <th>Minutes</th>
                    <th>Internet</th>
                    <th>Price</th>
                </tr>
                </thead>
                <tbody id="tariffTable">
                </tbody>
            </table>
        </div>
    </div>
</div>

<div id="optionModal" class="modal">
    <div class="modal-content" style="height: auto; width: auto; left: 50px; top: 70px">
        <div class="modal-header">
            <h5>Options</h5>
            <span id="optionSpan" class="close">&times;</span>
        </div>
        <div class="modal-body">
            <h5>Connected options</h5>
            <table class="table table-striped" style="font-size: medium">
                <tbody id="conOptionsTable" style="font-size: medium">
                </tbody>
            </table>

            <h5>Available options</h5>
            <table class="table table-striped" style="font-size: medium">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Parameter</th>
                    <th>Value</th>
                    <th>Price</th>
                    <th>Act-cost</th>
                    <th>Requirements</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody id="avOptionsTable">
                </tbody>
            </table>
        </div>
    </div>
</div>
</div>

<jsp:include page="footer.jsp"/>
<script src="<c:url value="/resources/scripts/blockNumber.js"/> "></script>
<script src="<c:url value="/resources/scripts/balance.js"/> "></script>
<script src="<c:url value="/resources/scripts/basket.js"/> "></script>
<script src="<c:url value="/resources/scripts/tariffList.js"/> "></script>
<script src="<c:url value="/resources/scripts/optionList.js"/> "></script>
</body>
</html>
