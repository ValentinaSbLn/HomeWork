package threads2.task;

/**
 * Created by Valentina on 28.10.2016.
 */
public class ResultNotCompletedException extends RuntimeException {
    private static final long serialVersionUID = -258641449658775105L;
    ResultNotCompletedException(String massage){
        super(massage);
    }
}
