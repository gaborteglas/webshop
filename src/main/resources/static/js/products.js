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
    allOptionListElementAnchor.href = "#";
    allOptionListElement.appendChild(allOptionListElementAnchor);
    categoryList.appendChild(allOptionListElement);

    for (i in categories) {
        let option = document.createElement("li");
        option.className = "p-t-4";
        optionListElementAnchor = document.createElement("a");
        optionListElementAnchor.className = "s-text13";
        optionListElementAnchor.innerHTML = categories[i].name;
        optionListElementAnchor.href = "#";
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
        image.src = "images/item-02.jpg";
        image.alt = "IMG-PRODUCT";

        let blockOverlay = document.createElement("div");
        blockOverlay.className = "block2-overlay trans-0-4";
        let addToCartHolder = document.createElement("div");
         addToCartHolder.className = "block2-btn-addcart w-size1 trans-0-4";
         let addToCartButton = document.createElement("button");
         addToCartButton.className = "flex-c-m size1 bg4 bo-rad-23 hov1 s-text1 trans-0-4";
         addToCartButton.innerHTML = "Kosárba"

         let textHolder = document.createElement("div");
         textHolder.className = "block2-txt p-t-20";
         let textHolderAnchor = document.createElement("a");
         textHolderAnchor.href = "/product.html?address=" + product.address;
         textHolderAnchor.className = "block2-name dis-block s-text3 p-b-5";
         textHolderAnchor.innerHTML = product.producer + " - " + product.name;
         let priceSpan = document.createElement("span");
         priceSpan.className = "block2-price m-text6 p-r-5";
         priceSpan.innerHTML = product.currentPrice + " Ft";

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
