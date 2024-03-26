// URL base para las peticiones al servidor
const baseUrl = 'http://localhost:8080/odontologos';

// Función para cargar los pacientes al cargar la página
document.addEventListener('DOMContentLoaded', () => {
    cargarOdontologos();
});



    fetch(baseUrl)
        .then(response => response.json())
        .then(odontologos => mostrarOdontologos(odontologos))
        .catch(error => console.error('Error al cargar los Odontologos:', error));
}

// Función para mostrar los odontologos en la tabla
function mostrarOdontologos(odontologos) {
    const tbodyOdontologos = document.getElementById('tbody-odontologos');
    tbodyOdontologos.innerHTML = ''; // Limpiar el contenido actual
    odontologos.forEach(odontologos => {
        const row = `
            <tr>
            <td>${odontologo.id}</td>
                <td>${odontologo.matricula}</td>
                <td>${odontologo.nombre}</td>
                <td>${odontologo.apellido}</td>

                <td>
                    <button class="btn btn-primary" onclick="editarOdontologo(${odontologo.id})">Editar</button>
                    <button class="btn btn-danger" onclick="eliminarOdontologo(${odontologo.id})">Eliminar</button>
                </td>
            </tr>
        `;
        tbodyOdontologos.innerHTML += row;
    });
}














/*let boton = document.getElementById("btn_registrar")

boton.addEventListener("click", evento =>{
registrarOdontologo();
});*/

// JavaScript (script.js)
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('id_odontologo');
    const tableBody = document.querySelector('#odontologosTable tbody');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        const matricula = document.getElementById('matricula').value;
        const nombre = document.getElementById('nombre').value;
        const apellido = document.getElementById('apellido').value;

        try {
            const response = await fetch('http://localhost:8080/odontologos/registrar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ matricula, nombre, apellido })
            });

            if (response.ok) {
                const odontologo = await response.json();
                addRowToTable(odontologo);
            } else {
                console.error('Error al guardar el odontólogo');
            }
        } catch (error) {
            console.error('Error en la solicitud:', error);
        }
    });

    async function fetchOdontologos() {
        try {
            const response = await fetch('/odontologos');
            if (response.ok) {
                const odontologos = await response.json();
                odontologos.forEach(addRowToTable);
            } else {
                console.error('Error al obtener los odontólogos');
            }
        } catch (error) {
            console.error('Error en la solicitud:', error);
        }
    }

    function addRowToTable(odontologo) {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${odontologo.matricula}</td>
            <td>${odontologo.nombre}</td>
            <td>${odontologo.apellido}</td>
            <td>
            <button class="btn btn-danger" onclick="eliminarOdontologo(${odontologo.id})">Eliminar</button>
        </td>
        `;
        tableBody.appendChild(row);
    }

    fetchOdontologos();

    function eliminarOdontologo(id) {
        // Eliminar la fila de la tabla
        const fila = document.getElementById(`odontologo-${id}`);
        fila.remove();

        // Realizar la solicitud para eliminar el odontólogo en el backend
        fetch(`http://localhost:8080/odontologos/eliminar/${id}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                console.log('Odontólogo eliminado correctamente');
            } else {
                console.error('Error al eliminar el odontólogo');
            }
        })
        .catch(error => {
            console.error('Error en la solicitud:', error);
        });
    }



});