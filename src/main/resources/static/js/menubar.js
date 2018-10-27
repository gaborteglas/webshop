window.addEventListener('load', setUserName());

function setUserName() {
     fetch("api/user")
             .then(function (response) {
                 return response.json();
             })
             .then(function(jsonData) {
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
userId.innerHTML = jsonData.id;
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
}