$(document).ready(function() {
    function loadProfile() {
        //check if token is present
        const token = localStorage.getItem('token')
        const email = localStorage.getItem('email')
        if (token === null || email === null) {
            window.location.href = 'index.html';
            alert("Session expired, please login again!")
        } else {
            //get user profile
            $.ajax({
                url: 'http://localhost:8080/api/v1/user/retrieve?email=' + email,
                type: 'GET',
                headers: {"Authorization": "Bearer " + token},
                success: function (res) {
                    $('#userName').text(res.data.name);
                },
                error: function (error) {
                    console.log(error);
                    alert("Something went wrong, please login again!");
                    window.location.href = 'index.html';
                }
            })
        }
    }

    loadProfile();
})