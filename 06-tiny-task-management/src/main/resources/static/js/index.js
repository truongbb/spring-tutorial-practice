$(document).ready(function () {
    toastr.options.timeOut = 2500; // 2.5s

    // get task status
    $.ajax({
        url: "/api/v1/tasks/status",
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            const statusSelection = $('#task-modal #status');
            if (statusSelection.children().length === 0) {
                if (!data || data.length === 0) {
                    return;
                }
                let statusOptions = "";
                for (let i = 0; i < data.length; i++) {
                    statusOptions += "<option value='" + data[i].code + "'>" + data[i].name + "</option>";
                }
                statusSelection.append($(statusOptions));
            }
        },
        error: function (data) {
            toastr.warning(data.responseJSON.error);
        }
    });

    $.validator.addMethod("futureDateCustom", function (value, element) {
        const now = new Date();
        const myDate = new Date(value);
        return this.optional(element) || myDate > now;
    });
    $("#task-modal-form").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            "name": {
                required: true,
                maxlength: 255
            },
            "expectedEndTime": {
                required: true,
                date: true,
                futureDateCustom: true
            },
            "status": {
                required: true
            }
        },
        messages: {
            "name": {
                required: "Task name is required",
                maxlength: "Task name must be less than 255 characters"
            },
            "expectedEndTime": {
                required: "Expected end time is required",
                date: "Invalid date format",
                futureDateCustom: "Expected end time must be a future date"
            },
            "status": {
                required: "Task status is required"
            }
        }
    });

    // open modal to create a task
    $(".create-task-btn").click((event) => {
        const taskStatus = $(event.currentTarget).attr("task-status");
        $("#task-modal #status").val(taskStatus);
        $("#task-modal #save-task").attr("action-type", "CREATE");

        $("#task-modal").modal("show");
    });

    // open modal to update a task
    $(".task-title").click(async event => {
        const taskId = $(event.currentTarget).attr("task-id");
        let task = null;
        await $.ajax({
            url: "/api/v1/tasks/" + taskId,
            type: "GET",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                task = data;
            },
            error: function (data) {
                toastr.warning(data.responseJSON.error);
            }
        });

        if (!task) {
            toastr.error("Có lỗi xảy ra, vui lòng thử lại");
            return;
        }
        $("#task-modal-form #name").val(task.name);
        $("#task-modal-form #status").val(task.status);
        $("#task-modal-form #expectedEndTime").val(task.expectedEndTime);
        $("#task-modal-form #description").val(task.description);

        $("#task-modal #save-task").attr("action-type", "UPDATE");
        $("#task-modal #save-task").attr("task-id", taskId);
        $("#task-modal").modal("show");
    });

    // close modal -> clear form + reset form, delete action-type attribute at submit button
    $(".close-modal").click(() => {
        $("#task-modal #save-task").attr("action-type", "");
        $("#task-modal #save-task").attr("task-id", "");
        $('#task-modal-form').trigger("reset");
    });

    // create or update a task
    $("#save-task").click(async event => {
        const isValidForm = $("#task-modal-form").valid();
        if (!isValidForm) {
            return;
        }

        const actionType = $(event.currentTarget).attr("action-type");
        const taskId = $(event.currentTarget).attr("task-id");
        const formData = $('#task-modal-form').serializeArray();
        if (!formData || formData.length === 0) {
            return;
        }
        const requestBody = {};
        for (let i = 0; i < formData.length; i++) {
            requestBody[formData[i].name] = formData[i].value;
        }

        const method = actionType === "CREATE" ? "POST" : "PUT";
        if (method === "PUT") {
            requestBody["id"] = taskId;
        }
        await $.ajax({
            url: "/api/v1/tasks",
            type: method,
            data: JSON.stringify(requestBody),
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                toastr.success((actionType === "CREATE" ? "Create" : "Update") + " a new task successfully");
                $(event.currentTarget).attr("action-type", "");
                $("#task-modal").modal("hide");
                setTimeout(() => {
                    location.reload();
                }, 500);
            },
            error: function (data) {
                toastr.warning(data.responseJSON.error);
            }
        });

        $("#task-modal #save-task").attr("action-type", "");
        $("#task-modal #save-task").attr("task-id", "");
        $('#task-modal-form').trigger("reset");
    });

    // open delete confirmation modal
    $(".delete-btn").click(event => {
        const taskId = $(event.currentTarget).attr("task-id");
        $("#task-delete-confirmation-modal #delete-task").attr("task-id", taskId);
        $("#task-delete-confirmation-modal").modal("show");
    });

    // do delete task
    $("#delete-task").click(event => {
        const taskId = $("#task-delete-confirmation-modal #delete-task").attr("task-id");

        $.ajax({
            url: "/api/v1/tasks/" + taskId,
            type: "DELETE",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                toastr.success("Delete successfully");
                $("#task-delete-confirmation-modal #delete-task").attr("task-id", "");
                $("#task-delete-confirmation-modal").modal("hide");
                setTimeout(() => {
                    location.reload();
                }, 500);
            },
            error: function (data) {
                toastr.warning(data.responseJSON.error);
            }
        });
    });

});

function dragStart(event) {
    event.dataTransfer.setData("text", event.target.id);
}

function allowDrop(event) {
    event.preventDefault();
}

function dropHandler(event) {
    event.preventDefault();
    const id = event.dataTransfer.getData("text");
    const taskId = id.split("-")[1];
    const targetStatus = event.target.getAttribute("task-status");
    $.ajax({
        url: "/api/v1/tasks/" + taskId + "/" + targetStatus,
        type: "PUT",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            event.target.appendChild(document.getElementById(id));
            toastr.success("Change status successfully");
        },
        error: function (data) {
            toastr.warning(data.responseJSON.error);
        }
    });

}
