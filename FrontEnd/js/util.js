
$(document).ajaxStart(function () {
    $("#loading").fadeIn();
}).ajaxStop(function () {
    $("#loading").fadeOut();
})

function showAlert(icon, title, text) {
    Swal.fire({
        icon: icon,
        title: title,
        text: text,
        timer: 3000,
        backdrop: "rgba(0,0,0,0.8)",
    });
}

function showAlertThenRedirect(icon, title, text, url) {
    Swal.fire({
        icon: icon,
        title: title,
        text: text,
        timer: 2000,
        backdrop: "rgba(0,0,0,0.8)",
    }).then(() => {
        window.location.href = url; // Using the utility function for redirection
    });

}

function logout() {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('accessToken');
    localStorage.removeItem('profile');
    window.location.href = 'index.html';
}

function loadNavProfile() {
    let jwtToken = localStorage.getItem("jwtToken");
    if (jwtToken === null) {
        window.location.href = 'index.html';
    } else {
        //get user profile
        $.ajax({
            url: `http://localhost:8080/api/v1/user/profile`,
            type: 'GET',
            headers: {"Authorization": "Bearer " + jwtToken},
            success: function (res) {
                // localStorage.setItem("profile", JSON.stringify(res.data));
                let profile = res.data;
                $('#userName').text(profile.name);
                if (profile.imagePath === null){
                    $('#navProfileLogo').attr('src', 'assets/img/illustrations/user.svg');
                } else {
                    $('#navProfileLogo').attr('src', profile.imagePath);
                }
                $('#navProfileName').text(profile.name);
            },
            error: function (error) {
                showAlertThenRedirect(
                    "error",
                    "Oops...",
                    "Something went wrong!",
                    "index.html")
            }
        })
    }
}

function loadProfileData() {
    let jwtToken = localStorage.getItem("jwtToken");
    $.ajax({
        url: `http://localhost:8080/api/v1/user/profile`,
        type: `GET`,
        headers: {"Authorization": "Bearer " + jwtToken},
        success: function (res) {
            localStorage.setItem("profile", JSON.stringify(res.data));
        },
        error: function (error) {
            showAlertThenRedirect("warning", "Warning!", "Cannot load profile data!", "index.html");
        }
    })
}

function setNavigations() {
    let jwtToken = localStorage.getItem("jwtToken");
    $.ajax({
        url: `http://localhost:8080/api/v1/user/profile`,
        type: `GET`,
        headers: {"Authorization": "Bearer " + jwtToken},
        success: function (res) {
            let profile = res.data;

            if (profile.role === "ADMIN") {
                $('#nav-logo-link').attr('href', 'admin.html');
                $('#nav-dashboard').attr('href', 'admin.html');
                $('#nav-arts').attr('href', 'admin-items.html');
                $('#nav-contact').attr('href', 'admin-contact.html');
                $('#nav-faq').attr('href', 'admin-faq.html');
            } else if (profile.role === "USER") {
                $('#nav-logo-link').attr('href', 'user.html');
                $('#nav-dashboard').attr('href', 'user.html');
                $('#nav-arts').attr('href', 'user-items.html');
                $('#nav-contact').attr('href', 'contact.html');
                $('#nav-faq').attr('href', 'faq.html');
            }
        },
        error: function (error) {
            showAlertThenRedirect("warning", "Warning!", "Cannot load profile data!", "index.html");
        }
    })

}

function changePhoto(prevImage, fileInput) {
    fileInput.on('change', function (event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                prevImage.attr('src', e.target.result); // Set the image preview
            };
            reader.readAsDataURL(file);
        }
    });
}

function checkXHR(xhr) {
    if (xhr.status === 0) {
        if (xhr.readyState === 0) {
            // Server is down (Connection refused)
            Swal.fire({
                icon: "warning",
                title: "Warning!",
                text: "Cannot connect to the server!",
            }).then(() => {
                window.location.href = "404.html";
            })
        } else {
            // Request Blocked (CORS, Firewall, Network issue)
            Swal.fire({
                icon: "warning",
                title: "Request Blocked!",
                text: "The request was blocked. Try logging again!.",
            }).then(() => {
                window.location.href = "index.html";
                // localStorage.removeItem('token');
            })
        }
    }
}
