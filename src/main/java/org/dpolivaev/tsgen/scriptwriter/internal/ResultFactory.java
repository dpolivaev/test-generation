package org.dpolivaev.tsgen.scriptwriter.internal;

import javax.xml.transform.Result;

interface ResultFactory{
    Result newResult(String scriptName, String FileExtension);
}