$(document).ready(function () {
    jQuery.ajaxSetup({
        beforeSend: function (xhr) {
            const token = localStorage.getItem("access-token");
            if (!token || token.trim() === "") {
                return;
            }
            xhr.setRequestHeader('Authorization', "Bearer " + token);
        }
    });
});