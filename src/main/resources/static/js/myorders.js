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
    fetch("api/myorders/" + userId)
    .then(function (response) {
        return response.json();
    })
    .then(function(orderData) {
        fillTable(orderData);
        gatherOrderItemDatas(orderData);
    })
}

function gatherOrderItemDatas(orderData){
    let userId = orderData[0].userId;
    orderId = orderData[orderData.length-1].id;
    orderItems = [];
    fetch("api/basket")
        .then(function (response) {
            return response.json();
        })
        .then(function(basketData) {
            for(i in basketData){
            if(basketData[i].userId == userId){
                console.log(basketData[i].productId)
                order = {"orderId" : orderId, "productId" : basketData[i].productId,"productPrice" : 1}
                orderItems.push(order);
                }
            }
            createOrderItems(orderItems);
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
            dateTd.innerHTML = orderData[i].date;
            tr.appendChild(dateTd);

            var q = orderData[i].id
            tbody.appendChild(tr);
        }
    clickableRowsEventListener();
}

function clickableRowsEventListener() {
  var clickableRows = document.querySelectorAll('.clickable-row');
  console.log(clickableRows);
  for (var i = 0; i < clickableRows.length; i++) {
    clickableRows[i].addEventListener('click', function () {clickingOnRows(this);});
  }
}

function clickingOnRows(data){
    orderId = data.innerText[0];
    window.location = "/myorderitem.html?address=" + orderId;
}