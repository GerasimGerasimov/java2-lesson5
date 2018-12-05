/*
 * Создаю Два потока:
*   OneThread   - он обрабатывает массив в одиночку
*   TwoThreads  - он нарезает задачу на 2 потока (переиспользует OneThread ),
*                 запускает их в работу, ждёт завершения выполнения обоих внутренних потоков
*                 и обобщает результат.
 */

/**
 *
 * @author Gerasimov Gerasim
 */
public class MainClass {
    //данные для вычислений
    private final int size = 10000000;
    private float [] farr = new float [size];
     
    /**
     * Работа над массивом в два потока
     */
    static class TwoThreads extends Thread {
        private float [] farr;
        private int halfSize;
        public long runTime = 0;//счётчик времени
        private OneThread oneThread1;//поток 1
        private OneThread oneThread2;//поток 2
        //экспериментирую со временем обработки в двух потоках
        //разделяя массив в разных пропорциях
        private int h1 = 0;
        private int h2 = 0;
        
        TwoThreads (float [] farr) {
            this.farr = farr;
            this.halfSize = this.farr.length / 2;
            //для того чтобы убедится что ожидание завершения потоков работает,
            //сделал сильно разный размер массивов для потоков (чтобы время работы было сильно разным)
            //и нашёл что для двух нитей isAlive работает не корректно, что исправил
            //применяя join()
            this.h1 = 4000000;
            this.h2 = this.farr.length - this.h1;
        }
                
        public void run () {
            //0) засекаю время
            this.runTime = 0;
            long t = System.currentTimeMillis();
            //1) разбиваю исходный массив на 2 части
            float [] a1 = new float[this.h1];//this.halfSize];
            System.arraycopy(this.farr, 0, a1, 0, this.h1/*this.halfSize*/);
            float [] a2 = new float[this.h2];//this.halfSize];
            System.arraycopy(this.farr, this.h1/*this.halfSize*/, a2, 0, this.h2/*this.halfSize*/);
            //2) создание потоков
            oneThread1 = new OneThread (a1);
            oneThread2 = new OneThread (a2);
            //3) запуск потоков
            oneThread1.start();
            oneThread2.start();
            //4) жду пока оба потока отработают
            try {
                System.out.println("Ожидание завершения потоков.");
		oneThread1.join();
		oneThread2.join();	
            } catch ( InterruptedException e ) {
		System.out.println("Главный поток прерван");
            }
            //5) объединение массивов
            System.arraycopy(a1,0,this.farr,0, this.h1/*this.halfSize*/);
            System.arraycopy(a2,0,this.farr, this.h1/*this.halfSize*/, this.h2/*this.halfSize*/);
            //6) Фиксирую время
            this.runTime = System.currentTimeMillis() - t;
            System.out.println("Two threads run time:" + this.runTime);
        }        
    }
        
    
    public static void main ( String [] args ) throws InterruptedException {
        MainClass mc = new MainClass();
        //----------------------------------------------------------------------
        System.out.println("Один поток >");
        //Рассчёт массивов в Один поток
        OneThread oneThread = new OneThread (mc.farr);
        oneThread.start();
        while (oneThread.isAlive()){}
        System.out.printf("One Thread run time: %d \n", oneThread.getRunTime());
        //----------------------------------------------------------------------
        //Рассчёт массивов в Два потока
        System.out.println("Два потока >");
        TwoThreads twoThreads = new TwoThreads (mc.farr);
        twoThreads.start();
    }  

}
