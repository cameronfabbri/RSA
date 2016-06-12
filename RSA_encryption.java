import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

public class RSA_encryption {
	public static void main(String[] args)
	{
		if (args.length != 4)
		{
			System.out.println("Usage: java RSA_encryption [plaintext.txt] [ciphertext.cipher] [decrypted.txt] [bit-length]");
		}
		else
		{
			String plaintext, ciphertext, decrypted;
			int bit_length;
			plaintext   = args[0];
			ciphertext  = args[1];
			decrypted   = args[2];
			bit_length  = Integer.parseInt(args[3]);
			BigInteger p, q, n, e;
			p = get_p(bit_length);
			q = get_q(bit_length);
			n = p.multiply(q);
			e = get_e(n, p, q);
			encrypt(plaintext, ciphertext, decrypted, n, e);
			decrypt(n, e, decrypted, ciphertext);
			System.out.println("Using n value: " + n);
		}		
	}
	
	public static BigInteger get_e(BigInteger n, BigInteger p, BigInteger q)
	{
		BigInteger one, e, left, right, phi;
		one = new BigInteger("1");
		e = new BigInteger("3");
		left = p.subtract(one);
		right = p.subtract(one);
		phi = left.multiply(right);
		while (!e.gcd(phi).equals(one))
		{
			System.out.println("gcd(" + e + "," + phi + " = " + e.gcd(phi));
			e = e.nextProbablePrime();
		}
		System.out.println("Using e value: " + e);
		return e;
	}

	public static BigInteger get_p(int bit_length)
	{
	    SecureRandom rnd = new SecureRandom();
		BigInteger p = new BigInteger(bit_length, 90, rnd);
		return p;
	}
	
	public static BigInteger get_q(int bit_length)
	{
	    SecureRandom rnd = new SecureRandom();
		BigInteger q = new BigInteger(bit_length, 90, rnd);
		return q;
	}
	
	public static void encrypt(String plaintext, String ciphertext, String decrypted, BigInteger n, BigInteger e)
	{
		try {
			FileInputStream fileInput = new FileInputStream(plaintext);
			int r;
			while ((r = fileInput.read()) != -1)
			{
				char c = (char)r;
				int decVal = (int)c;
				String j = String.valueOf(decVal);
				BigInteger E = new BigInteger(j);
				BigInteger c_ = E.modPow(e, n);
				try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ciphertext, true)))) {
				    out.println(c_);
				}catch (IOException o) {
				}				
			}
		} catch (Exception o) {
			// TODO Auto-generated catch block
			o.printStackTrace();
		}
	}
	
	public static void decrypt(BigInteger n, BigInteger e, String decrypted, String ciphertext)
	{
		HashMap<String, BigInteger> encrypted_ascii = new HashMap<String, BigInteger>();
		HashMap<Integer, BigInteger> encrypted_file  = new HashMap<Integer, BigInteger>();
				
		for (int i = 0; i < 128; i++)
		{
			String j = String.valueOf(i);
			BigInteger E = new BigInteger(j);
			BigInteger c_ = E.modPow(e, n);
			encrypted_ascii.put(Character.toString((char)i), c_);
		}
		
        BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(ciphertext));
	        String line = "";
	        Integer i = 1;
	        while ((line = in.readLine()) != null) {
	        	i++;
	            String parts[] = line.split("\n");
	            BigInteger t = new BigInteger(parts[0]);
	            encrypted_file.put(i, t);
	        }
	        in.close();
		} catch (Exception p) {
			// TODO Auto-generated catch block
			p.printStackTrace();
		}
		
		for (Integer name: encrypted_file.keySet())
		{
			String key = name.toString();
			String value = encrypted_file.get(name).toString();
			
			for (String name_ : encrypted_ascii.keySet())
			{
				String key_ = name_.toString();
				String value_ = encrypted_ascii.get(name_).toString();

				if (value_.equals(value))
				{
					try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(decrypted, true)))) {
					    out.print(key_);
					}catch (IOException z) {
					}				
				}
			}
			
		}
	}
	
}
