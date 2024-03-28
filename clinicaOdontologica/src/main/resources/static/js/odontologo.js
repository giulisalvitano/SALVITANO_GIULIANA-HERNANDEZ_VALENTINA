// URL base para las peticiones al servidor
const baseUrl = 'http://localhost:8080/odontologos';

// Función para cargar a la cargar la página
document.addEventListener('DOMContentLoaded', () => {
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
                    <button class="btn btn-primary" onclick="editarOdontologo(${odontologo.id})">Editar</button>
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



