package im.status.ethereum.keycard;

import android.support.annotation.NonNull;
import android.util.Base64;
import static android.util.Base64.NO_PADDING;

import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class SmartCardSecrets {
    public static long PIN_BOUND = 999999L;
    public static long PUK_BOUND = 999999999999L;

    private String pin;
    private String puk;
    private String pairingPassword;

    public SmartCardSecrets(String pin, String puk, String pairingPassword) {
        this.pin = pin;
        this.puk = puk;
        this.pairingPassword = pairingPassword;
    }

    @NonNull
    public static SmartCardSecrets generate() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String pairingPassword = randomToken(12);
        long pinNumber = randomLong(PIN_BOUND);
        long pukNumber = randomLong(PUK_BOUND);
        String pin = String.format("%06d", pinNumber);
        String puk = String.format("%012d", pukNumber);

        return new SmartCardSecrets(pin, puk, pairingPassword);
    }

    public String getPin() {
        return pin;
    }

    public String getPuk() {
        return puk;
    }

    public String getPairingPassword() {
        return pairingPassword;
    }

    public static long randomLong(long bound) {
        SecureRandom random = new SecureRandom();
        return Math.abs(random.nextLong()) % bound;
    }

    public static String randomToken(int length) {
        return Base64.encodeToString(randomBytes(length), NO_PADDING);
    }

    public static byte[] randomBytes(int length) {
        SecureRandom random = new SecureRandom();
        byte data[] = new byte[length];
        random.nextBytes(data);

        return data;
    }
}
