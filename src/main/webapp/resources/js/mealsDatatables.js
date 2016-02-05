var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;


function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: function (data) {
            updateTableByData(data);
            coloredTable();
        }
    });
    return false;
}

function init() {
    $('#filter').submit(function () {
        updateTable();
        return false;
    });

    $('.datepicker').datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });
    $('.timepicker').datetimepicker({
        datepicker: false,
        format: 'H:i'
    });
    $('.datetimepicker').datetimepicker({
        format: 'Y-m-d H:i'
    });

    coloredTable();
}
function coloredTable() {
    $.each($('td:nth-child(4)'), function (key, item) {
        var span = $(item);
        span.css('display', 'none');
        var cell = datatableApi.cell(span);
        var data = cell.data();
        span.parent().css("color", data == true ? 'red' : 'green');
    });
}
$(function () {
    datatableApi = $('#datatable').DataTable({
        "sAjaxSource": ajaxUrl,
        "sAjaxDataProp": "",
        "bPaginate": false,
        "bInfo": false,
        "aoColumns": [
            {
                "mData": "dateTime",
                "mRender": function (date, type, row) {
                    if (type == 'display') {
                        var dateObject = new Date(date);
                        return '<span>' + dateObject.toISOString().substring(0, 10) + '</span>';
                    }
                    return date;
                }
            },

            {
                "mData": "description"
            },
            {
                "mData": "calories"
            },

            {
                "mData": "exceed"

            },
            {
                "sDefaultContent": "",
                "bSortable": false,
                "mRender": renderEditBtn
            },
            {
                "sDefaultContent": "",
                "bSortable": false,
                "mRender": renderDeleteBtn
            }
        ],

        "aaSorting": [
            [
                0,
                "desc"
            ]
        ],
        "initComplete": makeEditable
    })
});

