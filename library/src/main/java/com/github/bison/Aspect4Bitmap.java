package com.github.bison;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect public class Aspect4Bitmap {

    @Before("call (* android.graphics.Bitmap.createBitmap(..))")
    public void beforeCreateBitmap(JoinPoint joinPoint) {
        System.out.println("************* beforeCreateBitmap *************");
        inspectArguments(joinPoint);
        dumpStackTrace();
    }

    private void inspectArguments(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] objects = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("width")) {
                builder.append("width:" + objects[i] + " ");
            } else if (parameterNames[i].equals("height")) {
                builder.append("height:" + objects[i] + " ");
            } else if (parameterNames[i].equals("config")) {
                builder.append("config:" +  ((Enum) objects[i]).ordinal() + " ");
            }
        }
        System.out.println(builder.toString());
    }

    private void dumpStackTrace() {
        StringBuilder builder = new StringBuilder();
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        /*
          dalvik.system.VMStack.getThreadStackTrace(Native Method)
          java.lang.Thread.getStackTrace(Thread.java:591)
          com.tencent.ttpic.aspectj.BitmapMonitor.dumpStackTrace(BitmapMonitor.java:41)
          com.tencent.ttpic.aspectj.BitmapMonitor.adviceOnCreateBitmap(BitmapMonitor.java:18)
         */
        for (int i = 0; i < elements.length; i++) {
            builder.append(elements[i].toString()).append("\n");
        }
        System.out.println(builder.toString());
    }
}
