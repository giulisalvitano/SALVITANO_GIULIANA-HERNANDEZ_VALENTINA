package com.backend.clinicaOdontologica.dao.impl;

import com.backend.clinicaOdontologica.dao.IDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.backend.clinicaOdontologica.entity.Odontologo;
import java.util.List;

public class OdontologoDaoMemoria implements IDao<Odontologo> {
    private final Logger LOGGER = LoggerFactory.getLogger(DomicilioDaoH2.class);
    private List<Odontologo> OdontologoRepositorio;


    public OdontologoDaoMemoria(List<Odontologo> OdontologoRepositorio) {
        this.OdontologoRepositorio = OdontologoRepositorio;
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) {

        int id = OdontologoRepositorio.size() + 1;
        Odontologo odontologoGuardado = new Odontologo(id, odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());

        OdontologoRepositorio.add(odontologo);
        LOGGER.info("Odontologo guardado: " + odontologoGuardado);
        return odontologoGuardado;

    }

    @Override
    public List<Odontologo> listarTodos() {

        LOGGER.info("Todos los odontologos: " + OdontologoRepositorio);
        return OdontologoRepositorio;
    }

    @Override
    public Odontologo buscarPorId(int id) {
        return null;
    }
}
