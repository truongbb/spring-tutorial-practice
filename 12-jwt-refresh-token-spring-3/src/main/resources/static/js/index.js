$(document).ready(function () {
    const loginObj = {
        username: "admin",
        password: "admin123"
    };

    $.ajax({
        type: "POST",
        url: "/api/v1/authentication/login",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(loginObj),
        success: function (data) {
            console.log(data);
            // $(".my-data").text(JSON.stringify(data));
            localStorage.setItem("access-token", data.jwt);
            localStorage.setItem("refresh-token", data.refreshToken);
        },
        error: function (error) {
            console.log(error);
        }
    });

    $.ajax({
        type: "GET",
        url: "/api/v1/users",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            console.log(data);
        },
        error: function (error) {
            console.log(error);
        }
    });
});