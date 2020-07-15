package davidof.misrutas.repository.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Ruta {
	@Id
	private String id;
	private String usuario;
	private String gpxFile;
	private float latitud;
	private float longitud;
	private String titulo;
	private String desc;
	private LocalDate fecha;
	private List<String> imagenes;
	private byte[] fichero;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public float getLatitud() {
		return latitud;
	}
	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}
	public float getLongitud() {
		return longitud;
	}
	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public List<String> getImagenes() {
		return imagenes;
	}
	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public byte[] getFichero() {
		return fichero;
	}
	public void setFichero(byte[] fichero) {
		this.fichero = fichero;
	}
	public String getGpxFile() {
		return gpxFile;
	}
	public void setGpxFile(String gpxFile) {
		this.gpxFile = gpxFile;
	}
}
