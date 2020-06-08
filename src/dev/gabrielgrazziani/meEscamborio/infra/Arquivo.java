package dev.gabrielgrazziani.meEscamborio.infra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;

import javax.servlet.http.Part;

public class Arquivo {
	
	public final static String SERVER_PATH = "C:/Users/Public";

	public String write(String path, Part arquivo) {
		String relativePath = path + "/" + arquivo.getSubmittedFileName();

		try {
			arquivo.write(SERVER_PATH + "/" + relativePath);
			
			return relativePath;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void transfer(Path source, OutputStream outputStream) {
	    try {
	        FileInputStream input = new FileInputStream(source.toFile());
	        try( ReadableByteChannel inputChannel = Channels.newChannel(input);
	                  WritableByteChannel outputChannel = Channels.newChannel(outputStream);) {
	              ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);

	        while(inputChannel.read(buffer) != -1) {
	        	buffer.flip();
	            outputChannel.write(buffer);
	            buffer.clear();
	        }
	        } catch (IOException e) {
	            throw new RuntimeException(e); 
	        }
	    } catch (FileNotFoundException e) {
	        throw new RuntimeException(e); 
	    }
	}

}