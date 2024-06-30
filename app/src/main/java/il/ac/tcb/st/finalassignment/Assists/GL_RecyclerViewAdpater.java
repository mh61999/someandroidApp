package il.ac.tcb.st.finalassignment.Assists;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import il.ac.tcb.st.finalassignment.R;

public class GL_RecyclerViewAdpater extends RecyclerView.Adapter<GL_RecyclerViewAdpater.MyViewHolder> {
    Context context;
    List<Game> list;
    public  GL_RecyclerViewAdpater(Context context, List<Game> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GL_RecyclerViewAdpater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.content_holder,parent,false);
        return new GL_RecyclerViewAdpater.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GL_RecyclerViewAdpater.MyViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext()).load(list.get(position).thumbnail).into(holder.imageView);
        holder.title.setText(list.get(position).title);
        holder.card.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id",list.get(position).id);
            bundle.putString("title", list.get(position).title);
            bundle.putString("disc",list.get(position).short_description);
            bundle.putString("thumbnail", list.get(position).thumbnail);
            bundle.putString("developer", list.get(position).developer);
            bundle.putString("publisher", list.get(position).publisher);
            bundle.putString("platform",list.get(position).platform);
            bundle.putBoolean("isFromFav",list.get(position).isFromFav);
            Navigation.findNavController(view).navigate(R.id.gameContentFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        CardView card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageHolder);
            title = itemView.findViewById(R.id.gameTitleHolder);
            card = itemView.findViewById(R.id.clickableCardView);
        }
    }
}

