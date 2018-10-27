window.onload = function() {
   getUserId();
}

function getUserId() {
    fetch("api/user")
        .then(function (response) {
            return response.json();
        })
        .then(function(jsonData) {
            updateTable(jsonData);
        });
}

function updateTable(userStuff){
    let orderId = new URL(window.location).searchParams.get("address");
    console.log(orderId);
    console.log(userStuff);
    fetch("/api/myorderitems/" + orderId)
    .then(function (response) {
        return response.json();
    })
    .then(function(jsonData) {
        fillTable(jsonData)
    })
}

function fillTable(jsonData){
    let tbody = document.querySelector("#product-tbody")
    console.log(jsonData);
    for(i in jsonData){
        fetch("/api/products/" + jsonData[i].productAddress)
        .then(function (response) {
            return response.json();
        })
        .then(function(producData) {
            let tr = document.createElement("tr");
            let nameTd = document.createElement("td");
            nameTd.innerHTML = producData.name;
            tr.appendChild(nameTd);
                    let idTd = document.createElement("td");
                    idTd.innerHTML = producData.id;
                    tr.appendChild(idTd);
                                let producerTd = document.createElement("td");
                                producerTd.innerHTML = producData.producer;
                                tr.appendChild(producerTd);
                                            let currentPriceTd = document.createElement("td");
                                            currentPriceTd.innerHTML = producData.currentPrice + " Ft";
                                            tr.appendChild(currentPriceTd);

            tbody.appendChild(tr);

        })
    }
  }
