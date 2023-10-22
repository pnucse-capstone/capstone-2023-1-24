package com.c9pay.authservice.token;

import com.c9pay.authservice.web.dto.ExchangeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Component
public class TokenProvider {
    private final long tokenValidateInMilliSeconds;
    private final PrivateKey privateKey;

    public TokenProvider(@Value("${token.time}") long tokenValidateTime,
                         @Value("${auth.private-key}") String stringPrivateKey){
        this.tokenValidateInMilliSeconds = tokenValidateTime;

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] bytePrivateKey = Base64.getDecoder().decode(stringPrivateKey.getBytes());
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
            privateKey = keyFactory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ExchangeToken generateToken(String plainText) {
        String encrypt = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] byteEncryptedData = cipher.doFinal(plainText.getBytes());
            encrypt = Base64.getEncoder().encodeToString(byteEncryptedData);

            Long expiredAt = System.currentTimeMillis() + tokenValidateInMilliSeconds;

            Signature signature = Signature.getInstance("SHA1withRSA");

            signature.initSign(privateKey);
            String signText = encrypt + expiredAt;
            signature.update(signText.getBytes());
            byte[] sign = signature.sign();
            String signString = Base64.getEncoder().encodeToString(sign);

            return new ExchangeToken(encrypt, expiredAt, signString);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
