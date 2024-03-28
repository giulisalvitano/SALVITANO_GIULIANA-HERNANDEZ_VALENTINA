// URL base para las peticiones al servidor
const baseUrl = 'http://localhost:8080/turnos';

// Función para cargar al cargar la página
document.addEventListener('DOMContentLoaded', () => {
    cargarTurnos();
    cargarOdontologos(); // Esta función deberá cargar la lista de odontólogos en el select
    cargarPacientes(); // Esta función deberá cargar la lista de pacientes en el select
    agregarEventoFormulario();
});

// Función para cargar y mostrar los turnos en la página
function cargarTurnos() {
    fetch(baseUrl)
        .then(response => response.json())
        .then(turnos => mostrarTurnos(turnos))
        .catch(error => console.error('Error al cargar los turnos:', error));
}

// Función para mostrar los turnos en la página
function mostrarTurnos(turnos) {
    const turnosContainer = document.getElementById('turnosContainer');
    turnosContainer.innerHTML = ''; // Limpiar el contenido actual

    turnos.forEach(turno => {
        const card = `
            <div class="col-md-4">
                <div class="card mb-4 shadow-sm">
                    <div class="card-body">
                        <p class="card-text">ID: ${turno.id}</p>
                        <p class="card-text">Odontólogo: ${turno.odontologo.nombre} ${turno.odontologo.apellido}</p>
                        <p class="card-text">Paciente: ${turno.paciente.nombre} ${turno.paciente.apellido}</p>
                        <p class="card-text">Fecha y Hora: ${turno.fechaHora}</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="btn-group">
                                <button type="button" class="btn btn-sm btn-outline-secondary" onclick="editarTurno(${turno.id})">Editar</button>
                                <button type="button" class="btn btn-sm btn-outline-danger" onclick="eliminarTurno(${turno.id})">Eliminar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
        turnosContainer.innerHTML += card;
    });
}

// Función para agregar un evento al formulario de agregar turno
function agregarEventoFormulario() {
    const formAgregarTurno = document.getElementById('formAgregarTurno');
    formAgregarTurno.addEventListener('submit', (event) => {
        event.preventDefault();

        const odontologoId = document.getElementById('selectOdontologo').value;
        const pacienteId = document.getElementById('selectPaciente').value;
        const fechaYHora = document.getElementById('fechaYHora').value;

        const nuevoTurno = {
            odontologoId: odontologoId,
            pacienteId: pacienteId,
            fechaYHora: fechaYHora
        };

        guardarTurno(nuevoTurno);
    });
}

// Función para guardar un nuevo turno
function guardarTurno(turno) {
    fetch(baseUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(turno)
    })
    .then(response => {
        if (response.ok) {
            cargarTurnos();
            limpiarFormulario();
        } else {
            throw new Error('Error al guardar el turno');
        }
    })
    .catch(error => console.error('Error al guardar el turno:', error));
}

// Función para limpiar el formulario después de guardar un turno
function limpiarFormulario() {
    document.getElementById('selectOdontologo').value = '';
    document.getElementById('selectPaciente').value = '';
    document.getElementById('fechaYHora').value = '';
}
