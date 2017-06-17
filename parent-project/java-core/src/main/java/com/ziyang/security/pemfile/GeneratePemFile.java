package com.ziyang.security.pemfile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratePemFile {
	private static final Logger logger = LoggerFactory.getLogger(GeneratePemFile.class);

	public static final int KEY_SIZE = 1024;

	public static void main(String[] args)
			throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());
		logger.info("BouncyCastle provider added.");

		KeyPair keyPair = generateRSAKeyPair();
		RSAPrivateKey priv = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey pub = (RSAPublicKey) keyPair.getPublic();

		writePemFile(priv, "RSA PRIVATE KEY", "KeyPair/id_rsa.pem");
		writePemFile(pub, "RSA PUBLIC KEY", "KeyPair/id_rsa.pub.pem");

	}

	private static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
		generator.initialize(KEY_SIZE);

		KeyPair keyPair = generator.generateKeyPair();
		logger.info("RSA key pair generated.");
		return keyPair;
	}

	private static void writePemFile(Key key, String description, String filename)
			throws FileNotFoundException, IOException {
		PemFile pemFile = new PemFile(key, description);
		pemFile.write(filename);

		logger.info(String.format("%s successfully writen in file %s.", description, filename));
	}

	public static class PemFile {

		private PemObject pemObject;

		public PemFile(Key key, String description) {
			this.pemObject = new PemObject(description, key.getEncoded());
		}

		public void write(String filename) throws FileNotFoundException, IOException {
			PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(filename)));
			try {
				pemWriter.writeObject(this.pemObject);
			} finally {
				pemWriter.close();
			}
		}

	}
}
