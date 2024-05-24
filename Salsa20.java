import java.util.Arrays;

class Salsa20{
    private static final long MOD = 4294967295L;
    public static int rotateLeft(int value, int shift)
    {
        int output=(value << shift) | (value >>> (32 - shift));
        return output;
    }
    public static int[] quarterRound(int a0, int a1, int a2, int a3)
    {
        int[] result = new int[4];
        result[1] = a1 ^ rotateLeft((int) ((a0 + a3) & MOD), 7);
        result[2] = a2 ^ rotateLeft((int) ((result[1] + a0) & MOD), 9);
        result[3] = a3 ^ rotateLeft((int) ((result[2] + result[1]) & MOD), 13);
        result[0] = a0 ^ rotateLeft((int) ((result[3] + result[2]) & MOD), 18);
        return result;
    }
    public static int[] rowround(int[] inputArray)
    {
        int[] result = new int[16];
        int[] quarterRound = quarterRound(inputArray[0], inputArray[1], inputArray[2], inputArray[3]);
        result[0] = quarterRound[0];
        result[1] = quarterRound[1];
        result[2] = quarterRound[2];
        result[3] = quarterRound[3];
        quarterRound = quarterRound(inputArray[5], inputArray[6], inputArray[7], inputArray[4]);
        result[5] = quarterRound[0];
        result[6] = quarterRound[1];
        result[7] = quarterRound[2];
        result[4] = quarterRound[3];
        quarterRound = quarterRound(inputArray[10], inputArray[11], inputArray[8], inputArray[9]);
        result[10] = quarterRound[0];
        result[11] = quarterRound[1];
        result[8] = quarterRound[2];
        result[9] = quarterRound[3];
        quarterRound = quarterRound(inputArray[15], inputArray[12], inputArray[13], inputArray[14]);
        result[15] = quarterRound[0];
        result[12] = quarterRound[1];
        result[13] = quarterRound[2];
        result[14] = quarterRound[3];
        return result;
    }

    public static int[] columnround(int[] inputArray)
    {
        int[] result = new int[16];
        int[] quarterRound = quarterRound(inputArray[0], inputArray[4], inputArray[8], inputArray[12]);
        result[0] = quarterRound[0];
        result[4] = quarterRound[1];
        result[8] = quarterRound[2];
        result[12] = quarterRound[3];
        quarterRound = quarterRound(inputArray[5], inputArray[9], inputArray[13], inputArray[1]);
        result[5] = quarterRound[0];
        result[9] = quarterRound[1];
        result[13] = quarterRound[2];
        result[1] = quarterRound[3];
        quarterRound = quarterRound(inputArray[10], inputArray[14], inputArray[2], inputArray[6]);
        result[10] = quarterRound[0];
        result[14] = quarterRound[1];
        result[2] = quarterRound[2];
        result[6] = quarterRound[3];
        quarterRound = quarterRound(inputArray[15], inputArray[3], inputArray[7], inputArray[11]);
        result[15] = quarterRound[0];
        result[3] = quarterRound[1];
        result[7] = quarterRound[2];
        result[11] = quarterRound[3];
        return result;
    }

    public static int[] doubleround(int[] a)
    {
        return rowround(columnround(a));
    }
    public static int littleendian(int a0, int a1, int a2, int a3)
    {
        return a0 + (a1 << 8) + (a2 << 16) + (a3 << 24);
    }

    public static int[] invertLittleendian(int a)
    {
        int[] output = new int[4];
        output[0] = a & 0xFF;
        output[1] = a >>> 8 & 0xFF;
        output[2] = a >>> 16 & 0xFF;
        output[3] = a >>> 24 & 0xFF;
        return output;
    }

    public static int[] salsa20Hash(int[] seq)
    {
        int[] x = new int[16];
        int[] w = new int[16];
        for (int i = 0; i < 16; i++)
        {
            x[i] = w[i] = littleendian(seq[4 * i], seq[4 * i + 1], seq[4 * i + 2], seq[4 * i + 3]);
        }
        for (int i = 0; i < 6; i++)
        {
            x = doubleround(x);
        }
        int[] littleEndianInverted;
        int[] hashoutput = new int[64];
        for (int i = 0; i < 16; i++)
        {
            littleEndianInverted = invertLittleendian(x[i] + w[i]);
            hashoutput[i * 4] = littleEndianInverted[0];
            hashoutput[i * 4 + 1] = littleEndianInverted[1];
            hashoutput[i * 4 + 2] = littleEndianInverted[2];
            hashoutput[i * 4 + 3] = littleEndianInverted[3];
        }
        return hashoutput;
    }

    public static int[] salsa20Expand8(int[] k, int[] n) {
        int[] keystream = new int[64];
        int[][] alpha =
        {
                { 101, 120, 112, 97 },
                { 110, 100, 32, 48 },
                { 56, 45, 98, 121 },
                { 116, 101, 32, 107 }
        };
        for (int i = 0; i < 64; i += 20)
        {
            for (int j = 0; j < 4; j++)
            {
                keystream[i + j] = alpha[i / 20][j];
            }
        }
        for (int i = 0; i < 8; i++)
        {
            keystream[i + 4] = k[i]; 
            keystream[i + 24] = n[i];
            keystream[i + 44] = k[i];
        }
        for (int i = 0; i < 8; i++)
        {
            keystream[i + 12] = k[i];
            keystream[i + 52] = k[i];
        }
        return salsa20Hash(keystream);
    }
    public static int[] salsa20Expand16(int[] k, int[] n)
    {
        int[] keystream = new int[64];
        int[][] tau =
         {
                { 101, 120, 112, 97 },
                { 110, 100, 32, 49 },
                { 54, 45, 98, 121 },
                { 116, 101, 32, 107 }
        };
        for (int i = 0; i < 64; i += 20){
            for (int j = 0; j < 4; j++) {
                keystream[i + j] = tau[i / 20][j];
            }
        }
        for(int i = 0; i < 16; i++)
        {
            keystream[i + 4] = k[i];
            keystream[i + 24] = n[i];
            keystream[i + 44] = k[i];
        }
        return salsa20Hash(keystream);
    }

    public static int[] salsa20Expand32(int[] k, int[] n)
    {
        int[] keystream = new int[64];
        int[][] sigma =
        {
                { 101, 120, 112, 97 },
                { 110, 100, 32, 51 },
                { 50, 45, 98, 121 },
                { 116, 101, 32, 107 }
        };
        for (int i = 0; i < 64; i += 20)
        {
            for (int j = 0; j < 4; j++) {
                keystream[i + j] = sigma[i / 20][j];
            }
        }
        for(int i = 0; i < 16; i++)
        {
            keystream[i + 4] = k[i];
            keystream[i + 24] = n[i];
            keystream[i + 44] = k[i + 16];
        }
        return salsa20Hash(keystream);
    }
    public static int[] salsa20Encryption(int keylen, int[] key, int[] nonce, int[] message)
    {
        long streamIndex = 0;
        int[] n = new int[16];
        int[] keystream = new int[64];
        int[] encrypted = message.clone();
        if (keystream == null || key == null || nonce == null)
        {
            return null;
        }
        for (int i = 0; i < 8; i++)
        {
            n[i] = nonce[i];
        }
        if (Long.remainderUnsigned(streamIndex, 64) != 0)
        {
            if (keylen == 8)
            {
                keystream = salsa20Expand8(key, n);
            }
            if (keylen == 16)
            {
                keystream = salsa20Expand16(key, n);
            }
            else if (keylen == 32)
            {
                keystream = salsa20Expand32(key, n);
            }
        }
        for (int i = 0; i < message.length; i++)
        {
            if (Long.remainderUnsigned(streamIndex + i, 64) == 0)
            {
                if (keylen == 8)
                {
                    keystream = salsa20Expand8(key, n);
                }
                if (keylen == 16)
                {
                    keystream = salsa20Expand16(key, n);
                }
                else if (keylen == 32)
                {
                    keystream = salsa20Expand32(key, n);
                }
            }
            encrypted[i] ^= keystream[(int) Long.remainderUnsigned(streamIndex + i, 64)];
        }
        return encrypted;
    }

    public static int bitsToBytes(int bits)
    {
        return (bits + 7) / 8;
    }
    public static int[] hexStringToIntArray(String hexString)
    {
        int[] intArray = new int[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2)
        {
            String byteString = hexString.substring(i, i + 2);
            intArray[i / 2] = Integer.parseInt(byteString, 16);
        }
        return intArray;
    }
    public static String intArrayToHexString(int[] intArray)
    {
        StringBuilder hexString = new StringBuilder();
        for (int value : intArray)
        {
            hexString.append(String.format("%02x", value)); 
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        int keyLength = Integer.parseInt(args[0]);
        int keylen = bitsToBytes(keyLength);
        String keyString = args[1];
        int[] key = hexStringToIntArray(keyString);
        String nonceString = args[2];
        int[] nonce = hexStringToIntArray(nonceString);
        String textString = args[3];
        //System.out.println("Plain text: "+args[3]);
        int[] text = hexStringToIntArray(textString);
        int[] result = salsa20Encryption(keylen, key, nonce, text);
        String ciphertext = intArrayToHexString(result);
        System.out.println("Ciphertext: " + ciphertext);
        int[] decryptedMessage = salsa20Encryption(keylen, key, nonce, result);
        String output = intArrayToHexString(decryptedMessage);
        //System.out.println("Decrypted Message: " + output);
    }
}
