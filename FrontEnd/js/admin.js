$(document).ready(function () {
    loadProfile("admin");
});

$('#logOutBtn').click(function () {
    logout()
});

$('#image').on('change', function(event) {
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
    let token = localStorage.getItem('token');
    const formData = new FormData($('#postArtForm')[0]);

    $.ajax({
        url: 'http://localhost:8080/api/v1/item/save',
        type: 'POST',
        data: formData,
        cache: false,
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
                $('#photoPreview').attr('src', 'assets/img/items/3.jpg');
                $('#artPostModal').modal('hide');
            });
        },
        error: function (xhr, status, error) {
            console.log(error)
        }
    });
});