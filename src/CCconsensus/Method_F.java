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
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import org.apache.log4j.BasicConfigurator;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.web3j.crypto.CipherException;
//import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.BatchResponse;
//import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;
//import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/bonjour")
public class Method_F {
	public static String get_RelayID;
	public static BigInteger get_Sai,get_Sbj,get_Hai,get_Hbj,get_CTaibj,get_Haibj,get_Xaibj,get_taibj;
	public static Element get_Rai,get_Rbj,get_PKai,get_PKbj,get_PKaibj,get_Taibj;
	private static final Charset UTF_8 = StandardCharsets.UTF_8;
	private static final String OUTPUT_FORMAT = "%-20s:%s";
	static String algorithm = "SHA3-256";
	
	public static String result() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		long timeCount = StartCountTime();
		
		long timeD0 =StartCountTime();
        		
		String walletPassword = "123456";
		String walletDirectory = "C:\\go_work\\Ethereum_1\\nodedata0\\keystore";
		//String walletName = "UTC--2022-01-09T08-03-18.210009200Z--ff958bb4163679a90783258278045d439b9cc8b1";
		String walletName = "UTC--2021-05-19T15-31-06.015465000Z--6c20864b3f187776776930bded2fe6d9cee30245";
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8549"));
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(30000000L), BigInteger.valueOf(6720000L));
		String walletPassword2 = "123456";
		String walletDirectory2 = "C:\\go_work\\Ethereum_PoA\\node\\keystore";
		String walletName2 = "UTC--2021-05-20T06-04-00.812610300Z--15be92c798cf6dbac416b4b707afd2a7e9180022";
		Web3j web32 = Web3j.build(new HttpService("http://127.0.0.1:8545"));
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		Credentials credentials2 = WalletUtils.loadCredentials(walletPassword2, walletDirectory2+"/" + walletName2);
		System.out.println(" credentials  " + credentials2);
		ContractGasProvider provider2 = new StaticGasProvider(BigInteger.valueOf(8000000L), BigInteger.valueOf(1800000L));
		StopCountTime("Web3????????????AB: ",timeD0);
		int n;
		n=1;
		//BasicConfigurator.configure();
		//ContractTest();
		/*SetUp Environment*/
		
		//??????????????????
		long timeC1 = StartCountTime();
		long timet0 =StartCountTime();
		Pairing[] pairing = new Pairing[n];
		Field[] G_1 = new Field[n];
		for(int i=0;i<=n-1;i++) {
			pairing[i] = initPairing("C:\\go_work\\a.properties");
			G_1[i] = initG_1(pairing[i]);
		}
	    StopCountTime("?????????????????????????????? : ",timeC1);
		//????????????
	    long timeC2 = StartCountTime();
	    int Prime = generatePrime(5,100);//????????????5~100?????????????????????
	    StopCountTime("???????????????????????? : ",timeC2);
		//?????? Generator P
	    long timeC3 = StartCountTime();
	    Element[] P = new Element[n];
	    for(int i=0;i<=n-1;i++) {
	    	P[i] = G_1[i].newRandomElement().getImmutable();
	    	System.out.println(P[i]);
	    }
	    StopCountTime("??????Generator P ???????????? : ",timeC3);
	    //??????Hash Function SHA3-256 
	    //String algorithm = "SHA3-256"

	    //ContractTest();

	    //SetUp_1-1
	    long timeC0 = StartCountTime();
	    String[] SetUp1_Contract = new String[n];
	    String[] PPKeyExtract_1 = new String[n];
	    boolean[] validation1 = new boolean[n];
	    String[] PPKeyExtract_2 = new String[n];
	    boolean[] validation2 = new boolean[n];
	    boolean[] validation3 = new boolean[n];
	    
	  //SetUp_1-1
	    long timeC4 = StartCountTime();
	    SetUp1_Contract[0] = EnviromentSetUP_And_Deploy(web3, credentials, provider, G_1[0],P[0],algorithm);
	    StopCountTime("SetUp_1-1???????????? : ",timeC4);
	    
	    //SetUp_1-2
	    long timeC5 = StartCountTime();
	    PPKeyExtract_1[0] = ChainA_SetUP_And_Deploy(web3, credentials, provider, SetUp1_Contract[0],G_1[0]);
	    StopCountTime("SetUp_1-2???????????? : ",timeC5);
	    
	    //Relay Validation
	    long timeC6 = StartCountTime();
	    validation1[0] = Relay_validation(getSai(),P[0],getRai(),getRelayID(),getHai(),getPKai(),
	    		"// Validate Partial Private Key Extract From Node Ai //");
	    System.out.println("// ????????????1 // " + validation1[0]);
	    StopCountTime("Relay Validation A???????????? : ",timeC6);
	    //SetUp_2-1
	    long timeC7 = StartCountTime();
	    PPKeyExtract_2[0] = ChainB_SetUP_And_Deploy(web32, credentials2, provider2, P[0],getPKai(),algorithm);
	    StopCountTime("SetUp_2-1???????????? : ",timeC7);
	    //Relay Validation
	    long timeC8 = StartCountTime();
	    validation2[0] = Relay_validation(getSbj(),P[0],getRbj(),getRelayID(),getHbj(),getPKbj()
	    		,"// Validate Partial Private Key Extract From Node bj //");
	    
	    System.out.println("// ????????????2 // " + validation2[0]);
	    StopCountTime("Relay Validation B???????????? : ",timeC8);
	    //Relay SetUp
	    long timeC9 = StartCountTime();
	    Relay_SetUP(P[0]);
	    StopCountTime("Relay SetUp ???????????? : ",timeC9);
	    
	    //Child Chain Validation
	    long timeC10 = StartCountTime();
	    validation3[0]= Child_Chain_validation(P[0],getCTaibj(),getPKaibj(),getXaibj(),getRai()
	    		,getRelayID(),getPKai(),getHai(),getRbj()
	    		,getPKbj(),getHbj(),gettaibj(),getHaibj(),getTaibj());
	    
	    System.out.println("// ????????????3 // "+ validation3[0]);
	    StopCountTime("Child Chain Validation ???????????? : ",timeC10);
	    ChildChainA_finish(web3,credentials,provider,SetUp1_Contract[0]);
	    ChildChainB_finish(web32,credentials2,provider2,PPKeyExtract_2[0]);
	    StopCountTime("First???????????? : ",timeC0);
	    
	    for(int i=1;i<=n-2;i++) {
	    	SetUp1_Contract[i] = EnviromentSetUP_And_Deploy1(web3,credentials,provider,G_1[i],P[i],algorithm,SetUp1_Contract[0]);
	    
	    //StopCountTime("SetUp_1-1???????????? : ",timeC4);
	    
	    //SetUp_1-2
	    //long timeC5 = StartCountTime();
	    
	    	PPKeyExtract_1[i] = ChainA_SetUP_And_Deploy1(web3,credentials,provider,SetUp1_Contract[i],G_1[i],PPKeyExtract_1[0]);
	  
	    //StopCountTime("SetUp_1-2???????????? : ",timeC5);
	    //Relay Validation
	    
	    
	    	validation1[i] = Relay_validation(getSai(),P[i],getRai(),getRelayID(),getHai(),getPKai(),
	    			"// Validate Partial Private Key Extract From Node Ai //");

	    	
	    //StopCountTime("Relay Validation A???????????? : ",timeC6);
	    //SetUp_2-1
	    //long timeC7 = StartCountTime();
	    	PPKeyExtract_2[i] = ChainB_SetUP_And_Deploy1(web32,credentials2,provider2,P[i],getPKai(),algorithm,PPKeyExtract_2[0]);

	    //StopCountTime("SetUp_2-1???????????? : ",timeC7);
	    //Relay Validation
	    //long timeC8 = StartCountTime();
	    	validation2[i] = Relay_validation(getSbj(),P[i],getRbj(),getRelayID(),getHbj(),getPKbj()
	    		,"// Validate Partial Private Key Extract From Node bj //");
	    	System.out.println("// ????????????2 // " + validation2[i]);
	    //StopCountTime("Relay Validation B???????????? : ",timeC8);
	    //Relay SetUp
	    //long timeC9 = StartCountTime();
	    	Relay_SetUP(P[i]);
	    //StopCountTime("Relay SetUp ???????????? : ",timeC9);
	    
	    //Child Chain Validation
	    
	    //long timeC10 = StartCountTime();

	    	validation3[i]= Child_Chain_validation(P[i],getCTaibj(),getPKaibj(),getXaibj(),getRai()
	    		,getRelayID(),getPKai(),getHai(),getRbj()
	    		,getPKbj(),getHbj(),gettaibj(),getHaibj(),getTaibj());
	  	    

	    	System.out.println("// ????????????3 // "+ validation3[i]);
	    //StopCountTime("Child Chain Validation ???????????? : ",timeC10);
	    
	    
	    //????????????
	    	long timeC11 = StartCountTime();
	    	ChildChainA_finish(web3,credentials,provider,SetUp1_Contract[i]);
	    	StopCountTime("?????????????????????A???????????? : ",timeC11);
	    	long timeC12 = StartCountTime();
	    	ChildChainB_finish(web32,credentials2,provider2,PPKeyExtract_2[i]);
	    	StopCountTime("?????????????????????B???????????? : ",timeC12);
	    }
	    long timeC15 = StartCountTime();
	    SetUp1_Contract[n-1] = EnviromentSetUP_And_Deploy(web3,credentials,provider,G_1[n-1],P[n-1],algorithm);
	    PPKeyExtract_1[n-1] = ChainA_SetUP_And_Deploy(web3,credentials,provider,SetUp1_Contract[n-1],G_1[n-1]);
	    validation1[n-1] = Relay_validation(getSai(),P[n-1],getRai(),getRelayID(),getHai(),getPKai(),
    			"// Validate Partial Private Key Extract From Node Ai //");
	    PPKeyExtract_2[n-1] = ChainB_SetUP_And_Deploy(web32,credentials2,provider2,P[n-1],getPKai(),algorithm);
	    validation2[n-1] = Relay_validation(getSbj(),P[n-1],getRbj(),getRelayID(),getHbj(),getPKbj()
	    		,"// Validate Partial Private Key Extract From Node bj //");
	    Relay_SetUP(P[n-1]);
	    validation3[n-1]= Child_Chain_validation(P[n-1],getCTaibj(),getPKaibj(),getXaibj(),getRai()
	    		,getRelayID(),getPKai(),getHai(),getRbj()
	    		,getPKbj(),getHbj(),gettaibj(),getHaibj(),getTaibj());
	    
	   
	    long timeC13 = StartCountTime();
	    ChildChainA_finish1(web3,credentials,provider,SetUp1_Contract[n-1]);
	    StopCountTime("final?????????????????????A???????????? : ",timeC13);
	    long timeC14 = StartCountTime();
	    ChildChainB_finish1(web32,credentials2,provider2,PPKeyExtract_2[n-1]);
	    StopCountTime("t0 ",timet0);
	    StopCountTime("?????????????????? : " ,timeCount);
	    //StopCountTime("final?????????????????????B???????????? : ",timeC14);
	    //StopCountTime("Final???????????? : ",timeC15);
	    String data = Long.toString(timeCount);
	    return data;
	}
	
	public static void ChildChainA_finish1(Web3j web3, Credentials credentials, ContractGasProvider provider, String contractAddress) throws Exception {
		
		//Method contract = Method.deploy(web3, credentials, provider).send();
		//String contractAddress = contract.getContractAddress();
		//System.out.println("Conrtract Address : " + contractAddress);
		
		//TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 80,3000);
		FastRawTransactionManager transactionManager = new org.web3j.tx.FastRawTransactionManager(web3, credentials, new PollingTransactionReceiptProcessor(web3, 2000, 80));
		Method contract_use = Method.load(contractAddress, web3, transactionManager, provider);
		//Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		System.out.println("// Finish : //" + contract_use.setInfo("Test Child Chain Accept Consensus!").send());
		//System.out.println("// Finish : //" + contract_use.setInfo("Test Child Chain Accept Consensus!").sendAsync().get());
				
	}
	public static void ChildChainB_finish1(Web3j web3, Credentials credentials, ContractGasProvider provider, String contractAddress) throws Exception{
		
		//Method contract = Method.deploy(web3, credentials, provider).send();
		//String contractAddress = contract.getContractAddress();
		//System.out.println("Conrtract Address : " + contractAddress);
		
		FastRawTransactionManager transactionManager = new org.web3j.tx.FastRawTransactionManager(web3, credentials, new PollingTransactionReceiptProcessor(web3, 2000, 80));
		//TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 80, 3000);
		Method contract_use = Method.load(contractAddress, web3, transactionManager, provider);
		//Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		System.out.println("// Finish : //" + contract_use.setInfo("Child Chain Accept Consensus from Destination!").send());
		//System.out.println("// Finish : //" + contract_use.setInfo("Child Chain Accept Consensus from Destination!").sendAsync().get());
	}
	
	public static void ChildChainA_finish(Web3j web3, Credentials credentials, ContractGasProvider provider, String contractAddress) throws Exception {
		
		//String walletPassword = "123456";
		//String walletDirectory = "C:\\go_work\\Ethereum_1\\nodedata0\\keystore";
		//String walletName = "UTC--2022-01-09T08-03-18.210009200Z--ff958bb4163679a90783258278045d439b9cc8b1";
		//String walletName = "UTC--2021-05-19T16-00-35.421648900Z--b473ac5dac7e41d941148df22434f8b6b90e9868";
		//Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8550"));
		
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		//Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		//ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(30000000L), BigInteger.valueOf(6720000L));
		//Method contract = Method.deploy(web3, credentials, provider).send();
		//String contractAddress = contract.getContractAddress();
		//System.out.println("Conrtract Address : " + contractAddress);
		
		FastRawTransactionManager transactionManager = new org.web3j.tx.FastRawTransactionManager(web3, credentials, new PollingTransactionReceiptProcessor(web3, 2000, 80));
		//TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 70,2000);
		Method contract_use = Method.load(contractAddress, web3, transactionManager, provider);
		//Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		//System.out.println("// Finish : //" + contract_use.setInfo("Test Child Chain Accept Consensus!").send());
		System.out.println("// Finish : //" + contract_use.setInfo("Test Child Chain Accept Consensus!").sendAsync().get());
		
		
	}
	public static void ChildChainB_finish(Web3j web3, Credentials credentials, ContractGasProvider provider, String contractAddress) throws Exception{
		//String walletPassword = "123456";
		//String walletDirectory = "C:\\go_work\\Ethereum_PoA\\node\\keystore";
		//String walletName = "UTC--2021-05-20T06-04-00.812610300Z--15be92c798cf6dbac416b4b707afd2a7e9180022";
		//Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8545"));
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		//Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		//System.out.println(" credentials  " + credentials);
		//ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(8000000L), BigInteger.valueOf(1800000L));
		//Method contract = Method.deploy(web3, credentials, provider).send();
		//String contractAddress = contract.getContractAddress();
		System.out.println("Conrtract Address : " + contractAddress);
		
		FastRawTransactionManager transactionManager = new org.web3j.tx.FastRawTransactionManager(web3, credentials, new PollingTransactionReceiptProcessor(web3, 2000, 80));
		//TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 70, 2000);
		Method contract_use = Method.load(contractAddress, web3, transactionManager, provider);
		//Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		//System.out.println("// Finish : //" + contract_use.setInfo("Child Chain Accept Consensus from Destination!").send());
		System.out.println("// Finish : //" + contract_use.setInfo("Child Chain Accept Consensus from Destination!").sendAsync().get());
	}
	public static void ContractTest() throws Exception {
		long timeCount = StartCountTime();
		
		String walletPassword = "123456";
		String walletDirectory = "C:\\go_work\\Ethereum_PoA\\node\\keystore";
		String walletName = "UTC--2021-05-20T06-04-00.812610300Z--15be92c798cf6dbac416b4b707afd2a7e9180022";
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8545"));
		
		/*String walletPassword = "123456";
		String walletDirectory = "C:\\go_work\\Ethereum_1\\nodedata1\\keystore";
		String walletName = "UTC--2021-05-19T16-00-35.421648900Z--b473ac5dac7e41d941148df22434f8b6b90e9868";
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8550"));*/
		
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		System.out.println(" credentials  " + credentials);
		//ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(30000000L), BigInteger.valueOf(6720000L));
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000L), BigInteger.valueOf(4000000L));
		//MethodX contract = MethodX.deploy(web3, credentials, provider).send();
		MethodX contract = MethodX.deploy(web3, credentials, provider).send();
		String contractAddress = contract.getContractAddress();
		
		//use function
		Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		System.out.println(contract_use.setP("Test ?????? Deploy Time").send());
		System.out.println(contract_use.getP().send());

		StopCountTime("???????????????????????? : " ,timeCount);
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
		Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8550"));
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(30000000L), BigInteger.valueOf(6720000L));
		StopCountTime("Web3????????????A: ",timeD0);
		
		long timeD1 =StartCountTime();
		Method contract = Method.deploy(web3, credentials, provider).send();
		StopCountTime("???A???????????????SetUp?????????????????????????????????: ",timeD1);
		
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
		StopCountTime("???A????????????Set Generator P ????????????: ",timeD2);
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
		
		StopCountTime("???A????????????Set Hash Algorithm ????????????: ",timeD3);
		
		
		return contractAddress;
	}
	*/
	
	public static String EnviromentSetUP_And_Deploy1(Web3j web3, Credentials credentials, ContractGasProvider provider, Field G_1,Element P,String H,String contractAddress) throws Exception {
		//Call Contract Deploy 
		//1. Generator P 
		//2. Hash function H
		String Generator_P = ElementToString(P);
        String _Hash = H;
        
        FastRawTransactionManager transactionManager = new org.web3j.tx.FastRawTransactionManager(web3, credentials, new PollingTransactionReceiptProcessor(web3, 2000, 80));
		//TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 1000, 200);
		//long timeD1 =StartCountTime();
		//Method contract = Method.deploy(web3, transactionManager, provider).send();
		//Method contract = Method.deploy(web3, transactionManager, provider).sendAsync().get();
		//Method contract = Method.deploy(web3, credentials, provider).send();
		//StopCountTime("???A???????????????SetUp?????????????????????????????????: ",timeD1);
		
		//String contractAddress = contract.getContractAddress();
		//System.out.println("Conrtract Address : " + contractAddress);
		
		//use function
		long timeD2 =StartCountTime();
		//Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		Method contract_use = Method.load(contractAddress, web3, transactionManager, provider);
		//System.out.println("Generator_P" + Generator_P);
		
		//System.out.println(contract_use.setP(Generator_P).send());
		System.out.println(contract_use.setP(Generator_P).sendAsync().get());
		StopCountTime("???A????????????Set Generator P ????????????: ",timeD2);
		//System.out.println("//Get Generator : //" + contract_use.getP().send());
		long timeD3 =StartCountTime();
		//System.out.println(contract_use.setH(_Hash).send());
		System.out.println(contract_use.setH(_Hash).sendAsync().get());
		StopCountTime("???A????????????Set Hash Algorithm ????????????: ",timeD3);
		//System.out.println("//Get Hash function // : " + contract_use.getH().send());
		
		return contractAddress;
	}
	
	public static String ChainA_SetUP_And_Deploy1(Web3j web3, Credentials credentials, ContractGasProvider provider, String contractAddress,Field G1,String contractAddress_PPKeyExtract_Address) throws Exception {
		long timeD4 = StartCountTime();
		//String walletPassword = "123456";
		//String walletDirectory = "C:\\go_work\\Ethereum_1\\nodedata0\\keystore";
		
		//String walletName = "UTC--2022-01-09T08-03-18.210009200Z--ff958bb4163679a90783258278045d439b9cc8b1";
		//String walletName = "UTC--2021-05-19T16-00-35.421648900Z--b473ac5dac7e41d941148df22434f8b6b90e9868";
		//Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8550"));
		//Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		//ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(30000000L), BigInteger.valueOf(6720000L));
		//StopCountTime("Web3????????????A???????????? : ",timeD4);
		
		FastRawTransactionManager transactionManager = new org.web3j.tx.FastRawTransactionManager(web3, credentials, new PollingTransactionReceiptProcessor(web3, 2000, 80));
		//TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 400, 200);
		long timeD5 = StartCountTime();
		Method contract_use = Method.load(contractAddress, web3, transactionManager, provider);
		//Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		StopCountTime("??????A???????????????????????? : ",timeD5);
		
		long timeD6 = StartCountTime();
		//String GP = contract_use.getP().send();
		String GP = contract_use.getP().sendAsync().get();
		StopCountTime("??????A???????????????Generator P???????????? : ",timeD6);
		
		Element P = StringToElement(GP,G1);
		//?????????????????? Sai,Pkai
		long timeD7 = StartCountTime();
        Random random = new Random();
	    BigInteger Sai = new BigInteger(160, 160,random);
	    StopCountTime("??????A????????????Sai???????????? : ",timeD7);
	    System.out.println("Sai : " + Sai);
	    
	    long timeD8 = StartCountTime();
	    Element PKai = P.duplicate().mul(Sai);
	    StopCountTime("??????A????????????PKai???????????? : ",timeD8);
	    
	    System.out.println("// Node Ai???????????? //" + PKai.toString());
	    System.out.println("// Node Ai???????????? //" + Sai.toString());
	    
	    //ChainId = 8888
	    String RelayChain_ID = "8888";
	    //?????????rai
	    long timeD9 = StartCountTime();
	    Random random_rai = new Random();
	    BigInteger rai = new BigInteger(160, 160,random_rai);
	    StopCountTime("??????A??????Random number ???????????? : ",timeD9);
	    
	    //?????????Rai
	    long timeD10 = StartCountTime();
	    Element Rai = P.duplicate().mul(rai);
	    StopCountTime("??????A??????Rai ???????????? : ",timeD10);
	    
	    //??????hai
	    long timeD11 = StartCountTime();
	    String hai_pre = (RelayChain_ID +Rai.toString()+PKai.toString());
	    StopCountTime("??????A??????Hash hai ???????????? : ",timeD11);
	    System.out.println(hai_pre);
	    //hai?????????
	    long timeD12 = StartCountTime();
	    byte[] hai = digest(hai_pre.getBytes(UTF_8), algorithm);
	    System.out.println("Hash ai"+String.format(OUTPUT_FORMAT, algorithm + " (hex) ", bytesToHex(hai)));
	    int[] int_hai = bytearray2intarray(hai);
	    BigInteger BI_hai = new BigInteger(intArrayToArrayString(int_hai));
	    StopCountTime("??????A???Hai??????BigInteger???????????? : ",timeD12);
	    
	    System.out.println("// BigInteger Hashai // : " + BI_hai);
	    //??????sai (PartialPrivateKeyExtract)
	    long timeD13 = StartCountTime();
	    BigInteger BI_saiX = rai.multiply(BigInteger.valueOf(Integer.valueOf(RelayChain_ID)));
	    BigInteger BI_saiY = BI_hai.multiply(Sai);
	    BigInteger NMod_Sai = BI_saiX.add(BI_saiY);
	    StopCountTime("??????A?????????????????? : ",timeD13);
	    
	    System.out.println("// Partial Private Key Extract // : " + NMod_Sai);
	    
	    //1. ??????????????????????????????????????????????????????
	    //2. ??????(Sai,Rai)????????????
	    //long timeD14 = StartCountTime();
	    //Method contract = Method.deploy(web3, transactionManager, provider).send();
	    //Method contract = Method.deploy(web3, transactionManager, provider).sendAsync().get();
	    //Method contract = Method.deploy(web3, credentials, provider).send();
		//String contractAddress_PPKeyExtract_Address = contract.getContractAddress();
		//System.out.println("Conrtract PPKeyExtract Address : " + contractAddress_PPKeyExtract_Address);
		//StopCountTime("??????Ai???????????????????????? : ",timeD14);
		
		long timeD15 = StartCountTime();
	    //Method contract_PPKeyExtract = Method.load(contractAddress_PPKeyExtract_Address, web3, credentials, provider);
		Method contract_PPKeyExtract = Method.load(contractAddress_PPKeyExtract_Address, web3, transactionManager, provider);
	    //System.out.println("// ?????? : // " +contract_PPKeyExtract.setInfo("NodeAi Partial Private Key Extract Finish").send());
		System.out.println("// ?????? : // " +contract_PPKeyExtract.setInfo("NodeAi Partial Private Key Extract Finish").sendAsync().get());
	    //System.out.println("// ?????? : // " +contract_PPKeyExtract.getInfo().send());
	    StopCountTime("??????Ai??????????????????????????????????????????????????????????????????????????? : ",timeD15);
	    
	    setSai(NMod_Sai); setRai(Rai);setHai(BI_hai);setPKai(PKai);setRelayID(RelayChain_ID);
	    //System.out.println("/////SetSai" + getSai());
		//????????????????????????
		return contractAddress_PPKeyExtract_Address;
	}
		
	public static String ChainB_SetUP_And_Deploy1(Web3j web3, Credentials credentials, ContractGasProvider provider, Element P ,Element _PKai,String hash,String contractAddress_PPKeyExtract_Address) throws Exception {
		
		
		//Element P = G1.newRandomElement().getImmutable();
		//?????????????????? Sbj,Pkbj
		long timeD17 = StartCountTime();
        Random randomB = new Random();
	    BigInteger Sbj = new BigInteger(160, 160,randomB);
	    StopCountTime("??????B????????????Sbj???????????? : ",timeD17);
	    
	    long timeD18 = StartCountTime();
	    Element PKbj = P.duplicate().mul(Sbj);
	    StopCountTime("??????B????????????PKbj???????????? : ",timeD18);
	    
	    //??????Random Number rai
	    long timeD19 = StartCountTime();
	    Random random_rbj = new Random();
	    BigInteger rbj = new BigInteger(160, 160,random_rbj);
	    StopCountTime("??????B??????Random number ???????????? : ",timeD19);
	    
	    //?????????Rbj
	    long timeD20 = StartCountTime();
	    Element Rbj = P.duplicate().mul(rbj);
	    StopCountTime("??????B??????Rbj ???????????? : ",timeD20);
	    
	    String RelayChain_ID = "8888";
	    String hbj_pre = (RelayChain_ID+Rbj.duplicate().toString()+_PKai.toString()+PKbj.toString());
	    System.out.println("//"+hbj_pre);
	    //hbj?????????
	    long timeD21 = StartCountTime();
	    byte[] hbj = digest(hbj_pre.getBytes(UTF_8), algorithm);
	    System.out.println(String.format(OUTPUT_FORMAT, algorithm + " (hex) ", bytesToHex(hbj)));
	    StopCountTime("??????B??????Hash Hbj ???????????? : ",timeD21);
	    
	    
	    long timeD22 = StartCountTime();
	    int[] int_hbj = bytearray2intarray(hbj);
	    BigInteger BI_hbj = new BigInteger(intArrayToArrayString(int_hbj));
	    StopCountTime("???Hbj??????BigInteger ???????????? : ",timeD22);
	    
	    //??????sbj (PartialPrivateKeyExtract)
	    long timeD23 = StartCountTime();
	    BigInteger BI_sbjX = rbj.multiply(BigInteger.valueOf(Integer.valueOf(RelayChain_ID)));
	    BigInteger BI_sbjY = BI_hbj.multiply(Sbj);
	    BigInteger NMod_Sbj = BI_sbjX.add(BI_sbjY);
	    System.out.println("// Partial Private Key Extract // : " + NMod_Sbj);
	    StopCountTime("?????????????????? sbj ???????????? : ",timeD23);

		
	    //BigInteger BI_sbj = (BI_sbjX.add(BI_sbjY)).mod(BigInteger.valueOf(Prime));
	    
	    setSbj(NMod_Sbj); setRbj(Rbj);setHbj(BI_hbj);setPKbj(PKbj);
	    
	    FastRawTransactionManager transactionManager = new org.web3j.tx.FastRawTransactionManager(web3, credentials, new PollingTransactionReceiptProcessor(web3, 2000, 80));
	    //TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 400, 200);
	    //1. ??????????????????????????????????????????????????????
	    //2. ??????(Sai,Rai)????????????
	    long timeD24 = StartCountTime();
	    //MethodX contract = MethodX.deploy(web3, credentials, provider).send();
	    //MethodX contract = MethodX.deploy(web3,transactionManager, provider).send();
	    //MethodX contract = MethodX.deploy(web3,transactionManager, provider).sendAsync().get();
		//String contractAddress_PPKeyExtract_Address = contract.getContractAddress();
		//System.out.println("Conrtract PPKeyExtract Address : " + contractAddress_PPKeyExtract_Address);
		MethodX contract_PPKeyExtract = MethodX.load(contractAddress_PPKeyExtract_Address, web3, transactionManager, provider);
	    //MethodX contract_PPKeyExtract = MethodX.load(contractAddress_PPKeyExtract_Address, web3, credentials, provider);
	    //System.out.println("// ?????? : // " +contract_PPKeyExtract.setInfo("Nodebj Partial Private Key Extract Finish").send());
		System.out.println("// ?????? : // " +contract_PPKeyExtract.setInfo("Nodebj Partial Private Key Extract Finish").sendAsync().get());
		//System.out.println("// ?????? : // " +contract_PPKeyExtract.getInfo().send());
	    StopCountTime("??????Bj???????????????????????????????????????????????????????????????????????????: ",timeD24);

	    return contractAddress_PPKeyExtract_Address;
	}
	
	
	public static String EnviromentSetUP_And_Deploy(Web3j web3, Credentials credentials, ContractGasProvider provider, Field G_1,Element P,String H) throws Exception {
		//Call Contract Deploy 
		//1. Generator P 
		//2. Hash function H
		String Generator_P = ElementToString(P);
        String _Hash = H;
        
        //long timeD0 =StartCountTime();
        //String walletPassword = "123456";
		//String walletDirectory = "C:\\go_work\\Ethereum_1\\nodedata0\\keystore";
		//String walletName = "UTC--2022-01-09T08-03-18.210009200Z--ff958bb4163679a90783258278045d439b9cc8b1";
		//String walletName = "UTC--2021-05-19T16-00-35.421648900Z--b473ac5dac7e41d941148df22434f8b6b90e9868";
		//Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8550"));
		
        //Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		
        //Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		//ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(30000000L), BigInteger.valueOf(6720000L));
		//StopCountTime("Web3????????????A: ",timeD0);
        
        FastRawTransactionManager transactionManager = new org.web3j.tx.FastRawTransactionManager(web3, credentials, new PollingTransactionReceiptProcessor(web3, 2000, 80));
		//TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 1000, 200);
		long timeD1 =StartCountTime();
		//Method contract = Method.deploy(web3, transactionManager, provider).send();
		Method contract = Method.deploy(web3, transactionManager, provider).sendAsync().get();
		//Method contract = Method.deploy(web3, credentials, provider).send();
		StopCountTime("???A???????????????SetUp?????????????????????????????????: ",timeD1);
		
		String contractAddress = contract.getContractAddress();
		System.out.println("Conrtract Address : " + contractAddress);
		
		//use function
		long timeD2 =StartCountTime();
		//Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		Method contract_use = Method.load(contractAddress, web3, transactionManager, provider);
		System.out.println("Generator_P" + Generator_P);
		
		//System.out.println(contract_use.setP(Generator_P).send());
		System.out.println(contract_use.setP(Generator_P).sendAsync().get());
		StopCountTime("???A????????????Set Generator P ????????????: ",timeD2);
		//System.out.println("//Get Generator : //" + contract_use.getP().send());
		long timeD3 =StartCountTime();
		//System.out.println(contract_use.setH(_Hash).send());
		System.out.println(contract_use.setH(_Hash).sendAsync().get());
		StopCountTime("???A????????????Set Hash Algorithm ????????????: ",timeD3);
		//System.out.println("//Get Hash function // : " + contract_use.getH().send());
		
		return contractAddress;
	}
	
	public static String ChainA_SetUP_And_Deploy(Web3j web3, Credentials credentials, ContractGasProvider provider, String contractAddress,Field G1) throws Exception {
		long timeD4 = StartCountTime();
		//String walletPassword = "123456";
		//String walletDirectory = "C:\\go_work\\Ethereum_1\\nodedata0\\keystore";
		
		//String walletName = "UTC--2022-01-09T08-03-18.210009200Z--ff958bb4163679a90783258278045d439b9cc8b1";
		//String walletName = "UTC--2021-05-19T16-00-35.421648900Z--b473ac5dac7e41d941148df22434f8b6b90e9868";
		//Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8550"));
		//Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		//ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(30000000L), BigInteger.valueOf(6720000L));
		//StopCountTime("Web3????????????A???????????? : ",timeD4);
		
		FastRawTransactionManager transactionManager = new org.web3j.tx.FastRawTransactionManager(web3, credentials, new PollingTransactionReceiptProcessor(web3, 2000, 80));
		//TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 400, 200);
		long timeD5 = StartCountTime();
		Method contract_use = Method.load(contractAddress, web3, transactionManager, provider);
		//Method contract_use = Method.load(contractAddress, web3, credentials, provider);
		StopCountTime("??????A???????????????????????? : ",timeD5);
		
		long timeD6 = StartCountTime();
		//String GP = contract_use.getP().send();
		String GP = contract_use.getP().sendAsync().get();
		StopCountTime("??????A???????????????Generator P???????????? : ",timeD6);
		
		Element P = StringToElement(GP,G1);
		//?????????????????? Sai,Pkai
		long timeD7 = StartCountTime();
        Random random = new Random();
	    BigInteger Sai = new BigInteger(160, 160,random);
	    StopCountTime("??????A????????????Sai???????????? : ",timeD7);
	    System.out.println("Sai : " + Sai);
	    
	    long timeD8 = StartCountTime();
	    Element PKai = P.duplicate().mul(Sai);
	    StopCountTime("??????A????????????PKai???????????? : ",timeD8);
	    
	    System.out.println("// Node Ai???????????? //" + PKai.toString());
	    System.out.println("// Node Ai???????????? //" + Sai.toString());
	    
	    //ChainId = 8888
	    String RelayChain_ID = "8888";
	    //?????????rai
	    long timeD9 = StartCountTime();
	    Random random_rai = new Random();
	    BigInteger rai = new BigInteger(160, 160,random_rai);
	    StopCountTime("??????A??????Random number ???????????? : ",timeD9);
	    
	    //?????????Rai
	    long timeD10 = StartCountTime();
	    Element Rai = P.duplicate().mul(rai);
	    StopCountTime("??????A??????Rai ???????????? : ",timeD10);
	    
	    //??????hai
	    long timeD11 = StartCountTime();
	    String hai_pre = (RelayChain_ID +Rai.toString()+PKai.toString());
	    StopCountTime("??????A??????Hash hai ???????????? : ",timeD11);
	    
	    //hai?????????
	    long timeD12 = StartCountTime();
	    byte[] hai = digest(hai_pre.getBytes(UTF_8), algorithm);
	    System.out.println("Hash ai"+String.format(OUTPUT_FORMAT, algorithm + " (hex) ", bytesToHex(hai)));
	    int[] int_hai = bytearray2intarray(hai);
	    BigInteger BI_hai = new BigInteger(intArrayToArrayString(int_hai));
	    StopCountTime("??????A???Hai??????BigInteger???????????? : ",timeD12);
	    
	    System.out.println("// BigInteger Hashai // : " + BI_hai);
	    //??????sai (PartialPrivateKeyExtract)
	    long timeD13 = StartCountTime();
	    BigInteger BI_saiX = rai.multiply(BigInteger.valueOf(Integer.valueOf(RelayChain_ID)));
	    BigInteger BI_saiY = BI_hai.multiply(Sai);
	    BigInteger NMod_Sai = BI_saiX.add(BI_saiY);
	    StopCountTime("??????A?????????????????? : ",timeD13);
	    
	    System.out.println("// Partial Private Key Extract // : " + NMod_Sai);
	    
	    //1. ??????????????????????????????????????????????????????
	    //2. ??????(Sai,Rai)????????????
	    long timeD14 = StartCountTime();
	    //Method contract = Method.deploy(web3, transactionManager, provider).send();
	    Method contract = Method.deploy(web3, transactionManager, provider).sendAsync().get();
	    //Method contract = Method.deploy(web3, credentials, provider).send();
		String contractAddress_PPKeyExtract_Address = contract.getContractAddress();
		System.out.println("Conrtract PPKeyExtract Address : " + contractAddress_PPKeyExtract_Address);
		StopCountTime("??????Ai???????????????????????? : ",timeD14);
		
		long timeD15 = StartCountTime();
	    //Method contract_PPKeyExtract = Method.load(contractAddress_PPKeyExtract_Address, web3, credentials, provider);
		Method contract_PPKeyExtract = Method.load(contractAddress_PPKeyExtract_Address, web3, transactionManager, provider);
	    //System.out.println("// ?????? : // " +contract_PPKeyExtract.setInfo("NodeAi Partial Private Key Extract Finish").send());
		System.out.println("// ?????? : // " +contract_PPKeyExtract.setInfo("NodeAi Partial Private Key Extract Finish").sendAsync().get());
	    //System.out.println("// ?????? : // " +contract_PPKeyExtract.getInfo().send());
	    StopCountTime("??????Ai??????????????????????????????????????????????????????????????????????????? : ",timeD15);
	    
	    setSai(NMod_Sai); setRai(Rai);setHai(BI_hai);setPKai(PKai);setRelayID(RelayChain_ID);
	    //System.out.println("/////SetSai" + getSai());
		//????????????????????????
		return contractAddress_PPKeyExtract_Address;
	}
	
	public static String ChainB_SetUP_And_Deploy(Web3j web3, Credentials credentials, ContractGasProvider provider, Element P ,Element _PKai,String hash) throws Exception {
		//long timeD16 = StartCountTime();
		//String walletPassword = "123456";
		//String walletDirectory = "C:\\go_work\\Ethereum_PoA\\node\\keystore";
		//String walletName = "UTC--2021-05-20T06-04-00.812610300Z--15be92c798cf6dbac416b4b707afd2a7e9180022";
		//Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8545"));
		//Credentials credentials = WalletUtils.loadCredentials("123456", path_to_walletfile);
		//Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory+"/" + walletName);
		//System.out.println(" credentials  " + credentials);
		//ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(3000000L), BigInteger.valueOf(672000L));
		//ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000L), BigInteger.valueOf(4000000L));
		//StopCountTime("Web3????????????B???????????? : ",timeD16);

		
		//Element P = G1.newRandomElement().getImmutable();
		//?????????????????? Sbj,Pkbj
		long timeD17 = StartCountTime();
        Random randomB = new Random();
	    BigInteger Sbj = new BigInteger(160, 160,randomB);
	    StopCountTime("??????B????????????Sbj???????????? : ",timeD17);
	    
	    long timeD18 = StartCountTime();
	    Element PKbj = P.duplicate().mul(Sbj);
	    StopCountTime("??????B????????????PKbj???????????? : ",timeD18);
	    
	    //??????Random Number rai
	    long timeD19 = StartCountTime();
	    Random random_rbj = new Random();
	    BigInteger rbj = new BigInteger(160, 160,random_rbj);
	    StopCountTime("??????B??????Random number ???????????? : ",timeD19);
	    
	    //?????????Rbj
	    long timeD20 = StartCountTime();
	    Element Rbj = P.duplicate().mul(rbj);
	    StopCountTime("??????B??????Rbj ???????????? : ",timeD20);
	    
	    String RelayChain_ID = "8888";
	    String hbj_pre = (RelayChain_ID+Rbj.duplicate().toString()+_PKai.toString()+PKbj.toString());
	    
	    //hbj?????????
	    long timeD21 = StartCountTime();
	    byte[] hbj = digest(hbj_pre.getBytes(UTF_8), algorithm);
	    System.out.println(String.format(OUTPUT_FORMAT, algorithm + " (hex) ", bytesToHex(hbj)));
	    StopCountTime("??????B??????Hash Hbj ???????????? : ",timeD21);
	    
	    long timeD22 = StartCountTime();
	    int[] int_hbj = bytearray2intarray(hbj);
	    BigInteger BI_hbj = new BigInteger(intArrayToArrayString(int_hbj));
	    StopCountTime("???Hbj??????BigInteger ???????????? : ",timeD22);
	    
	    //??????sbj (PartialPrivateKeyExtract)
	    long timeD23 = StartCountTime();
	    BigInteger BI_sbjX = rbj.multiply(BigInteger.valueOf(Integer.valueOf(RelayChain_ID)));
	    BigInteger BI_sbjY = BI_hbj.multiply(Sbj);
	    BigInteger NMod_Sbj = BI_sbjX.add(BI_sbjY);
	    System.out.println("// Partial Private Key Extract // : " + NMod_Sbj);
	    StopCountTime("?????????????????? sbj ???????????? : ",timeD23);

		
	    //BigInteger BI_sbj = (BI_sbjX.add(BI_sbjY)).mod(BigInteger.valueOf(Prime));
	    
	    setSbj(NMod_Sbj); setRbj(Rbj);setHbj(BI_hbj);setPKbj(PKbj);
	    
	    FastRawTransactionManager transactionManager = new org.web3j.tx.FastRawTransactionManager(web3, credentials, new PollingTransactionReceiptProcessor(web3, 2000, 80));
	    //TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3, credentials, 1000, 3000);
	    //1. ??????????????????????????????????????????????????????
	    //2. ??????(Sai,Rai)????????????
	    long timeD24 = StartCountTime();
	    //MethodX contract = MethodX.deploy(web3, credentials, provider).send();
	    //MethodX contract = MethodX.deploy(web3,transactionManager, provider).send();
	    MethodX contract = MethodX.deploy(web3,transactionManager, provider).sendAsync().get();
		String contractAddress_PPKeyExtract_Address = contract.getContractAddress();
		System.out.println("Conrtract PPKeyExtract Address : " + contractAddress_PPKeyExtract_Address);
		MethodX contract_PPKeyExtract = MethodX.load(contractAddress_PPKeyExtract_Address, web3, transactionManager, provider);
	    //MethodX contract_PPKeyExtract = MethodX.load(contractAddress_PPKeyExtract_Address, web3, credentials, provider);
	    //System.out.println("// ?????? : // " +contract_PPKeyExtract.setInfo("Nodebj Partial Private Key Extract Finish").send());
		System.out.println("// ?????? : // " +contract_PPKeyExtract.setInfo("Nodebj Partial Private Key Extract Finish").sendAsync().get());
		//System.out.println("// ?????? : // " +contract_PPKeyExtract.getInfo().send());
	    StopCountTime("??????Bj???????????????????????????????????????????????????????????????????????????: ",timeD24);

	    return contractAddress_PPKeyExtract_Address;
	}
	public static void Relay_SetUP(Element P) {
	    BigInteger hai = getHai();
	    BigInteger hbj = getHbj();
	    BigInteger NMod_Sai = getSai();
	    BigInteger NMod_Sbj = getSbj();
	    
		//Relay node Set Secret Value
	    long timeD25 = StartCountTime();
	    Random random = new Random();
	    BigInteger Xaibj = new BigInteger(160, 160,random);
	    StopCountTime("Relay ?????? Secret Value Xaibj ???????????? : ",timeD25);
	    
	    //Relay node setPublicKey
	    long timeD26 = StartCountTime();
	    Element PKaibj = P.duplicate().mul(Xaibj);
	    StopCountTime("Relay ???????????? PKaibj ???????????? : ",timeD26);
	    
	    //Transaction Hash Tran_aibj    
	    String Tran_aibj ="0xb5c8bd9430b6cc87a0e2fe110ece6bf527fa4f170a4bc8cd032f768fc5219838";
	    String Sign_last = "0000007b5dd0cd9fb87cf16213c8ffa4e8b903377b4d833a50a6236d";
	    
	    //Set Random number
	    long timeD27 = StartCountTime();
	    Random Random = new Random();
	    BigInteger t_aibj = new BigInteger(160,160,Random);
	    //Compute T_aibj
	    Element T_aibj = P.duplicate().mul(t_aibj);
	    StopCountTime("Relay ??????Taibj ???????????? : ",timeD27);
	    
	    //Compute Hash h_aibj
	    long timeD28 = StartCountTime();
	    String h_aibj_pre = (Sign_last.toString() +Tran_aibj.toString()+T_aibj.toString()+PKaibj.toString()
	    			+hai.toString()+hbj.toString());
	    System.out.println(String.format(OUTPUT_FORMAT, "Input (string)", h_aibj_pre));
	    StopCountTime("Relay ??????Hash Haibj ???????????? : ",timeD28);
	
	    long timeD29 = StartCountTime();
	    byte[] h_aibj = digest(h_aibj_pre.getBytes(UTF_8), algorithm);
	    System.out.println(String.format(OUTPUT_FORMAT, algorithm + " (hex) ", bytesToHex(h_aibj)));
	    int[] int_h_aibj = bytearray2intarray(h_aibj);
	    BigInteger BI_h_aibj = new BigInteger(intArrayToArrayString(int_h_aibj));
	    StopCountTime("Relay ???Haibj??????BigInteger ???????????? : ",timeD29);
	    
	    //Compute ??Aibj
	    long timeD30 = StartCountTime();
	    BigInteger CT_aibjX = t_aibj;//.add(BI_h_aibj);	
	    BigInteger CT_aibjY = BI_h_aibj.multiply((Xaibj.add(NMod_Sai)).add(NMod_Sbj));
	    BigInteger CT_aibjZ  = CT_aibjX.add(CT_aibjY);
	    //BigInteger CT_aibj = CT_aibjZ.mod(BigInteger.valueOf(Prime));
	    BigInteger NMod_CT_aibj = CT_aibjZ;
	    StopCountTime("Relay ?????????????????? CTaibj ???????????? : ",timeD30);
	    //setSbj(NMod_Sbj); setRbj(Rbj);setHbj(BI_hbj);setPKbj(PKbj);
	    setCTaibj(NMod_CT_aibj); settaibj(t_aibj); setHaibj(BI_h_aibj);setXaibj(Xaibj);setPKaibj(PKaibj); setTaibj(T_aibj);
	}
	public static boolean Relay_validation(BigInteger PPkey,Element P,Element BigR,
			String RelayNodeID,BigInteger hash,Element PubK,String Info) {
		long timeD16 = StartCountTime();
		long timecl = StartCountTime();
		Element Check_Left = P.duplicate().mul(PPkey);
		StopCountTime("CL:  ",timecl);
		long timecr = StartCountTime();
		BigInteger big = BigInteger.valueOf(Integer.valueOf(RelayNodeID));
		Element Check_Right1 = BigR.duplicate().mul(big); 
		Element Check_Right2 = PubK.duplicate().mul(hash);
		
		Element Check_Right = Check_Right1.add(Check_Right2);
		StopCountTime("CL:  ",timecr);
		//System.out.println(Info + Check_Left.equals(Check_Right));
		StopCountTime(Info+"??????????????????????????? : " ,timeD16);
		
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
		
		long sl = StartCountTime();
	    Element Sign_verfication_Left = P.duplicate().mul(NMod_CT_aibj);
	    StopCountTime("final Sign_verfication_Left",sl);
	    BigInteger Identity_Relay_node_hash = BigInteger.valueOf(Integer.valueOf(RelayID));
	    
	    
	    long f1 = StartCountTime();
	    Element SV_R0 = PKaibj;
	    Element Q = P.duplicate().mul(Xaibj);
	    System.out.println("Check : Q : " +Q.equals(SV_R0));
	    //Rai???IDaibj
	    Element SV_R1 = Rai.duplicate().mul(Identity_Relay_node_hash);
	    //hai ??? PKai
	    Element SV_R2 = PKai.duplicate().mul(BI_hai);
	    
	    //Rbj ??? IDaibj
	    Element SV_R3 = Rbj.duplicate().mul(Identity_Relay_node_hash);
	    //Element SV_R3 = SV_R2;
	    System.out.println("Check RBJ in Line 369 : " +Rbj);
	    
	    //hbj ??? PKbj
	    Element SV_R4 = PKbj.duplicate().mul(BI_hbj);

	    
	    Element SV_RG = (((SV_R0.add(SV_R1)).add(SV_R2)).add(SV_R3)).add(SV_R4);
	    Element SV_R = T_aibj.duplicate().add(SV_RG.duplicate().mul(BI_h_aibj));
	    StopCountTime("final",f1);
	    System.out.println("T_aibj Check " + T_aibj.equals(P.duplicate().mul(t_aibj)));
	    System.out.println("//  Final Check in Child Chain : //" + Sign_verfication_Left.equals(SV_R));
	    
		return T_aibj.equals(P.duplicate().mul(t_aibj));
	}

	public static String ElementToString(Element e1) {
		//Element Encode???byte 
		byte[] s = Base64.getEncoder().encode(e1.toBytes());
	    //byte ?????? String
        String Str_P = new String(s);
		
        return Str_P;		
	}
	
	public static Element StringToElement(String e2,Field G1) {
		//?????????????????????????????????Decode;
        Element P2 = G1.newElementFromBytes(Base64.getDecoder().decode(e2));
		return P2;
	}
	public static int generatePrime(int Start , int end) {
		int prime_n =0;
		while(true){
		int num_p = (int) (Math.random()*end)+Start; //?????? Start ???End ?????????????????????
			if(isPrime(num_p)) {
				System.out.println("????????? N : " +num_p);
				prime_n = num_p;
				break;
			}
		
		}
		return prime_n;
	}
	public static boolean isPrime(int num) {
		for (int i = 2; i<=num /2 ;i++) {
			if (num % i  ==0) {
				System.out.println(num + "?????????" + i + "??????");
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
      // ?????? Arrays.toString ????????????????????? array
      // ???????????????[4, 2, 5, 1, 5, 2, 4, 3]
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
	    System.out.println("???????????????????????????????????????????????????");
	    //?????????
	    Pairing pairing = PairingFactory.getPairing(parameter);
	    System.out.println("????????????????????????");
	    return pairing;
	}
	
	public static Field initG_1(Pairing pairing) {
	    System.out.println("????????????????????????????????????");
	    //?????????
	    Field G1 = pairing.getG1();
	    
	    System.out.println("??????????????????????????????");
	    
	    return G1;
	}
	public static Element initG(Field G1) {
	    System.out.println("???????????????????????????G??????");
	    //???????????????G
	    Element G = G1.newRandomElement().getImmutable();
	    System.out.println("?????????????????????G??????" + G.toString());
	    return G;
	}

	public static Element initP_t(Field G1) {
	    System.out.println("???????????????????????????P_t??????");
	    //???????????????P_t
	    Element P_t = G1.newRandomElement().getImmutable();
	    System.out.println("?????????????????????P_t??????" + P_t.toString());
	    return P_t;
	}
	//??????????????????
	public static void KeyGenerator(Element G,Field G_1) throws Exception {
		//??????????????????n_b ????????????
	    Random random = new Random();
	    BigInteger n_b = new BigInteger(160, 160,random);//BigInteger????????????1???
	    //????????????P_b
	    Element P_b = G.duplicate().mul(n_b);
	    byte[] b = P_b.toCanonicalRepresentation();
	    System.out.println("\n");
	    //?????????????????????????????????publicKey.key???privateKey.key?????????
	    String path = new File("").getCanonicalPath();
	    out(path + "\\privateKey.key", n_b.toString());
	    out(path + "\\publicKey.key",
	    Base64.getEncoder().encodeToString((P_b).toCanonicalRepresentation())
	    + ",,,,,," +     Base64.getEncoder().encodeToString(G.toCanonicalRepresentation()));
	    System.out.println("????????????????????????" + path + "\\privateKey.key");
	    System.out.println("????????????????????????" + path + "\\publicKey.key");
	}//????????????
     public static String encrypt(Element P_b, String data, BigInteger k, Element P_t, Element G){
        try {
              byte[] datasource=data.getBytes("utf8");
              String CArray = "A";
              //??????P_1
              Element P_1 = G.duplicate().getImmutable().mul(k);
              System.out.println("???????????????????????????P_1:"+ P_1);
              //??????P_2
              Element P_2 = P_b.duplicate().getImmutable().mul(k);
              System.out.println("???????????????????????????P_2:"+ P_2);
              //??????P_end
              Element P_end = P_t.add(P_2);
              System.out.println("???????????????????????????P_end:"+ P_end);
              //????????????C
              String[] p_txy = P_t.toString().split(",");
              BigInteger p_tx = new BigInteger(p_txy[0]);
              BigInteger p_ty = new BigInteger(p_txy[1]);
              for(int i=0;i<datasource.length;i++)
              {
                  BigInteger M = new BigInteger(datasource[i]+"");
                  BigInteger C_mid = M.multiply(p_tx).add(p_ty);
                  CArray = CArray +","+C_mid.toString();
              }
              CArray = CArray + ",,"+
              Base64.getEncoder().encodeToString(P_1.toCanonicalRepresentation())+",,"+
              Base64.getEncoder().encodeToString(P_end.toCanonicalRepresentation());
              return Base64.getEncoder().encodeToString(CArray.getBytes());
          }
          catch(Exception ex) {
              ex.printStackTrace();
          }
          return null;
      }
     public static String decrypt(BigInteger Privatekey, String data, Field G_1,Element G) {
        try {
              String ciphertext= new String(Base64.getDecoder().decode(data),"utf8");
              //????????????
              String[] CS=ciphertext.split(",,");
              String m = "";
              //??????P_t+kP_b
              Element P_end = G_1.newElementFromBytes(Base64.getDecoder().decode(CS[2]));
              //??????P_1
              Element P_1 = G_1.newElementFromBytes(Base64.getDecoder().decode(CS[1]));
              //??????P_t
              Element P_t = P_end.getImmutable().sub(P_1.getImmutable().mul(Privatekey));
              System.out.println("???????????????????????????P_t:"+ P_t);
              //????????????M
              String[] p_txy = P_t.toString().split(",");
              BigInteger p_tx = new BigInteger(p_txy[0]);
              BigInteger p_ty = new BigInteger(p_txy[1]);
              //????????????c
              String[] Plaintext = CS[0].split(",");
              for(int i=1;i<Plaintext.length;i++){
                  BigInteger C = new BigInteger(Plaintext[i]);
                  BigInteger M_mid = C.subtract(p_ty).divide(p_tx);
                  m = m+new String(M_mid.toByteArray(),"GBK");;
              }
              return m;
          }
          catch(Exception ex) {
              ex.printStackTrace();
          }
          return null;
      }

      //???????????????
      public static void out(String path, String val) {
         try {
              val = Base64.getEncoder().encodeToString(val.getBytes("utf8"));
              FileWriter fw = new FileWriter(path);
              BufferedWriter bw = new BufferedWriter(fw);
              PrintWriter outs = new PrintWriter(bw);
              outs.println(val);
              outs.flush();
              outs.close();
          } catch (Exception ex) {
              ex.printStackTrace();
          }
      }

      //?????????????????????
      public static Element readPk(String path, Field G_1){
          Element sk = null;
          try {
              File f=new File(path);
              FileReader fr=new FileReader(f);
              BufferedReader br=new BufferedReader(fr);
              String line=null;
              StringBuffer sb=new StringBuffer();
              while((line=br.readLine())!=null) {
                  byte[] b = Base64.getDecoder().decode(line);
                  String[] key = new String(b).split(",,,,,,");
                  byte [] a = Base64.getDecoder().decode(key[0]);
                  System.out.println("\n");
                  if(key.length == 2){
                      sk = G_1.newElementFromBytes(a);
                  } else{
                      throw new Exception("????????????");
                  }
              }

              br.close();
              return sk;
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
          return sk;
      }

  //????????????????????????
  public static BigInteger readSk(String path, Field G_1){
          BigInteger sk = null;
          try {
              File f=new File(path);
              FileReader fr=new FileReader(f);
              BufferedReader br=new BufferedReader(fr);
              String line=null;

              StringBuffer sb=new StringBuffer();
              while((line=br.readLine())!=null) {
                  byte[] b = Base64.getDecoder().decode(line);
                  String[] key = new String(b).split(",,,,,,");
                  if (key.length == 1) {
                      sk = new BigInteger(key[0]);
                  }else{
                      throw new Exception("????????????");
                  }
              }
              br.close();
              return sk;
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
          return sk;
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
	      time1 = System.nanoTime();
	      // doSomething()
	      //time2 = System.nanoTime();
	      // doAnotherthing()
	      //time3 = System.nanoTime();
	      //System.out.println("doSomething()?????????" + (time2-time1)/1000 + "???");
	      //System.out.println("doAnotherthing()?????????" + (time3-time2)/1000 + "???");
	      
	      return time1;
	  }
	  public static void StopCountTime(String Info,long time1) {
		  long time2;
		  time2 = System.nanoTime();
		  System.out.println(Info +" : "  + (time2-time1) + "??????");
	  }
}
