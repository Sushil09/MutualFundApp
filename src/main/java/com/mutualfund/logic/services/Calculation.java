package com.mutualfund.logic.services;

import com.mutualfund.logic.model.Datanumbers;
import com.mutualfund.logic.model.WholeData;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.format;

public class Calculation {
    static Map<String,Double> valuesDatabase;//to map nav values to date

    public static void callRestService(String schemaNumber,int period,int horizon) throws Exception{
        String url="https://api.mfapi.in/mf/"+schemaNumber; //api hit url

        RestTemplate restTemplate=new RestTemplate();
        WholeData wholeData =restTemplate.getForObject(url, WholeData.class);
        fillDatatoMap(wholeData);// to call fill method of map

        //viewDatabase();// to view stored value

        showReturns(wholeData,period,horizon);
    }

    private static void showReturns(WholeData wholeData, int period, int horizon) throws Exception{
        String endDateString= wholeData.getData().get(0).getDate();
        Date endDate=convertToDate(endDateString);

        //increating endDay day by 1
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE,1);

        Date endDatePlusOne = c.getTime();

        c.add(Calendar.YEAR,-horizon);
        Date startDate = c.getTime();

        Date endModule = c.getTime();
        c.setTime(endDatePlusOne);
        c.add(Calendar.YEAR,-period);
        Date startModule = c.getTime();

        //iterating over date ranges
        Calendar startCalender = Calendar.getInstance();
        startCalender.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        System.out.println("Months       Returns               Calculations");

        for(; startCalender.compareTo(endCalendar)<=0; startCalender.add(Calendar.MONTH, 1))
            printValues(c,startCalender,period);

        printLastMonthValue(endDatePlusOne,endDate,startModule,endDateString);

    }

    private static void printValues(Calendar c,Calendar startCalender,int period) throws Exception{
        Date endModule = startCalender.getTime();
        c.setTime(endModule);
        c.add(Calendar.YEAR,-period);
        Date startModule = c.getTime();

        String endModulDate = convertToString(endModule);
        String startModulDate = convertToString(startModule);

        Double startValue=fetchValue(startModulDate);
        Double endValue=fetchValue(endModulDate);

        String returns=getReturns(endModule,startModule,startValue,endValue);

        String thirdColumnStartModule = new SimpleDateFormat("dd-MMM-yy").format(startModule);
        String thirdColumnEndModule = new SimpleDateFormat("dd-MMM-yy").format(endModule);

        String firstColumnEndModule = new SimpleDateFormat("MMM-yy").format(endModule);

        System.out.println(firstColumnEndModule+"       " + returns+"%                  "+thirdColumnStartModule);
        System.out.println("                                    "+thirdColumnEndModule);

        System.out.println();
    }

    private static String getReturns(Date end,Date start,Double startModule, Double endModule) {
        int yearDiff=end.getYear()-start.getYear();

        //to prevent divide by zero error
        if(startModule==0.0)
            startModule=1.0;

        double ans=Math.pow((endModule/startModule),(double)1/yearDiff)-1;
        ans*=100;
        return String.valueOf(format("%.2f",ans));
    }

    private static void printLastMonthValue(Date endDatePlusOne,Date endDate,Date startDate,String endDateString) throws Exception{
        String firstColumnEndModule = new SimpleDateFormat("MMM-yy").format(endDatePlusOne);
        String returns=getReturns(endDate,startDate,fetchValue(convertToString(startDate)),fetchValue(endDateString));
        String thirdColumnStartModule = new SimpleDateFormat("dd-MMM-yy").format(startDate);
        String thirdColumnEndModule = new SimpleDateFormat("dd-MMM-yy").format(endDatePlusOne);
        System.out.println(firstColumnEndModule+"       " + returns+"%                  "+thirdColumnStartModule);
        System.out.println("                                    "+thirdColumnEndModule);
    }
    private static double fetchValue(String date) throws Exception{
        // if value exist
        if(valuesDatabase.get(date)!=null)
            return valuesDatabase.get(date);
            //when value does not exist
        else{
            String nextDate=convertToString(decrementDayByOne(convertToDate(date)));
            if(valuesDatabase.get(nextDate)==null){
                String nextTwoDate=convertToString(decrementDayByOne(convertToDate(nextDate)));
                Double value;
                if(valuesDatabase.get(nextTwoDate)==null)
                    value=0.0;
                else
                value=valuesDatabase.get(convertToString(decrementDayByOne(convertToDate(nextTwoDate))));
                return value==null?0.0:value;
            }
            else
                return valuesDatabase.get(nextDate)==null?0.0:valuesDatabase.get(nextDate);
        }
    }

    //Storing values in map
    private static void fillDatatoMap(WholeData wholeData) {
        valuesDatabase=new HashMap<>();

        for(Datanumbers datanumbers : wholeData.getData()){
            String date= datanumbers.getDate().trim();
            Double value=Double.parseDouble(datanumbers.getNav().trim());
            valuesDatabase.put(date,value);
        }
    }

    //viewing our data mapping
    private static void viewDatabase() {
        for(Map.Entry<String,Double> hm:valuesDatabase.entrySet()){
            System.out.println(hm.getKey()+" "+hm.getValue());
        }
    }

    private static Date convertToDate(String date) throws Exception{
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }

    private static String convertToString(Date date){
        //to get date in string to fetch from Map
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    //decrement date by one
    private static Date decrementDayByOne(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,-1);
        return c.getTime();
    }
}
