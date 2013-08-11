package scriptproducer;

import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;

public class DomResultFactory implements ResultFactory{
    public Result newResult(String scriptName) {
        return new DOMResult();
    }
}