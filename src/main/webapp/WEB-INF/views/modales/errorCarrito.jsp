<!-- Modal -->
<div id="modalError" class="modal" tabindex="-1" aria-labelledby="modalErrorLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalErrorLabel">Error</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p id="modalErrorMessage"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>

<script>
    // Mostrar el modal si hay un error
    window.onload = function() {
        var errorMessage = "${error}";
        if (errorMessage) {
            // Establecer el mensaje del error en el modal
            document.getElementById("modalErrorMessage").innerText = errorMessage;

            // Mostrar el modal
            var modal = new bootstrap.Modal(document.getElementById('modalError'));
            modal.show();
        }
    };
</script>

