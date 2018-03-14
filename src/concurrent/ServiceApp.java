package concurrent;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

/**
 * Created by mcalancea
 * Date: 14 Mar 2018
 * Time: 07:50
 */
public class ServiceApp extends Application {
    private final static Logger LOG = LogManager.getLogger(ServiceApp.class);
    private final static int MIN = 3;
    private final static int MAX = 15;

    private Worker<Void> onLoadingCollections;
    private Command<Integer> onGetCountWorker_Task;
    private BiFunction<Integer, Worker, Void> onGetCountWorker_Success;
    private BiFunction<Integer, Worker, Void> onGetCountWorker_Fail;
    private SequenceStep<Integer> job2_onGetCountWorker_refresh;
    private SequenceStep<Integer> job1_onGetCountWorker_getInvoiceAndTransferWorker;

    private Command<Boolean> onGetPageNumberWorker_Task;
    private BiFunction<Boolean, Worker, Void> onGetPageNumberWorker_Success;
    private BiFunction<Boolean, Worker, Void> onGetPageNumberWorker_Fail;
    private SequenceStep<Boolean> job3_onGetPageNumberWorker_refresh;
    private SequenceStep<Boolean> job2_onGetPageNumberWorker_getInvoiceAndTransferWorker;

    private boolean isJob1Finished = false;

    private WorkerSequencer refresh = new WorkerSequencer();
    private WorkerSequencer getInvoiceAndTransferWorker = new WorkerSequencer();
    private ProgressIndicatorForm pForm = new ProgressIndicatorForm();

    public ServiceApp() {
        /////////////////1//////////////////////////////
        this.onLoadingCollections = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        LOG.info("onLoadingCollections started.........");
                        Random random = new Random() ;
                        int randomNumber = random.nextInt(MAX) + MIN;
                        LOG.info("onLoadingCollections randomNumber: " + randomNumber);
                        Thread.sleep(TimeUnit.SECONDS.toMillis(randomNumber));
                        LOG.info("onLoadingCollections ended.........");
                        return null;
                    }
                };
            }
        };

        this.onGetCountWorker_Task = new Command<Integer>() {
            @Override
            public Integer execute() throws Exception {
                LOG.info("onGetCountWorker_Task started.........");
                Random random = new Random() ;
                int randomNumber = random.nextInt(MAX) + MIN;
                LOG.info("onGetCountWorker_Task randomNumber: " + randomNumber);
                Thread.sleep(TimeUnit.SECONDS.toMillis(randomNumber));
                LOG.info("onGetCountWorker_Task ended.........");
                return randomNumber;
            }
        };

        this.onGetCountWorker_Success = new BiFunction<Integer, Worker, Void>() {

            @Override
            public Void apply(Integer value, Worker worker) {
                LOG.info("onGetCountWorker_Success: " + value);
                pForm.close();
                if(value > 10) {
                    LOG.info("onGetCountWorker_Success: next worker");
                    startService(worker, null);
                }else {
                    LOG.info("onGetCountWorker_Success: stop, is not ( > 10)");
                }
                return null;
            }
        };

        this.onGetCountWorker_Fail = new BiFunction<Integer, Worker, Void>() {
            @Override
            public Void apply(Integer value, Worker worker) {
                LOG.info("onGetCountWorker_Fail");
                pForm.close();
                return null;
            }
        };
        job2_onGetCountWorker_refresh = new SequenceStep<>(onGetCountWorker_Task, onGetCountWorker_Fail, onGetCountWorker_Success, refresh);
        job1_onGetCountWorker_getInvoiceAndTransferWorker = new SequenceStep<>(onGetCountWorker_Task, onGetCountWorker_Fail, onGetCountWorker_Success, getInvoiceAndTransferWorker);
        /////////////////2//////////////////////////////
        this.onGetPageNumberWorker_Task = new Command<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
                LOG.info("onGetPageNumberWorker_Task started.........");
                Random random = new Random() ;
                int randomNumber = random.nextInt(MAX) + MIN;
                LOG.info("onGetPageNumberWorker_Task randomNumber: " + randomNumber);
                Thread.sleep(TimeUnit.SECONDS.toMillis(randomNumber));
                LOG.info("onGetPageNumberWorker_Task ended.........");
                return randomNumber > 10;
            }
        };

        this.onGetPageNumberWorker_Success = new BiFunction<Boolean, Worker, Void>() {
            @Override
            public Void apply(Boolean value, Worker worker) {
                LOG.info("onGetPageNumberWorker_Success: " + value);
                pForm.close();
                if(value){
                    LOG.info("onGetPageNumberWorker_Success: next worker");
                    startService(worker, null);
                }else {
                    LOG.info("onGetPageNumberWorker_Success: stop, value is FALSE");
                }
                return null;
            }
        };

        this.onGetPageNumberWorker_Fail = new BiFunction<Boolean, Worker, Void>() {
            @Override
            public Void apply(Boolean value, Worker worker) {
                LOG.info("onGetPageNumberWorker_Fail");
                pForm.close();
                return null;
            }
        };
        job3_onGetPageNumberWorker_refresh = new SequenceStep<>(onGetPageNumberWorker_Task, onGetPageNumberWorker_Fail, onGetPageNumberWorker_Success, refresh);
        job2_onGetPageNumberWorker_getInvoiceAndTransferWorker = new SequenceStep<>(onGetPageNumberWorker_Task, onGetPageNumberWorker_Fail, onGetPageNumberWorker_Success, getInvoiceAndTransferWorker);
    }

    public <V> void startService(Worker<V> worker, ActionEvent event){
        pForm.activateProgressBar(worker);
        LOG.debug(">>>>>>>>pForm.getDialogStage().show()");
        pForm.show();

        Service<V> service = ((Service<V>)worker);
        if(service != null){
            service.restart();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        refresh.add(onLoadingCollections);
        refresh.add(job2_onGetCountWorker_refresh);
        refresh.add(job3_onGetPageNumberWorker_refresh);

        getInvoiceAndTransferWorker.add(job2_onGetPageNumberWorker_getInvoiceAndTransferWorker);
        getInvoiceAndTransferWorker.add(job1_onGetCountWorker_getInvoiceAndTransferWorker);

        ((Service<Void>) onLoadingCollections).setOnSucceeded(event -> {
            LOG.debug(">>>>>>>>onLoadingCollections.setOnSucceeded()");
            pForm.close();
            startService(refresh.next(), null);
        });
        ((Service<Void>) onLoadingCollections).setOnFailed(event -> {
            LOG.debug(">>>>>>>>onLoadingCollections.setOnFailed()");
            pForm.close();
        });

        primaryStage.setTitle("HBox Experiment 1");

        Button button1 = new Button("REFRESH");
        button1.setOnAction(event -> {
            LOG.info("button1");
            startService(refresh.start(), null);
        });

        Button button2 = new Button("Invoice And Transfer");
        button2.setOnAction(event -> {
            LOG.info("button2");
            startService(getInvoiceAndTransferWorker.start(), null);
        });

        VBox vBox = new VBox();
        vBox.setSpacing(5d);
        vBox.getChildren().addAll(button1, button2);

        Scene scene = new Scene(vBox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}