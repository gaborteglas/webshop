window.onload = function () {
    updateTable();
    getCategories();
};

function getCategories() {
    fetch("/api/categories")
        .then(function (response) {
            return response.json();
        })
        .then(function (categories) {
            fillSelectWithCategories(categories);
        });
}

function fillSelectWithCategories(categories) {
    let categoryList = document.querySelector("#category-list");
    let allOptionListElement = document.createElement("li");
    allOptionListElement.className = "p-t-4";
    allOptionListElementAnchor = document.createElement("a");
    allOptionListElementAnchor.className = "s-text13 active1";
    allOptionListElementAnchor.innerHTML = "Összes";
    allOptionListElementAnchor.addEventListener("click", function () { updateTable() }, false);
    allOptionListElement.appendChild(allOptionListElementAnchor);
    categoryList.appendChild(allOptionListElement);

    for (i in categories) {
        let option = document.createElement("li");
        option.className = "p-t-4";
        optionListElementAnchor = document.createElement("a");
        optionListElementAnchor.className = "s-text13";
        optionListElementAnchor.innerHTML = categories[i].name;
        let catId = categories[i].id;
        option.onclick = function () { updateTable(catId) }
        option.appendChild(optionListElementAnchor);
        categoryList.appendChild(option);
    }
    //    select.addEventListener("change", function () { updateTable(this.value) })
}

function updateTable(categoryId) {
    let url = "";
    if (categoryId === undefined || categoryId === "Összes") {
        url = "api/products";
    } else {
        url = "api/products/category/" + categoryId;
    }
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            fillTable(jsonData);
        });
}

function fillTable(products) {
    let productContainer = document.getElementById("product-container");
    productContainer.innerHTML = "";
    //törölni while-lal
    //    let categorySelector = document.querySelector("#category-selector");
    //    let value = categorySelector.value;

    for (let i = 0; i < products.length; i++) {
        let product = products[i];
        let productDiv = document.createElement("div");
        productDiv.className = "col-sm-12 col-md-6 col-lg-4 p-b-50";

        let block = document.createElement("div");
        block.className = "block2";
        let imageHolder = document.createElement("div");
        imageHolder.className = "block2-img wrap-pic-w of-hidden pos-relative";
        let image = document.createElement("img");
        image.src = "data:image/png;base64, " + product.image;
        image.alt = "IMG-PRODUCT";

        let blockOverlay = document.createElement("div");
        blockOverlay.className = "block2-overlay trans-0-4";
        let addToCartHolder = document.createElement("div");
        addToCartHolder.className = "block2-btn-addcart w-size1 trans-0-4";
        let addToCartButton = document.createElement("button");
        addToCartButton.className = "flex-c-m size1 bg4 bo-rad-23 hov1 s-text1 trans-0-4 add-to-basket";
        addToCartButton.id = "productid-" + product.id;
        addToCartButton.onclick = activateAddToBasketButtons;
        addToCartButton.innerHTML = "Kosárba"

        let textHolder = document.createElement("div");
        textHolder.className = "block2-txt p-t-20";
        let textHolderAnchor = document.createElement("a");
        textHolderAnchor.href = "/product.html?address=" + product.address;
        textHolderAnchor.className = "block2-name dis-block s-text3 p-b-5";
        textHolderAnchor.innerHTML = product.producer + " - " + product.name;
        let priceSpan = document.createElement("span");
        priceSpan.className = "block2-price m-text6 p-r-5";
        priceSpan.innerHTML = product.currentPrice.toLocaleString() + " Ft";
        let feedbackText = document.createElement("span");
        let classNameWithProductId = "feedback-" + product.id;
        feedbackText.className = "s-text8 p-t-10 " + classNameWithProductId;
        feedbackText.id = "to-basket-feedback-message";
        let br = document.createElement("br");
        priceSpan.appendChild(br);
        priceSpan.appendChild(feedbackText);

        textHolder.appendChild(textHolderAnchor);
        textHolder.appendChild(priceSpan);
        addToCartHolder.appendChild(addToCartButton);
        blockOverlay.appendChild(addToCartHolder);
        imageHolder.appendChild(image);
        imageHolder.appendChild(blockOverlay);
        block.appendChild(imageHolder);
        block.appendChild(textHolder);
        productDiv.appendChild(block);
        productContainer.appendChild(productDiv);
    }
}

function activateAddToBasketButtons() {
    let productId = this.id;
    productId = productId.substring(productId.indexOf("-") + 1);
    addToBasket(productId);
}

function addToBasket(productId) {
    fetch("api/basket/" + productId + "/" + 1, {
        method: "POST"
    }).then(function (response) {
        return response.json()
    }).then(function (jsonData) {
        giveFeedbackToUser(jsonData.message, productId);
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

function giveFeedbackToUser(message, productId) {
        classToSelect = ".feedback-" +  productId;
        let spanToSelect = document.querySelector(classToSelect);
        spanToSelect.innerHTML = message;
}
