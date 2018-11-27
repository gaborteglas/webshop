window.addEventListener('load', setUserName());
window.addEventListener('load', updateCart());
window.addEventListener('load', activateCartResetButton());

function activateCartResetButton() {
    let cartResetButton = document.querySelector("#basket-reset-button");
    cartResetButton.onclick = handleResetButton;
}

function setUserName() {
    fetch("api/user")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            showUser(jsonData)
            if (jsonData.role == "ROLE_ADMIN") {
                showForAdmin();
            }
            if (jsonData.role == "ROLE_USER") {
                showForUser();
            }
        })
        .catch(error => showUserNotLogged("Vendég"), showForUnregistered());
}

function showUser(jsonData) {
    let userDiv = document.getElementById("username");
    userDiv.innerHTML = jsonData.fullName;
    let userId = document.querySelector("#id-hidden-input");
    userId.value = jsonData.id;
}

function showUserNotLogged(name) {
    let userDiv = document.getElementById("username");
    userDiv.innerHTML = name
}

function showForUser() {
    let menuToShow1 = document.getElementById("to-hide-allproducts");
    menuToShow1.style.display = "block";
    let menuToShow2 = document.getElementById("to-hide-basket");
    menuToShow2.style.display = "block";
    let menuToShow3 = document.getElementById("to-hide-login");
    menuToShow3.style.display = "none";
    let menuToHide9 = document.getElementById("to-hide-product");
    let menuToShow21 = document.getElementById("to-hide-myorder");
    menuToShow21.style.display = "block";
    menuToHide9.style.display = "none";
    let menuToHide10 = document.getElementById("to-hide-user");
    menuToHide10.style.display = "none"
    let menuToShow8 = document.getElementById("to-hide-logout");
    menuToShow8.style.display = "block";
    let menuToHideOrdersForUser = document.getElementById("to-hide-orders");
    menuToHideOrdersForUser.style.display = "none";
    let menuToHideDashboardForUser = document.getElementById("to-hide-dashboard");
    menuToHideDashboardForUser.style.display = "none";
    let menuToHideReportsForUser = document.getElementById("to-hide-reports");
    menuToHideReportsForUser.style.display = "none";
    let menuToHideCategoriesForUser = document.getElementById("to-hide-categories");
    menuToHideCategoriesForUser.style.display = "none";
    let menuToShowIndexUser = document.getElementById("to-hide-index");
    menuToShowIndexUser.style.display = "block";
    let menuToShowProfileForUser = document.getElementById("to-hide-profile");
    menuToShowProfileForUser.style.display = "block";
    let basketIcon = document.querySelector(".header-wrapicon2");
    basketIcon.classList.remove("disabled");

    let avatarSmall = document.querySelector(".header-icon1");
    avatarSmall.src = "images/icons/avatar_mozes.png"

}

function showForAdmin() {
    let menuToHide6 = document.getElementById("to-hide-basket");
    menuToHide6.style.display = "none";
    let menuToHide7 = document.getElementById("to-hide-allproducts");
    menuToHide7.style.display = "none";
    let menuToHide8 = document.getElementById("to-hide-login");
    menuToHide8.style.display = "none";
    menuToShow4 = document.getElementById("to-hide-product");
    menuToShow4.style.display = "block";
    let menuToShow5 = document.getElementById("to-hide-user");
    menuToShow5.style.display = "block";
    let menuToShow9 = document.getElementById("to-hide-logout");
    menuToShow9.style.display = "block";
    let menuToHide21 = document.getElementById("to-hide-myorder");
    menuToHide21.style.display = "none";
    let menuToHideOrdersForAdmin = document.getElementById("to-hide-orders");
    menuToHideOrdersForAdmin.style.display = "block"
    let menuToShowDashboardForAdmin = document.getElementById("to-hide-dashboard");
    menuToShowDashboardForAdmin.style.display = "block"
    let menuToShowReportsForAdmin = document.getElementById("to-hide-reports");
    menuToShowReportsForAdmin.style.display = "block"
    let menuToShowCategoriesForAdmin = document.getElementById("to-hide-categories");
    menuToShowCategoriesForAdmin.style.display = "block"
    let menuToHideIndexAdmin = document.getElementById("to-hide-index");
    menuToHideIndexAdmin.style.display = "none";
    let menuToShowProfileForUser = document.getElementById("to-hide-profile");
    menuToShowProfileForUser.style.display = "none";
    let basketIcon = document.querySelector(".header-wrapicon2");
    basketIcon.setAttribute("style", "display: none");
    let lineDivide = document.querySelector(".linedivide1");
    lineDivide.setAttribute("style", "display: none");

    let avatarSmall = document.querySelector(".header-icon1");
    avatarSmall.src = "images/icons/avatar_dori.png"

}

function showForUnregistered() {
    let menuToShow6 = document.getElementById("to-hide-allproducts");
    menuToShow6.style.display = "block";
    let menuToShow7 = document.getElementById("to-hide-login");
    menuToShow7.style.display = "block";
    let menuToHide11 = document.getElementById("to-hide-product");
    menuToHide11.style.display = "none";
    let menuToHide12 = document.getElementById("to-hide-user");
    menuToHide12.style.display = "none";
    let menuToHide13 = document.getElementById("to-hide-basket");
    menuToHide13.style.display = "none";
    let menuToHide14 = document.getElementById("to-hide-logout");
    menuToHide14.style.display = "none";
    let menuToHide31 = document.getElementById("to-hide-myorder");
    menuToHide31.style.display = "none";
    let menuToHideOrdersForUnreg = document.getElementById("to-hide-orders");
    menuToHideOrdersForUnreg.style.display = "none";
    let menuToHideDashboardForUnreg = document.getElementById("to-hide-dashboard");
    menuToHideDashboardForUnreg.style.display = "none";
    let menuToHideReportsForUnreg = document.getElementById("to-hide-reports");
    menuToHideReportsForUnreg.style.display = "none";
    let menuToHideCategoriesForUnreg = document.getElementById("to-hide-categories");
    menuToHideCategoriesForUnreg.style.display = "none";
    let menuToShowIndexUnreg = document.getElementById("to-hide-index");
    menuToShowIndexUnreg.style.display = "block";
    let menuToShowProfileForUser = document.getElementById("to-hide-profile");
    menuToShowProfileForUser.style.display = "none";
    let basketIcon = document.querySelector(".header-wrapicon2");
    basketIcon.classList.add("disabled");

    let avatarSmall = document.querySelector(".header-icon1");
    avatarSmall.src = "images/icons/icon-header-01.png"
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
        basketIcon.click();
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

function handleResetButton() {
    if (confirm("Biztos hogy üríteni szeretné a kosár tartalmát?")) {
        fetch("api/basket/", {
            method: "DELETE"
        }).then(function (response) {
            return response.json()
        }).then(responseJson => updateCart())
    }
}

function clickingOnResetProductButtons(clickEvent) {
    let deleteButtonId = this.getAttribute("id");
    let productId = parseInt(deleteButtonId.substring(deleteButtonId.indexOf("Id") + 2));
    let url = "api/basket/" + productId;
    if (confirm("Biztos szeretné törölni ezt az elemet a kosárból?")) {
        fetch(url, {
            method: "DELETE"
        }).then(function (response) {
            return response.json()
        }).then(responseJson => updateCart())
    }
}