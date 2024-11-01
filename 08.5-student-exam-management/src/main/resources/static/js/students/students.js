$(document).ready(function () {

    let totalPage;
    let totalRecord;
    let paging;
    let pageIndex = 0;
    let pageSize = 10;

    function getStudentData(student) {
        $.ajax({
            url: '/api/v2/students',
            type: 'GET',
            data: {
                pageIndex: pageIndex,
                pageSize: pageSize,
                ...student
            },
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                renderStudentTable(data);
            },
            error: function (data) {
                showToast(data.responseJSON.error, "error");
            }
        })
    }

    function renderStudentTable(data) {
        $("#student-table tbody").empty();
        $("#student-paging .pagination").empty();
        $(".total-record").empty();
        if (!data) {
            return;
        }

        const students = data.data;
        totalPage = data.totalPage;
        totalRecord = data.totalRecord;
        paging = data.paging;
        pageIndex = paging.pageIndex;

        if (!students || students.length === 0) {
            return;
        }

        for (let i = 0; i < students.length; i++) {
            const student = students[i];
            const tr = "<tr>" +
                "<td>" + student.id + "</td>" +
                "<td>" + student.name + "</td>" +
                "<td>" + student.email + "</td>" +
                "<td>" + student.phone + "</td>" +
                "<td>" + student.gender + "</td>" +
                "<td>" + student.dob + "</td>" +
                "<td>" + student.gpa + "</td>" +
                "<td>" +
                "<span role=\"button\" class=\"text-primary btn-update\" id='" + student.id + "'>\n" +
                "   <i class=\"fa-solid fa-pencil\"></i>\n" +
                "</span>\n" +
                "<span role=\"button\" class=\"text-danger btn-delete\">\n" +
                "    <i class=\"fa-solid fa-trash\"></i>\n" +
                "</span>" +
                "</td>" +
                "</tr>";
            $("#student-table tbody").append(tr);
        }

        $("#student-paging .pagination").append("<li class=\"page-item go-to-first-page\"><a class=\"page-link\" href=\"#\"><<</a></li>");
        $("#student-paging .pagination").append("<li class=\"page-item previous-page\"><a class=\"page-link\" href=\"#\"><</a></li>");
        for (let i = 1; i <= totalPage; i++) {
            const page = "<li class='page-item " + (i === paging.pageIndex + 1 ? "active" : '') + "' page='" + (i - 1) + "'><a class='page-link' href='#'>" + i + "</a></li>";
            $("#student-paging .pagination").append(page);
        }

        $("#student-paging .pagination").append("<li class=\"page-item next-page\"><a class=\"page-link\" href=\"#\">></a></li>");
        $("#student-paging .pagination").append("<li class=\"page-item go-to-last-page\"><a class=\"page-link\" href=\"#\">>></a></li>");


        $(".total-record").append("<span>Tong so ban ghi: " + totalRecord + "</span>")

        $(".page-item").click(function (event) {
            pageIndex = $(event.currentTarget).attr("page");
            if (!pageIndex) {
                return;
            }
            getStudentData({});
        });

        $(".go-to-first-page").click(function () {
            pageIndex = 0;
            getStudentData({});
        });

        $(".go-to-last-page").click(function () {
            pageIndex = totalPage - 1;
            getStudentData({});
        });

        $(".previous-page").click(function () {
            if (paging.pageIndex === 0) {
                return;
            }
            pageIndex = paging.pageIndex - 1;
            getStudentData({});
        });

        $(".next-page").click(function () {
            if (paging.pageIndex === totalPage - 1) {
                return;
            }
            pageIndex = paging.pageIndex + 1;
            getStudentData({});
        });
    }

    getStudentData({});

    $("#search-student").click(function () {
        // lay du lieu tu form
        const formValues = $("#search-student-form").serializeArray();
        const student = {};
        formValues.forEach(input => {
            student[input.name] = input.value;
        });

        // goi seach
        getStudentData(student);
    });

    $("#reset-search-student").click(function () {
        $("#search-student-form")[0].reset();
        getStudentData({});
    });

    /// code xử lý create update, delete, ....

});