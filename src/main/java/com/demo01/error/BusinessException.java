package com.demo01.error;

/**
 *  包装器业务异常类实现
 * @author sparks.org.cn
 */
public class BusinessException extends Exception implements CommonError {

    private final CommonError commonError;
    //直接接收EmBusinessError的传参用于构造业务异常
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }
    //接收自定义Errmsg的构造方法异常
    public BusinessException(CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }
    @Override
    public int getErrCode() {
       return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
         this.commonError.setErrMsg(errMsg);
         return this;
    }
    
}
