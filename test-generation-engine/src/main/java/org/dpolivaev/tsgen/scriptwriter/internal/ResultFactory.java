package org.dpolivaev.tsgen.scriptwriter.internal;

import javax.xml.transform.Result;

import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;

public interface ResultFactory{
    Result newResult(ScriptConfiguration scriptConfiguration);
}