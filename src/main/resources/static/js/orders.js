window.onload = function () {
    updateTable();
    filterActiveButtonClick();
    filterAllButtonClick();
};

function updateTable() {
    fetch("api/orders")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            fillTable(jsonData);
        });
}

function fillTable(orders) {
    let tbody = document.getElementById("orders-tbody");
    tbody.innerHTML = "";
    for (let i = 0; i < orders.length; i++) {
        let order = orders[i];
        let tr = document.createElement("tr");
        tr.onclick = clickingOnRows;
        tr.setAttribute("id", order.id);
        tr.className = "clickable-row";
        tr["raw-data"] = order;

        let idTd = document.createElement("td");
        idTd.className = "column1";
        idTd.innerHTML = order.id;
        tr.appendChild(idTd);

        let userTd = document.createElement("td");
        userTd.className = "column1";
        userTd.innerHTML = order.userId;
        tr.appendChild(userTd);

        let dateTd = document.createElement("td");
        dateTd.className = "column1";
        dateTd.innerHTML = new Date(order.date).toLocaleString();
        tr.appendChild(dateTd);

        let statusTd = document.createElement("td");
        statusTd.className = "column1";
        if (order.status === "ACTIVE") {
            statusTd.innerHTML = "aktív";
        } else if (order.status === "DELIVERED") {
            statusTd.innerHTML = "kiszállítva";
        } else if (order.status === "DELETED") {
            statusTd.innerHTML = "törölve";
        }
        tr.appendChild(statusTd);

        let addressTd = document.createElement("td");
        addressTd.className = "column1";
        addressTd.innerHTML = order.deliveryAddress;
        tr.appendChild(addressTd);

        let quantityTd = document.createElement("td");
        quantityTd.className = "column1";
        quantityTd.innerHTML = order.quantity + " db";
        tr.appendChild(quantityTd);

        let priceTd = document.createElement("td");
        priceTd.className = "column1";
        priceTd.innerHTML = order.price + " Ft";
        tr.appendChild(priceTd);

        let deliveredTd = document.createElement("td");
        deliveredTd.className = "column1";
        let deliveredBox = document.createElement("input");
        deliveredBox.setAttribute("type", "checkbox");
        deliveredBox.setAttribute("class", "deliveredCheckBox")
        deliveredBox.setAttribute("name", "delivered")
        deliveredBox.setAttribute("value", "delivered")
        deliveredBox.onclick = deliveredButtonClick;
        deliveredTd.appendChild(deliveredBox);
        tr.appendChild(deliveredTd);

        if (order.status === "DELIVERED") {
            deliveredBox.checked = true;
            deliveredBox.disabled = true;
        } else if (order.status === "DELETED") {
            deliveredBox.disabled = true;
        }

        let trashTd = document.createElement("td");
        trashTd.className = "column1";
        if (order.status === "ACTIVE") {
            let trashIcon = document.createElement("img");
            trashIcon.setAttribute("src", "img/trash-solid.svg");
            trashIcon.setAttribute("alt", "trash-icon");
            trashIcon.setAttribute("id", "trash-icon")
            trashIcon.onclick = deleteOrderButtonClick;
            trashTd.appendChild(trashIcon);
        }
        tr.appendChild(trashTd);

        tbody.appendChild(tr);
    }
}

function clickingOnRows() {
    let orderId = this.getAttribute("id");
    updateOrderItemTable(orderId);
}

function updateOrderItemTable(orderId) {
    location.href = "#openModal";
    fetch("/api/orders/" + orderId)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            createOrderItemsTable(jsonData)
        })
}


function createOrderItemsTable(orderItems) {
    let orderItemsTable = document.getElementById("orderitems-tbody");
    let totalPrice = 0;
    orderItemsTable.innerHTML = "";
    for (i in orderItems) {
        let tr = document.createElement("tr");
        tr.className = "clickable-row";
        tr["raw-data"] = orderItems[i];

        let productIdTd = document.createElement("td");
        productIdTd.innerHTML = orderItems[i].productId;
        productIdTd.className = "column1";
        tr.appendChild(productIdTd);

        let producerTd = document.createElement("td");
        producerTd.innerHTML = orderItems[i].producer;
        producerTd.className = "column1";
        tr.appendChild(producerTd);

        let nameTd = document.createElement("td");
        nameTd.innerHTML = orderItems[i].productName;
        nameTd.className = "column1";
        tr.appendChild(nameTd);

        let currentPriceTd = document.createElement("td");
        currentPriceTd.innerHTML = orderItems[i].productPrice + " Ft";
        currentPriceTd.className = "column1";
        tr.appendChild(currentPriceTd);

        let quantityTd = document.createElement("td");
        quantityTd.innerHTML = orderItems[i].quantity + " db";
        quantityTd.className = "column4";
        tr.appendChild(quantityTd);

        let trashTd = document.createElement("td");
        trashTd.className = "column1";
        let trashIcon = document.createElement("img");
        trashIcon.setAttribute("src", "img/trash-solid.svg");
        trashIcon.setAttribute("alt", "trash-icon");
        trashIcon.setAttribute("id", "trash-icon")
        trashIcon.onclick = deleteOrderItemButtonClick;
        trashTd.appendChild(trashIcon);
        tr.appendChild(trashTd);

        orderItemsTable.appendChild(tr);

        totalPrice += orderItems[i].productPrice * orderItems[i].quantity;
        }

    let priceP = document.getElementById("price-p");
    priceP.innerHTML = "Összesen: " + totalPrice + " Ft"
}

function deleteOrderButtonClick(event) {
    var result = confirm("Biztosan törli a kijelölt rendelést?");
    if (result) {
        let order = this.parentElement.parentElement["raw-data"];

        fetch("api/orders/" + order.id, {
            method: "DELETE",
        }).then(function (response) {
            updateTable();
        })
    }
    event.stopPropagation();
}

function deliveredButtonClick(event) {
    let order = this.parentElement.parentElement["raw-data"];

    fetch("api/orders/" + order.id + "/status", {
        method: "POST",
    }).then(function (response) {
        updateTable();
    });
    event.stopPropagation();
}

function activeButtonClick() {
    fetch("api/activeorders/")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            fillTable(jsonData);
        })
}

function filterActiveButtonClick() {
    let theadActiveButton = document.getElementById("orders-filter-active");
    theadActiveButton.setAttribute("class", "btn btn-warning");
    theadActiveButton.onclick = activeButtonClick;
}

function filterAllButtonClick() {
    let theadAllButton = document.getElementById("orders-filter-all");
    theadAllButton.setAttribute("class", "btn btn-info");
    theadAllButton.onclick = updateTable;
}

function deleteOrderItemButtonClick() {
    let result = confirm("Biztosan törli a kijelölt elemet?");
    if (result) {
        let orderitem = this.parentElement.parentElement["raw-data"];

        fetch("api/orders/" + orderitem.orderId + "/" + orderitem.productAddress, {
            method: "DELETE",
        })
        .then(function(response) {
            updateTable();
            updateOrderItemTable(orderitem.orderId);
        });
    }
}