window.onload = function () {
    createDivs();
    activateAddToBasketButtons();
}

function createDivs() {
    fetch("/api/products/lastsold")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            creatingLastSold(jsonData);
        });
}

function activateAddToBasketButtons() {
    let addToBasketButtons = document.querySelectorAll(".add-to-basket");
    for (let i = 0; i < addToBasketButtons.length; i++) {
        addToBasketButtons[i].addEventListener("click", function () {
            let productId = this.parentElement.parentElement.parentElement.parentElement.id;
            productId = productId.substring(productId.indexOf("-") + 1);
            addToBasket(productId);
        });
    }
}

function creatingLastSold(productList) {

    for (let i = 1; i < productList.length + 1; i++) {
        let product = productList[i - 1];

        let name = "name-" + i;
        let nameDiv = document.getElementById(name);
        nameDiv.innerHTML = product.name;
        nameDiv.href = "/product.html?address=" + product.address;

        let price = "price-" + i;
        let priceDiv = document.getElementById(price);
        priceDiv.innerHTML = product.currentPrice.toLocaleString() + " Ft";

        let image = "picture-" + i;
        let imageDiv = document.getElementById(image);
        imageDiv.src = "data:image/png;base64, " + product.image;
        imageDiv.parentElement.parentElement.id = "productid-" + product.id;
    }
}

function addToBasket(productId) {
    fetch("api/basket/" + productId + "/" + 1, {
        method: "POST"
    }).then(function (response) {
        return response.json()
    }).then(function (jsonData) {
        giveFeedbackToUser(jsonData.message);
        updateCart();
    });
    return false;
}

function updateCart() {
    fetch("api/basket")
        .then(function (response) {
            return response.json();
        })
        .then(function (products) {
            fillCart(products);
        });
}

function fillCart(products) {
    alert("a");
    let totalPrice = 0
    let totalQuantity = 0;
    let cart = document.querySelector(".header-cart-wrapitem");
    cart.innerHTML = "";
    let totalPriceField = document.querySelector(".header-cart-total");
    let cartQuantity = document.querySelector(".header-icons-noti");
    if (products.length === 0) {
        let basketIcon = document.querySelector(".header-wrapicon2");
        basketIcon.classList.add("disabled");
    } else {
        let basketIcon = document.querySelector(".header-wrapicon2");
        basketIcon.classList.remove("disabled");
        for (let k = 0; k < products.length; k++) {
            let cartElement = document.createElement("li");
            cartElement.className = "header-cart-item";

            let imageHolderDiv = document.createElement("div");
            imageHolderDiv.className = "header-cart-item-img";
            imageHolderDiv.id = "deleteButtonId" + products[k].id;
            imageHolderDiv.onclick = clickingOnResetProductButtons;

            let imageOfProduct = document.createElement("img");
            imageOfProduct.src = "data:image/png;base64, " + products[k].image;
            imageOfProduct.alt = "IMG";

            imageHolderDiv.appendChild(imageOfProduct);

            let textHolderDiv = document.createElement("div");
            textHolderDiv.className = "header-cart-item-txt";

            let anchor = document.createElement("a");
            anchor.href = "/product.html?address=" + products[k].address;
            anchor.className = "header-cart-item-name";
            anchor.innerHTML = products[k].name;

            let price = document.createElement("span");
            price.className = "header-cart-item-info";
            price.id = "header-cart-price-" + products[k].id;
            price.innerHTML = products[k].quantity + " x " + products[k].currentPrice.toLocaleString() + " Ft";

            textHolderDiv.appendChild(anchor);
            textHolderDiv.appendChild(price);

            cartElement.appendChild(imageHolderDiv);
            cartElement.appendChild(textHolderDiv);
            cart.appendChild(cartElement);

            totalPrice = totalPrice + (products[k].quantity * products[k].currentPrice);
            totalQuantity += products[k].quantity;
        }
        totalPriceField.innerHTML = "Összesen: " + totalPrice.toLocaleString() + " Ft";
    }
    cartQuantity.innerHTML = totalQuantity;
}

function giveFeedbackToUser(message) {
if (document.querySelector("#to-basket-feedback-message") !== null) {
        document.querySelector("#to-basket-feedback-message").innerHTML = message;
    } else {
        let container = document.querySelector(".container");
        let feedbackText = document.createElement("p");
        feedbackText.className = "s-text8 p-t-70 row-center-text";
        feedbackText.id = "to-basket-feedback-message";
        feedbackText.innerHTML = message;
        container.appendChild(feedbackText);
    }
}