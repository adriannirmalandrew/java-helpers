import java.net.*;
import javax.net.*;
import javax.net.ssl.*;
import java.io.*;
import java.security.*;

public class SSLSocketCreator {
	//Path to TLS TrustStore:
	private String trustStorePath=null;
	//Type of SSLContext to create:
	private String ctxType=null;
	//IP address:
	private String address=null;
	//Port number:
	private int portno=0;
	
	//Constructor:
	public SSLSocketCreator(String pa, String c, String add, int p) {
		this.trustStorePath=pa;
		this.ctxType=c; //ctxType should be TLS, usually
		this.address=add;
		this.portno=p;
	}
	
	//Necessary stuff:
	private SSLContext ctx=null;
	private TrustManagerFactory tmf=null;
	private KeyStore ks=null;
	//Socket factory:
	SSLSocketFactory sfac=null;
	
	//initialize() should throw CertificateException, but it refused to compile, so Exception is used instead.
	public void initialize(char[] passwd) throws IOException,NoSuchAlgorithmException,KeyStoreException,Exception,UnrecoverableKeyException,KeyManagementException {
		//Instantiate SSL Context:
		this.ctx=SSLContext.getInstance(this.ctxType);
		
		//Create TrustMangerFactory:
		this.tmf=TrustManagerFactory.getInstance("SunX509");
		
		//Initialize TrustStore and kmf:
		this.ks=KeyStore.getInstance("PKCS12");
		File store=new File(this.trustStorePath);
		if(!store.exists())
			throw new IOException("KeyStore file does not exist!");
		this.ks.load(new FileInputStream(store), passwd);
		this.tmf.init(ks);
		
		//Initialize SSL context:
		this.ctx.init(null, this.tmf.getTrustManagers(), new SecureRandom());
		
		//Initialize sfac:
		this.sfac=(SSLSocketFactory)this.ctx.getSocketFactory();
	}
	
	//Returns a ServerSocket:
	public SSLSocket getSocket() throws IOException {
		return (SSLSocket)this.sfac.createSocket(this.address, this.portno);
	}
}
