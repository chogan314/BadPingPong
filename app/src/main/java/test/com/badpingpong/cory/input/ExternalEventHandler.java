package test.com.badpingpong.cory.input;

import java.util.ArrayList;
import java.util.List;

import test.com.badpingpong.cory.input.Input.ExternalEvent;
import test.com.badpingpong.cory.util.Pool;
import test.com.badpingpong.cory.util.Pool.PoolObjectFactory;

/**
 * Created by Cory on 4/17/2017.
 */

public class ExternalEventHandler {
    public interface ExternalEventSource {
        void registerListener(ExternalEventHandler handler);
    }

    private final Pool<ExternalEvent> externalEventPool;
    private final List<ExternalEvent> externalEvents = new ArrayList<>();
    private final List<ExternalEvent> externalEventsBuffer = new ArrayList<>();

    public ExternalEventHandler() {
        PoolObjectFactory<ExternalEvent> factory = new PoolObjectFactory<ExternalEvent>() {
            @Override
            public ExternalEvent createObject() {
                return new ExternalEvent();
            }
        };

        externalEventPool = new Pool<>(factory, 100);
    }

    public ExternalEvent createEvent(String tag) {
        ExternalEvent event = externalEventPool.newObject();
        event.tag = tag;
        return event;
    }

    public void queueEvent(ExternalEvent event) {
        synchronized (this) {
            externalEventsBuffer.add(event);
        }
    }

    public List<ExternalEvent> getExternalEvents() {
        synchronized (this) {
            int len = externalEvents.size();
            for (int i = 0; i < len; i++) {
                externalEventPool.free(externalEvents.get(i));
            }
            externalEvents.clear();
            externalEvents.addAll(externalEventsBuffer);
            externalEventsBuffer.clear();
            return externalEvents;
        }
    }
}
