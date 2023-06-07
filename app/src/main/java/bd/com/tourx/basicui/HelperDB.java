package bd.com.tourx.basicui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HelperDB extends SQLiteOpenHelper {
    private  static final String DB_NAME ="travel.db";
    private  static final String TABLE_NAME ="user";
    private  static final int DB_VERSION =1;


    private  static final String TB_NAME ="name";
    private  static final String TB_EMAIL ="email";
    private  static final String TB_PASSWORD ="password";
    private  static final String TB_GENDER ="gender";
    private  static final String TB_ID ="uid";

    private  static final String END=";";

    private  static final String createQuery = "CREATE TABLE "+TABLE_NAME + " ("+TB_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+TB_NAME+" VARCHAR(25) NOT NULL , "+TB_GENDER+" VARCHAR(10) NOT NULL , "+TB_EMAIL +" VARCHAR(25) NOT NULL UNIQUE,"+TB_PASSWORD+" VARCHAR(25) NOT NULL  )"+END;

    private  static final String existQuery = "DROP TABLE " +TABLE_NAME+END;
    private  static final String eventTable = String.format("CREATE TABLE event (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(30) NOT NULL,fee INTEGER NOT NULL,content TEXT NOT NULL , img INTEGER NOT NULL);");


    private Context context;

    public HelperDB( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
try {
    System.out.println(createQuery);
    sqLiteDatabase.execSQL(createQuery);
    sqLiteDatabase.execSQL(eventTable);


    Toast.makeText(context,"Successfully DB setup and Table create",Toast.LENGTH_LONG).show();
}catch (Exception err){
    Toast.makeText(context,"Error: "+err,Toast.LENGTH_LONG).show();
}

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


       try{
           sqLiteDatabase.execSQL(existQuery);
           onCreate(sqLiteDatabase);
           Toast.makeText(context,"Update Successfully DB setup",Toast.LENGTH_LONG).show();

       } catch(Exception err){
            Toast.makeText(context,"Error: "+err,Toast.LENGTH_LONG);
        }
    }




    public  HashMap<String,String> signup(String name,String gender,String email,String password){
        HashMap<String, String> data = new HashMap<String, String>();

        try{
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values =new ContentValues();
            values.put(TB_NAME,name);
            values.put(TB_GENDER,gender);
            values.put(TB_EMAIL,email);
            values.put(TB_PASSWORD,password);
            long id =database.insert(TABLE_NAME,null,values);
            if(id !=-1){
                data.put("name", name);
                data.put("gender", gender);
                data.put("email", email);
                data.put("error", "False");
                Log.d("mirza","inserted data id: " + id);

            }else{
                Log.d("mirza","failed data: " + id);
                data.put("error", "True");
                data.put("msg",String.format("Already have an account using %s!",email));
            }
            database.close();
            return  data;
        }catch (Exception err){
            data.put("error", "True");
            data.put("msg",""+err);
            return data;
        }


    }

    public Boolean insertEvent (String title,int fee,String content){
        int[] imgs=new int[] {R.drawable.sajek, R.drawable.shundorbon,R.drawable.shundorbon,R.drawable.daffodil,R.drawable.sadapathor,R.drawable.banner}; //inline initialization
        Random random = new Random();

try{
    SQLiteDatabase database = this.getWritableDatabase();

    ContentValues
            values =new ContentValues();
    values.put("content",content);
    values.put("fee",fee);
    values.put("title",title);
    values.put("img", imgs[ThreadLocalRandom.current().nextInt(0, 5)]);
    database.insert("event",null,values);
    database.close();
    return true;
}catch (Exception err){
    return  false;
}


    }


    public void createEvent (){
        SQLiteDatabase database = this.getWritableDatabase();

        String content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        Random random = new Random();
        int[] imgs=new int[] {R.drawable.sajek, R.drawable.shundorbon,R.drawable.shundorbon,R.drawable.daffodil,R.drawable.sadapathor,R.drawable.banner}; //inline initialization
        String[] titles=new String[] {"Sajek 3 days 4 nights tour", "Explore shundorband tour", "Jaflong 2 days budget tour","Cox\'s Bazar Honeymoon package","Saint martin coral sea beach"}; //inline initialization
        for(int x=0; x<5;x++){
            ContentValues values =new ContentValues();
            values.put("content",content);
            values.put("fee",random.nextInt(10000));
            values.put("title",titles[x]);
            values.put("img",imgs[x]);
            database.insert("event",null,values);
        }
database.close();
    }




    public Boolean editEvent (int id,String title,int fee,String content){
        Integer[] imgs=new Integer[] {R.drawable.sajek, R.drawable.shundorbon,R.drawable.jaflong,R.drawable.hakaloki,R.drawable.saintmartin}; //inline initialization
        SQLiteDatabase database = this.getWritableDatabase();
        try{
            ContentValues val =new ContentValues();
            val.put("title",title);
            val.put("fee",fee);
            val.put("content",content);
            val.put("img",imgs[0]);

            Log.w("mirzapp","fee:"+fee);
            String strSQL = String.format("UPDATE event SET fee='%s' WHERE id=%s ",title,fee,content);

          database.update("event",val,"id = ?"  , new String[] { String.valueOf(id) });
//database.execSQL(strSQL);
            database.close();

        return true;
    }catch (Exception err){
database.close();
            return  false;
    }


    }


    public Boolean   deleteEvent(int id){

        try{
            SQLiteDatabase database = this.getWritableDatabase();
       database.delete("event",String.format("id=%s",id),null);
            database.close();
            return true;
        }catch (Exception err){
            return  false;
        }


    }



    public HashMap<String,String> login(String email,String password) {
        HashMap<String, String> data = new HashMap<String, String>();

        try {
            SQLiteDatabase database = this.getReadableDatabase();
            String query =String.format("SELECT * FROM %S WHERE %s='%s' and %s='%s';",TABLE_NAME,TB_EMAIL,email,TB_PASSWORD,password);
            //Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + TB_EMAIL + "=" + email + " and  " + TB_EMAIL + "=" + password +""+ END, null);
            Cursor cursor = database.rawQuery(query, null);

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();

                data.put("name", cursor.getString(1));
                data.put("gender", cursor.getString(2));
                data.put("email", cursor.getString(3));
                data.put("error", "False");


            }

return  data;

        } catch (Exception err) {
            data.put("msg",""+err);
            data.put("error", "True");
            return data;
        }

        //return null;

    }








    public HashMap<String,String>  fetchOneData(int id){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursorData= database.rawQuery(String.format("SELECT * FROM event WHERE id='%s'",id),null);

   if(cursorData.getCount() >0){
       HashMap<String,String>   arrayData
               = new HashMap<String,String>();
       cursorData.moveToFirst();
       arrayData.put("title",cursorData.getString(1));
       arrayData.put("fee",cursorData.getString(2));
       arrayData.put("content",cursorData.getString(3));

return arrayData;
   }
        database.close();
        return null;

    }

    public ArrayList<DBModel> fetchAllData(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursorData= database.rawQuery("SELECT * FROM event",null);

        ArrayList<DBModel>  arrayData
                = new ArrayList<>();

        while (cursorData.moveToNext()){
            DBModel model= new DBModel();
            model.id = cursorData.getInt(0);
            model.title = cursorData.getString(1);
            model.fee = cursorData.getInt(2);
            model.content = cursorData.getString(3);
            model.img = cursorData.getInt(4);
            arrayData.add(model);
        }

        database.close();


return arrayData;

    }


}
