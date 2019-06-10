package com.tradehistoryaccess.BrokerService.History;

public class IdGenerator {
    private final long startTime=0L;
    private final int timeBits=15;
    private final int flagBits=2;
    private final int productbits=2;

    private long lastTime=-1L;

    private int flag=0;

    public String getId(String productId){
        long currentTime=System.currentTimeMillis();
        if(lastTime==currentTime){
            flag=(flag+1)%100;
        }
        else {
            flag=0;
            lastTime=currentTime;
        }
        String flagStr=String.format("%0"+flagBits+"d",flag);
        String timeStr=String.format("%0"+timeBits+"d",currentTime-startTime);
        return productId+timeStr+flagStr;
    }

}
