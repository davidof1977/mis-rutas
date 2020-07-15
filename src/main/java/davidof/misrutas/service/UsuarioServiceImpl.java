package davidof.misrutas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import davidof.misrutas.repository.UsuarioRepository;
import davidof.misrutas.repository.entity.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public void guardar(Usuario usuario) {
		repository.save(usuario);
	}

	@Override
	public List<Usuario> obtenerUsuario(String nombre) {
		return repository.findByNombre(nombre);
	}
}
