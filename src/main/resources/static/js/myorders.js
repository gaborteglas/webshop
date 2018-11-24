window.onload = function () {
    fetchOrders();
}

function fetchOrders() {
    fetch("api/myorders")
        .then(function (response) {
            return response.json();
        })
        .then(function (orderData) {
            console.log(orderData);
            fillTable(orderData);
        })
}

function fillTable(orderData) {

    let tbody = document.querySelector("#orders-tbody");
    tbody.innerHTML = "";
    for (let i = 0; i < orderData.length; i++) {

        let tr = document.createElement("tr");
        tr.onclick = clickingOnRows;
        tr.setAttribute("id", orderData[i].id);
        tr.className = "clickable-row";
        tr["raw-data"] = orderData[i];

        let idTd = document.createElement("td");
        idTd.className = "column1";
        idTd.innerHTML = orderData[i].id;
        tr.appendChild(idTd);

        let dateTd = document.createElement("td");
        dateTd.className = "column2";
        dateTd.innerHTML = new Date(orderData[i].date).toLocaleString();
        tr.appendChild(dateTd);

        let statusTd = document.createElement("td");
        statusTd.className = "column3";
        if (orderData[i].status === "ACTIVE") {
            statusTd.innerHTML = "aktív";
        } else if (orderData[i].status === "DELIVERED") {
            statusTd.innerHTML = "kiszállítva";
        } else if (orderData[i].status === "DELETED") {
            statusTd.innerHTML = "törölve";
        }
        tr.appendChild(statusTd);

        let addressTd = document.createElement("td");
        addressTd.className = "column4";
        addressTd.innerHTML = orderData[i].deliveryAddress;
        tr.appendChild(addressTd);

        tbody.appendChild(tr);
    }
}

function clickingOnRows(data) {
    let orderId = this.getAttribute("id");
    window.location = "/myorderitem.html?order_id=" + orderId;
}