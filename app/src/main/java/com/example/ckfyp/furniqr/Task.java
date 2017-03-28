package com.example.ckfyp.furniqr;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nick Yau on 3/26/2017.
 */

public class Task implements Serializable {

    int taskid;
    String idarray;
    String quantityarray;
    String amountarray;
    String totalamount;
    String cartqrcode;
    String userid;
    String username;
    String status;

    public Task(int taskid, String idarray, String quantityarray, String amountarray, String totalamount, String cartqrcode, String userid, String username, String status) {
        this.taskid = taskid;
        this.idarray = idarray;
        this.quantityarray = quantityarray;
        this.amountarray = amountarray;
        this.totalamount = totalamount;
        this.cartqrcode = cartqrcode;
        this.userid = userid;
        this.username = username;
        this.status = status;
    }

    public Task(){}

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getIdarray() {
        return idarray;
    }

    public void setIdarray(String idarray) {
        this.idarray = idarray;
    }

    public String getQuantityarray() {
        return quantityarray;
    }

    public void setQuantityarray(String quantityarray) {
        this.quantityarray = quantityarray;
    }

    public String getAmountarray() {
        return amountarray;
    }

    public void setAmountarray(String amountarray) {
        this.amountarray = amountarray;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getCartqrcode() {
        return cartqrcode;
    }

    public void setCartqrcode(String cartqrcode) {
        this.cartqrcode = cartqrcode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
