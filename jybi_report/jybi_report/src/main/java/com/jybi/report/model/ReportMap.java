package com.jybi.report.model;

/**
 * 预览表单公共类
 * Created by js_gg on 2017/12/25.
 */
public class ReportMap<T> {

    private String objName;

    private Integer size;

    private T objNextTemp;

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public T getObjNextTemp() {
        return objNextTemp;
    }

    public void setObjNextTemp(T objNextTemp) {
        this.objNextTemp = objNextTemp;
    }
}
