package com.vm.test.task.demo;

import com.vm.test.oo.LevelOne;
import com.vm.test.oo.LevelTwo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestControllerApplication {

    public static void main(String[] args) {

        double k = 10;
        SpringApplication.run(TestControllerApplication.class, args);
       for (int i = 0; i <  40; i++) {
           try {
               Thread.sleep(30 * 1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           new Thread(()->new LevelOne().run()).start();

           new Thread(()->new LevelTwo().run()).start();

           if (true) {
               return;
           }

           int d = 0;
           int p = 1;
           p -= 1 + 2;
           if (true) {
               try {
                   throw new Exception("new exeption");
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
           if (true) {
               try {
                   throw new Exception("new exp 3");
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           if (true) {
               try {
                   throw new Exception("new exp 4");
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           if (true) {
               try {
                   throw new Exception("new exp 5");
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           if (true) {
               try {
                   throw new Exception("new exc " + String.valueOf(Math.random()));
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           if (true) {
               try {
                   throw new Exception("new exc 7");
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           if (true) {
               try {
                   throw new Exception("new exc 8");
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           if (true) {
               try {
                   throw new Exception("new exp 99");
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           try {
               if (p < 2){
                   int [] arr =  new int[2];
                   arr[2] = 2;
               }
           } catch (Exception e) {
               System.out.println(e.getMessage());
           }

           int r2 = (p / i) * 0;
           int r = p / i;

       }
    }
}
