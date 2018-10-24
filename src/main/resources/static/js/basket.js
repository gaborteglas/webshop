window.onload = function() {
   updateTable();
};

function updateTable() {
    fetch("api/basket")
        .then(function (request) {
            return request.json();
        })
        .then(function(jsonData) {
            fetchProduct(jsonData);
        });
}

function fetchProduct(basketData) {
        fetch("api/products")
        .then(function(request){
            return request.json();
        })
        .then(function(products) {
            fillTable(products,basketData);
         })
}

function fillTable(products,basketData){
        console.log(products);
        console.log(basketData);
        let tbody = document.querySelector("#basket-tbody");
        let productId = basketData.productId;
        let userId = 1 //document.querySelector("#username").innerHTML;
        console.log(productId);
        let usersProductIds = [];
        for(i in basketData){
            if (userId === basketData[i].userId){
                usersProductIds.push(basketData[i].productId);
            }
        }
        let userItems = {};
        for(j in usersProductIds){
            for(k in products){
                  if(usersProductIds[j] === products[k].id){
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
                    currentPriceTd.innerHTML = products[k].currentPrice;
                    tr.appendChild(currentPriceTd);
                    tbody.appendChild(tr);
                    }
             }
        }
}
