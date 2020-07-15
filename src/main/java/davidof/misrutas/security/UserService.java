package davidof.misrutas.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import davidof.misrutas.repository.entity.Usuario;
import davidof.misrutas.service.UsuarioService;
 
@Service
public class UserService implements UserDetailsService {
 
	@Autowired
	private UsuarioService usuarioService;
	  private final PasswordEncoder passwordEncoder;
	  
	    public UserService(PasswordEncoder passwordEncoder) {
	        this.passwordEncoder = passwordEncoder;
	    }
	

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    	List<Usuario> usuarios = usuarioService.obtenerUsuario(login);
		//bCryptPasswordEncoder = new BCryptPasswordEncoder();
		if (usuarios != null) {
			return new User(login, passwordEncoder.encode(usuarios.get(0).getPassword()), new ArrayList<>());
			//return usuarios.stream().anyMatch(u -> u.getPassword().equalsIgnoreCase(bCryptPasswordEncoder.encode(usuario.getPassword())));
		}else {

	        throw new UsernameNotFoundException("User not found with login: " + login);
		}			
    }
}
