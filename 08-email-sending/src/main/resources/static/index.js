$(document).ready(function () {

    toastr.options.timeOut = 2500; // 2.5s

    $(".send-mail").click(() => {
        const email = $("#email").val();
        if (!email || email.trim().length === 0) {
            toastr.warning("Chưa nhập email");
        }

        $.ajax({
            url: '/api/v1/users/' + email + '/otp-sending',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                console.log(data);
                toastr.success(data);
            },
            error: function (errorData) {
                console.log(errorData);
                toastr.error(errorData);
            }
        });

    });

});