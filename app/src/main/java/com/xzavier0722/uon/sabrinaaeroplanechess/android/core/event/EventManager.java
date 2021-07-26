package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventManager {

    private final Map<ListenerType, Map<Class<? extends Event>, Set<ListenerExecutor>>> listeners = new HashMap<>();

    public void registerListener(Listener listener) {
        for (Method each : listener.getClass().getMethods()) {
            // Get annotation
            EventListener annotation = each.getAnnotation(EventListener.class);
            if (annotation==null) {
                // Not a listener method, next
                continue;
            }

            // Check parameter
            Class<?>[] paraTypes = each.getParameterTypes();
            Class<?> clazz;
            if (paraTypes.length != 1 || !Event.class.isAssignableFrom((clazz = paraTypes[0]))) {
                // Not a valid parameter count
                System.err.println("Invalid handler: "+each.toGenericString());
                continue;
            }

            // Cast class
            Class<? extends Event> eventClass = clazz.asSubclass(Event.class);
            each.setAccessible(true);

            // Get map for specific type
            Map<Class<? extends Event>, Set<ListenerExecutor>> typedListeners = listeners.get(annotation.type());
            if (typedListeners == null) {
                typedListeners = new HashMap<>();
                listeners.put(annotation.type(), typedListeners);
            }

            // Get executor set
            Set<ListenerExecutor> executors = typedListeners.get(eventClass);
            if (executors == null) {
                executors = new HashSet<>();
                typedListeners.put(eventClass, executors);
            }

            // Add executor
            executors.add(event -> {
                try {
                    each.invoke(listener, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void callListener(Event event, ListenerType type){
        Map<Class<? extends Event>, Set<ListenerExecutor>> typedListeners = listeners.get(type);
        if (typedListeners == null) return;

        Set<ListenerExecutor> executors = typedListeners.get(event.getClass());
        if (executors == null) return;

        for (ListenerExecutor each : executors) {
            each.handle(event);
        }
    }

}
