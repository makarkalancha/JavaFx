package concurrent;

/**
 * Created by mcalancea
 * Date: 14 Mar 2018
 * Time: 07:59
 */
public interface Command<T> {
    T execute() throws Exception;
}
