package org.dpolivaev.tsgen.scriptwriter.internal;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;

public class StreamResultFactory implements ResultFactory{
     public Result newResult(ScriptConfiguration scriptConfiguration) {
		return new StreamResult(new File(scriptConfiguration.outputConfiguration.directory, 
				scriptConfiguration.scriptName + "." + scriptConfiguration.outputConfiguration.fileExtension));
    }
}