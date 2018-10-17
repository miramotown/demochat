package lixang.me.about.demochat.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lixang.me.about.demochat.R;
import lixang.me.about.demochat.data.entity.ChatMessage;

public class ChatMessage_RecyclerViewAdapter extends RecyclerView.Adapter<ChatMessage_RecyclerViewAdapter.ChatViewHolder>{
    List<ChatMessage> chats;

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        CardView cvChat;
        TextView nombre;
        TextView mensaje;

        ChatViewHolder(View itemView) {
            super(itemView);
            cvChat = itemView.findViewById(R.id.cvChat);
            nombre = itemView.findViewById(R.id.nombre);
            mensaje = itemView.findViewById(R.id.mensaje);
        }
    }

    public ChatMessage_RecyclerViewAdapter(){
        this.chats = new ArrayList<ChatMessage>();
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_chatmessage, viewGroup, false);
        ChatViewHolder pvh = new ChatViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder personViewHolder, int i) {
        personViewHolder.nombre.setText(chats.get(i).getNameSender());
        personViewHolder.mensaje.setText(chats.get(i).getMessage());
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void Add(ChatMessage item){
        this.chats.add(item);
        notifyDataSetChanged();
    }
}
