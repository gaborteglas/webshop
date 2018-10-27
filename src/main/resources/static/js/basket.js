window.onload = function() {
   updateTable();
   let resetButton = document.querySelector("#reset-button");
   resetButton.onclick = handleResetButton;
   let orderButton = document.querySelector("#order-button");
   orderButton.onclick = handleOrderButton;
};

function updateTable() {
    fetch("api/basket")
        .then(function (response) {
            return response.json();
        })
        .then(function (products) {
            fillTable(products);
        });
}

function fillTable(products){
    let totalPrice = 0
    let tbody = document.querySelector("#basket-tbody");
    tbody.innerHTML = "";
    for(k in products){
        let tr = document.createElement("tr");
        let idTd = document.createElement("td");
        idTd.innerHTML = products[k].id;
        tr.appendChild(idTd);
        let nameTd = document.createElement("td");
        nameTd.innerHTML = products[k].name;
        tr.appendChild(nameTd);
        let producerTd = document.createElement("td");
        producerTd.innerHTML = products[k].producer;
        tr.appendChild(producerTd);
        let currentPriceTd= document.createElement("td");
        currentPriceTd.innerHTML = products[k].currentPrice + " Ft";
        tr.appendChild(currentPriceTd);
        deleteButton = document.createElement("input");
        deleteButton.setAttribute("type","reset");
        deleteButton.setAttribute("id",products[k].id);
        deleteButton.setAttribute("class","btn btn-danger resetProductButtons")
        deleteButton.setAttribute("value","Törlés");
        deleteButton.onclick = clickingOnResetProductButtons;
        totalPrice += products[k].currentPrice;
        tr.appendChild(deleteButton);
        tbody.appendChild(tr);
    }
    let sumParagraph = document.querySelector("#sumofproducts");
    sumParagraph.innerHTML = "A kosár tartalmának ára összesen : " + totalPrice + " Ft";
}

function clickingOnResetProductButtons(clickEvent){
    let productId = this.getAttribute("id");
    let url = "api/basket/" + productId;
    if (confirm("Biztos szeretné törölni ezt az elemet a kosárból?")) {
        fetch(url, {
            method: "DELETE"
        }).then(function (response) {
            return response.json()
        }).then(responseJson => updateTable())
    }
}

function handleResetButton(){
    if (confirm("Biztos hogy üríteni szeretné a kosár tartalmát?")) {
        fetch("api/basket/", {
            method: "DELETE"
        }).then(function (response) {
            return response.json()
        }).then(responseJson => updateTable())
    }
}







function handleOrderButton(basketData,userId) {
    if(confirm("Megrendeli a termékeket?")){
        fetch("/api/myorders/" + userId, {
                method: "POST"
        });
        setTimeout(function(){ createOrderItems(basketData,userId); }, 1000);
    } else {
     location.reload();
    }
}

function createOrderItems(basketData,userId){
    fetch("api/myorders/" + userId)
    .then(function (response) {
        return response.json();
    })
    .then(function(orderData) {
        console.log(orderData);
        gatherOrderItemDatas(orderData,basketData,userId);
    })
}

function gatherOrderItemDatas(orderData,basketData,userId){
    orderItemsList = [];
    console.log(basketData);
    orderId = orderData[orderData.length-1].id;
    for(i in basketData){
        order = {"orderId" : orderId,"productId" : basketData[i].id,"productAddress" : basketData[i].address, "productPrice" : basketData[i].currentPrice}
        orderItemsList.push(order);
    }
    fetch("api/myorderitems2", {
        method: "POST",
        headers: {
                    "Content-Type": "application/json; charset=utf-8"
                },
        body: JSON.stringify(orderItemsList)
    });
    setTimeout(function(){ deleteBasketAfterOrder(userId); }, 1000);
}

function deleteBasketAfterOrder(userId){
    console.log("ja");
    console.log(userId);
    let url = "api/basket/" + userId;
    fetch(url, {
        method: "DELETE"
    })
    setTimeout(function(){ window.location="http://localhost:8080/myorders.html"; }, 1000);
}

function isOrderButtonAvailable(){
    let tbody = document.querySelector("#basket-tbody");
    orderButton = document.querySelector("#order-button");
    if (tbody.childElementCount === 0){
        orderButton.disabled = true;
    }
}





