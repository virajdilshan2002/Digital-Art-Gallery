let itemBrowseContainer = $('#itemBrowseContainer');

function getAllItemsForBrowse() {
    let jwtToken = localStorage.getItem('jwtToken');
    $.ajax({
        url: "http://localhost:8080/api/v1/item/getAll",
        type: "GET",
        headers: {
            Authorization: "Bearer " + jwtToken
        },
        success: function (response) {
            itemBrowseContainer.empty()
            if (response.data.length === 0) {
                itemBrowseContainer.append('<div class="d-flex flex-column justify-content-center align-items-center w-100 bg-light text-dark" \n' +
                    '             style="height: 450px; border-radius: 10px;">\n' +
                    '            <i class="bi bi-search display-4"></i>\n' +
                    '            <h4 class="mt-3">No items posted yet</h4>\n' +
                    '            <p class="text-muted">Try posting an item or check back later.</p>\n' +
                    '        </div>')
                return
            }
            for (let item of response.data) {
                itemBrowseContainer.append(`
                <div class="bg-light" style="width: calc(100% / 4); max-height: 450px;">
                    <div class="h-50">
                        <img class="w-100 h-100 object-fit-cover" loading="lazy" src="${item.image}" alt="Art"/>
                    </div>
                    <div>
                        <div>
                            <div>ON SALE</div>
                            <h5>${item.name}</h5>
                            <p>${item.description}</p>
                            <p class="text-muted">Posted By: <a href="#">${item.user.name}</a></p>
                        </div>
                        <strong>LKR${item.price}</strong>
                    </div>
                    <div class="mt-2">
                        <div class="mt-2">
                        <button class="btn-view-item btn btn-dark shadow rounded-5 px-4" type="button" 
                        data-uid="${item.uid}"
                        data-name="${item.name}"
                        data-image="${item.image}"
                        data-description="${item.description}"
                        data-price="${item.price}"
                        data-qty="${item.qty}"
                        data-username="${item.user.name}"
                        >View Art</button>
                    </div>
                </div>
                `)
            }
        },
        error: function (error) {
            console.error("Error fetching items:", error);
        }
    });
}

function getAllItemsForAdmin() {
    let jwtToken = localStorage.getItem("jwtToken");
    itemBrowseContainer.empty()
    $.ajax({
        url: "http://localhost:8080/api/v1/item/getAll",
        type: "GET",
        headers: {
            Authorization: "Bearer " + jwtToken
        },
        success: function (response) {
            if (response.data.length === 0) {
                itemBrowseContainer.append('<div class="d-flex flex-column justify-content-center align-items-center w-100 bg-light text-dark" \n' +
                    '             style="height: 450px; border-radius: 10px;">\n' +
                    '            <i class="bi bi-search display-4"></i>\n' +
                    '            <h4 class="mt-3">No items posted yet</h4>\n' +
                    '            <p class="text-muted">Try posting an item or check back later.</p>\n' +
                    '        </div>')
                return
            }

            for (let item of response.data) {
                itemBrowseContainer.append(`
                <div class="bg-light" style="width: calc(100% / 4); max-height: 400px;">
                    <div class="h-50">
                        <img class="w-100 h-100 object-fit-cover" loading="lazy" src="${item.image}" alt="Art"/>
                    </div>
                    <div>
                        <div>
                            <div>ON SALE</div>
                            <h5>${item.name}</h5>
                            <p>${item.description}</p>
                            <p class="text-muted">Posted By: <a href="#">${item.user.name}</a></p>
                        </div>
                        <strong>LKR${item.price}</strong>
                    </div>
                    <div class="mt-2">
                        <button class="btn-view-item btn btn-dark shadow rounded-5 px-4" type="button" 
                        data-uid="${item.uid}"
                        data-name="${item.name}"
                        data-image="${item.image}"
                        data-description="${item.description}"
                        data-price="${item.price}"
                        data-qty="${item.qty}"
                        data-username="${item.user.name}"
                        >View Art</button>
                        <button class="btn-delete-item btn btn-danger shadow rounded-5 px-4" type="button" data-iid="${item.iid}">Delete</button>
                    </div>
                </div>
                `)
            }
        },
        error: function (error) {
            console.error("Error fetching items:", error);
        }
    });
}

function getAllCategoriesAndSet() {
    let jwtToken = localStorage.getItem('jwtToken');
    let categorySelect = $('#categorySelect');
    $.ajax({
        url: `http://localhost:8080/api/v1/category/getAll`,
        type: 'GET',
        headers: {'Authorization': 'Bearer ' + jwtToken},
        success: function (res) {
            categorySelect.empty();
            categorySelect.append(`
                    <option selected disabled>Choose category...</option>
                `);
            res.data.forEach(category => {
                categorySelect.append(`
                        <option value="${category.cid}">${category.name}</option>
                    `);
            });


        },
        error: function (error) {
            showAlert("warning", "Warning!", "Cannot load categories! or may be empty!");
        }
    })

}

$('#postArtBtn').click(function () {
    let jwtToken = localStorage.getItem('jwtToken');
    const formData = new FormData($('#postArtForm')[0]);
    let categorySelect = $('#categorySelect');
    if (categorySelect.val() === null) {
        showAlert("warning", "Warning!", "Please select a category!");
        return
    }
    formData.append('categoryId', categorySelect.val());
    $.ajax({
        url: 'http://localhost:8080/api/v1/item/save',
        type: 'POST',
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        headers: {
            Authorization: 'Bearer ' + jwtToken
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
                window.location.reload()
            });
        },

        error: function (xhr, status, error) {
            Swal.fire({
                icon: "warning",
                title: "Error!",
                text: "Something went wrong, please try again!"
            })
        }
    });

});

$('#itemImageFileInput').on('change', function (event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            $('#selectItemPhotoPreview').attr('src', e.target.result); // Set the image preview
        };
        reader.readAsDataURL(file);
    }
});

$('#chooseItemImageBtn').click(function () {
    $('#itemImageFileInput').click();
})

$('#myItemsBtn').click(function () {
    let jwtToken = localStorage.getItem('jwtToken');
    itemBrowseContainer.empty()

    $.ajax({
        url: `http://localhost:8080/api/v1/item/ownedItemsToUser`,
        type: 'GET',
        headers: {
            "Authorization": "Bearer " + jwtToken
        },
        success: function (response) {
            if (response.data.length === 0) {
                itemBrowseContainer.append('<div class="d-flex flex-column justify-content-center align-items-center w-100 bg-light text-dark" \n' +
                    '             style="height: 450px; border-radius: 10px;">\n' +
                    '            <i class="bi bi-search display-4"></i>\n' +
                    '            <h4 class="mt-3">No items found</h4>\n' +
                    '            <p class="text-muted">Try posting an item or check back later.</p>\n' +
                    '        </div>')
                return
            }
            for (let item of response.data) {
                itemBrowseContainer.append(`
                <div class="bg-light" style="width: calc(100% / 4); max-height: 450px;">
                    <div class="h-50">
                        <img class="w-100 h-100 object-fit-cover" loading="lazy" src="${item.image}" alt="Art"/>
                    </div>
                    <div>
                        <div>
                            <div>ON SALE</div>
                            <h5>${item.name}</h5>
                            <p>${item.description}</p>
                            <p class="text-muted">Posted By: <a href="#">${item.user.name}</a></p>
                        </div>
                        <strong>LKR${item.price}</strong>
                    </div>
                    <div class="mt-2">
                        <button class="btn-edit-item btn btn-primary shadow rounded-5 px-4" type="button" 
                        data-uid="${item.uid}"
                        data-name="${item.name}"
                        data-image="${item.image}"
                        data-description="${item.description}"
                        data-price="${item.price}"
                        data-qty="${item.qty}"
                        data-username="${item.user.name}"
                        >Edit</button>
                        <button class="btn-delete-item btn btn-danger shadow rounded-5 px-4" type="button" data-iid="${item.iid}">Delete</button>
                    </div>
                </div>
                `)
            }
        },
        error: function (error) {

        }

    })
})

$(document).on("click", ".btn-view-item", function () {
    let item = {
        uid: $(this).data("uid"),
        name: $(this).data("name"),
        image: $(this).data("image"),
        description: $(this).data("description"),
        price: $(this).data("price"),
        qty: $(this).data("qty"),
        username: $(this).data("username")
    };

    viewArt(item);
});

$(document).on("click", ".btn-edit-item", function () {
    let item = {
        uid: $(this).data("uid"),
        name: $(this).data("name"),
        image: $(this).data("image"),
        description: $(this).data("description"),
        price: $(this).data("price"),
        qty: $(this).data("qty")
    };

    editItemModal(item);

});

$(document).on("click", ".btn-delete-item", function () {
    let iid = {
        iid: $(this).data("iid"),
    };

    deleteItem(iid);

});

function editItemModal(item) {
    $("#editItemTitle").val(item.name);
    $("#editItemDescription").val(item.description);
    $("#editItemPrice").val(item.price);
    $("#editItemImagePreview").attr("src", item.image);
    $("#editItemQty").val(item.qty);

    $('#editItemModal').modal("show");
}

function viewArt(item) {
    $("#itemModalTitle").text(item.name);
    $("#itemModalDescription").text(item.description);
    $("#itemModalPrice").text("LKR " + item.price);
    $("#itemModalImage").attr("src", item.image);
    $("#itemModalQty").text(item.qty);
    $("#itemModalUser").text(item.username);

    $("#viewItemModal").modal("show");
}

function deleteItem(iid) {
    let jwtToken = localStorage.getItem('jwtToken');
    console.log(iid)
    Swal.fire({
        title: "Warning!",
        text: "Confirm Deletion?",
        icon: "question",
        showCancelButton: true,
        confirmButtonColor: "#9e0018",
        cancelButtonColor: "#63aeff",
        confirmButtonText: "Register"
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: `http://localhost:8080/api/v1/item/delete?iid=${iid.iid}`,
                type: 'DELETE',
                headers: {
                    "Authorization": "Bearer " + jwtToken
                },
                success: function (response) {
                    Swal.fire({
                        icon: 'success',
                        title: 'Item Deleted Successfully',
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        window.location.reload();
                    });
                },
                error: function (error) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error Deleting Item',
                        text: error.responseText
                    });
                }
            })
        }
    })


}


/*$('#itemImage').on('change', function (event) {
    const file = $(this).prop('files')[0];
    const preview = $('#photoPreview');

    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            preview.attr('src', e.target.result);
        }
        reader.readAsDataURL(file);
    } else {
        preview.css('display', 'none');
    }
});*/

$('#editItemImage').on('change', function (event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            $('#editItemImagePreview').attr('src', e.target.result); // Set the image preview
        };
        reader.readAsDataURL(file);
    }
});

$('#editItemImgBtn').click(function () {
    // const fileInput = $(this).next();
    $('#editItemImage').click();
})

