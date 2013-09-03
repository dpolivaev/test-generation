package org.dpolivaev.tsgen.scriptproducer.internal;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class StreamResultFactory implements ResultFactory{
    private File baseDirectory = null;
	private String fileExtension = "xml";
    public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public File getBaseDirectory() {
        return baseDirectory;
    }
    public void setBaseDirectory(File baseDir) {
        this.baseDirectory = baseDir;
    }
    public Result newResult(String scriptName) {
		return new StreamResult(new File(baseDirectory, scriptName + "." + fileExtension));
    }
}