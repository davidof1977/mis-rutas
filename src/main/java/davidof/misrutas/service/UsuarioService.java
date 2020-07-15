package davidof.misrutas.service;

import java.util.List;

import davidof.misrutas.repository.entity.Usuario;

public interface UsuarioService {
	
	/**
	 * Guarda el producto
	 * 
	 * @param producto
	 */
	void guardar(Usuario usuario);
	
	/**
	 * Devuelve el producto correspondiente al id si existe
	 * 
	 * @param id Id del producto
	 * @return
	 */
	List<Usuario> obtenerUsuario(String nombre);
	


}
