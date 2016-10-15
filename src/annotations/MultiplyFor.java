package annotations;

import static annotations.CacheType.FILE;

/**
 * Created by Valentina on 02.10.2016.
 */
public interface MultiplyFor {
    /*
    *Метод, умножающий на заданное число. Кешируется
    *@params d - число, на которое будет умножжаться
    *@return результат умножения
     */
    @Cache(cacheType= FILE)
    int multiply(int d);

    /*
   *@params d - степень
   *@return результат возведения в степень
    */
    int powOfTwo(int d);

    /*
   *@params d - показатель степень
   *@return результат возведения в квадрат числа
    */
    @Cache
    int multiplyCount(int d);
}
