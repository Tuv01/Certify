package com.tuv01.certification

/**
 * Code used for Authenticated Encryption with GCM
 * AES 256 GCM (Galois/Counter Mode)
 *
 */
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Be sure to use a SecureRandom!
 */
val secureRandom = SecureRandom()

/**
 * Generates a key with [sizeInBits] bits.
 */
fun generateKey(sizeInBits: Int): ByteArray {
    val result = ByteArray(sizeInBits / 8)
    secureRandom.nextBytes(result)
    return result
}


/**
 * Generates a nonce for GCM. The nonce is always 96 bit long.
 */
fun generateNonce(): ByteArray {
    val result = ByteArray(96 / 8)
    secureRandom.nextBytes(result)
    return result
}


class Cyphertext(val cyphertext: ByteArray, val iv: ByteArray)

/**
 * Encrypts the given [plaintext] with the given [key] under AES GCM.
 *
 * This method generates a random nonce.
 *
 * @return Ciphertext and nonce
 */
fun encryptGcm(plaintext: ByteArray, key: ByteArray): Cyphertext {

    // Get cipher instance
    val cipher = Cipher.getInstance("AES/GCM/NoPadding")

    // Create SecretKeySpec
    val keySpec = SecretKeySpec(key, "AES")

    val nonce = generateNonce()

    // Create GCMParameterSpec
    val gcmSpec = GCMParameterSpec(128, nonce) // 128 bit authentication tag

    // Initialize Cipher for ENCRYPT_MODE
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec)

    // Perform Encryption
    val cyphertext = cipher.doFinal(plaintext)

    return Cyphertext(cyphertext, nonce)
}


/**
 * Decrypts the given [ciphertext] using the given [key] under AES GCM.
 *
 * @return Plaintext
 */
fun decryptGcm(cyphertext: Cyphertext, key: ByteArray): ByteArray {

    // Get Cipher Instance
    val cipher = Cipher.getInstance("AES/GCM/NoPadding")

    // Create SecretKeySpec
    val keySpec = SecretKeySpec(key, "AES")

    // Create GCMParameterSpec
    val gcmSpec = GCMParameterSpec(128, cyphertext.iv) // 128 bit authentication tag

    // Initialize Cipher for DECRYPT_MODE
    cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec)

    // Perform Decryption
    val plaintext = cipher.doFinal(cyphertext.cyphertext)

    return plaintext
}