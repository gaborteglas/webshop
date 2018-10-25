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
                order = {"order_id" : orderId, "product_id" : basketData[i].productId,"product_price" : 1}
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