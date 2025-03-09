$(document).ready(function () {

    // Login button click event
    $("#login-form").submit(function (e) {
        e.preventDefault();
        const email = $("#email").val();
        const password = $("#password").val();
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/api/v1/auth/authenticate",
            contentType: 'application/json',
            data: JSON.stringify({
                "email": email,
                "password": password
            }),
            success: function (response) {
                alert(response.message);
            },
            error: function (error) {
                alert(error.responseJSON.message);
                if (confirm("Do you want to register?")) {
                    window.location.href = "signup.html";
                }
            },
        });
    });



});