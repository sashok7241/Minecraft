package net.minecraft.src;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptManager
{
	public static final Charset charSet = Charset.forName("ISO_8859_1");
	
	private static byte[] cipherOperation(int par0, Key par1Key, byte[] par2ArrayOfByte)
	{
		try
		{
			return createTheCipherInstance(par0, par1Key.getAlgorithm(), par1Key).doFinal(par2ArrayOfByte);
		} catch(IllegalBlockSizeException var4)
		{
			var4.printStackTrace();
		} catch(BadPaddingException var5)
		{
			var5.printStackTrace();
		}
		System.err.println("Cipher data failed!");
		return null;
	}
	
	private static BufferedBlockCipher createBufferedBlockCipher(boolean par0, Key par1Key)
	{
		BufferedBlockCipher var2 = new BufferedBlockCipher(new CFBBlockCipher(new AESFastEngine(), 8));
		var2.init(par0, new ParametersWithIV(new KeyParameter(par1Key.getEncoded()), par1Key.getEncoded(), 0, 16));
		return var2;
	}
	
	public static KeyPair createNewKeyPair()
	{
		try
		{
			KeyPairGenerator var0 = KeyPairGenerator.getInstance("RSA");
			var0.initialize(1024);
			return var0.generateKeyPair();
		} catch(NoSuchAlgorithmException var1)
		{
			var1.printStackTrace();
			System.err.println("Key pair generation failed!");
			return null;
		}
	}
	
	public static SecretKey createNewSharedKey()
	{
		CipherKeyGenerator var0 = new CipherKeyGenerator();
		var0.init(new KeyGenerationParameters(new SecureRandom(), 128));
		return new SecretKeySpec(var0.generateKey(), "AES");
	}
	
	private static Cipher createTheCipherInstance(int par0, String par1Str, Key par2Key)
	{
		try
		{
			Cipher var3 = Cipher.getInstance(par1Str);
			var3.init(par0, par2Key);
			return var3;
		} catch(InvalidKeyException var4)
		{
			var4.printStackTrace();
		} catch(NoSuchAlgorithmException var5)
		{
			var5.printStackTrace();
		} catch(NoSuchPaddingException var6)
		{
			var6.printStackTrace();
		}
		System.err.println("Cipher creation failed!");
		return null;
	}
	
	public static PublicKey decodePublicKey(byte[] par0ArrayOfByte)
	{
		try
		{
			X509EncodedKeySpec var1 = new X509EncodedKeySpec(par0ArrayOfByte);
			KeyFactory var2 = KeyFactory.getInstance("RSA");
			return var2.generatePublic(var1);
		} catch(NoSuchAlgorithmException var3)
		{
			var3.printStackTrace();
		} catch(InvalidKeySpecException var4)
		{
			var4.printStackTrace();
		}
		System.err.println("Public key reconstitute failed!");
		return null;
	}
	
	public static byte[] decryptData(Key par0Key, byte[] par1ArrayOfByte)
	{
		return cipherOperation(2, par0Key, par1ArrayOfByte);
	}
	
	public static InputStream decryptInputStream(SecretKey par0SecretKey, InputStream par1InputStream)
	{
		return new CipherInputStream(par1InputStream, createBufferedBlockCipher(false, par0SecretKey));
	}
	
	public static SecretKey decryptSharedKey(PrivateKey par0PrivateKey, byte[] par1ArrayOfByte)
	{
		return new SecretKeySpec(decryptData(par0PrivateKey, par1ArrayOfByte), "AES");
	}
	
	private static byte[] digestOperation(String par0Str, byte[] ... par1ArrayOfByte)
	{
		try
		{
			MessageDigest var2 = MessageDigest.getInstance(par0Str);
			byte[][] var3 = par1ArrayOfByte;
			int var4 = par1ArrayOfByte.length;
			for(int var5 = 0; var5 < var4; ++var5)
			{
				byte[] var6 = var3[var5];
				var2.update(var6);
			}
			return var2.digest();
		} catch(NoSuchAlgorithmException var7)
		{
			var7.printStackTrace();
			return null;
		}
	}
	
	public static byte[] encryptData(Key par0Key, byte[] par1ArrayOfByte)
	{
		return cipherOperation(1, par0Key, par1ArrayOfByte);
	}
	
	public static OutputStream encryptOuputStream(SecretKey par0SecretKey, OutputStream par1OutputStream)
	{
		return new CipherOutputStream(par1OutputStream, createBufferedBlockCipher(true, par0SecretKey));
	}
	
	public static byte[] getServerIdHash(String par0Str, PublicKey par1PublicKey, SecretKey par2SecretKey)
	{
		try
		{
			return digestOperation("SHA-1", new byte[][] { par0Str.getBytes("ISO_8859_1"), par2SecretKey.getEncoded(), par1PublicKey.getEncoded() });
		} catch(UnsupportedEncodingException var4)
		{
			var4.printStackTrace();
			return null;
		}
	}
	
	static
	{
		Security.addProvider(new BouncyCastleProvider());
	}
}
