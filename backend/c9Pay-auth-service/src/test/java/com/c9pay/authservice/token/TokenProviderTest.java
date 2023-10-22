package com.c9pay.authservice.token;

import com.c9pay.authservice.web.dto.ExchangeToken;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.security.Signature;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TokenProviderTest {
    static String privateKeyString = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAxKDUR0cZlqlQMApI1elJYRI2nl18kl1viRvvbiZy+SeC2YM0NCbzoBcuG2sq3FaercxKt4tWtMR+Cyix1+jjkwIDAQABAkAso05NuCho+ZsIrO0IEuPjs5zOqu6C089kUwEyAmyLfzSEDrX0xLjlyE+v4SqQ/pnbbMZuSN6PCGhJfPgK9rHRAiEAzXLLnv9o/BDIaGs9/I5ievVu+0Mu9UX5U8JZtiIW+2UCIQD1AnNFuG4H5buhYQLp7DM0TpE+DsAqGRaKtkowz53/lwIgeLNzy6LUpBqcEzTGQyXH1+Nv43CSGwmUNNBe8nFOvT0CIQDNeo+YUlOdNfNFJaoe6uQKozW2OQK6i63XCYhxJT4Z4wIgTv1gxrNiHRPuTu8zAsfnO3COT/f2Yd8wXf4eHNSo/gk=";
    static String publicKeyString = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMSg1EdHGZapUDAKSNXpSWESNp5dfJJdb4kb724mcvkngtmDNDQm86AXLhtrKtxWnq3MSreLVrTEfgsosdfo45MCAwEAAQ==";

    @Test
    void 암복호화테스트() throws Exception {

        KeyManager keyManager = new KeyManager(publicKeyString, privateKeyString);
        TokenProvider tokenProvider = new TokenProvider(6000, privateKeyString);
        UUID expected = UUID.randomUUID();

        ExchangeToken exchangeToken = tokenProvider.generateToken(expected.toString());

        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(keyManager.getPublicKey());
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyManager.getPublicKey());

        byte[] encrypted = Base64.getDecoder().decode(exchangeToken.getContent().getBytes());
        byte[] decrypted = cipher.doFinal(encrypted);
        String decoded = new String(decrypted);

        String verifyString = exchangeToken.getContent() + exchangeToken.getExpiredAt();
        signature.update(verifyString.getBytes());
        byte[] sign = Base64.getDecoder().decode(exchangeToken.getSign().getBytes());
        assertTrue(signature.verify(sign));
        assertEquals(expected.toString(), decoded);
    }
}