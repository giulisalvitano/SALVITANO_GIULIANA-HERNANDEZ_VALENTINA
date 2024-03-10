package com.backend.clinicaOdontologica.dao.impl;

import com.backend.clinicaOdontologica.dao.IDao;
import com.backend.clinicaOdontologica.dbconnection.H2Connection;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.entity.Turno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Clase que implementa las operaciones de acceso a datos para la entidad Turno utilizando una base de datos H2
@Component
public class TurnoDaoH2 implements IDao<Turno> {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoDaoH2.class);

    // Método para guardar un turno en la base de datos
    @Override
    public Turno guardar(Turno turno) {
        Connection connection = null;
        Turno turnoRegistrado = null;

        try {
            // Establece la conexión a la base de datos
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            // Prepara y ejecuta la sentencia SQL para insertar un turno en la tabla TURNOS
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TURNOS (ODONTOLOGO_ID, PACIENTE_ID, FECHA_Y_HORA) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, turno.getOdontologo().getId());
            preparedStatement.setInt(2, turno.getPaciente().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(turno.getFechaYHora()));
            preparedStatement.execute();

            // Crea un nuevo objeto Turno con el ID generado
            turnoRegistrado = new Turno(turno.getOdontologo(), turno.getPaciente(), turno.getFechaYHora());

            // Obtiene el ID generado y lo asigna al turno registrado
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                turnoRegistrado.setId(resultSet.getInt("id"));
            }

            // Confirma la transacción
            connection.commit();
            LOGGER.info("Se ha registrado el turno: " + turnoRegistrado);
        } catch (Exception e) {
            // Manejo de errores y rollback en caso de excepción
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Tuvimos un problema");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } catch (SQLException exception) {
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        } finally {
            // Cierra la conexión a la base de datos
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                LOGGER.error("No se pudo cerrar la conexión: " + ex.getMessage());
            }
        }
        return turnoRegistrado;
    }

    // Método para buscar un turno por su ID en la base de datos
    @Override
    public Turno buscarPorId(int id) {
        Connection connection = null;
        Turno turno = null;

        try {
            // Establece la conexión a la base de datos
            connection = H2Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM TURNOS WHERE ID = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                turno = crearObjetoTurno(rs);
            }

            // Registra información sobre el turno encontrado o un mensaje de error si no se encuentra
            if (turno == null) {
                LOGGER.error("No se ha encontrado el turno con id: " + id);
            } else {
                LOGGER.info("Se ha encontrado el turno: " + turno);
            }
        } catch (Exception e) {
            // Manejo de errores
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra la conexión a la base de datos
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la conexión. " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return turno;
    }

    // Método para listar todos los turnos en la base de datos
    @Override
    public List<Turno> listarTodos() {
        Connection connection = null;
        List<Turno> turnos = new ArrayList<>();

        try {
            // Establece la conexión a la base de datos
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TURNOS");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Crea y agrega un objeto Turno a la lista de turnos
                Turno turno = crearObjetoTurno(resultSet);
                turnos.add(turno);
            }
            LOGGER.info("Listado de todos los turnos: " + turnos);
        } catch (Exception e) {
            // Manejo de errores
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra la conexión a la base de datos
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la conexión. " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return turnos;
    }

    // Método privado para crear un objeto Turno a partir de un ResultSet
    private Turno crearObjetoTurno(ResultSet resultSet) throws SQLException {
        // Obtiene los datos del odontólogo y paciente asociados al turno
        Odontologo odontologo = new OdontologoDaoH2().buscarPorId(resultSet.getInt("odontologo_id"));
        Paciente paciente = new PacienteDaoH2().buscarPorId(resultSet.getInt("paciente_id"));
        LocalDateTime fechaYHora = resultSet.getTimestamp("fecha_y_hora").toLocalDateTime();
        // Crea y retorna un nuevo objeto Turno
        return new Turno(resultSet.getInt("id"), odontologo, paciente, fechaYHora);
    }
}
