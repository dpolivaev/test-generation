package org.dpolivaev.tsgen.scriptwriter.internal;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;

public class StreamResultFactory implements ResultFactory{
     public Result newResult(ScriptConfiguration scriptConfiguration) {
		final File outputFile = new File(scriptConfiguration.outputConfiguration.directory, 
				scriptConfiguration.scriptName + "." + scriptConfiguration.outputConfiguration.fileExtension);
		try {
			return new StreamResult(new BufferedOutputStream(new FileOutputStream(outputFile)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
    }
}