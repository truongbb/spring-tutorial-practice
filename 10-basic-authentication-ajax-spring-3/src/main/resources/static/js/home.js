$(document).ready(function () {

    function makeBasicAuthenticationHeader(user, password) {
        const hash = btoa(user + ':' + password);
        return "Basic " + hash;
    }

    function getUsers() {
        $.ajax({
            type: "GET",
            url: "/api/v1/users",
            contentType: "application/json; charset=utf-8",
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', makeBasicAuthenticationHeader('admin', 'admin123'));
            },
            success: function (data) {
                console.log(data);
                $(".my-data").text(JSON.stringify(data));
            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    getUsers();

});