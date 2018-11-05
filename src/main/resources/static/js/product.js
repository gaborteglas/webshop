window.onload = function() {
    updateTable();
    showBasketButton();
    let putIntoBasketButton = document.getElementById("puttobasket");
    putIntoBasketButton.onclick = handlePutIntoBasket;
    let ratingSubmitButton = document.getElementById("rating-submit");
    ratingSubmitButton.onclick = handleRatingSubmit;
}

function handlePutIntoBasket(){
    let productId = document.querySelector("#product-id").innerHTML;
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
    })
    .catch(error => creatingHeaderForName("Nincs ilyen termék"));
}

function fillTable(product){
    let name = product.name;
    let id = product.id;
    let address = product.address;
    let producer = product.producer;
    let currentPrice = product.currentPrice;
    let categoryName = product.category.name;
    let feedbackList = product.feedbacks;
    let average = product.averageScore;
    let image = product.image;

    let nameDiv = document.getElementById("product-name");
    nameDiv.innerHTML = name;
    let idDiv = document.getElementById("product-id");
    idDiv.innerHTML = id;
    let producerDiv = document.getElementById("product-author");
    producerDiv.innerHTML = producer;
    let priceDiv = document.getElementById("product-price");
    priceDiv.innerHTML = currentPrice + " Ft";
    let categoryDiv = document.getElementById("product-category");
    categoryDiv.innerHTML = categoryName;
    let addressDiv = document.getElementById("product-address");
    addressDiv.innerHTML = "URL: " + address;
    let averageDiv = document.getElementById("product-average");
    if(average > 0) {
            averageDiv.innerHTML = "Átlag pontszám: " + Math.round(average * 100) / 100;
        } else {
            averageDiv.innerHTML = "";
        }
    let imageDiv = document.getElementById("image-src");
    imageDiv.src = "data:image/png;base64, " + image;

    creatingFeedbackFields(feedbackList);
}

function creatingHeaderNull(name, average){
    let productName = document.querySelector("#product-name");
    productName.innerHTML = name;
    let averageScore = document.querySelector("#product-average-rating-score");
    if(average > 0) {
        averageScore.innerHTML = "Átlag pontszám: " + Math.round(average * 100) / 100;
    } else {
        averageScore.innerHTML = "";
    }
}


function creatingFeedbackFields(feedbackList) {

    let ratingsDiv = document.querySelector(".product-ratings");
    ratingsDiv.innerHTML = "";
    if (feedbackList.length > 0) {
        let title = document.createElement("h5");
        title.innerHTML = "Vásárlói értékelések:"
        ratingsDiv.appendChild(title);
    }
    for (let i = 0; i < feedbackList.length; i++) {
        let feedbackDiv = document.createElement("div");
        feedbackDiv.setAttribute("id", "one-feedback-div");
        feedbackDiv["raw-data"] = feedbackList[i];

        let leftDiv = document.createElement("div");
        leftDiv.setAttribute("id", "left-div");

        let userNameTag = document.createElement("h5");
        userNameTag.setAttribute("id", "feedback-username")
        userNameTag.innerHTML = feedbackList[i].user.loginName;
        leftDiv.appendChild(userNameTag);

        let feedbackDate = document.createElement("p");
        feedbackDate.setAttribute("id", "feedback-date");
        feedbackDate.innerHTML = new Date(feedbackList[i].ratingDate).toLocaleString();
        leftDiv.appendChild(feedbackDate);

        let middleDiv = document.createElement("div");
        middleDiv.setAttribute("id", "middle-div");

        let feedbackScore = document.createElement("p");
        feedbackScore.setAttribute("id", "feedback-score");
        feedbackScore.innerHTML = " Értékelés pontszáma: " + feedbackList[i].ratingScore;
        middleDiv.appendChild(feedbackScore);

        let feedback = feedbackList[i].ratingText;
        feedback = feedback.replace(new RegExp("&", "g"), "&amp;");
        feedback = feedback.replace(new RegExp("<", "g"), "&lt;");
        feedback = feedback.replace(new RegExp(">", "g"), "&gt;");
        feedback = feedback.replace(new RegExp("\"", "g"), "&quot;");
        feedback = feedback.replace(new RegExp("'", "g"), "&apos");
        let feedbackText = document.createElement("p");
        feedbackText.setAttribute("id", "feedback-text");
        feedbackText.innerHTML = "Értékelés szövege: " + feedback;
        middleDiv.appendChild(feedbackText);

        let rightDiv = document.createElement("div");
        rightDiv.setAttribute("id", "right-div");

        if(feedbackList[i].canEditOrDelete === true) {
            let editButton = document.createElement("button");
            editButton.setAttribute("id", "edit-button");
            editButton.setAttribute("class", "btn btn-secondary");
            editButton.innerHTML = "Szerkesztés";
            editButton.onclick = handleRatingModifyButtonClick;
            let deleteButton = document.createElement("button");
            deleteButton.setAttribute("id", "delete-button");
            deleteButton.setAttribute("class", "btn btn-danger");
            deleteButton.innerHTML = "Törlés";
            deleteButton.onclick = handleRatingDelete;
            rightDiv.appendChild(editButton);
            rightDiv.appendChild(deleteButton);
        }
        feedbackDiv.appendChild(leftDiv);
        feedbackDiv.appendChild(middleDiv)
        feedbackDiv.appendChild(rightDiv);
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
    button.setAttribute("style","display:block");

    let input = document.getElementById("quantity");
    input.setAttribute("style","display:block");
}

function hideBasketButton() {
   let button1 = document.getElementById("puttobasket");
   button1.style.display = "none";

   let input = document.getElementById("quantity");
   input.style.display = "none";
}

function handleRatingSubmit() {
    let productId = document.querySelector("#product-id").innerHTML;
    let ratingScoreInput = document.getElementById("rating-score");
    let ratingTextInput = document.getElementById("rating-textarea");
    let ratingScore = ratingScoreInput.value;
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
        }).then(function(response) {
            return response.json()
        }).then(function(response) {
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
            .then(function(response) {
                return response.json()
            })
            .then(function(response) {
                updateTable();
            });
        }
}

function handleRatingModifyButtonClick() {
    let modifyButton = document.getElementById("rating-submit");
    modifyButton.innerHTML = "Értékelés módosítása";
    modifyButton.onclick = handleModify;

    let feedback = this.parentElement.parentElement["raw-data"];

    let ratingInput = document.getElementById("rating-score");
    ratingInput.value = feedback.ratingScore;
    let textInput = document.getElementById("rating-textarea");
    textInput.value = feedback.ratingText;
}

function handleModify() {
    let ratingScoreInput = document.getElementById("rating-score");
    let ratingTextInput = document.getElementById("rating-textarea");
    let ratingScore = ratingScoreInput.value;
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
        }).then(function(response) {
            return response.json()
        }).then(function(response) {
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
    for(let i = 0; i < starHolders.length; i++) {
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

