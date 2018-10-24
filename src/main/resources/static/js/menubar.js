window.addEventListener('load', setUserName);

function setUserName() {
     fetch("api/user")
             .then(function (response) {
                 return response.json();
             })
             .then(function(jsonData) {
                 showUser(jsonData);
                 if (jsonData.role == "ROLE_ADMIN") {
                 showMenus();
                 }
                 if (jsonData.role == "ROLE_CUSTOMER") {
                 hideMenus();
                 }
             })
             .catch(error => showUserNotLogged("Vendég"), hideMenus());
}

function showUser(jsonData) {
let userDiv = document.getElementById("username");
userDiv.innerHTML = jsonData.loginName
let userId = document.querySelector("#id-hidden-input");
userId.innerHTML = jsonData.id;
}

function showUserNotLogged(name) {
let userDiv = document.getElementById("username");
userDiv.innerHTML = name
}

function hideMenus() {
let menuToHide1 = document.getElementById("to-hide-product");
menuToHide1.style.display = "none";
let menuToHide2 = document.getElementById("to-hide-user");
menuToHide2.style.display = "none";
}

function showMenus() {
let menuToHide1 = document.getElementById("to-hide-product");
menuToHide1.style.display = "block";
let menuToHide2 = document.getElementById("to-hide-user");
menuToHide2.style.display = "block";
}