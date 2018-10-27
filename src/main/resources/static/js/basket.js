window.onload = function() {
   getUserId();
};

function getUserId(){
    fetch("api/user")
         .then(function (response) {
             return response.json();
         })
         .then(function(userData) {
            updateTable(userData);
         })
}

function updateTable(userData) {
    fetch("api/basket/" + userData.id)
        .then(function (response) {
            return response.json();
        })
        .then(function(basketData) {
            fillTable(basketData,userData.id);
        });
}

function fillTable(basketData,userId){
        let sumOfProducts = 0
        let tbody = document.querySelector("#basket-tbody");
            for(k in basketData){
            let tr = document.createElement("tr");
                let idTd = document.createElement("td");
                idTd.innerHTML = basketData[k].id;
                tr.appendChild(idTd);
                let nameTd = document.createElement("td");
                nameTd.innerHTML = basketData[k].name;
                tr.appendChild(nameTd);
                let producerTd = document.createElement("td");
                producerTd.innerHTML = basketData[k].producer;
                tr.appendChild(producerTd);
                let currentPriceTd= document.createElement("td");
                currentPriceTd.innerHTML = basketData[k].currentPrice + " Ft";
                tr.appendChild(currentPriceTd);
                deleteButton = document.createElement("input");
                deleteButton.setAttribute("type","reset");
                deleteButton.setAttribute("id",basketData[k].id);
                deleteButton.setAttribute("class","btn btn-danger resetProductButtons")
                deleteButton.setAttribute("value","Törlés");
                deleteButton.addEventListener('click', function () {clickingOnResetProductButtons(this,userId);});
                    sumOfProducts += basketData[k].currentPrice;
                    tr.appendChild(deleteButton);
                        tbody.appendChild(tr);

            }
            sumProducts(sumOfProducts);
            let resetButton = document.querySelector("#reset-button");
            resetButton.addEventListener('click', function () {handleResetButton(userId);});
            let orderButton = document.querySelector("#order-button");
            orderButton.addEventListener('click', function () {handleOrderButton(basketData,userId);});
}

function sumProducts(sumOfProducts){
         let sumParagraph = document.querySelector("#sumofproducts");
         sumParagraph.innerHTML = "A kosár tartalmának ára összesen : " + sumOfProducts + " Ft";
}

function clickingOnResetProductButtons(data,userId){
    let productId = data.id;
    let element = data.parentElement;
    var td = element.getElementsByTagName("td");
    productName = td[1].innerHTML;
    let url = "api/basket/" + userId + "/" + productId;
    if (confirm("Biztos szeretné törölni ezt az elemet a kosárból?")) {
        fetch(url, {
                    method: "DELETE",
                      headers: {
                                  "Content-Type": "application/json; charset=utf-8"
                              }
                          });
        location.reload()
    } else {
        location.reload();
    }
}

function handleResetButton(userId){
        let url = "api/basket/" + userId;
        if (confirm("Biztos hogy üríteni szeretné a kosár tartalmát?")) {
            fetch(url, {
                    method: "DELETE",
                    headers: {
                                "Content-Type": "application/json; charset=utf-8"
                            }
                })
        location.reload();
        } else {
        location.reload();
        }
}

function handleOrderButton(basketData,userId){
    if(confirm("Megrendeli a termékeket?")){
        fetch("/api/myorders/" + userId, {
                method: "POST",
                headers: {
                            "Content-Type": "application/json; charset=utf-8"
                        }
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
    }
}





