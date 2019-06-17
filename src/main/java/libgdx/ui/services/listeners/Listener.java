package libgdx.ui.services.listeners;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import libgdx.controls.ScreenRunnable;
import libgdx.ui.screens.AbstractScreen;

public abstract class Listener<TScreen extends AbstractScreen> implements ListenerAll {

    public static final int DEFAULT_QUERY_PERIOD_MILLIS = 2000;

    private ListenerConfig[] configs;
    private int queryPeriodMillis = DEFAULT_QUERY_PERIOD_MILLIS;
    private TScreen screen;
    private ScheduledExecutorService executorService;

    public Listener(TScreen screen, ListenerConfig... configs) {
        this.screen = screen;
        this.configs = configs;
    }

    @Override
    public void start() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new ScreenRunnable(screen) {
            @Override
            public synchronized void executeOperations() {
                execute();
            }

            @Override
            public void executeOperationsAfterScreenChanged() {
                executorService.shutdown();
            }
        }, 0, queryPeriodMillis, TimeUnit.MILLISECONDS);
    }

    public void setQueryPeriodMillis(int queryPeriodMillis) {
        this.queryPeriodMillis = queryPeriodMillis;
    }

    public int getQueryPeriodMillis() {
        return queryPeriodMillis;
    }

    public ListenerConfig[] getConfigs() {
        return configs;
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    protected abstract void execute();

    public TScreen getScreen() {
        return screen;
    }
}
