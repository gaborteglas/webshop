window.onload = function () {
    updateTable();

};

function updateTable() {
    fetch("/dashboard")
        .then(function (response) {
            return response.json();
        })
        .then(function (dashboard) {
            fillTable(dashboard);
            console.log(dashboard);
        });
}

function fillTable(dashboard) {
    let userCount = document.querySelector(".user-count");
    userCount.innerHTML = dashboard.userCount;

    let activeOrderCount = document.querySelector(".active-order-count");
    activeOrderCount.innerHTML = dashboard.activeOrderCount;

    let activeProductCount = document.querySelector(".active-product-count");
    activeProductCount.innerHTML = dashboard.activeProductCount;

    let orderCount = document.querySelector(".order-count");
    orderCount.innerHTML = dashboard.orderCount;

    let productCount = document.querySelector(".product-count");
    productCount.innerHTML = dashboard.productCount;

}