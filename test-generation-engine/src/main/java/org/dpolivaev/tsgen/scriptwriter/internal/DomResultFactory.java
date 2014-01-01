package org.dpolivaev.tsgen.scriptwriter.internal;

import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;

import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;

public class DomResultFactory implements ResultFactory{
	@Override
	public Result newResult(ScriptConfiguration scriptConfiguration) {
		return new DOMResult();
	}
}