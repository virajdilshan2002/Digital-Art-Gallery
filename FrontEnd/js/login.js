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
                localStorage.setItem("token", response.data.token);

                if (response.data.user.role === "ADMIN") {
                    showAlertThenRedirect("success","Success","Login Successful!", "admin.html")
                }else {
                    showAlertThenRedirect("success","Success","Login Successful!", "user.html")
                }
            },
            error: function (error) {
                showAlert("error","Oops...","Invalid Email or Password!")
            },
        });
    });



});