package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import javax.xml.transform.Result;

public interface ResultFactory{
    Result newResult(ScriptConfiguration scriptConfiguration);
}