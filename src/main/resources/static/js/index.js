window.onload = function () {
    createDivs();
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


    function creatingLastSold(productList) {

        for (let i = 1; i < productList.length + 1; i++) {
                let product = productList[i-1];

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
        }
}