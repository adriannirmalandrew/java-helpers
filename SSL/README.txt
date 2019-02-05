Using the SSLServerSocketCreator:
>	First, the object is initialized with the path to the keystore, the context type (TLS, TLSv1.2 etc) and a port number to listen on.
	e.g. SSLServerSocketCreator foo=new SSLServerSocketCreator("foo.p12", "TLS", 420);
>	Then, the Creator is initialized with the keystore's password:
	e.g. foo.initialize("letmein");
>	Finally, call getServerSocket() to finally get an SSLServerSocket object.

Using the SSLSocketCreator:
>	The object is initialized with the path to the truststore, the context type, the target IP address (as a string) and port number.
	e.g. SSLSocketCreator bar=new SSLSocketCreator("bar.p12", "TLS", "169.69.69.69", 420);
>	Then, the Creator is initialized with the truststore's password:
	e.g. bar.initialize("letmein");
>	Finally, call getSocket() to finally get an SSLSocket object.

These two classes are used in my other project "tls_files", a simple file server.
