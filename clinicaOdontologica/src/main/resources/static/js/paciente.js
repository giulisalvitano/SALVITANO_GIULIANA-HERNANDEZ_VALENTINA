// pacientes.js

// URL base para las peticiones al servidor
const baseUrl = 'http://localhost:8080/pacientes';

// Función para cargar los pacientes al cargar la página
document.addEventListener('DOMContentLoaded', () => {
    cargarPacientes();
});

// Función para cargar los pacientes desde el servidor y mostrarlos en la página
function cargarPacientes() {
    fetch(baseUrl)
        .then(response => response.json())
        .then(pacientes => mostrarPacientes(pacientes))
        .catch(error => console.error('Error al cargar los pacientes:', error));
}

// Función para mostrar los pacientes en la tabla
// Función para mostrar los pacientes en la tabla
function mostrarPacientes(pacientes) {
    const tbodyPacientes = document.getElementById('tbody-pacientes');
    tbodyPacientes.innerHTML = ''; // Limpiar el contenido actual
    pacientes.forEach(paciente => {
        const row = `
            <tr>
                <td>${paciente.id}</td>
                <td>${paciente.nombre}</td>
                <td>${paciente.apellido}</td>
                <td>${paciente.dni}</td>
                <td>${paciente.fechaIngreso}</td>
                <td>${paciente.calle}</td>
                <td>${paciente.numero}</td>
                <td>${paciente.localidad}</td>
                <td>${paciente.provincia}</td>
                <td>
                    <button class="btn btn-primary" onclick="editarPaciente(${paciente.id})">Editar</button>
                    <button class="btn btn-danger" onclick="eliminarPaciente(${paciente.id})">Eliminar</button>
                </td>
            </tr>
        `;
        tbodyPacientes.innerHTML += row;
    });
}


// Función para manejar la creación o actualización de un paciente
document.getElementById('form-paciente').addEventListener('submit', function(event) {
    event.preventDefault(); // Evitar que se envíe el formulario por defecto

    const id = document.getElementById('idPaciente').value;
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;
    const dni = document.getElementById('dni').value;
    const fechaIngreso = document.getElementById('fechaIngreso').value;
    const calle = document.getElementById('calle').value;
    const numero = document.getElementById('numero').value;
    const localidad = document.getElementById('localidad').value;
    const provincia = document.getElementById('provincia').value;

    const pacienteData = {
        nombre,
        apellido,
        dni,
        fechaIngreso,
        calle,
        numero,
        localidad,
        provincia
    };

    let requestUrl = 'http://localhost:8080/pacientes/registrar';
    let requestMethod = 'POST';

    if (id) {
        requestUrl += `/${id}`;
        requestMethod = 'PUT';
    }

    fetch(requestUrl, {
        method: requestMethod,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pacienteData)
    })
    .then(response => {
        if (response.ok) {
            // Limpiar el formulario después de la creación/actualización exitosa
            document.getElementById('form-paciente').reset();
            // Recargar la lista de pacientes
            cargarPacientes();
        } else {
            throw new Error('No se pudo guardar el paciente');
        }
    })
    .catch(error => console.error('Error al guardar el paciente:', error));
});

let actualizarUrl = 'http://localhost:8080/pacientes/actualizar';

// Función para cargar los datos de un paciente en el formulario para editar
function editarPaciente(id) {
    fetch(`${actualizarUrl}/${id}`)
    .then(response => response.json())
    .then(paciente => {
        document.getElementById('idPaciente').value = paciente.id;
        document.getElementById('nombre').value = paciente.nombre;
        document.getElementById('apellido').value = paciente.apellido;
        document.getElementById('dni').value = paciente.dni;
        document.getElementById('fechaIngreso').value = paciente.fechaIngreso;
        document.getElementById('calle').value = paciente.calle;
        document.getElementById('numero').value = paciente.numero;
        document.getElementById('localidad').value = paciente.localidad;
        document.getElementById('provincia').value = paciente.provincia;
    })
    .catch(error => console.error('Error al cargar los datos del paciente para editar:', error));
}


let eliminarUrl = 'http://localhost:8080/pacientes/eliminar';


// Función para eliminar un paciente
function eliminarPaciente(id) {
    if (confirm('¿Estás seguro de eliminar el paciente?')) {
        fetch(`http://localhost:8080/pacientes/eliminar/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                // Si la eliminación es exitosa, recargar la lista de pacientes
                cargarPacientes();
            } else {
                // Manejar errores de eliminación
                throw new Error('Error al eliminar el paciente');
            }
        })
        .catch(error => console.error('Error al eliminar el paciente:', error));
    }
}