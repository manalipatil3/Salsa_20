### Manali Patil
### B-Number :- B01035189
### Programming Language : Java

### Run the code using Make.sh

Command :- ./make.sh $arg0 $args1 $arg2 $arg3 $arg4 

for instance:-+
mpatil3@remote02:~/CS$ ./make.sh 64 "9add4d0ca0098aaa" "3769208a28190ec0" "54686973697361706c61696e74657874"

### Note :-
if get below error for make.sh file while running :-

mpatil3@remote02:~/CS/bkp/proj1_mpatil3/proj1_mpatil3$ ./make.sh
bash: ./make.sh: Permission denied

Run below command

command :-chmod +x make.sh

for example:-
mpatil3@remote02:~/CS/bkp/proj1_mpatil3/proj1_mpatil3$ chmod +x make.sh
mpatil3@remote02:~/CS/bkp/proj1_mpatil3/proj1_mpatil3$ ./make.sh 64 "9add4d0ca0098aaa" "3769208a28190ec0" "54686973697361706c61696e74657874"

### Run withous using Make.sh

Compile command :-javac Salsa20.java 
Run command :- java Salsa20 64 "9add4d0ca0098aaa" "3769208a28190ec0" "54686973697361706c61696e74657874"

for instance:-

mpatil3@remote02:~/CS$ javac Salsa20.java 
mpatil3@remote02:~/CS$ java Salsa20 64 "9add4d0ca0098aaa" "3769208a28190ec0" "54686973697361706c61696e74657874"


### Status :- Complete

### Description :-

Constants and Methods:

MOD: A constant holding the value 4294967295L (2^32 - 1).
rotateLeft(int value, int shift): Performs a left rotation on a 32-bit integer.
quarterRound(int a0, int a1, int a2, int a3): Performs a quarter round operation.
rowround(int[] inputArray): Performs a row round operation.
columnround(int[] inputArray): Performs a column round operation.
doubleround(int[] a): Performs a double round operation.
littleendian(int a0, int a1, int a2, int a3): Converts four bytes to a little-endian integer.
invertLittleendian(int a): Converts a little-endian integer to four bytes.
salsa20Hash(int[] seq): Generates a hash using the Salsa20 hash function.
salsa20Expand8(int[] k, int[] n), salsa20Expand16(int[] k, int[] n), salsa20Expand32(int[] k, int[] n): Expand the key and nonce to produce keystream blocks of various lengths.
salsa20Encryption(int keylen, int[] key, int[] nonce, int[] message): Encrypts the message using Salsa20 encryption.
bitsToBytes(int bits): Converts bits to bytes.
hexStringToIntArray(String hexString): Converts a hexadecimal string to an integer array.
intArrayToHexString(int[] intArray): Converts an integer array to a hexadecimal string.
Main Method:

The main method takes four arguments from the command line: keyLength, keyString, nonceString, and textString.
It converts the keyString, nonceString, and textString into integer arrays.
It encrypts the text using Salsa20 encryption with the provided key and nonce.
It prints the plaintext, ciphertext, and decrypted message.


### Test cases :-

log:-

mpatil3@remote02:~/CS$ ./make.sh 64 "9add4d0ca0098aaa" "3769208a28190ec0" "54686973697361706c61696e74657874"
Ciphertext: 4db0c1de8b570799b87c214d46ba5bce
mpatil3@remote02:~/CS$ ./make.sh 128 "014689370014c327d3fbca723b39ea9e" "d6f2cdeb82f905e2" "7465787420666f722031323862697420656e6372797470696f6e"
Ciphertext: 4fb0717e6fcbf05e16c8006240cdde1ccc33b9e24990e94675db
mpatil3@remote02:~/CS$ ./make.sh 256 "fb423b4a0be74f7d1e5091158b5b2a510d1e5161dc7ab8dfd495d19949adf3a3" "11d4d7e4e368c8e9" "54686973697364656372797074656474657874" 
Ciphertext: 91418d3f013015b6dab24e443db06166ea4188
mpatil3@remote02:~/CS$ ./make.sh 128 "deadbeefdeadbeefdeadbeefdeadbeef" "1234567890abcdef" "546869736973706c61696e74657874"
Ciphertext: a1c7720e1abadb96e5a2600d0ce028
 
