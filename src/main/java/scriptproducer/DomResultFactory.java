package scriptproducer;

import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;

class DomResultFactory implements ResultFactory{
    public Result newResult(String scriptName) {
        return new DOMResult();
    }
}