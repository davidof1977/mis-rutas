package davidof.misrutas.controller;

import static davidof.misrutas.security.Constantes.SUPER_SECRET_KEY;
import static davidof.misrutas.security.Constantes.TOKEN_BEARER_PREFIX;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import davidof.misrutas.repository.entity.Ruta;
import davidof.misrutas.service.FileStorageService;
import davidof.misrutas.service.RutaService;
import io.jsonwebtoken.Jwts;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class RutasController {
	
		
		@Autowired
		private RutaService service;

		@Autowired
		private FileStorageService fileStorageService;
		
		@GetMapping("/rutas")
		public List<Ruta> obtenerRutasUsuario(@RequestHeader("authorization") String jwt) {
			List<Ruta> rutas = service.obtenerRutasUsuario(getUserFormJWT(jwt));

			return rutas;
		}

	
		@GetMapping("/ruta/{id}")
		public Ruta obtenerRuta(@PathVariable String id) {
			Ruta ruta = service.obtenerRuta(id);
			try {
			    byte[] bytes = ruta.getFichero();
			      
				ruta.setGpxFile(new String(bytes));
			}catch(Exception e) {
				e.printStackTrace();
			}
			return ruta;
		}
		
		@DeleteMapping("/rutas/{id}")
		public void eliminar(@PathVariable String id) {
			service.eliminar(id);	
		}

		 @PostMapping("/rutas/uploadfile")
		    public void uploadFile(@RequestParam("file") MultipartFile file, 
		    		@RequestParam("titulo") String titulo,
		    		@RequestParam("desc") String desc,
		    		@RequestHeader("authorization") String jwt) throws Exception {
			 
				try {

					InputStream is = file.getResource().getInputStream();
					Document doc =	DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
					Ruta ruta = new Ruta();
					ruta.setLatitud(new Float(doc.getElementsByTagName("trkpt").item(0).getAttributes().getNamedItem("lat").getNodeValue()));
					ruta.setLongitud(new Float(doc.getElementsByTagName("trkpt").item(0).getAttributes().getNamedItem("lon").getNodeValue()));
					ruta.setFecha(LocalDate.parse(doc.getElementsByTagName("time").item(0).getTextContent().substring(0,10)));
					ruta.setDesc(desc);
					ruta.setTitulo(titulo);
					ruta.setUsuario(getUserFormJWT(jwt));
					ruta.setFichero(file.getBytes());
					service.guardar(ruta);

				}catch(Exception ex) {
					ex.printStackTrace();
				}

		   }
		 
		 @GetMapping("/rutas/downloadgpx/{id}")
		    public ResponseEntity<Resource> downloadFile(@PathVariable String id, HttpServletRequest request) {
		        // Load file as Resource
		        Resource resource=null;
				try {
					Ruta ruta = service.obtenerRuta(id);
					resource = fileStorageService.loadFileAsResource(ruta.getFichero());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        // Try to determine file's content type
		        String contentType = null;
		        try {
		            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		        } catch (IOException ex) {
		            System.out.println("Could not determine file type.");
		        }

		        // Fallback to the default content type if type could not be determined
		        if(contentType == null) {
		            contentType = "application/octet-stream";
		        }

		        return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(contentType))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		                .body(resource);
		    }
			/**
			 * @param jwt
			 * @return
			 */
			private String getUserFormJWT(String jwt) {
				String usuario = Jwts.parser()
						.setSigningKey(SUPER_SECRET_KEY)
						.parseClaimsJws(jwt.replace(TOKEN_BEARER_PREFIX, ""))
						.getBody()
						.getSubject();
				return usuario;
			}
}
