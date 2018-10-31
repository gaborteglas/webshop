window.onload = function() {
    updateTable();
    showBasketButton();
    let putIntoBasketButton = document.getElementById("puttobasket");
    putIntoBasketButton.onclick = handlePutIntoBasket;
    let ratingSubmitButton = document.getElementById("rating-submit");
    ratingSubmitButton.onclick = handleRatingSubmit;
}

function handlePutIntoBasket(){
    let productId = document.querySelector("#productId").innerHTML;
    let quantity = document.querySelector("#quantity").value;

    fetch("api/basket/" + productId + "/" + quantity, {
        method: "POST"
    }).then(function(response) {
        return response.json()
    }).then(function(jsonData) {
        alert(jsonData.message);
    });
    return false;
}

function updateTable() {
    let productNameFromUrl = new URL(window.location).searchParams.get("address");
    let productToFetch = "api/products/" + productNameFromUrl;
    fetch(productToFetch, {
        method: "GET"
    }).then(function(response) {
         return response.json();
    }).then(function(jsonData) {
        fillTable(jsonData);
    }).catch(error => creatingHeaderForName("Nincs ilyen termék"));
}

function fillTable(product){
    let name = product.name;
    let id = product.id;
    let producer = product.producer;
    let currentPrice = product.currentPrice;
    let categoryName = product.category.name;
    let feedbackList = product.feedbacks;

    creatingHeaderForName(name);
    creatingTableRowForData(id,producer,currentPrice, categoryName);
}

function creatingHeaderForName(name){
    let productName = document.querySelector("#product-name");
    productName.innerHTML = name;
}

function creatingTableRowForData(id,producer,currentPrice, categoryName){
    let tbody = document.querySelector("#product-tbody");
    let tr = document.createElement("tr");
    tr["raw-data"] = id;
    let idTd = document.createElement("td");
    let producerTd = document.createElement("td");
    let currentPriceTd = document.createElement("td");
    let categoryTd = document.createElement("td");
    idTd.innerHTML = id;
    idTd.setAttribute("id","productId")
    producerTd.innerHTML = producer;
    currentPriceTd.innerHTML = currentPrice + " Ft";
    categoryTd.innerHTML = categoryName;
    tr.appendChild(idTd);
    tr.appendChild(producerTd);
    tr.appendChild(currentPriceTd);
    tr.appendChild(categoryTd);
    tbody.appendChild(tr);
}

function updateFeedbacks() {
    let productNameFromUrl = new URL(window.location).searchParams.get("address");
    let productToFetch = "api/products/" + productNameFromUrl;
    fetch(productToFetch, {
        method: "GET"
    }).then(function(response) {
         return response.json();
    }).then(function(product) {
        creatingFeedbackFields(product.feedbacks);
    }).catch(error => creatingHeaderForName("Nincs ilyen termék"));
}

function creatingFeedbackFields(feedbackList) {

    let ratingsDiv = document.querySelector(".product-ratings");
    for (let i = 0; i < feedbackList.length; i++) {
        let feedbackDiv = document.createElement("div");
        let userNameTag = document.createElement("h5");
        userNameTag.setAttribute("id", "feedback-username")
        userNameTag.innerHTML = feedbackList[i].user.loginName;
        feedbackDiv.appendChild(userNameTag);

        let feedbackDate = document.createElement("p");
        feedbackDate.setAttribute("id", "feedback-date");
        feedbackDate.innerHTML = new Date(feedbackList[i].ratingDate).toLocaleString();
        feedbackDiv.appendChild(feedbackDate);

        let feedbackScore = document.createElement("p");
        feedbackScore.setAttribute("id", "feedback-score");
        feedbackScore.innerHTML = feedbackList[i].ratingScore;
        feedbackDiv.appendChild(feedbackScore);

        let feedbackText = document.createElement("p");
        feedbackText.setAttribute("id", "feedback-text");
        feedbackText.innerHTML = feedbackList[i].ratingText;
        feedbackDiv.appendChild(feedbackText);

        ratingsDiv.appendChild(feedbackDiv);
    }
}

function showBasketButton() {
      fetch("api/user")
              .then(function (response) {
                  return response.json();
              })
              .then(function(jsonData) {
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
    button.style.display = "block";
    button.setAttribute("style","display:block");

    let input = document.getElementById("quantity");
    input.style.display = "block";
    input.setAttribute("style","display:block");
}

function hideBasketButton() {
   let button1 = document.getElementById("puttobasket");
   button1.style.display = "none";

   let input = document.getElementById("quantity");
   input.style.display = "none";
}

function handleRatingSubmit() {
    let productId = document.querySelector("#productId").innerHTML;
    let ratingScoreInput = document.getElementById("rating-score");
    let ratingTextInput = document.getElementById("rating-textarea");
    let ratingScore = ratingScoreInput.value;
    let ratingText = ratingTextInput.value;

    let feedback = {
                    "ratingScore": ratingScore,
                    "ratingText": ratingText
                    }

    console.log(feedback);

    fetch("api/products/" + productId + "/feedback", {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=utf-8"
                    },
            body: JSON.stringify(feedback)
        }).then(function(response) {
            return response.json()
        }).then(function(response) {
            updateFeedbacks();
        });
    return false;
}