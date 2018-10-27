window.onload = function() {
    getUserId();
}

function getUserId() {
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
    fetch("api/myorders/" + userId)
    .then(function (response) {
        return response.json();
    })
    .then(function(orderData) {
        fillTable(orderData);
    })
}



function createOrderItems(orderItems){
    console.log(orderItems);
    fetch("api/myorderitems2", {
        method: "POST",
        headers: {
                    "Content-Type": "application/json; charset=utf-8"
                },
        body: JSON.stringify(orderItems)
        })
}

function fillTable(orderData){

    let tbody = document.querySelector("#orders-tbody");
    tbody.innerHTML = "";
        for(let i = 0; i < orderData.length; i++){
            let tr = document.createElement("tr");
            tr.className = "clickable-row";
            tr["raw-data"] = orderData[i];

            let idTd = document.createElement("td");
            idTd.innerHTML = orderData[i].id;
            tr.appendChild(idTd);

            let dateTd = document.createElement("td");
            dateTd.innerHTML = new Date(orderData[i].date).toLocaleString();
            tr.appendChild(dateTd);

            let statusTd = document.createElement("td");
            statusTd.innerHTML = orderData[i].status
            tr.appendChild(statusTd);

            var q = orderData[i].id
            tbody.appendChild(tr);
        }
    clickableRowsEventListener();
}

function clickableRowsEventListener() {
  var clickableRows = document.querySelectorAll('.clickable-row');
  for (var i = 0; i < clickableRows.length; i++) {
    clickableRows[i].addEventListener('click', function () {clickingOnRows(this);});
  }
}

function clickingOnRows(data){
    console.log(data);
    orderId = data.innerText[0];
    window.location = "/myorderitem.html?address=" + orderId;
}