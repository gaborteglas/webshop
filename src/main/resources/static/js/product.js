window.onload = function () {
    updateTable();
    showBasketButton();
    let putIntoBasketButton = document.getElementById("puttobasket");
    putIntoBasketButton.onclick = handlePutIntoBasket;
    //    let ratingSubmitButton = document.getElementById("rating-submit");
    //    ratingSubmitButton.onclick = handleRatingSubmit;
}

function handlePutIntoBasket() {
    let productId = document.querySelector("#product-id").innerHTML;
    let quantity = document.querySelector("#quantity-number").value;
    productId = productId.substring(productId.indexOf(":") + 2);

    fetch("api/basket/" + productId + "/" + quantity, {
        method: "POST"
    }).then(function (response) {
        return response.json()
    }).then(function (jsonData) {
        giveFeedbackToUser(jsonData.message);
        updateCart();
    });
    return false;
}

function updateTable() {
    let productNameFromUrl = new URL(window.location).searchParams.get("address");
    let productToFetch = "api/products/" + productNameFromUrl;
    fetch(productToFetch, {
        method: "GET"
    }).then(function (response) {
        return response.json();
    }).then(function (jsonData) {
        fillTable(jsonData);
    })
        .catch(error => creatingHeaderNull());
}

function fillTable(product) {
    let name = product.name;
    let id = product.id;
    let address = product.address;
    let producer = product.producer;
    let currentPrice = product.currentPrice;
    let categoryName = product.category.name;
    let feedbackList = product.feedbacks;
    let average = product.averageScore;
    let image = product.image;

    let nameSpan = document.getElementById("product-name-span");
    nameSpan.innerHTML = name;
    let nameH4 = document.getElementById("product-name-h4");
    nameH4.innerHTML = name;
    let idSpan = document.getElementById("product-id");
    idSpan.innerHTML = "Id: " + id;
    let producerH5 = document.getElementById("product-producer-h5");
    producerH5.innerHTML = producer;
    let priceSpan = document.getElementById("price-span");
    priceSpan.innerHTML = currentPrice.toLocaleString() + " Ft";
    let categorySpan = document.getElementById("product-category");
    categorySpan.innerHTML = "Kategória: " + categoryName;
    let addressSpan = document.getElementById("product-address");
    addressSpan.innerHTML = "URL: " + address;
    let averageSpan = document.getElementById("product-average");
    if (average > 0) {
        averageSpan.innerHTML = "Átlag pontszám: " + Math.round(average * 100) / 100;
        let starAverageStarHolder = document.createElement("span");
        starAverageStarHolder.style.color = "#ffca00";
        let averageStar = document.createElement("i");
        averageStar.className = "fa fa-star p-l-3";
        starAverageStarHolder.appendChild(averageStar);
        averageSpan.appendChild(starAverageStarHolder);
    } else {
        averageSpan.innerHTML = "";
    }
    let imageDiv = document.getElementById("image-holder-div");
    let imageTag = document.createElement("img");
    imageTag.src = "data:image/png;base64, " + image;
    imageTag.alt = "IMG-PRODUCT";
    imageDiv.appendChild(imageTag);

    creatingFeedbackFields(feedbackList);

    let categoryHrefDiv = document.getElementById("category-href");
    categoryHrefDiv.innerHTML = categoryName;
}

function creatingHeaderNull() {
    let ulDiv = document.getElementById("to-append");
    let noProduct = document.getElementById("if-hided");
    noProduct.innerHTML = "Sajnáljuk, nincs ilyen termék!"
    ulDiv.appendChild(noProduct);
    let divToHide = document.querySelector(".container-2");
    divToHide.style.display = "none";
    let feedbackToHide = document.getElementById("feedback-hide");
    feedbackToHide.style.display = "none";
}

function creatingFeedbackFields(feedbackList) {

    let reviewCount = document.querySelector("#reviews-count");
    reviewCount.innerHTML = "Értékelések (" + feedbackList.length + ")";
    let reviewDiv = document.querySelector("#single-review");
    for (let i = 0; i < feedbackList.length; i++) {
        let reviewAuthor = document.createElement("p");
        reviewAuthor.className = "s-text8";
        reviewAuthor.innerHTML = feedbackList[i].user.loginName + ": " + feedbackList[i].ratingScore;
        let starAverageStarHolder = document.createElement("span");
        starAverageStarHolder.style.color = "#ffca00";
        let averageStar = document.createElement("i");
        averageStar.className = "fa fa-star p-l-3";
        starAverageStarHolder.appendChild(averageStar);
        reviewAuthor.appendChild(starAverageStarHolder);
        let reviewDate = document.createElement("p");
        reviewDate.className = "s-text8";
        reviewDate.innerHTML = new Date(feedbackList[i].ratingDate).toLocaleString();
        let reviewText = document.createElement("p");
        let quotation = document.createElement("q");
        reviewText.className = "s-text8";
        quotation.innerHTML = feedbackList[i].ratingText;
        reviewText.appendChild(quotation);
        reviewDiv.appendChild(reviewAuthor);
        reviewDiv.appendChild(reviewText);
        reviewDiv.appendChild(reviewDate);

        let breakLine = document.createElement("br");
        reviewDiv.appendChild(breakLine);
    }
    //        let feedbackDiv = document.createElement("div");
    //        feedbackDiv.setAttribute("id", "one-feedback-div");
    //        feedbackDiv["raw-data"] = feedbackList[i];
    //
    //        let leftDiv = document.createElement("div");
    //        leftDiv.setAttribute("id", "left-div");
    //
    //        let userNameTag = document.createElement("h5");
    //        userNameTag.setAttribute("id", "feedback-username")
    //        userNameTag.innerHTML = feedbackList[i].user.loginName;
    //        leftDiv.appendChild(userNameTag);
    //
    //        let feedbackDate = document.createElement("p");
    //        feedbackDate.setAttribute("id", "feedback-date");
    //        feedbackDate.innerHTML = new Date(feedbackList[i].ratingDate).toLocaleString();
    //        leftDiv.appendChild(feedbackDate);
    //
    //        let middleDiv = document.createElement("div");
    //        middleDiv.setAttribute("id", "middle-div");
    //
    //        let ratingStarDiv = document.createElement("div");
    //        ratingStarDiv.setAttribute("class", "rating-star-div");
    //        for (let j = 0; j < feedbackList[i].ratingScore; j++) {
    //            let star = document.createElement("div");
    //            star.setAttribute("class", "star");
    //            star.setAttribute("style", "background: url(\"/img/star-solid-yellow.svg\") no-repeat;");
    //            ratingStarDiv.appendChild(star);
    //        }
    //        middleDiv.appendChild(ratingStarDiv);
    //        middleDiv.appendChild(document.createElement("br"));
    //
    //        let feedback = feedbackList[i].ratingText;
    //        feedback = feedback.replace(new RegExp("&", "g"), "&amp;");
    //        feedback = feedback.replace(new RegExp("<", "g"), "&lt;");
    //        feedback = feedback.replace(new RegExp(">", "g"), "&gt;");
    //        feedback = feedback.replace(new RegExp("\"", "g"), "&quot;");
    //        feedback = feedback.replace(new RegExp("'", "g"), "&apos");
    //        let feedbackText = document.createElement("p");
    //        feedbackText.setAttribute("id", "feedback-text");
    //        feedbackText.innerHTML = feedback;
    //        middleDiv.appendChild(feedbackText);
    //
    //        let rightDiv = document.createElement("div");
    //        rightDiv.setAttribute("id", "right-div");
    //
    //        if(feedbackList[i].canEditOrDelete === true) {
    //            let editButton = document.createElement("button");
    //            editButton.setAttribute("id", "edit-button");
    //            editButton.setAttribute("class", "btn btn-secondary");
    //            editButton.innerHTML = "Szerkesztés";
    //            editButton.onclick = handleRatingModifyButtonClick;
    //            let deleteButton = document.createElement("button");
    //            deleteButton.setAttribute("id", "delete-button");
    //            deleteButton.setAttribute("class", "btn btn-danger");
    //            deleteButton.innerHTML = "Törlés";
    //            deleteButton.onclick = handleRatingDelete;
    //            rightDiv.appendChild(editButton);
    //            rightDiv.appendChild(deleteButton);
    //        }
    //        feedbackDiv.appendChild(leftDiv);
    //        feedbackDiv.appendChild(middleDiv)
    //        feedbackDiv.appendChild(rightDiv);
    //        ratingsDiv.appendChild(feedbackDiv);
    //    }
}

function showBasketButton() {
    fetch("api/user")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            if (jsonData.role == "ROLE_USER") {
                switchBasketButton();
            }
            if (jsonData.role == "ROLE_ADMIN") {
                hideBasketButton();
            }
        })
        .catch(error => hideBasketButton());
}

function switchBasketButton() {
    let button = document.getElementById("puttobasket");
    button.setAttribute("style", "visibility:visible");

    let input = document.getElementById("quantity");
    input.setAttribute("style", "visibility:visible");
}

function hideBasketButton() {
    let button1 = document.getElementById("puttobasket");
    button1.style.visibility = "hidden";

    let input = document.getElementById("quantity");
    input.style.visibility = "hidden";
}

function handleRatingSubmit() {
    let productId = document.querySelector("#product-id").innerHTML;
    let ratingTextInput = document.getElementById("rating-textarea");
    let ratingText = ratingTextInput.value;

    if (ratingText.trim().length === 0 && score === null) {
        alert("Kérjük, szövegesen és csillaggal is értékelje a terméket!");
        return;
    } else if (ratingText.trim().length === 0) {
        alert("Kérjük, szövegesen is értékelje a terméket!");
        return;
    } else if (score === null) {
        alert("Kérjük, csillaggal is értékelje a terméket!");
        return;
    }

    let feedback = {
        "ratingScore": score,
        "ratingText": ratingText
    }

    console.log(feedback);

    fetch("api/products/" + productId + "/feedback", {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
        body: JSON.stringify(feedback)
    }).then(function (response) {
        return response.json()
    }).then(function (response) {
        alert(response.message);
        updateTable();
        ratingTextInput.value = "";
        starReset();
    });
    return false;
}

function handleRatingDelete() {
    let productId = document.querySelector("#product-id").innerHTML;

    var result = confirm("Biztosan törli a kijelölt értékelést?");
    if (result) {
        fetch("api/products/" + productId + "/feedback", {
            method: "DELETE",
        })
            .then(function (response) {
                return response.json()
            })
            .then(function (response) {
                updateTable();
            });
    }
}

function handleRatingModifyButtonClick() {
    let modifyButton = document.getElementById("rating-submit");
    modifyButton.innerHTML = "Értékelés módosítása";
    modifyButton.onclick = handleModify;

    let feedback = this.parentElement.parentElement["raw-data"];

    if (feedback.ratingScore == 5) {
        fiveStarOnclick();
    } else if (feedback.ratingScore == 4) {
        fourStarOnclick();
    } else if (feedback.ratingScore == 3) {
        threeStarOnclick();
    } else if (feedback.ratingScore == 2) {
        twoStarOnclick();
    } else {
        oneStarOnclick();
    }

    let textInput = document.getElementById("rating-textarea");
    textInput.value = feedback.ratingText;
}

function handleModify() {
    let ratingTextInput = document.getElementById("rating-textarea");
    let ratingText = ratingTextInput.value;

    let modifyButton = document.getElementById("rating-submit");
    let productId = document.querySelector("#product-id").innerHTML;

    if (ratingText.trim().length === 0 && score === null) {
        alert("Kérjük, hogy szövegesen és csillaggal is értékelje a terméket!");
        return;
    } else if (ratingText.trim().length === 0) {
        alert("Kérjük, hogy szövegesen is értékelje a terméket!");
        return;
    } else if (score === null) {
        alert("Kérjük, hogy csillaggal is értékelje a terméket!");
        return;
    }

    let feedback = {
        "ratingScore": score,
        "ratingText": ratingText
    }


    fetch("api/products/" + productId + "/edit-feedback", {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
        body: JSON.stringify(feedback)
    }).then(function (response) {
        return response.json()
    }).then(function (response) {
        alert(response.message);
        updateTable();
        ratingTextInput.value = "";
        starReset();
        modifyButton.onclick = handleRatingSubmit;
        modifyButton.innerHTML = "Értékelés elküldése";
    });
    return false;
}

let score = null;

function starReset() {
    score = null;
    let starHolders = document.querySelectorAll(".star-holder");
    for (let i = 0; i < starHolders.length; i++) {
        starHolders[i].classList.remove("selected");
    }
}

function oneStarOnclick() {
    starReset();
    let oneStar = document.getElementById("star-1");
    oneStar.parentElement.classList.add("selected");
    score = 1;
}

function twoStarOnclick() {
    oneStarOnclick();
    let twoStar = document.getElementById("star-2");
    twoStar.parentElement.classList.add("selected");
    score = 2;
}

function threeStarOnclick() {
    twoStarOnclick();
    let threeStar = document.getElementById("star-3");
    threeStar.parentElement.classList.add("selected");
    score = 3;
}

function fourStarOnclick() {
    threeStarOnclick();
    let fourStar = document.getElementById("star-4");
    fourStar.parentElement.classList.add("selected");
    score = 4;
}

function fiveStarOnclick() {
    fourStarOnclick();
    let fiveStar = document.getElementById("star-5");
    fiveStar.parentElement.classList.add("selected");
    score = 5;
}

function giveFeedbackToUser(message) {
    if (document.querySelector("#to-basket-feedback-message") !== null) {
        document.querySelector("#to-basket-feedback-message").innerHTML = message;
    } else {
        let basketButtonHolder = document.querySelector("#basket-button-holder");
        let feedbackText = document.createElement("p");
        feedbackText.className = "s-text8 p-t-10";
        feedbackText.id = "to-basket-feedback-message";
        feedbackText.innerHTML = message;
        basketButtonHolder.appendChild(feedbackText);
    }
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

