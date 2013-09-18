package org.dpolivaev.tsgen.scriptwriter.internal;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class StreamResultFactory implements ResultFactory{
    private File baseDirectory = null;
	public File getBaseDirectory() {
        return baseDirectory;
    }
    public void setBaseDirectory(File baseDir) {
        this.baseDirectory = baseDir;
    }
    public Result newResult(String scriptName, String fileExtension) {
		return new StreamResult(new File(baseDirectory, scriptName + "." + fileExtension));
    }
}