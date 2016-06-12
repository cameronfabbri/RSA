import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;

public class RSA 
{
	static BigInteger n = new BigInteger("15304174110796219203426653444898611855892818881773700999953889436033835715616247909750233666983701800380923395923905810026540007674819369429416887351120639552312157605687583898811033604591945463313043999190287578155127830141652808971558235061560850230134450875251844658970074150182360036956568421157157148628541272220287512770505406897982040506803257877633619218424153026114987102296732135355824252392350647043877421603323411006721349349245738345706103598770261775696104795695953052383947924756392586774885748880520652773054308026918929655364248757352980554181673899329768222755359425489756616256610287079325309870683");
	static BigInteger e = new BigInteger("58520943409509727079150021704076174918542613863859354123898291611136796367697310300095007738574909317206949195605641584692796636855387254429087630024943670132803421326017252916102685209896428447634060996459183572394562143761786492230556832965945852824569940041932280103526327714344618865452067616426218613851");
	
	public static void main(String[] args)
	{
		encrypt();
	}
	// C = m^e (mod n)
	
	public static void encrypt()
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
			in = new BufferedReader(new FileReader("libs/a2.cipher"));
	        String line = "";
	        Integer i = 1;
	        while ((line = in.readLine()) != null) {
	        	i++;
	            String parts[] = line.split("\n");
	            BigInteger t = new BigInteger(parts[0]);
	            encrypted_file.put(i, t);
	        }
	        in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("libs/myfile.txt", true)))) {
					    out.print(key_);
					}catch (IOException e) {
					}				
				}
			}
			
		}
	}
	
}