package tp.pr5.view;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class JTextAreaOutputStream extends OutputStream {
	
	private JTextArea jTextArea;
	
	public JTextAreaOutputStream(JTextArea jTextArea) {
		this.jTextArea = jTextArea;
	}

	@Override
	public void write(int arg0) throws IOException {}
	
	@Override
	public void write(byte[] byteArray) {
		this.jTextArea.append(new String(byteArray));
	}

}
