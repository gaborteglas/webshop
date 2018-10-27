window.onload = function() {
   updateTable();
}


function updateTable(){
    let orderId = new URL(window.location).searchParams.get("order_id");
    fetch("/api/myorderitems/" + orderId)
    .then(function (response) {
        return response.json();
    })
    .then(function(jsonData) {
        fillTable(jsonData)
    })
}

function fillTable(orderItems){
    let tbody = document.querySelector("#product-tbody")
    for(i in orderItems){
        let tr = document.createElement("tr");

        let idTd = document.createElement("td");
        idTd.innerHTML = orderItems[i].productId;
        tr.appendChild(idTd);

        let producerTd = document.createElement("td");
        producerTd.innerHTML = orderItems[i].producer;
        tr.appendChild(producerTd);

        let nameTd = document.createElement("td");
        nameTd.innerHTML = orderItems[i].productName;
        tr.appendChild(nameTd);

        let currentPriceTd = document.createElement("td");
        currentPriceTd.innerHTML = orderItems[i].productPrice + " Ft";
        tr.appendChild(currentPriceTd);

        tbody.appendChild(tr);
    }
}
