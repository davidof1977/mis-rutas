package davidof.misrutas.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import davidof.misrutas.repository.entity.Ruta;
import davidof.misrutas.service.FileStorageService;
import davidof.misrutas.service.RutaService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class RutasController {
	
		
		@Autowired
		private RutaService service;

		@Autowired
		private FileStorageService fileStorageService;
		
		@GetMapping("/rutas/{usuario}")
		public List<Ruta> obtenerRutasUsuario(@PathVariable String usuario) {
			List<Ruta> rutas = service.obtenerRutasUsuario(usuario);

			return rutas;
		}

	
		@GetMapping("/ruta/{id}")
		public Ruta obtenerRuta(@PathVariable String id) {
			Ruta ruta = service.obtenerRuta(id);
			try {
				File f = Paths.get(fileStorageService.getPath() + "\\" + ruta.getGpxFile()).toFile();
				
				FileReader reader = new FileReader(f);
				BufferedReader b = new BufferedReader(reader);
				String cadena;
				String aux="";
				while((cadena = b.readLine())!=null) {
			          aux = aux + cadena;
			      }
			      b.close();
				ruta.setGpxFile(aux);
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
		    		@RequestParam("usuario") String usuario,
		    		@RequestParam("titulo") String titulo,
		    		@RequestParam("desc") String desc) throws Exception {
			 
				try {
			        String fileName = fileStorageService.storeFile(file);
					File f = Paths.get(fileStorageService.getPath() + "\\" + fileName).toFile();
					Document doc =	DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
					Ruta ruta = new Ruta();
					ruta.setLatitud(new Float(doc.getElementsByTagName("trkpt").item(0).getAttributes().getNamedItem("lat").getNodeValue()));
					ruta.setLongitud(new Float(doc.getElementsByTagName("trkpt").item(0).getAttributes().getNamedItem("lon").getNodeValue()));
					ruta.setFecha(LocalDate.parse(doc.getElementsByTagName("time").item(0).getTextContent().substring(0,10)));
					ruta.setDesc(desc);
					ruta.setGpxFile(fileName);
					ruta.setTitulo(titulo);
					ruta.setUsuario(usuario);
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
					resource = fileStorageService.loadFileAsResource(ruta.getGpxFile());
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
		
}
