package web.santiago.gcp.configurations;

public class SecurityConstants {
    // Authorization Bearer token
    public static final String SECRET = "gcpWeb";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/*/users/**";
    public static final String SEARCH_URL = "livreAcesso";
    public static final long EXPIRATION_TIME = 86400000L; //24 horas
}
