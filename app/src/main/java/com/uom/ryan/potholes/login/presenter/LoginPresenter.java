package com.uom.ryan.potholes.login.presenter;

/**
 * Created by Ryan on 03/11/2017.
 */

public interface LoginPresenter {
    void handleLogin(String email, String password);
    void handleRegister(String email, String password);

}
