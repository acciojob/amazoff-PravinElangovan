package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class OrderRepository {
    HashMap<String ,Order>oderItem=new HashMap<>();
    HashMap<String,Integer> deliveryPartner =new HashMap<>();
    HashMap<String, List<String>> deliveryItem=new HashMap<>();
    public void  addOrder( Order order){
        oderItem.put(order.getId(),order);
    }
    public void  addPartner(String partnerId){
        deliveryPartner.put(partnerId,0);
    }
    public void  addOrderPartnerPair(String orderId,String partnerId){
        if(!deliveryItem.containsKey(partnerId))
            deliveryItem.put(partnerId,new ArrayList<>());
        deliveryItem.get(partnerId).add(orderId);
    }
    public Order  getOrderById(String orderId){

        return oderItem.get(orderId);
    }

    public DeliveryPartner getPartnerById( String partnerId){

        int x=deliveryItem.get(partnerId).size();
        DeliveryPartner d=new DeliveryPartner(partnerId);
        d.setNumberOfOrders(x);
        return d;

    }
    public Integer getOrderCountByPartnerId( String partnerId){

        return  deliveryItem.get(partnerId).size();
    }
    public List<String> getOrdersByPartnerId( String partnerId){

        return deliveryItem.get(partnerId);


    }

    public List<String> getAllOrders(){

        List<String> all=new ArrayList<>();
        for(String s:oderItem.keySet())
            all.add(s);
        return all;
    }

    public Integer getCountOfUnassignedOrders(){
        int count=oderItem.size();
        for(Map.Entry <String, List<String>> m:deliveryItem.entrySet()){
            for(String s:m.getValue()){
                if(oderItem.containsKey(s))
                    count--;
            }
        }
        return count;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId( String time, String partnerId){
        int count=0;
        int hour=Integer.valueOf(time.substring(0,2));
        int min=Integer.valueOf(time.substring(3,5));

        int Time= hour*60+min;
        for(String s:deliveryItem.get(partnerId)){
            if(Time<oderItem.get(s).getDeliveryTime())
                count++;
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId( String partnerId){
        int t=0;
        for(String s:deliveryItem.get(partnerId)){
            if(t<oderItem.get(s).getDeliveryTime())
                t=oderItem.get(s).getDeliveryTime();
        }
        int s=t%60;
        int f=t/60;
        return String.valueOf(f)+":"+String.valueOf(s);

    }

    public void deletePartnerById( String partnerId){


        deliveryItem.remove((partnerId));

    }
    public void deleteOrderById( String orderId){

        String id= oderItem.get(orderId).getId();
        oderItem.remove(orderId);
        for(List<String>s:deliveryItem.values()){
            s.remove(String.valueOf(id));
        }

    }
}
