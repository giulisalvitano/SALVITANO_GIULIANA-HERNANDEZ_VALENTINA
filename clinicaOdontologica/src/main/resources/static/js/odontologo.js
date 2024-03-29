// URL base para las peticiones al servidor
const baseUrl = 'http://localhost:8080/odontologos';

// Función para cargar a la cargar la página
document.addEventListener('DOMContentLoaded', () => {
    const guardarButton = document.getElementById('guardarOdontologo');
    guardarButton.addEventListener('click', guardarOdontologo);
    cargarOdontologos();

});

// Función para cargar y mostrarlos en la página
function cargarOdontologos() {
    fetch(baseUrl)
        .then(response => response.json())
        .then(odontologos => mostrarOdontologos(odontologos))
        .catch(error => console.error('Error al cargar los odontologos:', error));
}

// Función para mostrar los odontólogos en la tabla
function mostrarOdontologos(odontologos) {
    const tbodyOdontologos = document.getElementById('tbody-odontologos');
    tbodyOdontologos.innerHTML = ''; // Limpiar el contenido actual
    odontologos.forEach(odontologo => {
        const row = `
            <tr>
                <td>${odontologo.id}</td>
                <td>${odontologo.nombre}</td>
                <td>${odontologo.apellido}</td>
                <td>${odontologo.matricula}</td>
                <td>
                    <button class="btn btn-danger eliminar-btn" onclick="eliminarOdontologo(${odontologo.id})">Eliminar</button>
                </td>
            </tr>
        `;
        tbodyOdontologos.innerHTML += row;
    });
}


function eliminarOdontologo(id) {
    if (confirm('¿Estás seguro de eliminar el odontólogo?')) {
        fetch(`http://localhost:8080/odontologos/eliminar/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                // Si la eliminación es exitosa, recargar la lista de odontólogos
                cargarOdontologos();
            } else {
                // Manejar errores de eliminación
                throw new Error('Error al eliminar el odontólogo');
            }
        })
        .catch(error => console.error('Error al eliminar el odontólogo:', error));
    }
}

const baseUrlRegistrar = 'http://localhost:8080/odontologos/registrar';

function guardarOdontologo() {
    const id = document.getElementById('idOdontologo').value;
    const matricula = document.getElementById('numeroDeMatricula').value;
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;

    const data = {
        matricula: matricula,
        nombre: nombre,
        apellido: apellido
    };

    let url = baseUrlRegistrar; // For creating a new dentist

    if (id) {
        // If there's an ID, it means we're updating an existing dentist
        url += `/${id}`;
    }

    fetch(url, {
        method: id ? 'PUT' : 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            // If the dentist is successfully saved/updated, reload the list of dentists
            cargarOdontologos();
            // Clear the form fields after successful submission
            document.getElementById('idOdontologo').value = '';
            document.getElementById('numeroDeMatricula').value = '';
            document.getElementById('nombre').value = '';
            document.getElementById('apellido').value = '';
        } else {
            // Handle errors during saving/updating
            throw new Error('Error al guardar el odontólogo');
        }
    })
    .catch(error => console.error('Error al guardar el odontólogo:', error));
}


