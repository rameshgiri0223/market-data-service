const loginForm =
    document.getElementById("loginForm");


loginForm.addEventListener(
    "submit",
    function (event) {

        event.preventDefault();


        const username =
            document.getElementById("username").value;

        const password =
            document.getElementById("password").value;


        const errorMessage =
            document.getElementById("errorMessage");


        errorMessage.textContent = "";


        fetch("/api/auth/login", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                userName: username,
                password: password
            })

        })

        .then(response => {

            if (!response.ok) {
                throw new Error(
                    "Login request failed"
                );
            }

            return response.json();
        })

        .then(data => {

            if (data.success) {

                window.location.href =
                    "/index.html";

            } else {

                errorMessage.textContent =
                    data.message ||
                    "Invalid username or password";
            }
        })

        .catch(error => {

            console.error(
                "Login error:",
                error
            );

            errorMessage.textContent =
                "Unable to connect to the server";
        });

    }
);