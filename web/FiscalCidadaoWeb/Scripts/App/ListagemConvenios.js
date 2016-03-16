
$().ready(function () {
    $('#conveniosTable').tablesorter({
        headers: {
            4: { sorter: false }
        }
    });

});

$(document).on('click', '.editarParecer', function () {
    showConfirmationModal('Atualizando parecer do convênio "' + $(this).parent().siblings('.tdObjeto').text().substring(0, 60) + '.."',
        '/Convenio/AtualizarParecer',
        $(this).parent().siblings('#convenioId').val(),
        $(this).parent().siblings('#parecerId').val());
});

function showConfirmationModal(message, urlDestination, convenioId, parecerAtualId) {

    $('#yesBtn').unbind();
    $('#noBtn').unbind();

    $('#dialog').dialog({
        autoOpen: false,
        modal: true,
        resizable: false,
        width: 400,
        height: 300,
        open: function () {
            $('#Parecer').val(parecerAtualId);
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
            data: { 'parecer': $('#Parecer').val(), 'convenio': convenioId },
            success: function (data) {
                window.location.href = '/Convenio/ListagemConvenios/';
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

$('#btnFiltrar').click(function () {

    $.ajax({
        type: "POST",
        url: '/Convenio/FiltroConvenios',
        data: {
            'filtroDescricao': $('#searchBox').val(), 'dataInicio': $('#Inicio').val(), 'dataFim': $('#Fim').val()
        },
        success: function (data) {
            $('.conveniosListagem').replaceWith(data);
        },
        error: function (request, status, error) {
        },
    });

});

