package com.db;

import static org.junit.Assert.assertTrue;

import com.db.common.Vars;
import com.db.log.LogType;
import com.db.log.Logger;
import com.db.log.LoggerFactory;
import com.db.util.SleepUtil;
import com.db.util.TimeUtil;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void t(){
        Logger logger = LoggerFactory.getLogger();
        for (int i = 1; i <= 1000000; i++) {
//            String time = TimeUtil.getTime();
            if(i % 2 == 0){
                logger.info(i + "");
//                System.out.println("[INFO][" + time + "]: " + i);
            }
            else{
                logger.warn(i + "");
//                System.out.println("[WARNING][" + time + "]: " + i);
            }
//            String time = TimeUtil.getTime();
//            System.out.println("[INFO][" + time + "]: " + i);
        }
        SleepUtil.sleep(20000);
    }

    @Test
    public void t2(){
        Vars.movingFiles.put("1", 123L);
        System.out.println(Vars.movingFiles);
        Vars.movingFiles.replace("2", 456L);
        System.out.println(Vars.movingFiles);
    }

    @Test
    public void t3(){
        System.out.println(" " + LogType.WARNING);
    }

}
