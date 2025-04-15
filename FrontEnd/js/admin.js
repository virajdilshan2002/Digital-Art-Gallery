function addCategory() {
    $.ajax({
        url: 'http://localhost:8080/api/v1/category/save',
        type: 'POST',
        contentType: "application/json",
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')},
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
                text: error
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

$('#addAdminBtn').click(function (){
    $.ajax({
        url: 'http://localhost:8080/api/v1/admin/register',
        type: 'POST',
        contentType: "application/json",
        headers: {'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')},
        data: JSON.stringify({
            "name": $('#adminName').val(),
            "email": $('#adminEmail').val(),
            "password": $('#adminPw').val()
        }),
        success: function (response) {
            Swal.fire({
                icon: 'success',
                title: 'Admin Added Successfully',
                showConfirmButton: false,
                timer: 1500
            });
            $('#addAdminModal').modal('hide');
            $('#AddAdminForm')[0].reset();
        },
        error: function (error) {
            if (error.responseJSON.code === 406) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Email Already Exists',
                    text: error.responseJSON.message
                });
            }
            let data = error.responseJSON.data;
            if (data != null){
                if (data.name != null){
                    errorAlert(data.name)
                }else if (data.password != null){
                    errorAlert(data.password)
                }
            }
        }
    });
})

function errorAlert(message){
    showAlert("error","Oops...",message)
}



