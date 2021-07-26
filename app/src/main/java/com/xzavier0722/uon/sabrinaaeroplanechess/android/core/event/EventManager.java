package com.xzavier0722.uon.sabrinaaeroplanechess.android.core.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventManager {

    private final Map<ListenerType, Map<Class<? extends Event>, Set<ListenerExecutor>>> listeners = new HashMap<>();
    private final List<Event> cacheList = new LinkedList<>();

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
                try {
                    each.invoke(listener, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void callListener(Event event) {
        cacheList.add(event);
        callListener(event, ListenerType.Listener);
    }

    public void updateMonitor() {
        for (Event each : cacheList) {
            callListener(each, ListenerType.Monitor);
        }
        cacheList.clear();
    }

    private void callListener(Event event, ListenerType type){
        Map<Class<? extends Event>, Set<ListenerExecutor>> typedListeners = listeners.get(type);
        if (typedListeners == null) return;

        Set<ListenerExecutor> executors = typedListeners.get(event.getClass());
        if (executors == null) return;

        for (ListenerExecutor each : executors) {
            each.handle(event);
        }
    }

}
