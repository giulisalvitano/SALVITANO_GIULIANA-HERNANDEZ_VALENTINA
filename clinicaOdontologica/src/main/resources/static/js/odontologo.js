// URL base para las peticiones al servidor
const baseUrl = 'http://localhost:8080/odontologos';

// Función para cargar a la cargar la página
document.addEventListener('DOMContentLoaded', () => {
const guardarOdontologo = async () => {
    const numeroDeMatricula = document.getElementById('numeroDeMatricula').value;
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;

    // Basic validation (optional, improve based on your requirements)
    if (!numeroDeMatricula || !nombre || !apellido) {
      alert('Por favor, complete todos los campos');
      return;
    }

    const odontologo = {
      numeroDeMatricula,
      nombre,
      apellido,
    };

    try {
      const response = await fetch('http://localhost:8080/odontologos/registrar', { // Adjust URL if needed
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(odontologo),
      });

      if (response.ok) {
        // Success
        const data = await response.json();
        alert(`Odontologo guardado con ID: ${data.id}`); // Or display success message
        limpiarFormulario();
        listarOdontologos(); // Update list (if implemented)
      } else {
        // Handle errors (consider more specific error handling)
        const errorData = await response.json();
        alert(`Error al guardar odontologo: ${errorData.message}`); // Or display specific error message
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Error al guardar odontologo'); // Or display generic error message
    }
  };

  const limpiarFormulario = () => {
    document.getElementById('idOdontologo').value = ''; // Clear hidden ID field (if applicable)
    document.getElementById('numeroDeMatricula').value = '';
    document.getElementById('nombre').value = '';
    document.getElementById('apellido').value = '';
  };

  // Add event listener to the guardarOdontologo button (or appropriate element)
  document.getElementById('guardarOdontologo').addEventListener('click', guardarOdontologo);

  // Consider implementing functionality to list odontologos (if needed)
    cargarOdontologos();





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






});