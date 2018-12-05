/**
 * Работа над массивом в один поток
 * он же переиспользуется для обработки в несколько потоков
 * @author Gerasimov V. Gerasim
 */

    public class OneThread extends Thread {
        private float [] farr;
        public long runTime = 0; 
        
        OneThread (float [] farr) {
            this.farr = farr;
        }
        
        /**
         * Возвращает время работы потока
         * @return 
         */
        public long getRunTime(){
            return this.runTime;
        }
        
        public void run () {
            System.out.println("Run:"+currentThread().getName());
            this.runTime = 0;
            long t = System.currentTimeMillis();
            try {
                Thread.sleep(0);
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
            utils.compute (farr.length, farr);
            this.runTime = System.currentTimeMillis() - t;
            System.out.printf("Stop: %s, %d ms \n",currentThread().getName(), this.runTime);
        }        
    } 