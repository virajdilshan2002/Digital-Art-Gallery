$(document).ready(function () {
    let token = localStorage.getItem('token');

    loadProfile("admin");
});

$('#logOutBtn').click(function () {
    logout()
});

$('#artPhoto').on('change', function(event) {
    const file = $(this).prop('files')[0];
    const preview = $('#photoPreview');

    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.attr('src', e.target.result);
        }
        reader.readAsDataURL(file);
    } else {
        preview.css('display', 'none');
    }
});

$('#postArtBtn').click(function () {
    const formData = new FormData($('#postArtForm')[0]);

    $.ajax({
        url: 'http://localhost:8080/api/v1/item/save',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        headers: {
            Authorization: 'Bearer ' + token
        },
        success: function (response) {
            Swal.fire(
                'Success!',
                'Your art has been posted successfully!',
                'success'
            ).then(() => {
                $('#postArtForm')[0].reset();
                $('#photoPreview').attr('src', 'assets/img/arts/3.jpg');
                $('#artPostModal').modal('hide');
            });
        },
        error: function (xhr, status, error) {
            console.log(error.responseJSON.message);
            console.log(xhr.responseJSON.message);
            console.log(status);
        }
    });
});