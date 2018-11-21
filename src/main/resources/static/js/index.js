window.onload = function () {
    //createDivs();
    updateTable();
    let cartResetButton = document.querySelector("#basket-reset-button");
    cartResetButton.onclick = handleResetButton;
}


function updateTable() {
    fetch("api/basket")
        .then(function (response) {
            return response.json();
        })
        .then(function (products) {
            fillTable(products);
        });
}

function fillTable(products) {
    let totalPrice = 0
    let totalQuantity = 0;
    let cart = document.querySelector(".header-cart-wrapitem");
    let totalPriceField = document.querySelector(".header-cart-total");
    let cartQuantity = document.querySelector(".header-icons-noti");
    for (let k = 0; k < products.length; k++) {
        let cartElement = document.createElement("li");
        cartElement.className = "header-cart-item";

        let imageHolderDiv = document.createElement("div");
        imageHolderDiv.className = "header-cart-item-img";

        let imageOfProduct = document.createElement("img");
        imageOfProduct.src = "images/item-cart-01.jpg";
        imageOfProduct.alt = "IMG";

        imageHolderDiv.appendChild(imageOfProduct);

        let textHolderDiv = document.createElement("div");
        textHolderDiv.className = "header-cart-item-txt";

        let anchor = document.createElement("a");
        anchor.href = "#";
        anchor.className = "header-cart-item-name";
        anchor.innerHTML = products[k].name;

        let price = document.createElement("span");
        price.className = "header-cart-item-info";
        price.innerHTML = products[k].quantity + " x " + products[k].currentPrice + " Ft";

        textHolderDiv.appendChild(anchor);
        textHolderDiv.appendChild(price);

        cartElement.appendChild(imageHolderDiv);
        cartElement.appendChild(textHolderDiv);
        cart.appendChild(cartElement);

        totalPrice = totalPrice + (products[k].quantity * products[k].currentPrice);
        totalQuantity += products[k].quantity;
    }
    cartQuantity.innerHTML = totalQuantity;
    totalPriceField.innerHTML = "Összesen: " + totalPrice + " Ft";
}

function emptyCartVisually() {
    let totalPriceField = document.querySelector(".header-cart-total");
    let cartQuantity = document.querySelector(".header-icons-noti");
    totalPriceField.innerHTML = "Összesen: 0 Ft";
    cartQuantity.innerHTML = 0;
    let cart = document.querySelector(".header-cart-wrapitem");
    while(cart.hasChildNodes) {
        cart.removeChild(cart.lastChild);
    }
}

function handleResetButton() {
    if (confirm("Biztos hogy üríteni szeretné a kosár tartalmát?")) {
        fetch("api/basket/", {
            method: "DELETE"
        }).then(function (response) {
            emptyCartVisually();
            return response.json()
        }).then(responseJson => updateTable())
    }
}

//    function createDivs() {
//        fetch("/api/products/lastsold")
//            .then(function (response) {
//                return response.json();
//            })
//            .then(function (jsonData) {
//                creatingLastSold(jsonData);
//            });
//    }
//

//    function creatingLastSold(productList) {
//
//        let containerDiv = document.querySelector(".container");
//        let mainDiv = document.createElement("div");
//        mainDiv.setAttribute("id", "main-div");
//        for (let i = 0; i < productList.length; i++) {
//            let product = productList[i];
//
//            let soldDiv = document.createElement("div");
//            soldDiv.setAttribute("id", "div-sold");
//
//            let nameDiv = document.createElement("h6");
//            nameDiv.innerHTML = product.name;
//            let newLine = document.createElement("br");
//            nameDiv.appendChild(newLine);
//
//            let categoryDiv = document.createElement("h6")
//            categoryDiv.innerHTML = product.producer;
//            nameDiv.appendChild(categoryDiv);
//
//            let imgDiv = document.createElement("img");
//            imgDiv.setAttribute("class", "book-image");
//            imgDiv.src = "data:image/png;base64, " + product.image;
//            categoryDiv.appendChild(imgDiv);
//
//            let priceDiv = document.createElement("h7");
//            priceDiv.innerHTML = product.currentPrice + " Ft";
//
//            nameDiv.appendChild(priceDiv);
//
//            soldDiv.appendChild(nameDiv);
//
//            let buttonDiv = document.createElement("div");
//            buttonDiv.setAttribute("id", "button-div");
//            let linkButton = document.createElement("button");
//            linkButton.setAttribute("id", "link-button");
//            linkButton.setAttribute("class", "btn-info");
//            linkButton.innerHTML = "Megnézem";
//            function visitPage() {
//                window.location = "/product.html?address=" + product.address;
//            }
//            linkButton.onclick = visitPage;
//            buttonDiv.appendChild(linkButton);
//            soldDiv.appendChild(buttonDiv);
//
//            mainDiv.appendChild(soldDiv);
//            containerDiv.appendChild(mainDiv);
//        }
//    }
//}