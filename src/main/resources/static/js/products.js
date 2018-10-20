window.onload = function() {
   updateTable();
};

function updateTable() {
    fetch("api/products")
        .then(function (request) {
            return request.json();
        })
        .then(function(jsonData) {
            fillTable(jsonData);
        });
}

function fillTable(products) {
    let tbody = document.getElementById("products-tbody");
    tbody.innerHTML = "";
    for (let i = 0; i < products.length; i++) {
        let product  = products[i];
        let tr = document.createElement("tr");
        tr["raw-data"] = product;

        let idTd = document.createElement("td");
        idTd.innerHTML = product.id;
        tr.appendChild(idTd);

        let nameTd = document.createElement("td");
        nameTd.innerHTML = product.name;
        tr.appendChild(nameTd);

        let addressTd = document.createElement("td");
        addressTd.innerHTML = product.address;
        tr.appendChild(addressTd);

        let producerTd = document.createElement("td");
        producerTd.innerHTML = product.producer;
        tr.appendChild(producerTd);

        let priceTd = document.createElement("td");
        priceTd.innerHTML = product.currentPrice;
        tr.appendChild(priceTd);


        tbody.appendChild(tr);

    }
}
