package annotations;

/**
 * Created by Valentina on 02.10.2016.
 */
public interface MultiplyFor {
    /*
    *Метод, умножающий на заданное число. Кешируется
    *@params d - число, на которое будет умножжаться
    *@return результат умножения
     */
    @Cache
    public int multiply(int d);

    /*
   *@params d - степень
   *@return результат возведения в степень
    */
    public int powOfTwo(int d);
}
