<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="header.jsp"/>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/core/css/modal.css"/>">
    <title>Addition</title>
</head>
<body>
<input type="hidden" id="optionId" value="${optionId}"/>
<div class="form-group">
    <label class="col-sm-2 control-label">Name</label>
    <div class="col-sm-10">
        <input type="text" id="nameIn"/>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-2 control-label">Parameter</label>
    <div class="col-sm-10">
        <select id="parameterIn">
            <option>SMS</option>
            <option>MINUTES</option>
            <option>INTERNET</option>
        </select>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-2 control-label">Value</label>
    <div class="col-sm-10">
        <input type="text" id="valueIn"/>
        <p id="valueStatus" style="color: red; display: none"> Wrong input!</p>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-2 control-label">Price, ₽</label>
    <div class="col-sm-10">
        <input type="text" id="priceIn"/>
        <p id="priceStatus" style="color: red; display: none">Wrong input!</p>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-2 control-label">Activation cost, ₽</label>
    <div class="col-sm-10">
        <input type="text" id="activationIn"/>
        <p id="costStatus" style="color: red; display: none">Wrong input!</p>
    </div>
</div>

<table style="font-size: large; padding-right: 30px">
    <thead>
    <tr id="tariffTr">
        <th>Choose incompatible tariffs</th>
        <th id="incomTh" style="margin-left: 100px">Incompatible tariffs</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td id="chTariffTd">
            <select id="tariffSelector" multiple="multiple" size="4">
            </select>
        </td>
        <td id="inTariffTd" style="min-width: 900px; width: auto">
        </td>
    </tr>
    </tbody>

</table>
<div>
    <button id="relBtn" class="btn-lg btn-primary">Relations</button>
</div>
<div>
    <button id="addBtn" class="btn-lg btn-primary" disabled="disabled">Add</button>
</div>

<div id="optionsWindow" class="modal">
    <div class="modal-content" style="min-height: 500px; height: auto;
            min-width: 300px; width: auto; left: 100px; top: 100px">
        <div class="modal-header">
            <h3>Option relations</h3>
            <span id="optionSpan" class="close">&times;</span>
        </div>
        <div class="modal-body">
            <table class="table table-striped" style="font-size: large">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody id="optionTBody">
                </tbody>
            </table>
            <div id="buttonDiv">
            </div>
            <div>
                <p id="checkStatus"></p>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script src="<c:url value="/resources/scripts/managerOption.js"/> "></script>
</body>
</html>
