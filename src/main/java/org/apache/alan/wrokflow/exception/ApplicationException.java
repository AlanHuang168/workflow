package org.apache.alan.wrokflow.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.alan.wrokflow.result.ActionResult;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 2156274765383407719L;

    private ActionResult<?> result;

    public ApplicationException() {
        result = ActionResult.error();
    }

    public ApplicationException(String errmsg) {
        result = ActionResult.error(errmsg);
    }

    public ApplicationException(int errcode, String errmsg) {
        result = ActionResult.error(errcode, errmsg);
    }

    public <T> ApplicationException(int errcode, String errmsg, T data) {
        result = ActionResult.error(errcode, errmsg, data);
    }

    public ApplicationException(ActionResult<?> result) {
        this.result = result;
    }

    @Override
    public String getMessage() {
        return result.getMessage();
    }

    public ActionResult<?> getResult() {
        return result;
    }
}
