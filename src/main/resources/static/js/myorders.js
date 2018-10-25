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
    productsList = [];
    console.log(orderData);
    console.log(userId);
    orderId = orderData[0].id;
    fetch("api/basket")
        .then(function (response) {
            return response.json();
        })
        .then(function(basketData) {

            for(i in basketData){
            if(basketData[i].userId == userId){
                productsList.push(basketData[i].productId);
                }
            }
        })
        updateOrderItem(productsList,orderId);
        console.log(productsList);
}

updateOrderItem(productsList,orderId){

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