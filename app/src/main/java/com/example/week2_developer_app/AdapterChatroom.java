package com.example.week2_developer_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;


public class AdapterChatroom extends RecyclerView.Adapter<AdapterChatroom.ViewHolder> implements OnItemClickListener2, Filterable{

    Context context;
    private ArrayList<Chatroom> chatrooms_filtered = new ArrayList<Chatroom>();
    private ArrayList<Chatroom> chatrooms = new ArrayList<Chatroom>();
    private ArrayList<Chatroom> chatrooms_list = new ArrayList<Chatroom>();
    private static OnItemClickListener2 listener;
    int viewtype = 0;

    public AdapterChatroom(ArrayList<Chatroom> myData){
        this.context = context;
        this.chatrooms_filtered = myData;
        this.chatrooms = myData;
        this.chatrooms_list.addAll(myData);
        this.viewtype = 0;
    }

    public void setViewtype(int type){
        this.viewtype = type;
    }
    public int getViewtype(){
        return this.viewtype;
    }

    @NonNull
    @Override
    public AdapterChatroom.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatroom, parent, false);
        return new ViewHolder(view,this);
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if(charString.isEmpty()) {
                    chatrooms_filtered = chatrooms_list;
                } else {
                    ArrayList<Chatroom> filteringList = new ArrayList<>();
                    for(Chatroom chatroom : chatrooms_list) {

                        if(
                            chatroom.getChat_name().toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteringList.add(chatroom);
                        }
                    }
                    chatrooms_filtered = filteringList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = chatrooms_filtered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                chatrooms.clear();
                chatrooms.addAll((ArrayList<Chatroom>)results.values);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.onBind(chatrooms.get(position), viewtype);
    }

    public void setFriendList(ArrayList<Chatroom> list){
        this.chatrooms = list;
        notifyDataSetChanged();
    }

    public void add(Chatroom chatroom){
        chatrooms.add(chatroom);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chatrooms.size();
    }

    public void setOnItemClicklistener(OnItemClickListener2 listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }
    @Override
    public int getItemViewType(int position){
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView lastchat;
        TextView chat_id;
        TextView chat_name;
        ImageButton imagebtn;
        TextView viewType;
        TextView regdata;

        public ViewHolder(@NonNull View itemView,final OnItemClickListener2 listener) {
            super(itemView);
            chat_id = (TextView) itemView.findViewById(R.id.chat_id);
            viewType = (TextView) itemView.findViewById(R.id.viewType);
            imagebtn = (ImageButton) itemView.findViewById(R.id.imagebtn);
            chat_name = (TextView) itemView.findViewById(R.id.chat_name);
            lastchat = (TextView) itemView.findViewById(R.id.lastchat);
            regdata = (TextView) itemView.findViewById(R.id.regdata);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        //동작 호출 (onItemClick 함수 호출)
                        if(listener != null){
                            listener.onItemClick(ViewHolder.this, v, pos);
                        }
                    }
                }
            });
        }

        void onBind(Chatroom chatroom, int viewtype) {

            if(viewtype == 1) {
                imagebtn.setImageResource(R.drawable.icon_bin);
                viewType.setText(Integer.toString(viewtype));
            }
            chat_id.setText(Integer.toString(chatroom.getChat_id()));
            chat_name.setText(chatroom.getChat_name());
            lastchat.setText(chatroom.getLastchat());
            regdata.setText(parseRegData(chatroom.getRegdata()));

        }

        public String parseRegData(String regdata){
            String year = regdata.substring(0, 4);
            String month = regdata.substring(4, 6);
            String day = regdata.substring(6, 8);
            String hour = regdata.substring(8, 10);
            String minute = regdata.substring(10, 12);
            return month+"-"+day+" "+hour+":"+minute;
        }
    }
    public void setItems(ArrayList<Chatroom> chatrooms){
        this.chatrooms = chatrooms;
    }
    public Chatroom getItem(int position){
        return chatrooms.get(position);
    }
    public void setItem(int position, Chatroom chatroom){
        chatrooms.set(position, chatroom);
    }
}



interface OnItemClickListener2 {
    void onItemClick(AdapterChatroom.ViewHolder holder, View v, int pos);
}