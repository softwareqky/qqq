package project.edge.flowable;

import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.springframework.stereotype.Component;

@Component(value = "flowableEventListener")
public class FlowableEventListener
        implements org.flowable.common.engine.api.delegate.event.FlowableEventListener {

    // @Resource
    // private FlowableSettingService flowableSettingService;

    @Override
    public String getOnTransaction() {
        return null;
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public void onEvent(FlowableEvent event) {
        // TODO
        // List<FlowableSetting> settings = flowableSettingService.list(null, null);
    }

}
