package concurrent;

import javafx.concurrent.Worker;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by mcalancea
 * Date: 14 Mar 2018
 * Time: 07:55
 */
public class WorkerSequencer {
    private Set<Worker> steps = new LinkedHashSet<>();
    private Iterator<Worker> stepsIterator;


    public WorkerSequencer(){
    }

    public void add(Worker worker) {
        steps.add(worker);
    }

    public Worker next() {
        if(stepsIterator.hasNext()){
            Worker worker = stepsIterator.next();
            return worker;
        }
        return null;
    }

    public Worker start(){
        stepsIterator = steps.iterator();
        if(stepsIterator.hasNext()){
            Worker worker = stepsIterator.next();
            return worker;
        }
        return null;
    }
}
