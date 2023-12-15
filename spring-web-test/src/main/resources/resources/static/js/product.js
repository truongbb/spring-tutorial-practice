$(document).ready(function () {

    let chosenFile = null;
    let currentImage = null;

    $("#choose-product-image").click(() => {
        $("#product-image").click();
    });

    $(".create-product-btn").click(function () {
        $("#product-creation-modal").modal("show");
    });

    $("#product-image").change(event => {
        const tempFiles = event.target.files;
        if (!tempFiles || tempFiles.length === 0) {
            return;
        }
        chosenFile = tempFiles[0];

        const imageBlob = new Blob([chosenFile], {type: chosenFile.type});
        const imageUrl = URL.createObjectURL(imageBlob);
        $("#product-img-show").attr("src", imageUrl);

    });

    $("#save-product-btn").click(function () {
        // const isValidForm = $("#create-product-form").valid();
        // if (!isValidForm) {
        //     return;
        // }

        const data = $('#create-product-form').serializeArray();
        if (!data || data.length === 0) {
            return;
        }
        const requestBody = {};
        for (let i = 0; i < data.length; i++) {
            requestBody[data[i].name] = data[i].value;
        }

        const formData = new FormData();
        formData.append('productRequest', JSON.stringify(requestBody));
        formData.append('image', chosenFile, chosenFile.name);
        // formData.append('images', chosenFile);
        $.ajax({
            url: '/admin/products',
            data: formData,
            type: 'POST',
            // enctype: 'multipart/form-data',
            contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
            processData: false, // NEEDED, DON'T OMIT THIS
            // cache: false,
            success: function (data) {
                toastr.success("Tạo mới sản phẩm thành công");
                setTimeout(function () {
                    location.reload();
                }, 1500);
            },
            error: function (errorData) {
                console.log(errorData);
                toastr.error("Có lỗi xảy ra, vui lòng thử lại");
            }
        });
    });

    $(".update-product-modal-open").click(function (event) {
        // get product info
        const productId = $(event.currentTarget).attr("product-id");
        $.ajax({
            url: '/admin/products/' + productId,
            type: 'GET',
            contentType: 'application/json',
            success: function (data) {
                $("#create-product-form #name").val(data.name);
                $("#create-product-form #price").val(data.price);
                $("#create-product-form #description").val(data.description);
                $("#create-product-form #product-img-show").attr("src", "/api/v1/files/" + data.image);
                currentImage = data.image;
                $("#create-product-form #save-product-btn").attr("product-id", data.id);

                $("#product-creation-modal").modal("show");
            },
            error: function (errorData) {
                console.log(errorData);
                toastr.error("Có lỗi xảy ra, vui lòng thử lại");
            }
        });
    });

});