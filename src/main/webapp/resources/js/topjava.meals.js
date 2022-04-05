const mealAjaxUrl = "ui/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "datetime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function saveMeal() {
    $.ajax({
        type: "POST",
        url: 'meals/',
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        updateTable();
        successNoty("Saved");
    });
}

function filtered() {
    var form = $("#filterForm");
    var actionUrl = form.attr('action');
    $.ajax({
        type: "GET",
        url: actionUrl,
        data: form.serialize(),
    });
}

function cancelFilter() {
    var form = $("#filterForm");
    var actionUrl = form.attr('action');
    $.ajax({
        type: "GET",
        url: actionUrl,
    });
}