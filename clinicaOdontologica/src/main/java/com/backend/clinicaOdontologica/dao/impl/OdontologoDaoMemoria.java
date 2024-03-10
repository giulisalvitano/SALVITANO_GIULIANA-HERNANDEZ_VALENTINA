package src.com.backend.parcial.dao.impl;
import src.com.backend.parcial.dao.IDao;
import src.com.backend.parcial.entity.Odontologo;
import org.apache.log4j.Logger;
import java.util.List;

public class OdontologoDaoMemoria implements IDao<Odontologo> {
    private final Logger LOGGER = Logger.getLogger(OdontologoDaoMemoria.class);
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
}
