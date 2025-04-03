

function addCategory() {
    $.ajax({
        url: 'http://localhost:8080/api/v1/category/save',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify({
            "name": $('#categoryName').val(),
            "description": $('#categoryDesc').val()
        }),
        success: function (response) {
            Swal.fire({
                icon: 'success',
                title: 'Category Added Successfully',
                showConfirmButton: false,
                timer: 1500
            });
            $('#addCategoryModal').modal('hide');
            $('#AddCategoryForm')[0].reset();
        },
        error: function (error) {
            Swal.fire({
                icon: 'error',
                title: 'Error Adding Category',
                text: error.responseText
            });
        }
    });

}

$('#logOutBtn').click(function () {
    logout()

});

$('#addCategoryBtn').on('click', function () {
    addCategory();
});



