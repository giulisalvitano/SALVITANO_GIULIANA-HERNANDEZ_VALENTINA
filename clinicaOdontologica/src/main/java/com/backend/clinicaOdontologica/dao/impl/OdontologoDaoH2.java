package src.com.backend.parcial.dao.impl;

import src.com.backend.parcial.dao.IDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import src.com.backend.parcial.dbconnection.H2Connection;
import src.com.backend.parcial.entity.Odontologo;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    private final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoGuardado = null;


        try {

            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ODONTOLOGOS(MATRICULA, NOMBRE, APELLIDO) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());

            preparedStatement.execute();

            connection.commit();

            odontologoGuardado = new Odontologo(odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                odontologoGuardado.setId(resultSet.getInt(1));
            }

            LOGGER.info("Odontologo guardado: " + odontologoGuardado);


        } catch (Exception e) {
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
            {
                try {
                    connection.close();
                } catch (Exception ex) {
                    LOGGER.error("No se pudo cerrar la conexion: " + ex.getMessage());
                }
            }
        }

        return odontologoGuardado;
    }

    @Override
    public List<Odontologo> listarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ODONTOLOGOS");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                odontologos.add(new Odontologo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }

            LOGGER.info("Odontologos encontrados: " + odontologos);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return odontologos;

    }


}

