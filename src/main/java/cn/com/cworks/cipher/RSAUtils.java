package cn.com.cworks.cipher;

import cn.com.cworks.file.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {

    private static final int KEY_SIZE = 1024;
    private static final String ALGORITHM = "RSA";
    private static final String PUBLIC_KEY = "PublicKey";
    private static final String PRIVATE_KEY = "PrivateKey";

    /**
     * 获取公钥 私钥对的Map，并存入文件中
     *
     * @return 存储公私钥的map
     */
    public static Map<String, String> getKeyPair() {
        Map<String, String> keyMap = new HashMap<>();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey aPublic = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
            String publicKeyString = Base64.getEncoder().encodeToString(aPublic.getEncoded());
            String privateKeyString = Base64.getEncoder().encodeToString(aPrivate.getEncoded());
            FileUtils.writeStr(publicKeyString, "./keys/public_key.txt");
            keyMap.put(PUBLIC_KEY, publicKeyString);
            FileUtils.writeStr(privateKeyString, "./keys/private_key.txt");
            keyMap.put(PRIVATE_KEY, privateKeyString);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个算法");
        }
        return keyMap;
    }

    public static String encodeWithPublicKey(String publicKey, String data) {
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
            PublicKey key = factory.generatePublic(spec);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = data.getBytes();
            int inputLen = bytes.length;
            int offLen = 0;//偏移量
            int i = 0;
            ByteArrayOutputStream bops = new ByteArrayOutputStream();
            while (inputLen - offLen > 0) {
                byte[] cache;
                if (inputLen - offLen > 117) {
                    cache = cipher.doFinal(bytes, offLen, 117);
                } else {
                    cache = cipher.doFinal(bytes, offLen, inputLen - offLen);
                }
                bops.write(cache);
                i++;
                offLen = 117 * i;
            }
            bops.close();
            byte[] encryptedData = bops.toByteArray();
            return Base64.getEncoder().encodeToString(encryptedData);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException("没有这个算法");
        } catch (BadPaddingException | IllegalBlockSizeException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("加密错误");
        }
    }

    public static String decryptWithPrivateKey(String privateKey, String data) {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
            PrivateKey key = factory.generatePrivate(spec);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = Base64.getDecoder().decode(data);
            int inputLen = bytes.length;
            int offLen = 0;
            int i = 0;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (inputLen - offLen > 0) {
                byte[] cache;
                if (inputLen - offLen > 128) {
                    cache = cipher.doFinal(bytes, offLen, 128);
                } else {
                    cache = cipher.doFinal(bytes, offLen, inputLen - offLen);
                }
                byteArrayOutputStream.write(cache);
                i++;
                offLen = 128 * i;

            }
            byteArrayOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return new String(byteArray);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException("没有这个算法");
        } catch (BadPaddingException | IllegalBlockSizeException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("解密错误");
        }
    }

}
