package org.clientapp.clientappbackend.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/** Class to read RSA keys from a JKS. */
public class JksReader {

  private JksReader() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Reads the RSA public key from a JKS.
   *
   * @param jksFilePath file path to the JKS
   * @param alias the alias
   * @param keystorePassword password of the keystore
   * @return an RSAPublicKey
   * @throws Exception in case of failure
   */
  public static RSAPublicKey readRsaPublicKeyFromJks(
      String jksFilePath, String alias, String keystorePassword)
      throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
    KeyStore keyStore;
    try (FileInputStream jksInputStream = new FileInputStream(jksFilePath)) {
      keyStore = KeyStore.getInstance("JKS");
      keyStore.load(jksInputStream, keystorePassword.toCharArray());
    }

    Certificate certificate = keyStore.getCertificate(alias);
    if (certificate == null) {
      throw new IllegalArgumentException("No certificate found for alias: " + alias);
    }

    return (RSAPublicKey) certificate.getPublicKey();
  }

  /**
   * Reads the RSA private key from a JKS.
   *
   * @param jksFilePath file path to the JKS
   * @param alias the alias
   * @param keystorePassword password of the keystore
   * @return an RSAPrivateKey
   * @throws Exception in case of failure
   */
  public static RSAPrivateKey readRsaPrivateKeyFromJks(
      String jksFilePath, String alias, String keystorePassword)
      throws IOException,
          KeyStoreException,
          CertificateException,
          NoSuchAlgorithmException,
          UnrecoverableEntryException {
    KeyStore keyStore;
    try (FileInputStream jksInputStream = new FileInputStream(jksFilePath)) {
      keyStore = KeyStore.getInstance("JKS");
      keyStore.load(jksInputStream, keystorePassword.toCharArray());
    }

    KeyStore.PrivateKeyEntry privateKeyEntry =
        (KeyStore.PrivateKeyEntry)
            keyStore.getEntry(
                alias, new KeyStore.PasswordProtection(keystorePassword.toCharArray()));

    if (privateKeyEntry == null) {
      throw new IllegalArgumentException("No private key found for alias: " + alias);
    }

    PrivateKey privateKey = privateKeyEntry.getPrivateKey();
    if (privateKey instanceof RSAPrivateKey key) {
      return key;
    } else {
      throw new IllegalArgumentException("Private key is not an RSA key.");
    }
  }
}
