package com.example.e_commerce_store.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_store.R;
import com.example.e_commerce_store.models.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrdersAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }


    public void submitOrdersList(List<Order> orderList){
        this.orderList=orderList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_row,parent,false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
      holder.bindData(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrdersViewHolder extends RecyclerView.ViewHolder{

        TextView  time_txt,date_txt,price_txt,orderId_txt;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId_txt=itemView.findViewById(R.id.order_id);
            price_txt=itemView.findViewById(R.id.price);
            date_txt=itemView.findViewById(R.id.date);
            time_txt=itemView.findViewById(R.id.time);

        }

        public void bindData(Order order) {
            orderId_txt.setText(String.valueOf(order.getId()));
            String price=context.getResources().getString(R.string.price,String.valueOf(order.getTotal_price()));
            price_txt.setText(price);


            String datestr=order.getCreated_at().replace("T"," ");

            Date date= convertStringToDate(datestr);
            String d=convertDateToString(date);
            date_txt.setText(d);

        }

        String convertDateToString(Date date){
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");//
            String sDate="";
            if (date!=null)
                return  sDate=dateFormat.format(date);
             return sDate;
        }
        Date convertStringToDate(String strdate){
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date=null;
            try {
                date=dateFormat.parse(strdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return date;
        }
    }


}
