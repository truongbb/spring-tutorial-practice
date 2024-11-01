function showToast(message, type) {
    let color;
    switch (type) {
        case "success":
            color = "#198754";
            break;
        case "error":
            color = "#dc3545";
            break;
        case "warning":
            color = "#fd7e14";
            break;
    }
    Toastify({
        text: message,
        duration: 3000, // bao lâu thì toast tự động mất (milisecond)
        // destination: "https://github.com/apvarun/toastify-js",
        // newWindow: true,
        close: true,
        gravity: "top", // `top` or `bottom`
        position: "right", // `left`, `center` or `right`
        stopOnFocus: true, // Prevents dismissing of toast on hover
        style: {
            background: color,
        },
        // onClick: function(){} // Callback after click
    }).showToast();
}