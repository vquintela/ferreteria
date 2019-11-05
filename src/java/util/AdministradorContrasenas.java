package util;

import org.apache.commons.codec.digest.DigestUtils;


public class AdministradorContrasenas {
    
    public static String encrypt (String texto){
        String key = "DfgTfdGbgh48951";
        String textoEncryptado = DigestUtils.sha512Hex(texto + key);
        return textoEncryptado;
    }
    
    public static boolean validarContrasena(String originalPassword, String storedPassword) {
        String original = encrypt(originalPassword);
        
        return original.equalsIgnoreCase(storedPassword);    
    }
}
