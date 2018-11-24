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
        console.log(order);
        let tr = document.createElement("tr");
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
            trashIcon.onclick = deleteButtonClick;
            trashTd.appendChild(trashIcon);
        }
        tr.appendChild(trashTd);

        tr.onclick = function () {
            window.location = "/orderitems.html?order-id=" + order.id;
        };

        tbody.appendChild(tr);
    }
}

function deleteButtonClick(event) {
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