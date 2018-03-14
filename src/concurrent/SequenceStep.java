package concurrent;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by mcalancea
 * Date: 14 Mar 2018
 * Time: 07:57
 */
public class SequenceStep<V> extends Service<V>{
    private final static Logger LOG = LogManager.getLogger(SequenceStep.class);

    private final Command<V> task;
    private final BiFunction<V, Worker, Void> failed;
    private final BiFunction<V, Worker, Void> succeeded;
    private final WorkerSequencer workerSequencer;

    public SequenceStep(Command<V> task, BiFunction<V, Worker, Void> failed, BiFunction<V, Worker, Void> succeeded, WorkerSequencer workerSequencer) {
        this.task = task;
        this.failed = failed;
        this.succeeded = succeeded;
        this.workerSequencer = workerSequencer;
    }

    @Override
    protected Task<V> createTask() {
        return new Task<V>() {
            @Override
            protected V call() throws Exception {
                return task.execute();
            }
        };
    }

    @Override
    protected void failed() {
        super.failed();
        try {
            failed.apply(getValue(), workerSequencer.next());
        }catch (Exception e){
            LOG.error("failed", e);
        }
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        try {
            succeeded.apply(getValue(), workerSequencer.next());
        }catch (Exception e){
            LOG.error("succeeded", e);
        }
    }
}
