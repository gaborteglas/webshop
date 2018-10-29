window.onload = function () {
    updateTable();
    filterActiveButtonClick();
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
        idTd.innerHTML = order.id;
        tr.appendChild(idTd);

        let userTd = document.createElement("td");
        userTd.innerHTML = order.userId;
        tr.appendChild(userTd);

        let dateTd = document.createElement("td");
        dateTd.innerHTML = new Date(order.date).toLocaleString();
        tr.appendChild(dateTd);

        let statusTd = document.createElement("td");
        if (order.status === "ACTIVE") {
            statusTd.innerHTML = "aktív";
        } else if (order.status === "DELIVERED") {
            statusTd.innerHTML = "kiszállítva";
        } else if (order.status === "DELETED") {
            statusTd.innerHTML = "törölve";
        }
        tr.appendChild(statusTd);

        let quantityTd = document.createElement("td");
        quantityTd.innerHTML = order.quantity + " db";
        tr.appendChild(quantityTd);

        let priceTd = document.createElement("td");
        priceTd.innerHTML = order.price + " Ft";
        tr.appendChild(priceTd);

        let buttonsTd = document.createElement("td");
        let deleteButton = document.createElement("button");
        deleteButton.innerHTML = "Törlés";
        deleteButton.setAttribute("class", "btn btn-danger");
        deleteButton.onclick = deleteButtonClick;
        buttonsTd.appendChild(deleteButton);
        tr.appendChild(buttonsTd);

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
        })
            .then(function (response) {
                updateTable();
            });
        event.stopPropagation();
    }
}

function activeButtonClick() {
        fetch("api/activeorders/")
        .then(function (response) {
        return response.json();
        })
        .then(function (jsonData) {
        fillTable(jsonData);
})}

function filterActiveButtonClick() {
let theadActiveButton = document.getElementById("orders-filter-active");
theadActiveButton.setAttribute("class", "btn btn-warning");
theadActiveButton.onclick = activeButtonClick;
}