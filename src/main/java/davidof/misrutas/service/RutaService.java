package davidof.misrutas.service;

import java.util.List;
import java.util.Optional;

import davidof.misrutas.repository.entity.Ruta;



public interface RutaService {
	
	/**
	 * Guarda el producto
	 * 
	 * @param producto
	 */
	void guardar(Ruta ruta);
	
	/**
	 * Recupera la lista completa de productos
	 * 
	 * @return
	 */
	List<Ruta> obtenerRutasUsuario(String usuario);
	
	/**
	 * Devuelve el producto correspondiente al id si existe
	 * 
	 * @param id Id del producto
	 * @return
	 */
	Ruta obtenerRuta(String id);
	
	/**
	 * Elimina el producto correspondiente al id
	 * 
	 * @param id Id del producto
	 */
	void eliminar(String id);
	
}
