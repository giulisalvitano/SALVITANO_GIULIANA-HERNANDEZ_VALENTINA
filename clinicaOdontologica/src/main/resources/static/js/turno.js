// turnos.js

// URL base para las peticiones al servidor
const baseUrl = 'http://localhost:8080/turnos';

// Función para cargar los turnos al cargar la página
document.addEventListener('DOMContentLoaded', () => {
    cargarTurnos();
});

// Función para cargar los turnos desde el servidor y mostrarlos en la página
function cargarTurnos() {
    fetch(baseUrl)
        .then(response => response.json())
        .then(turnos => mostrarTurnos(turnos))
        .catch(error => console.error('Error al cargar los turnos:', error));
}

// Función para mostrar los turnos en la página
function mostrarTurnos(turnos) {
    const divTurnos = document.getElementById('div-turnos');
    divTurnos.innerHTML = ''; // Limpiar el contenido actual
    turnos.forEach(turno => {
        const card = `
            <div class="col-md-4 mb-3">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${turno.fechaYHora}</h5>
                        <p class="card-text">Odontólogo: ${turno.odontologo}</p>
                        <p class="card-text">Paciente: ${turno.paciente}</p>
                        <button class="btn btn-primary" onclick="mostrarDetalleTurno(${turno.id})">Ver Detalle</button>
                        <button class="btn btn-danger" onclick="eliminarTurno(${turno.id})">Eliminar</button>
                    </div>
                </div>
            </div>
        `;
        divTurnos.innerHTML += card;
    });
}

// Función para mostrar el detalle de un turno en un modal
function mostrarDetalleTurno(id) {
    // Lógica para cargar los detalles del turno y mostrarlos en el modal
}

// Función para eliminar un turno
function eliminarTurno(id) {
    if (confirm('¿Estás seguro de que quieres eliminar este turno?')) {
        fetch(`${baseUrl}/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                // Recargar los turnos después de eliminar
                cargarTurnos();
            } else {
                throw new Error('No se pudo eliminar el turno');
            }
        })
        .catch(error => console.error('Error al eliminar el turno:', error));
    }
}

// Otros métodos para agregar, actualizar turnos...
