package ru.sfedu.Arch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

public class Result implements Serializable {
    private static Logger log = LogManager.getLogger(Result.class);
    private Enums.STATUS status;
    private Object returnValue;

    public Enums.STATUS getStatus() {
        return status;
    }

    public void setStatus(Enums.STATUS status) {
        this.status = status;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Result () {}
    public Result (Enums.STATUS status, Object returnValue) {
        this.setStatus(status);
        this.setReturnValue(returnValue);
        log.info(this.toString());
    }

    @Override
    public String toString() {
        return String.format("{ status: %s, value: %s }", getStatus().toString(), getReturnValue().toString());
    }
}
