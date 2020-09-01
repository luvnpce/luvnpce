package rpcdemo.rpc.protocol;

import java.io.Serializable;

/**
 * 通信内容
 */
public class MyContent implements Serializable {

    private String name;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] args;
    private Object result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
