
$().ready(function () {
    $('#convenioTable').tablesorter({
        headers: {
            2: { sorter: false }
        }
    });

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