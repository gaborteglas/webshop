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
        statusTd.innerHTML = order.status;
        tr.appendChild(statusTd);

        let countTd = document.createElement("td");
        countTd.innerHTML = "X db";
        tr.appendChild(countTd);

        let priceTd = document.createElement("td");
        priceTd.innerHTML = "Y FT";
        tr.appendChild(priceTd);

        tr.onclick = function() {
            console.log(order.id)
        };

        tbody.appendChild(tr);

    }
}