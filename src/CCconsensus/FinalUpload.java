package CCconsensus;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.BasicConfigurator;
import org.web3j.crypto.CipherException;
//import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.BatchResponse;
//import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;
//import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import CCconsensus.CrossConsensus;


@Path("/valid")
public class FinalUpload {
	public static String get_RelayID;
	public static String Ps;
	public static String Info,Info0;
	public static BigInteger get_Sai,get_Sbj,get_Hai,get_Hbj,get_CTaibj,get_Haibj,get_Xaibj,get_taibj;
	public static Element get_Rai,get_Rbj,get_PKai,get_PKbj,get_PKaibj,get_Taibj;
	private static final Charset UTF_8 = StandardCharsets.UTF_8;
	private static final String OUTPUT_FORMAT = "%-20s:%s";
	public static int pID;
	static String algorithm = "SHA-256";
	@GET
	public Response message(@QueryParam("id") int id) throws Exception {
		pID = id;
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            Class.forName("com.mysql.jdbc.Driver"); // 這個已經不能用了
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("找不到指定的類別");
        }
		//Web3j web3 = Web3j.build(new HttpService("http://140.118.9.225:23001"));
		//Web3j web31 = Web3j.build(new HttpService("http://140.118.9.226:23001"));
		//Web3j web32 = Web3j.build(new HttpService("http://140.118.9.227:23001"));
	    try {
            Connection conn = DriverManager
              					.getConnection(
              							"jdbc:mysql://127.0.0.1:3306/tran_db?user=root&password=demo2&useUnicode=true&characterEncoding=UTF-8"
         					   	);
            
            /*Statement stat = conn.createStatement();
          	int num = stat.executeUpdate("insert into MyGuests (firstname, lastname, email) values ('Chen', 'Tom', 'tom@xxx.com');");
            ResultSet resultSet = stat.executeQuery("Select * From MyGuests");
          */
            System.out.println(pID);
            String sql = "Select * From tran where id='"+pID+"'";
            PreparedStatement statement= conn.prepareStatement(sql);
          	System.out.println("Success!");
          	ResultSet resultSet = statement.executeQuery();
          	System.out.println("Select Success!");
          	
         	while (resultSet.next()) {
                String Info0 =  resultSet.getString("Info");
                String Taibj =  resultSet.getString("Taibj");
                

                System.out.println("================================");
                System.out.println(Info0);                
                System.out.println("================================");

            }
         	
    		if(resultSet !=null){
          		resultSet.close();
          	}
          	if(statement !=null){
          		statement.close();
          	}
          	if(conn !=null){
          		conn.close();
          	}
    		/*String NmodCTaibj = Nmod_CTaibj.toString();
    		String Taibj = T_aibj.toString();
    		String Rai = R_ai.toString();
    		String Rbj = R_bj.toString();
    		String PKai = PK_ai.toString();
    		String Hai = H_ai.toString();
    		String Sai = S_ai.toString();

    		Element PK_bj = getPKbj();
    		BigInteger H_bj = getHbj();
    		BigInteger S_bj = getSbj();
          	long timeC10 = StartCountTime();
    	    boolean validation3= Child_Chain_validation(P,getCTaibj(),getPKaibj(),getXaibj(),getRai()
    	    		,getRelayID(),getPKai(),getHai(),getRbj()
    	    		,getPKbj(),getHbj(),gettaibj(),getHaibj(),getTaibj());
    	    
    	    System.out.println("// 驗證結果3 // "+ validation3);
    	    StopCountTime("Child Chain Validation 所費時間 : ",timeC10);*/
          	
          	String walletPassword = "";
    		String walletDirectory = "/usr/local/tomcat/webapps/";
    		//String walletDirectory = "C:\\go_work\\Ethereum_1\\nodedata1\\keystore";
    		String walletName = "UTC--2022-06-08T07-09-14.151095565Z--5de4abbe713178e6c02ce484d4cff8d14549b7ca";
    		//String walletName = "UTC--2021-05-19T16-00-35.421648900Z--b473ac5dac7e41d941148df22434f8b6b90e9868";
    		Web3j web3 = Web3j.build(new HttpService("http://140.118.9.226:23001"));
    		
    		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
    		Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
    		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(0L), BigInteger.valueOf(3000000L));
    		
    		//Method contract = Method.deploy(web3, credentials, provider).send();
    		//String contractAddress = contract.getContractAddress();
    		TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 80, 2000);
    	    //1. 於鏈內部署合約表示提取部分私鑰完成。
    	    //2. 傳送(Sai,Rai)給中繼鏈
    		String contractAddress="";
    		 // Creates a new FileReader, given the name of the file to read from.
    		/*long time01 = StartCountTime();
    		FileReader fr = new FileReader("C:\\Users\\wealt\\eclipse-workspace\\CCconsensus\\contractaddr.txt");
            // Creates a buffering character-input stream that uses a default-sized input buffer.
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                contractAddress = br.readLine();
                System.out.println("Ready... read txt");
                System.out.println("-------------");
                System.out.println(contractAddress);
                System.out.println("-------------");
            }
            StopCountTime("read : ",time01);
*/
    		long time0 = StartCountTime();
    		//Method contract = Method.deploy(web3,transactionManager, provider).sendAsync().get();
    		long time01 = StartCountTime();
    		//FileReader fr = new FileReader("C:\\Users\\wealt\\eclipse-workspace\\CCconsensus\\contractaddr.txt");
    		FileReader fr = new FileReader("/usr/local/tomcat/webapps/contractaddr.txt");
            // Creates a buffering character-input stream that uses a default-sized input buffer.
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                contractAddress = br.readLine();
                System.out.println("Ready... read txt");
                System.out.println("-------------");
                System.out.println(contractAddress);
                System.out.println("-------------");
            }
            StopCountTime("read : ",time01);
    		 // Constructs a FileWriter object given a file name.
    		StopCountTime("read : ",time0);
    	  
    		System.out.println("Conrtract Address : " + contractAddress);
    		long time1 = StartCountTime();
    		Method contract_use = Method.load(contractAddress, web3, transactionManager, provider);
    		String Info = contract_use.getInfo().send().toString();
    		
    	    StopCountTime("load : ",time1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
	    if(Info==Info0) {
	    	return Response.status(200).entity("{\"Chain\":\"all\", \"message\":\"Valid Consensus Signature for Transaction!\"}").build();
	    }else {
		return Response.status(200).entity("{\"Chain\":\"all\", \"message\":\"Unvalid Consensus Signature for Transaction!\"}").build();}
	}
	public static void result() throws Exception {
	    
    	
    	Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8549"));
	    try {
            Connection conn = DriverManager
              					.getConnection(
	              					"jdbc:mysql://127.0.0.1:13317/demo2db", // DB 的位置
              						"root", // 使用者
          					    	"demo2" // 密碼
         					   	);
            
            /*Statement stat = conn.createStatement();
          	int num = stat.executeUpdate("insert into MyGuests (firstname, lastname, email) values ('Chen', 'Tom', 'tom@xxx.com');");
            ResultSet resultSet = stat.executeQuery("Select * From MyGuests");
          */
            
            Statement statement= conn.createStatement();
          	System.out.println("Success!");
          	ResultSet resultSet = statement.executeQuery("Select * From tran where id='"+pID+"'");
          	System.out.println("Select Success!");
          	
         	while (resultSet.next()) {
                int id =  resultSet.getInt("id");
                Ps =  resultSet.getString("Ps");
                String NmodCTaibj =  resultSet.getString("NmodCTaibj");
                String RelayID =  resultSet.getString("RelayID");
                String Taibj =  resultSet.getString("Taibj");
                String Rai =  resultSet.getString("Rai");
                String Rbj =  resultSet.getString("Rbj");
                String PKai =  resultSet.getString("PKai");
                String Hai =  resultSet.getString("Hai");
                String Sai =  resultSet.getString("Sai");
                String PKbj =  resultSet.getString("PKbj");
                String Hbj =  resultSet.getString("Hbj");
                String Sbj =  resultSet.getString("Sbj");
                

                System.out.println("================================");
                System.out.println(id);                
                System.out.println("================================");

            }
         	
    		if(resultSet !=null){
          		resultSet.close();
          	}
          	if(statement !=null){
          		statement.close();
          	}
          	if(conn !=null){
          		conn.close();
          	}
    		/*String NmodCTaibj = Nmod_CTaibj.toString();
    		String Taibj = T_aibj.toString();
    		String Rai = R_ai.toString();
    		String Rbj = R_bj.toString();
    		String PKai = PK_ai.toString();
    		String Hai = H_ai.toString();
    		String Sai = S_ai.toString();

    		Element PK_bj = getPKbj();
    		BigInteger H_bj = getHbj();
    		BigInteger S_bj = getSbj();
          	long timeC10 = StartCountTime();
    	    boolean validation3= Child_Chain_validation(P,getCTaibj(),getPKaibj(),getXaibj(),getRai()
    	    		,getRelayID(),getPKai(),getHai(),getRbj()
    	    		,getPKbj(),getHbj(),gettaibj(),getHaibj(),getTaibj());
    	    
    	    System.out.println("// 驗證結果3 // "+ validation3);
    	    StopCountTime("Child Chain Validation 所費時間 : ",timeC10);*/
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
	    
	    //Child Chain Validation
	    
	    
	    /*
	    //寫入二鏈
	    long timeC11 = StartCountTime();
	    ChildChainA_finish();
	    StopCountTime("跨鏈共識寫入鏈A所費時間 : ",timeC11);
	    long timeC12 = StartCountTime();
	    ChildChainB_finish();
	    StopCountTime("跨鏈共識寫入鏈B所費時間 : ",timeC12);*/
	    }
	public static void ChildChainA_finish() throws Exception {
		/*
		String walletPassword = "123456";
		String walletDirectory = "C:\\go_work\\Ethereum_1\\nodedata1\\keystore";
		String walletName = "UTC--2021-05-19T16-00-35.421648900Z--b473ac5dac7e41d941148df22434f8b6b90e9868";
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8549"));
		
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(30000000L), BigInteger.valueOf(6720000L));
		//Method contract = Method.deploy(web3, credentials, provider).send();
		//String contractAddress = contract.getContractAddress();
		TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 80, 2000);
	    //1. 於鏈內部署合約表示提取部分私鑰完成。
	    //2. 傳送(Sai,Rai)給中繼鏈
	    Method contract = Method.deploy(web3,transactionManager, provider).sendAsync().get();
		String contractAddress = contract.getContractAddress();
		System.out.println("Conrtract Address : " + contractAddress);

		Method contract_use = Method.load(contractAddress, web3, transactionManager, provider);
		//Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		*/
		BigInteger Nmod_CTaibj = getCTaibj(); 
		Element T_aibj = getTaibj();
		Element R_ai = getRai();
		Element R_bj = getRbj();
		Element PK_ai = getPKai();
		BigInteger H_ai = getHai();
		BigInteger S_ai = getSai();
		String RelayID = getRelayID();
		
		String NmodCTaibj = Nmod_CTaibj.toString();
		String Taibj = T_aibj.toString();
		String Rai = R_ai.toString();
		String Rbj = R_bj.toString();
		String PKai = PK_ai.toString();
		String Hai = H_ai.toString();
		String Sai = S_ai.toString();
		
		String Info = "Child Chain Accept Consensus from Destination! - RelayID: "+ RelayID +",PKai: "+PKai+",Hai: "+Hai+",Sai: "+Sai+",Nmod_CTaibj: "+NmodCTaibj +", Taibj: "+ T_aibj +", Rai: "+ Rai +",Rbj: "+ Rbj;
		//System.out.println("// Finish : //" + contract_use.setInfo(Info).sendAsync().get());
		
		
	}
	public static void ChildChainB_finish() throws Exception{
		/*String walletPassword = "123456";
		String walletDirectory = "C:\\go_work\\Ethereum_PoA\\node\\keystore";
		String walletName = "UTC--2021-05-20T06-04-00.812610300Z--15be92c798cf6dbac416b4b707afd2a7e9180022";
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8545"));
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		System.out.println(" credentials  " + credentials);
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(8000000L), BigInteger.valueOf(1800000L));
		//Method contract = Method.deploy(web3, credentials, provider).send();
		//String contractAddress = contract.getContractAddress();
		
		TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 80, 2000);
	    MethodX contract = MethodX.deploy(web3,transactionManager, provider).sendAsync().get();
		String contractAddress = contract.getContractAddress();
		System.out.println("Conrtract Address : " + contractAddress);
		
		System.out.println("Conrtract Address : " + contractAddress);
		MethodX contract_use = MethodX.load(contractAddress, web3, transactionManager, provider);
		//Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		 */
		BigInteger Nmod_CTaibj = getCTaibj(); 
		Element T_aibj = getTaibj();
		Element R_ai = getRai();
		Element R_bj = getRbj();
		Element PK_bj = getPKbj();
		BigInteger H_bj = getHbj();
		BigInteger S_bj = getSbj();
		String RelayID = getRelayID();
		
		String NmodCTaibj = Nmod_CTaibj.toString();
		String Taibj = T_aibj.toString();
		String Rai = R_ai.toString();
		String Rbj = R_bj.toString();
		String PKbj = PK_bj.toString();
		String Hbj = H_bj.toString();
		String Sbj = S_bj.toString();
		
		String Info = "Child Chain Accept Consensus from Destination! - RelayID: "+ RelayID +",PKbj: "+PKbj+",Hbj: "+Hbj+",Sbj: "+Sbj+",Nmod_CTaibj: "+NmodCTaibj +", Taibj: "+ T_aibj +", Rai: "+ Rai +",Rbj: "+ Rbj;
		//System.out.println("// Finish : //" + contract_use.setInfo(Info).sendAsync().get());
	}

	/*
	public static String EnviromentSetUP_And_Deploy(Field G_1,Element P,String H) throws Exception {
		//Call Contract Deploy 
		//1. Generator P 
		//2. Hash function H
		String Generator_P = ElementToString(P);
        String _Hash = H;
        
        long timeD0 =StartCountTime();
        String walletPassword = "123456";
		String walletDirectory = "C:\\go_work\\Ethereum_1\\nodedata1\\keystore";
		String walletName = "UTC--2021-05-19T16-00-35.421648900Z--b473ac5dac7e41d941148df22434f8b6b90e9868";
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8549"));
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(30000000L), BigInteger.valueOf(6720000L));
		StopCountTime("Web3連結至鏈A: ",timeD0);
		
		long timeD1 =StartCountTime();
		Method contract = Method.deploy(web3, credentials, provider).send();
		StopCountTime("鏈A將計算完的SetUp資料部屬至鏈中所花時間: ",timeD1);
		
		String contractAddress = contract.getContractAddress();
		System.out.println("Conrtract Address : " + contractAddress);
		
		//use function
		long timeD2 =StartCountTime();
		Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		System.out.println("Generator_P" + Generator_P);
		class RunnableA implements Runnable {

			  @Override
			  public void run() {
				  try {
					System.out.println(contract_use.setP(Generator_P).send());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  try {
					System.out.println("//Get Generator : //" + contract_use.getP().send());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }

			}
		class RunnableB implements Runnable {

			  @Override
			  public void run() {
				  try {
					System.out.println(contract_use.setH(_Hash).send());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  try {
					System.out.println("//Get Hash function // : " + contract_use.getH().send());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }

			}
		Thread a = new Thread(new RunnableA());
		a.start();
		StopCountTime("鏈A使用合約Set Generator P 所費時間: ",timeD2);
		long timeD3 =StartCountTime();
		Thread b = new Thread(new RunnableB());
		b.start();
		try {
			a.join();
			b.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StopCountTime("鏈A使用合約Set Hash Algorithm 所費時間: ",timeD3);
		
		
		return contractAddress;
	}
	*/
	
/*	
	public static String EnviromentSetUP_And_Deploy(Field G_1,Element P,String H) throws Exception {
		//Call Contract Deploy 
		//1. Generator P 
		//2. Hash function H
		String Generator_P = ElementToString(P);
        String _Hash = H;
        
        long timeD0 =StartCountTime();
        String walletPassword = "123456";
		String walletDirectory = "C:\\go_work\\Ethereum_1\\nodedata1\\keystore";
		String walletName = "UTC--2021-05-19T16-00-35.421648900Z--b473ac5dac7e41d941148df22434f8b6b90e9868";
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8549"));
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(30000000L), BigInteger.valueOf(6720000L));
		StopCountTime("Web3連結至鏈A: ",timeD0);
		TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 100, 2000);
		long timeD1 =StartCountTime();
		Method contract = Method.deploy(web3, transactionManager, provider).send();
		//Method contract = Method.deploy(web3, credentials, provider).send();
		StopCountTime("鏈A將計算完的SetUp資料部屬至鏈中所花時間: ",timeD1);
		
		String contractAddress = contract.getContractAddress();
		System.out.println("Conrtract Address : " + contractAddress);
		
		//use function
		long timeD2 =StartCountTime();
		//Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		Method contract_use = Method.load(contractAddress, web3, transactionManager, provider);
		System.out.println("Generator_P" + Generator_P);
		
		//System.out.println(contract_use.setP(Generator_P).send());
		StopCountTime("鏈A使用合約Set Generator P 所費時間: ",timeD2);
		//System.out.println("//Get Generator : //" + contract_use.getP().send());
		long timeD3 =StartCountTime();
		System.out.println(contract_use.setH(_Hash).send());
		StopCountTime("鏈A使用合約Set Hash Algorithm 所費時間: ",timeD3);
		//System.out.println("//Get Hash function // : " + contract_use.getH().send());
		
		return contractAddress;
	}
*/	
	
	public static void Relay_SetUP(Element P) {
	    BigInteger hai = getHai();
	    BigInteger hbj = getHbj();
	    BigInteger NMod_Sai = getSai();
	    BigInteger NMod_Sbj = getSbj();
	    
		//Relay node Set Secret Value
	    long timeD25 = StartCountTime();
	    Random random = new Random();
	    BigInteger Xaibj = new BigInteger(160, 160,random);
	    StopCountTime("Relay 設置 Secret Value Xaibj 所費時間 : ",timeD25);
	    
	    //Relay node setPublicKey
	    long timeD26 = StartCountTime();
	    Element PKaibj = P.duplicate().mul(Xaibj);
	    StopCountTime("Relay 計算公鑰 PKaibj 所費時間 : ",timeD26);
	    
	    //Transaction Hash Tran_aibj    
	    String Tran_aibj ="0xb5c8bd9430b6cc87a0e2fe110ece6bf527fa4f170a4bc8cd032f768fc5219838";
	    String Sign_last = "0000007b5dd0cd9fb87cf16213c8ffa4e8b903377b4d833a50a6236d";
	    
	    //Set Random number
	    long timeD27 = StartCountTime();
	    Random Random = new Random();
	    BigInteger t_aibj = new BigInteger(160,160,Random);
	    //Compute T_aibj
	    Element T_aibj = P.duplicate().mul(t_aibj);
	    StopCountTime("Relay 計算Taibj 所費時間 : ",timeD27);
	    
	    //Compute Hash h_aibj
	    long timeD28 = StartCountTime();
	    String h_aibj_pre = (Sign_last.toString() +Tran_aibj.toString()+T_aibj.toString()+PKaibj.toString()
	    			+hai.toString()+hbj.toString());
	    System.out.println(String.format(OUTPUT_FORMAT, "Input (string)", h_aibj_pre));
	    StopCountTime("Relay 計算Hash Haibj 所費時間 : ",timeD28);
	
	    long timeD29 = StartCountTime();
	    byte[] h_aibj = digest(h_aibj_pre.getBytes(UTF_8), algorithm);
	    System.out.println(String.format(OUTPUT_FORMAT, algorithm + " (hex) ", bytesToHex(h_aibj)));
	    int[] int_h_aibj = bytearray2intarray(h_aibj);
	    BigInteger BI_h_aibj = new BigInteger(intArrayToArrayString(int_h_aibj));
	    StopCountTime("Relay 將Haibj轉成BigInteger 所費時間 : ",timeD29);
	    
	    //Compute τAibj
	    long timeD30 = StartCountTime();
	    BigInteger CT_aibjX = t_aibj;//.add(BI_h_aibj);	
	    BigInteger CT_aibjY = BI_h_aibj.multiply((Xaibj.add(NMod_Sai)).add(NMod_Sbj));
	    BigInteger CT_aibjZ  = CT_aibjX.add(CT_aibjY);
	    //BigInteger CT_aibj = CT_aibjZ.mod(BigInteger.valueOf(Prime));
	    BigInteger NMod_CT_aibj = CT_aibjZ;
	    StopCountTime("Relay 提取部分私鑰 CTaibj 所費時間 : ",timeD30);
	    //setSbj(NMod_Sbj); setRbj(Rbj);setHbj(BI_hbj);setPKbj(PKbj);
	    setCTaibj(NMod_CT_aibj); settaibj(t_aibj); setHaibj(BI_h_aibj);setXaibj(Xaibj);setPKaibj(PKaibj); setTaibj(T_aibj);
	}
	public static boolean Relay_validation(BigInteger PPkey,Element P,Element BigR,
			String RelayNodeID,BigInteger hash,Element PubK,String Info) {
		long timeD16 = StartCountTime();
		Element Check_Left = P.duplicate().mul(PPkey);
		BigInteger big = BigInteger.valueOf(Integer.valueOf(RelayNodeID));
		Element Check_Right1 = BigR.duplicate().mul(big); 
		Element Check_Right2 = PubK.duplicate().mul(hash);
		
		Element Check_Right = Check_Right1.add(Check_Right2);
		
		System.out.println(Info + Check_Left.equals(Check_Right));
		StopCountTime(Info+"中繼鏈驗證所費時間 : " ,timeD16);
		
		if(Check_Left.equals(Check_Right)) {
			return true;
		}else {return false;}
		
	}
	public static boolean Child_Chain_validation(Element P,BigInteger NMod_CT_aibj,Element PKaibj,BigInteger Xaibj
				,Element Rai,String RelayID,Element PKai,BigInteger BI_hai,Element Rbj,Element PKbj,BigInteger BI_hbj,
				BigInteger t_aibj,BigInteger BI_h_aibj,Element T_aibj) {
		System.out.println(" P " + P);
		System.out.println(" NMod_CT_aibj " + NMod_CT_aibj);
		System.out.println(" PKaibj " + PKaibj);
		System.out.println(" Xaibj " + Xaibj);
		System.out.println(" Rai " + Rai);
		System.out.println(" RelayID " + RelayID);
		System.out.println(" PKai " + PKai);
		System.out.println(" BI_hai " + BI_hai);
		System.out.println(" Rbj " + Rbj);
		System.out.println(" PKbj " + PKbj);
		System.out.println(" BI_hbj " + BI_hbj);
		System.out.println(" t_aibj " + t_aibj);
		System.out.println(" BI_h_aibj " + BI_h_aibj);
		System.out.println(" T_aibj " + T_aibj);
		
		
	    Element Sign_verfication_Left = P.duplicate().mul(NMod_CT_aibj);
	    BigInteger Identity_Relay_node_hash = BigInteger.valueOf(Integer.valueOf(RelayID));
	    
	    
	    
	    Element SV_R0 = PKaibj;
	    Element Q = P.duplicate().mul(Xaibj);
	    System.out.println("Check : Q : " +Q.equals(SV_R0));
	    //Rai⋅IDaibj
	    Element SV_R1 = Rai.duplicate().mul(Identity_Relay_node_hash);
	    //hai ⋅ PKai
	    Element SV_R2 = PKai.duplicate().mul(BI_hai);
	    
	    //Rbj ⋅ IDaibj
	    Element SV_R3 = Rbj.duplicate().mul(Identity_Relay_node_hash);
	    //Element SV_R3 = SV_R2;
	    System.out.println("Check RBJ in Line 369 : " +Rbj);
	    
	    //hbj ⋅ PKbj
	    Element SV_R4 = PKbj.duplicate().mul(BI_hbj);

	    
	    Element SV_RG = (((SV_R0.add(SV_R1)).add(SV_R2)).add(SV_R3)).add(SV_R4);
	    Element SV_R = T_aibj.duplicate().add(SV_RG.duplicate().mul(BI_h_aibj));
	    System.out.println("T_aibj Check " + T_aibj.equals(P.duplicate().mul(t_aibj)));
	    System.out.println("//  Final Check in Child Chain : //" + Sign_verfication_Left.equals(SV_R));
	    
		return T_aibj.equals(P.duplicate().mul(t_aibj));
	}

	public static String ElementToString(Element e1) {
		//Element Encode成byte 
		byte[] s = Base64.getEncoder().encode(e1.toBytes());
	    //byte 轉成 String
        String Str_P = new String(s);
		
        return Str_P;		
	}
	
	public static Element StringToElement(String e2,Field G1) {
		//下方兩行為子節點拿到時Decode;
        Element P2 = G1.newElementFromBytes(Base64.getDecoder().decode(e2));
		return P2;
	}
	public static int generatePrime(int Start , int end) {
		int prime_n =0;
		while(true){
		int num_p = (int) (Math.random()*end)+Start; //產生 Start 到End 範圍的隨機數字
			if(isPrime(num_p)) {
				System.out.println("質數值 N : " +num_p);
				prime_n = num_p;
				break;
			}
		
		}
		return prime_n;
	}
	public static boolean isPrime(int num) {
		for (int i = 2; i<=num /2 ;i++) {
			if (num % i  ==0) {
				System.out.println(num + "此數被" + i + "整除");
				return false;
			}
		}
		return true;
	}
	
	 public static String bytesToHex(byte[] bytes) {
	        StringBuilder sb = new StringBuilder();
	        for (byte b : bytes) {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
	    }
	 public static int[]  bytearray2intarray(byte[] barray)
	 {
	   int[] iarray = new int[barray.length];
	   int i = 0;
	   for (byte b : barray)
	       iarray[i++] = b & 0xff;
	   return iarray;
	 }
	public static String intArrayToArrayString(int[] array) {
	  String output="";	 
      // 利用 Arrays.toString 可以超簡單輸出 array
      // 輸出結果：[4, 2, 5, 1, 5, 2, 4, 3]
      String arrayString = Arrays.toString(array);
      for(int i =0;i<array.length;i++) {
    	  output=output+array[i];
      }
      System.out.println(output);
      return output;
	 }
	public static byte[] digest(byte[] input, String algorithm) {
     MessageDigest md;
     try {
         md = MessageDigest.getInstance(algorithm);
     } catch (NoSuchAlgorithmException e) {
         throw new IllegalArgumentException(e);
     }
     byte[] result = md.digest(input);
     return result;
	}
	public static Pairing initPairing(String parameter) {
	    System.out.println("系統正在導入橢圓曲線的相關參數……");
	    //讀參數
	    Pairing pairing = PairingFactory.getPairing(parameter);
	    System.out.println("系統已經導入完畢");
	    return pairing;
	}
	
	public static Field initG_1(Pairing pairing) {
	    System.out.println("系統正在產生橢圓曲線……");
	    //讀參數
	    Field G1 = pairing.getG1();
	    
	    System.out.println("系統已經產生橢圓曲線");
	    
	    return G1;
	}
	public static Element initG(Field G1) {
	    System.out.println("系統正在挑選生成點G……");
	    //挑選生成點G
	    Element G = G1.newRandomElement().getImmutable();
	    System.out.println("系統挑選生成點G完畢" + G.toString());
	    return G;
	}

	public static Element initP_t(Field G1) {
	    System.out.println("系統正在挑選隨機點P_t……");
	    //挑選隨機點P_t
	    Element P_t = G1.newRandomElement().getImmutable();
	    System.out.println("系統挑選隨機點P_t完畢" + P_t.toString());
	    return P_t;
	}

    public static void setPKai(Element PKai) {
		get_PKai = PKai;
	}
	public static Element getPKai() {
		return get_PKai;
	}
	public static void setPKbj(Element PKbj) {
		get_PKbj = PKbj;
	}
	public static Element getPKbj() {
		return get_PKbj;
	}
	public static void setHai(BigInteger hai) {
		get_Hai = hai;
	}
	public static BigInteger getHai() {
		return get_Hai;
	}
	public static void setHbj(BigInteger hbj) {
		get_Hbj = hbj;
	}
	public static BigInteger getHbj() {
		return get_Hbj;
	}
	public static void setRelayID(String _ID) {
		get_RelayID=_ID;
	}
	public static String getRelayID() {
		return get_RelayID;
	}
	public static void setSai(BigInteger _sai) {
		get_Sai = _sai;
	}
	public static BigInteger getSai() {
		return get_Sai;
	}
	public static void setSbj(BigInteger _sbj) {
		get_Sbj = _sbj;
	}
	public static BigInteger getSbj() {
		return get_Sbj;
	}
	public static void setRai(Element _Rai) {
		get_Rai = _Rai;
		
	}
	public static Element getRai() {
		return get_Rai;
	}
	public static void setRbj(Element _Rbj) {
		get_Rbj = _Rbj;
	}
	public static Element getRbj() {
		return get_Rbj;
	}
	public static void setCTaibj(BigInteger _CTaibj) {
		get_CTaibj = _CTaibj;
	}
	public static BigInteger getCTaibj() {
		return get_CTaibj;
	}
	public static void settaibj(BigInteger _taibj) {
		get_taibj = _taibj;
	}
	public static BigInteger gettaibj() {
		return get_taibj;
	}
	public static void setHaibj(BigInteger _Haibj) {
		get_Haibj = _Haibj;
	}
	public static BigInteger getHaibj() {
		return get_Haibj;
	}
	public static void setXaibj(BigInteger _Xaibj) {
		get_Xaibj = _Xaibj;
	}
	public static BigInteger getXaibj() {
		return get_Xaibj;
	}
	public static void setPKaibj(Element _PKaibj) {
		get_PKaibj = _PKaibj;
	}
	public static Element getPKaibj() {
		return get_PKaibj;
	}
	public static void setTaibj(Element _Taibj) {
		get_Taibj = _Taibj;
	}
	public static Element getTaibj() {
		return get_Taibj;
	}
	public static long StartCountTime() {
		  long time1;
	      time1 = System.currentTimeMillis();
	      // doSomething()
	      //time2 = System.currentTimeMillis();
	      // doAnotherthing()
	      //time3 = System.currentTimeMillis();
	      //System.out.println("doSomething()花了：" + (time2-time1)/1000 + "秒");
	      //System.out.println("doAnotherthing()花了：" + (time3-time2)/1000 + "秒");
	      
	      return time1;
	  }
	  public static void StopCountTime(String Info,long time1) {
		  long time2;
		  time2 = System.currentTimeMillis();
		  System.out.println(Info +" : "  + (time2-time1) + "豪秒");
	  }
}
