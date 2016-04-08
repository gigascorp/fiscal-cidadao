
$().ready(function () {
    $('#convenioTable').tablesorter({
        headers: {
            2: { sorter: false }
        }
    });
});

$('.verFotos').on('click', function () {

    var id = $(this).parent().siblings('#denunciaId').val();
    var src;
    var img = '';

    $.get('/Convenio/GetImageBase64', { id: id }, function (data) {
        if (data != null && data != "") {
            for (var i = 0; i < data.length; i++) {
                img += '<div id=' + (i + 1) + (i > 0 ? ' hidden' : '') + '> <img src="data:image/png;base64,' + data[i] + '" class="img-responsive denunciaImg"/> </div>';
            }
        }
    }).done(function () {
        var index = 0;
        var html = '';
        html += img;
        html += '<div style="height:25px;clear:both;display:block;">';
        html += '<a class="controls next" href="' + (index + 2) + '">next &raquo;</a>';
        html += '<a class="controls previous" href="' + (index) + '">&laquo; prev</a>';
        html += '</div>';

        $('#myModal').modal();
        $('#myModal').on('shown.bs.modal', function () {
            $('#myModal .modal-body').html(html);
            $('a.controls').trigger('click');
        });
        $('#myModal').on('hidden.bs.modal', function () {
            $('#myModal .modal-body').html('');
        });
    });
});

$(document).on('click', 'a.controls', function () {
    var index = $(this).attr('href');
    var divVisible;

    $('.modal-body img').parent().each(function () {
        if ($(this).is(':visible')) {
            divVisible = $(this);
            return false;
        }
    });

    $(divVisible).hide();
    var src = $('.modal-body #' + index).show();

    var newPrevIndex = parseInt(index) - 1;
    var newNextIndex = parseInt(newPrevIndex) + 2;

    if ($(this).hasClass('previous')) {
        $(this).attr('href', newPrevIndex);
        $('a.next').attr('href', newNextIndex);
    } else {
        $(this).attr('href', newNextIndex);
        $('a.previous').attr('href', newPrevIndex);
    }

    var total = $('.modal-body img').length + 1;

    //hide next button
    if (total === newNextIndex) {
        $('a.next').hide();
    } else {
        $('a.next').show()
    }
    //hide previous button
    if (newPrevIndex === 0) {
        $('a.previous').hide();
    } else {
        $('a.previous').show()
    }
    return false;
});

$('#editarParecer').click(function () {
    showConfirmationModal('Atualizando parecer do convênio "' + $('#objetoNome').text().substring(0, 60) + '.."', '/Convenio/AtualizarParecer');
});

function showConfirmationModal(message, urlDestination) {

    $('#yesBtn').unbind();
    $('#noBtn').unbind();

    $('#dialog').dialog({
        autoOpen: false,
        modal: true,
        resizable: false,
        width: 400,
        height: 300,
        open: function () {
            $('#Parecer_Parecer').val($('#parecerId').val());
        }
    });

    $('#dialogContent').text(message)
    $("#dialog").dialog("open");

    $('#yesBtn').on('click', function (e) {
        e.preventDefault();

        $("#dialog").dialog("close");

        var id = $('#convenioId').text();

        $.ajax({
            type: "POST",
            url: urlDestination,
            data: { 'parecer': $('#Parecer_Parecer').val(), 'convenio': id },
            success: function (data) {
                window.location.href = '/Convenio/DetalhesConvenio/' + id;
            },
            error: function (request, status, error) {
            },
        });
    });

    $('#noBtn').on('click', function (e) {
        e.preventDefault();
        $("#dialog").dialog("close");
    });
}