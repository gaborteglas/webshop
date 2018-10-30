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

        let quantityTd = document.createElement("td");
        quantityTd.innerHTML = products[k].quantity + " db";
        tr.appendChild(quantityTd);

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
    let sumParagraph = document.querySelector("#totalPrice");
    sumParagraph.innerHTML = "A kosár tartalmának ára összesen : " + totalPrice + " Ft";

    let orderButton = document.querySelector("#order-button");
    orderButton.disabled = products.length === 0;

    let resetButton = document.querySelector("#reset-button");
    resetButton.disabled = products.length === 0;
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

function handleOrderButton() {
    if(confirm("Megrendeli a termékeket?")){
        fetch("/api/myorders", {
            method: "POST"
        }).then(function(response) {
            window.location = "/myorders.html"
        });
    }
}
