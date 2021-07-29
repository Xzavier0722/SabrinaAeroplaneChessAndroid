package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event;

import java.lang.reflect.InvocationTargetException;

public interface ListenerExecutor {

    void handle(Event event) throws InvocationTargetException, IllegalAccessException;

}
