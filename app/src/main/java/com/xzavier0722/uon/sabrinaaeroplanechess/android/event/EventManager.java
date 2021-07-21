package com.xzavier0722.uon.sabrinaaeroplanechess.android.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventManager {

    private final Map<Class<? extends Event>, Set<EventExecutor>> listeners = new HashMap<>();

    public void registerListener(Listener listener) {
        for (Method each : listener.getClass().getMethods()) {
            if (each.getAnnotation(EventHandler.class)==null) {
                // Not a listener method, next
                continue;
            }

            Class<?>[] paraTypes = each.getParameterTypes();
            Class<?> clazz;
            if (paraTypes.length != 1 || !Event.class.isAssignableFrom((clazz = paraTypes[0]))) {
                // Not a valid parameter count
                System.err.println("Invalid handler: "+each.toGenericString());
                continue;
            }

            Class<? extends Event> eventClass = clazz.asSubclass(Event.class);
            each.setAccessible(true);

            Set<EventExecutor> executors = listeners.get(eventClass);
            if (executors == null) {
                executors = new HashSet<>();
                listeners.put(eventClass, executors);
            }

            executors.add(event -> {
                try {
                    each.invoke(listener, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void callEvent(Event event){
        Set<EventExecutor> executors = listeners.get(event.getClass());

        if (executors == null) return;

        for (EventExecutor each : executors) {
            each.handle(event);
        }
    }

}
