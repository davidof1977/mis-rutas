package davidof.misrutas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import davidof.misrutas.repository.RutaRepository;
import davidof.misrutas.repository.entity.Ruta;

@Service
public class RutaServiceImpl implements RutaService {
	
	@Autowired
	private RutaRepository repo;

	@Override
	public void guardar(Ruta ruta) {
		repo.save(ruta);
	}

	@Override
	public List<Ruta> obtenerRutasUsuario(String usuario) {
		return repo.findByUsuario(usuario);
	}

	@Override
	public Ruta obtenerRuta(String id) {
		return repo.findById(id).get();
	}

	@Override
	public void eliminar(String id) {
		repo.deleteById(id);
	}
	
}
