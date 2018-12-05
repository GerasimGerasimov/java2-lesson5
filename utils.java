/*
 * класс утилит
 * содержит фычисляемую в потоках функцию compute
 * 
 */

/**
 *
 * @author Gerasim Gerasimov
 */
public class utils {

    /**
     * compute - вычисляемая функция
     * @param size int - размер массива
     * @param arr  float [] - массив
     */
    public static void compute(int size, float [] arr) {
    for (int i=0; i<size; i ++) {
        arr[i] = (float)(arr[i]*Math.sin(0.2f + i/5) *
                                    Math.cos( 0.2f + i/5 ) *
                                        Math.cos( 0.4f + i/2 ));
    }
}
}

