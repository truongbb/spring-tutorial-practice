$(document).ready(function () {
    toastr.options.timeOut = 2500; // 2.5s
    const taskStatusListName = "taskStatusList";

    // get task status and save into local storage
    $.ajax({
        url: "/api/v1/tasks/status",
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            localStorage.setItem(taskStatusListName, JSON.stringify(data));
        },
        error: function (data) {
            console.log(data);
            toastr.warning(data.responseJSON.message);
        }
    });

    $("#student-update-modal-form").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            "name": {
                required: true,
                maxlength: 255
            },
            "address": {
                required: true,
                maxlength: 255
            },
            "phone": {
                required: true,
                maxlength: 10
            }
        },
        messages: {
            "name": {
                required: "Bắt buộc nhập tên sinh viên",
                maxlength: "Hãy nhập tối đa 255 ký tự"
            },
            "address": {
                required: "Bắt buộc nhập địa chỉ sinh viên",
                maxlength: "Hãy nhập tối đa 255 ký tự"
            }
        }
    });


    // create a task
    $("#create-task-btn").click(() => {
        const taskStatusList = JSON.parse(localStorage.getItem(taskStatusListName));
        if (!taskStatusList || taskStatusList.length === 0) {
            return;
        }
        let statusOptions = "";
        for (let i = 0; i < taskStatusList.length; i++) {
            statusOptions += "<option value='" + taskStatusList[i].code + "'>" + taskStatusList[i].name + "</option>";
        }
        $('#task-modal #status').append($(statusOptions));
        $("#task-modal").modal("show");
    });

});