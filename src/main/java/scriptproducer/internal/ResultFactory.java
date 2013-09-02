package scriptproducer.internal;

import javax.xml.transform.Result;

interface ResultFactory{
    Result newResult(String scriptName);
}