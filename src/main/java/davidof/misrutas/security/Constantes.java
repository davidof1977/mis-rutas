package davidof.misrutas.security;

public class Constantes {
	// Spring Security

		public static final String LOGIN_URL = "/login";
		public static final String REGISTRO_URL = "/registro";
		public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
		public static final String HEADER_EXPIRATION_KEY = "Expiration";
		
		public static final String TOKEN_BEARER_PREFIX = "Bearer ";

		// JWT

		public static final String ISSUER_INFO = "Davidof1977";
		public static final String SUPER_SECRET_KEY = "12345678";
		public static final long TOKEN_EXPIRATION_TIME = 8000000; 
}
