window.onload = function() {
    console.log("1");
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
    console.log("2");
    let orderId = new URL(window.location).searchParams.get("address");
    console.log(orderId);
    console.log(userStuff);
    fetch("/api/myorderitems/" + orderId)
    .then(function (response) {
        return response.json();
    })
    .then(function(jsonData) {
        fillTable(jsonData)
        console.log(jsonData);
    })
}

function fillTable(jsonData){
    console.log("3");
    let tbody = document.querySelector("#product-tbody")
    console.log(tbody);
    console.log(jsonData);
    for(i in jsonData){
        let price = jsonData[i].productPrice;
        console.log("3.5")
        console.log(jsonData[i].productAddress)
        fetch("/api/products/" + jsonData[i].productAddress)
        .then(function (response) {
            console.log("4")
            return response.json();
        })
        .then(function(producData,jsonData) {
            console.log("5")
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
                                            currentPriceTd.innerHTML = price + " Ft";
                                            tr.appendChild(currentPriceTd);
                                            console.log("6");

            tbody.appendChild(tr);

        })
    }
  }
