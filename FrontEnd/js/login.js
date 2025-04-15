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
                localStorage.setItem("jwtToken", response.data.token);

                if (response.data.user.role === "ADMIN") {
                    showAlertThenRedirect("success","Success","Login Successful!", "admin.html")
                }else {
                    showAlertThenRedirect("success","Success","Login Successful!", "user.html")
                }
            },
            error: function (xhr,error) {
                if (xhr.responseJSON.code === 401) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: xhr.responseJSON.message
                    });
                }else if (xhr.responseJSON.code === 409){
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: xhr.responseJSON.message
                    });
                }
                checkXHR(xhr)
            },
        });
    });


});