package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;

public class DomResultFactory implements ResultFactory{
	@Override
	public Result newResult(ScriptConfiguration scriptConfiguration) {
		return new DOMResult();
	}
}