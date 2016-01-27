function makeEditable() {

    $('#add').click(function () {
        $('#id').val(0);
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).attr("name"));
    });
    $('.edit').click(function () {
        editRow($(this).attr("name"));
    });
    $('.checkbox').change(function () {
        checkboxEnable($(this).closest('tr').find('.btn-xs').attr("id"), $(this));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $('.form-horizontal').submit(function () {
        var startDate = $(this).find("#startDate").val();
        var startTime = $(this).find("#startTime").val();
        var endDate = $(this).find("#endDate").val();
        var endTime = $(this).find("#endTime").val();
        getBetween(startDate, startTime, endDate, endTime);
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}


function editRow(id) {
    var editForm = $('#detailsForm');
    $.ajax({
        url: ajaxUrl + id,
        type: 'GET',
        success: function (data) {
            editForm.find("input[name='id']").val(data['id']);
            editForm.find("input[name='dateTime']").val(data['dateTime']);
            editForm.find("input[name='description']").val(data['description']);
            editForm.find("input[name='calories']").val(data['calories']);
            $('#editRow').modal();
        }
    })
}


function updateTable() {
    $.get(ajaxUrl, function (data) {
        oTable_datatable.api().clear();
        $.each(data, function (key, item) {
            oTable_datatable.api().row.add(item);
        });
        oTable_datatable.api().draw();
    });
}
function getBetween(startDate, startTime, endDate, endTime) {
    $.post(ajaxUrl + 'filter', {
        "startDate": startDate,
        "startTime": startTime,
        "endDate": endDate,
        "endTime": endTime
    }, function (data) {
        oTable_datatable.api().clear();
        $.each(data, function (key, item) {
            oTable_datatable.api().row.add(item);
        });
        oTable_datatable.api().draw();
    });
}



function checkboxEnable(id, checkbox) {
    var enable = checkbox.is(":checked");
    $.ajax({
        url: ajaxUrl + id + '/enable',
        type: 'POST',
        data: 'enable=' + enable
    });
}


function save() {
    var form = $('#detailsForm');
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
