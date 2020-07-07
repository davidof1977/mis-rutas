package davidof.misrutas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import davidof.misrutas.repository.entity.Ruta;

@Repository
public interface RutaRepository extends MongoRepository<Ruta, String> {
	public List<Ruta> findByUsuario(String nombre);
}
