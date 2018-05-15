package com.example.express_delivery;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tv_location,tv_deliveryNum;
        ImageView deliveryStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            tv_location = itemView.findViewById(R.id.location);
            tv_deliveryNum = itemView.findViewById(R.id.delivery_num);
            deliveryStatus = itemView.findViewById(R.id.deliveryItem_status);
        }
    }

    @NonNull
    @Override
    public DeliveryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.delivery_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = null;
                String loc = Utility.location(position);
                List<Delivery> deliveries = DataSupport.where("location = ?",loc).find(Delivery.class);
                if (deliveries != null && deliveries.size() == 1){
                    intent = new Intent(mContext,ShowDeliveryItemActivity.class);
                    intent.putExtra(ShowDeliveryItemActivity.DELIVERY_LOC,position);
                    mContext.startActivity(intent);
                }else if (deliveries.size() > 1){
                    Toast.makeText(mContext, loc + "数据错误，重新添加", Toast.LENGTH_SHORT).show();
                    DataSupport.deleteAll(Delivery.class,"location = ?",loc);
                }else if (deliveries == null || deliveries.size() == 0){
                    intent = new Intent(mContext,addDeliveryActivity.class);
                    intent.putExtra(ShowDeliveryItemActivity.DELIVERY_LOC,position);
                    mContext.startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryAdapter.ViewHolder holder, int position) {
        String loc = Utility.location(position);
        holder.tv_location.setText(Utility.location_string(position));
        List<Delivery> deliveries = DataSupport.where("location = ?",loc).find(Delivery.class);
        if (deliveries != null && deliveries.size() == 1){
            holder.deliveryStatus.setImageResource(R.drawable.packet_close);
            Delivery delivery = deliveries.get(0);
            holder.tv_deliveryNum.setText(delivery.getdeliveryNum());
        }else if (deliveries.size() > 1){
            Toast.makeText(mContext, loc + "数据错误，重新添加", Toast.LENGTH_SHORT).show();
            DataSupport.deleteAll(Delivery.class,"location = ?",loc);
        }else if (deliveries == null || deliveries.size() == 0){
            holder.deliveryStatus.setImageResource(R.drawable.packet_open);
            holder.tv_deliveryNum.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
