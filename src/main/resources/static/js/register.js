window.onload = function() {
    let registerForm = document.getElementById("register-form");
    productForm.onsubmit = handleSubmit;
    productForm.onreset = handleReset;
}

function handleSubmit() {
    let lastNameInput = document.getElementById("last-name");
    let firstNameInput = document.getElementById("first-name");
    let usernameInput = document.getElementById("username");
    let passwordInput = document.getElementById("password");
    let passwordRepeatInput = document.getElementById("password-repeat");

    let fullName = lastNameInput.value + " " + firstNameInput.value;
    let username = usernameInput.value;
    let password = passwordInput.value;
    let passwordRepeat = passwordRepeatInput.value;

    if (password !== passwordRepeat) {
        alert("A két jelszó nem egyezik meg.")
    }

    let user = {
                "loginName": username,
                "fullName": fullName,
                "password": password
                }

    fetch("api/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=utf-8"
                    },
            body: JSON.stringify(user)
    }).then(function(response) {
        handleReset();
    }
    });

}

function handleReset() {
    let lastNameInput = document.getElementById("last-name");
    lastNameInput = "";

    let firstNameInput = document.getElementById("first-name");
    firstNameInput = "";

    let usernameInput = document.getElementById("username");
    usernameInput.value = "";

    let passwordInput = document.getElementById("password");
    passwordInput.value = "";

    let passwordRepeatInput = document.getElementById("password-repeat");
    passwordRepeatInput.value = "";
}