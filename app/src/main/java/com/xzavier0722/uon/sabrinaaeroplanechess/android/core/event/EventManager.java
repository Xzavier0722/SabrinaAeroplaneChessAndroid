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

            // Add executor
            listeners.computeIfAbsent(annotation.type(), k -> new HashMap<>()).computeIfAbsent(eventClass, k -> new HashSet<>()).add(event -> {
                each.invoke(listener, event);
            });
        }
    }

    public void callListener(Event event) {
        callListener(event, ListenerType.Listener);
        callListener(event, ListenerType.Monitor);
        callListener(event, ListenerType.Post);
        for (Event subEvent : event.getSubEvents()) {
            callListener(subEvent);
        }
    }

    private void callListener(Event event, ListenerType type){
        Map<Class<? extends Event>, Set<ListenerExecutor>> typedListeners = listeners.get(type);
        if (typedListeners == null) return;

        Set<ListenerExecutor> executors = typedListeners.get(event.getClass());
        if (executors == null) return;

        for (ListenerExecutor each : executors) {
            try {
                each.handle(event);
            } catch (Throwable e) {
                System.err.println("Exception thrown while calling the event: "+e.getClass().getSimpleName());
                e.printStackTrace();
            }
        }
    }

}
