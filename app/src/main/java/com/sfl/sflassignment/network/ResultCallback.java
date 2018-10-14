package com.sfl.sflassignment.network;

public interface ResultCallback<T> {

    void success(T result);

    void failure(Throwable error);
}
