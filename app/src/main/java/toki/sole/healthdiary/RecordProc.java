package toki.sole.healthdiary;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

//types->"type-unit"
public class RecordProc {
    private ArrayList<String> types;
    public RecordProc(){
        types = new ArrayList<>();
        types.add("血壓-mmHg");
        types.add("身高-cm");
        types.add("體重-kg");
    }

    public void setTypes(ArrayList<String> types){
        this.types = types;
    }

    public ArrayList<String> getTypes(){
        return types;
    }

    public boolean removeType(String type){
        return this.types.remove(type);
    }

    public void addType(String type,String unit){
        if(types.contains(type+"-"+unit)){
            return;
        }else{
            this.types.add(type+"-"+unit);
        }
    }
    public void saveType(Context context){
        String filename = "Types.txt";
        File file = new File(context.getFilesDir(),filename);

        try(FileOutputStream fos = context.openFileOutput(filename,Context.MODE_PRIVATE)){
            for(String content:this.types){
                content=content+"\n";
                fos.write(content.getBytes());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void updateTypes(Context context) throws FileNotFoundException {
        String filename = "Types.txt";
        FileInputStream fis = context.openFileInput(filename);
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(inputStreamReader)){
            String line = reader.readLine();
            while(line!=null){
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        String contents = stringBuilder.toString();
        //Log.d("TEST",contents);
        String[] tp =  contents.split("\n");
        for (String s : tp) {
            if (!types.contains(s)) {
                types.add(s);
            }
        }
    }


    //time format = yyyy-MM-dd-HH-mm-ss
    public void saveRecord(Context context,String type,double value,String time){
        String filename = time.substring(0,6)+"Record.txt";

        //need to adjust
        //read all records in and re-order than write
        String records = readAllRecordsOfMonth(context,time);
        records = String.format(Locale.CHINESE,"%s%s-%s-%.2f\n",records,time,type,value);

        File file = new File(context.getFilesDir(),filename);
        try(FileOutputStream fos = context.openFileOutput(filename,Context.MODE_PRIVATE)){
            for(String content:sortRecords(records.split("\n"))){
                content=content+"\n";
                fos.write(content.getBytes());
            }
            //fos.write("".getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public String readAllRecordsOfMonth(Context context,String time){
        String filename = time.substring(0,6)+"Record.txt";
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(filename);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(inputStreamReader)){
                String line = reader.readLine();
                while(line!=null){
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
            //Log.d("TEST",contents);
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String[] sortRecords(String[] records){
        Arrays.sort(records, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                for(int i=0;i<19;i++){
                    if(o1.charAt(i)>o2.charAt(i)){
                        return 1;
                    }
                }
                return 0;
            }
        });
        return records;
    }

    public void editRecord(){

    }


    /*
    public String readRecordsByDate(String date,String type){

    }
    public String readRecordsByMonth(String month,String type){

    }
    public String readRecordsByYear(String year,String type){

    }
    */
}
