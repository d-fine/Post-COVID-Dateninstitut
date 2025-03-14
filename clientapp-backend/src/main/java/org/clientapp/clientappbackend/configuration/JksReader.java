package org.clientapp.clientappbackend.configuration;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class JksReader {

  public static RSAPublicKey readRSAPublicKeyFromJKS(String jksFilePath, String alias,
      String keystorePassword) throws Exception {
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

  public static RSAPrivateKey readRSAPrivateKeyFromJKS(String jksFilePath, String alias,
      String keystorePassword) throws Exception {
    FileInputStream jksInputStream = new FileInputStream(jksFilePath);
    KeyStore keyStore = KeyStore.getInstance("JKS");
    keyStore.load(jksInputStream, keystorePassword.toCharArray());

    KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
        new KeyStore.PasswordProtection(keystorePassword.toCharArray()));

    if (privateKeyEntry == null) {
      throw new IllegalArgumentException("No private key found for alias: " + alias);
    }

    PrivateKey privateKey = privateKeyEntry.getPrivateKey();
    if (privateKey instanceof RSAPrivateKey) {
      return (RSAPrivateKey) privateKey;
    } else {
      throw new IllegalArgumentException("Private key is not an RSA key.");
    }
  }
}
