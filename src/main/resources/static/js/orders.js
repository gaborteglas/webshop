window.onload = function() {
   updateTable();
};

function updateTable() {
    fetch("api/orders")
        .then(function (response) {
            return response.json();
        })
        .then(function(jsonData) {
            fillTable(jsonData);
        });
}

function fillTable(orders) {
    let tbody = document.getElementById("orders-tbody");
    tbody.innerHTML = "";
    for (let i = 0; i < orders.length; i++) {
        let order  = orders[i];
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
        dateTd.innerHTML = order.date;
        tr.appendChild(dateTd);

        let statusTd = document.createElement("td");
        if(order.status === "ACTIVE") {
            statusTd.innerHTML = "aktív";
        } else if (order.status === "DELIVERED") {
            statusTd.innerHTML = "kiszállítva";
        } else if (order.status === "DELETED") {
            statusTd.innerHTML = "törölve";
        }
        tr.appendChild(statusTd);

        let countTd = document.createElement("td");
        countTd.innerHTML = order.count + " db";
        tr.appendChild(countTd);

        let priceTd = document.createElement("td");
        priceTd.innerHTML = order.price + " FT";
        tr.appendChild(priceTd);

        let buttonsTd = document.createElement("td");
        let editButton = document.createElement("button");
        editButton.innerHTML = "Törlés";
        editButton.setAttribute("class", "btn btn-danger");
//        editButton.onclick = handleEditButtonOnClick;
        buttonsTd.appendChild(editButton);
        tr.appendChild(buttonsTd);

        tr.onclick = function() {
            console.log(order.id)
        };

        tbody.appendChild(tr);

    }
}