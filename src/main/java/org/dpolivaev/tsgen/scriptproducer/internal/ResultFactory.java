package org.dpolivaev.tsgen.scriptproducer.internal;

import javax.xml.transform.Result;

interface ResultFactory{
    Result newResult(String scriptName);
}