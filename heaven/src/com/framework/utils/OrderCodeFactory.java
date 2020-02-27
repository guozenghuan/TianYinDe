package com.framework.utils;

import java.util.Random;
import java.util.UUID;
 

/**
 * 订单号
 * @author Administrator
 *
 */
public class OrderCodeFactory {
    private static final int[] r = new int[]{7, 9, 6, 2, 8 , 1, 3, 0, 5, 4};
    private static final int maxLength = 14;

    /**
     * 根据id生成订单号
     * @param id
     * @return
     */
    public static String toCode(Long id) {
        String idStr = id.toString();
        StringBuilder idsbs = new StringBuilder();
        for (int i = idStr.length() - 1 ; i >= 0; i--) {
            idsbs.append(r[idStr.charAt(i)-'0']);
        }
        return idsbs.append(getRandom(maxLength - idStr.length())).toString();
    }
     
    public static long getRandom(long n) {
        long min = 1,max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min ;
        return rangeLong;
    }
    
    public static void main(String[] args) {
		int machineId = 1;
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if(hashCodeV < 0){
			hashCodeV = -hashCodeV;
		}
		String number = machineId+String.format("%015d", hashCodeV);
		System.out.println(number);
		
		System.out.println(toCode(3L));
	}
    
}