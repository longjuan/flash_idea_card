package top.zway.fic.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ZZJ
 */
@SpringBootTest
public class PasswordTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void encodePassword(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

}
