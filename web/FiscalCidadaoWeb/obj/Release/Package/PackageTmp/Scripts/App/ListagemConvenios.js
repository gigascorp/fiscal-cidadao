/* Copyright (C) 2016  Andrea Mendonça, Emílio Weba, Guiherme Ribeiro, Márcio Oliveira, Thiago Nunes, Wallas Henrique

  This file is part of Fiscal Cidadão.

  Fiscal Cidadão is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  Fiscal Cidadão is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with Fiscal Cidadão.  If not, see <http://www.gnu.org/licenses/>. */

$().ready(function () {
    $('#conveniosTable').DataTable({
        "order": [[2, "desc"]],
        "columnDefs": [{
            targets: "datatable-nosort",
            orderable: false
        }],
        filter: false,
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

