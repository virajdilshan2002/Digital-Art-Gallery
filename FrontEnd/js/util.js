let jwtToken;

$(document).ready(function () {
    jwtToken = localStorage.getItem('jwtToken');
})

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

function loadNavProfile(role) {
    if (jwtToken === null) {
        window.location.href = 'index.html';
    } else {
        //get user profile
        $.ajax({
            url: `http://localhost:8080/api/v1/${role}/profile`,
            type: 'GET',
            headers: {"Authorization": "Bearer " + jwtToken},
            success: function (res) {
                let name = res.data.name;
                $('#userName').text(name);
                $('#navProfileLogo').attr('src', res.data.imagePath);
                $('#navProfileName').text(name);
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

function navUserProfile() {
    loadNavProfile("user")
}

function navAdminProfile() {
    loadNavProfile("admin")
}

function loadProfileData() {
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
