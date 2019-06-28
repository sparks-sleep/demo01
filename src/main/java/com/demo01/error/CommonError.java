package com.demo01.error;

/**
 *
 * @author sparks.org.cn
 */
public interface CommonError {
    int getErrCode();
    String getErrMsg();
    CommonError setErrMsg(String errMsg);
}
