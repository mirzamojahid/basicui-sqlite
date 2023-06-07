package bd.com.tourx.basicui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<DBModel> arrayList;
    private LayoutInflater layoutInflater;

    CustomAdapter(Context context,ArrayList<DBModel> arrayList){
        this.context =context;
        this.arrayList = arrayList;
    }




    @Override
    public int getCount() {
        return arrayList.size();
        //return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

          if(convertView ==null)
          {
              layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

          convertView=    layoutInflater.inflate(R.layout.list_fetch_data,parent,false);


          }


        TextView title=  convertView.findViewById(R.id.event_title);
        TextView fee=  convertView.findViewById(R.id.event_fee);
        TextView event_id=  convertView.findViewById(R.id.event_id);
        ImageView imageView = convertView.findViewById(R.id.event_imgs);
        imageView.setImageResource(arrayList.get(position).img);
        title.setText(""+arrayList.get(position).title);
        event_id.setText(""+arrayList.get(position).id);
        fee.setText("BDT "+arrayList.get(position).fee +" / Per Person");
        return convertView;
    }
}
