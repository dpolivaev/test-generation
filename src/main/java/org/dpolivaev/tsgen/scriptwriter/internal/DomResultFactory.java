package org.dpolivaev.tsgen.scriptwriter.internal;

import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;

public class DomResultFactory implements ResultFactory{
    public Result newResult(String scriptName, String FileExtension) {
        return new DOMResult();
    }
}