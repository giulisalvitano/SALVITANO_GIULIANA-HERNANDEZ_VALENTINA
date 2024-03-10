package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dao.IDao;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.service.IOdontologoService;

import java.util.List;

public class OdontologoService implements IOdontologoService {
    private final IDao<Odontologo> odontologoIDao;

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoIDao.guardar(odontologo);
    }

    @Override
    public List<Odontologo> listarOdontologos() {
        return odontologoIDao.listarTodos();
    }

}

