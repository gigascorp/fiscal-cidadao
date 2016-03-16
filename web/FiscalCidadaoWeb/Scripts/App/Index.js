
$('.detailsDenunciados').click(function () {
    window.location.href = "/DetalhesConvenio/" + $(this).siblings('#convenioId').val();
});