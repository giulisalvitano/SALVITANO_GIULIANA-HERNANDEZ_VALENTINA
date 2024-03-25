

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
        `;
        tableBody.appendChild(row);
    }

    fetchOdontologos();
});
