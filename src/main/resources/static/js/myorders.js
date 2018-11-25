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
    updateOrderItemTable(orderId);
    }

function updateOrderItemTable(orderId) {
    location.href = "#openModal";
    fetch("/api/myorderitems/" + orderId)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            createOrderItemsTable(jsonData)
        })
}


function createOrderItemsTable(orderItems) {
        let totalPrice = 0;
        let orderItemsTable = document.getElementById("orderitems-tbody");
        orderItemsTable.innerHTML = "";
        for (i in orderItems) {
            let tr = document.createElement("tr");
            tr.className = "clickable-row";

            let producerTd = document.createElement("td");
            producerTd.innerHTML = orderItems[i].producer;
            producerTd.className = "column1";
            tr.appendChild(producerTd);

            let nameTd = document.createElement("td");
            nameTd.innerHTML = orderItems[i].productName;
            nameTd.className = "column2";
            tr.appendChild(nameTd);

            let currentPriceTd = document.createElement("td");
            currentPriceTd.innerHTML = orderItems[i].productPrice + " Ft";
            currentPriceTd.className = "column3";
            tr.appendChild(currentPriceTd);

            let quantityTd = document.createElement("td");
            quantityTd.innerHTML = orderItems[i].quantity + " db";
            quantityTd.className = "column4";
            tr.appendChild(quantityTd);

            orderItemsTable.appendChild(tr);

            totalPrice += orderItems[i].productPrice * orderItems[i].quantity;
            }

        let priceP = document.getElementById("price-p");
        priceP.innerHTML = "Összesen: " + totalPrice + " Ft"
    }