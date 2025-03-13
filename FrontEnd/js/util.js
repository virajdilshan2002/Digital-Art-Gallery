$(document).ajaxStart(function () {
    $("#loading").fadeIn();
}).ajaxStop(function () {
    $("#loading").fadeOut();
})


function loadProfile(role) {
    //check if token is present
    const token = localStorage.getItem('token')
    if (token === null) {
        window.location.href = 'index.html';
    } else {
        //get user profile
        $.ajax({
            url: `http://localhost:8080/api/v1/${role}/profile`,
            type: 'GET',
            headers: {"Authorization": "Bearer " + token},
            success: function (res) {
                let name = res.data.name;
                $('#userName').text(name);
                // $('#profileLogo').attr('src', res.data.profileImage);
                $('#profileName').text(name);

            },
            error: function (error) {
                window.location.href = 'index.html';
            }
        })
    }
}
