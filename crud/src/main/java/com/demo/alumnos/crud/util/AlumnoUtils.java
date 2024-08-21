package com.demo.alumnos.crud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AlumnoUtils {

    Logger logger = LoggerFactory.getLogger(AlumnoUtils.class);

    public String getStackTrace(StackTraceElement[] stackTraceElements){
        String stack = null;
        try {
            logger.info("Getting stack trace");
            if (stackTraceElements.length > 0){
                stack = "Class: [" + stackTraceElements[0].getClassName() + "], Method: [" +
                        stackTraceElements[0].getMethodName() + "], Line ;[" + stackTraceElements[0].getLineNumber() + "]";

            }

        }catch (Exception e){
            logger.error("Getting stack trace Exception {}", e.getMessage());
            stack= "";
        }

        return stack;
    }

}
