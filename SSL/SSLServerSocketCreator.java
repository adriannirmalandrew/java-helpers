import java.net.*;
import javax.net.*;
import javax.net.ssl.*;
import java.io.*;
import java.security.*;

public class SSLServerSocketCreator {
	//Path to TLS KeyStore:
	private String keyStorePath=null;
	//Type of SSLContext to create:
	private String ctxType=null;
	//Port number:
	private int portno=0;
	
	//Constructor:
	public SSLServerSocketCreator(String p, String c, int pt) {
		this.keyStorePath=p;
		this.ctxType=c; //ctxType should be TLS, usually
		this.portno=pt;
	}
	
	//Necessary stuff:
	private SSLContext ctx=null;
	private KeyManagerFactory kmf=null;
	private KeyStore ks=null;
	//Socket factory:
	SSLServerSocketFactory sfac=null;
	
	//initialize() should throw CertificateException, but it refused to compile, so Exception is used instead.
	public void initialize(char[] passwd) throws IOException,NoSuchAlgorithmException,KeyStoreException,Exception,UnrecoverableKeyException,KeyManagementException {
		//Instantiate SSL Context:
		this.ctx=SSLContext.getInstance(this.ctxType);
		
		//Create KeyMangerFactory:
		this.kmf=KeyManagerFactory.getInstance("SunX509");
		
		//Initialize KeyStore and kmf:
		this.ks=KeyStore.getInstance("PKCS12");
		File store=new File(this.keyStorePath);
		if(!store.exists())
			throw new IOException("KeyStore file does not exist!");
		this.ks.load(new FileInputStream(store), passwd);
		this.kmf.init(ks, passwd);
		
		//Initialize SSL context:
		this.ctx.init(kmf.getKeyManagers(), null, new SecureRandom());
		
		//Initialize sfac:
		this.sfac=(SSLServerSocketFactory)this.ctx.getServerSocketFactory();
	}
	
	//Returns a ServerSocket:
	public SSLServerSocket getServerSocket() throws IOException {
		return (SSLServerSocket)this.sfac.createServerSocket(this.portno);
	}
}
