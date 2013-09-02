package scriptproducer.impl;

import javax.xml.transform.Result;

interface ResultFactory{
    Result newResult(String scriptName);
}