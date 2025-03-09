$(document).ready(function () {

    // Signup button click event
    $("#signup-form").submit(function (e) {
        e.preventDefault();
        const email = $("#email").val();
        const name = $("#name").val();
        const address = $("#address").val();
        const nic = $("#nic").val();
        const contact = $("#contact").val();
        const dob = $("#dob").val();
        const password = $("#password").val();
        const confirmPassword = $("#confirmPassword").val();

        if (password !== confirmPassword) {
            alert("Passwords do not match!");
            return;
        }

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/api/v1/user/signup",
            contentType: 'application/json',
            data: JSON.stringify({
                "email": email,
                "password": password,
                "name": name,
                "contact": contact,
                "address": address,
                "nic": nic,
                "dob": dob,
                "role": null
            }),
            success: function (response) {
                alert(response.message);
            },
            error: function (error) {
                console.log(error);
            },
        });
    });
})