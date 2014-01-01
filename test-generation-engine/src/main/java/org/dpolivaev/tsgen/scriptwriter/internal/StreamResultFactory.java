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
		final File outputFile = scriptConfiguration.outputFile();
		try {
			return streamResult(outputFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
    }

     public StreamResult streamResult(final File outputFile)
			throws FileNotFoundException {
		return new StreamResult(new BufferedOutputStream(createOutputFileStream(outputFile)));
	}

	private static FileOutputStream createOutputFileStream(final File outputFile)
			throws FileNotFoundException {
		File directory = outputFile.getParentFile();
		if(directory != null)
			directory.mkdirs();
		return new FileOutputStream(outputFile);
	}
}