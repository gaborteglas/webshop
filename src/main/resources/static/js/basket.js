window.onload = function() {
   updateTable();
   let resetButton = document.querySelector("#reset-button");
   resetButton.onclick = handleResetButton;
};

function resetProductButtonsEventListener(){
    let buttons = document.querySelectorAll(".resetProductButtons")
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].addEventListener('click', function () {clickingOnResetProductButtons(this);
        });
      }
}

function clickingOnResetProductButtons(data){
    let userId = 1 // document.querySelector("#username).split("#")[1];
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
    } else {
        location.reload();
    }
}

function handleResetButton(){
        let userId = 1 //document.querySelector("#username).split("#")[1];
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
                    deleteButton = document.createElement("input");
                    deleteButton.setAttribute("type","reset");
                    deleteButton.setAttribute("class","btn btn-danger");
                    deleteButton.setAttribute("id",products[k].id);
                    deleteButton.setAttribute("class","resetProductButtons")
                    deleteButton.setAttribute("value","Törlés");
                    tr.appendChild(deleteButton);
                    tbody.appendChild(tr);
                    }
             }
             resetProductButtonsEventListener();
        }
}
