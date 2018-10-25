window.onload = function() {
    updateTable();
    }

function updateTable() {

    fetch("api/user")
        .then(function (response) {
            return response.json();
        })
        .then(function(jsonData) {
            fetchOrders(jsonData);
        });
}

function fetchOrders(userData){
    let userId = userData.id;
    console.log(userId);
    fetch("api/myorders/" + userId)
    .then(function (response) {
        return response.json();
    })
    .then(function(orderData) {
        console.log(orderData);
        fillTable(orderData);
    })
}

function fillTable(orderData){
    console.log(orderData);
    let tbody = document.querySelector("#orders-tbody");
    console.log(tbody);
    for(i in orderData){
        let tr = document.createElement("tr");
        let idTd = document.createElement("td");
        idTd.innerHTML = orderData[i].id;
        let dateTd = document.createElement("td");
        dateTd.innerHTML = orderData[i].date;
        tr.appendChild(idTd);
        tr.appendChild(dateTd);
        tbody.appendChild(tr);
    }
}