package lixang.me.about.demochat.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import lixang.me.about.demochat.R;
import lixang.me.about.demochat.adapters.ChatMessage_RecyclerViewAdapter;
import lixang.me.about.demochat.data.entity.ChatMessage;

import static lixang.me.about.demochat.util.ParameterApplication.URL_CONNECTION;

public class ChatActivity extends AppCompatActivity {

    private WebSocketClient mWebSocketClient;
    private Button btnEnviar;
    private RecyclerView rvChat;
    private EditText txtNombre;
    private EditText txtMensaje;
    private Context ctx;
    private ChatMessage_RecyclerViewAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        BindUI();

        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        chatAdapter = new ChatMessage_RecyclerViewAdapter();
        rvChat.setLayoutManager(llm);
        rvChat.setHasFixedSize(true);
        rvChat.setAdapter(chatAdapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString(),
                        mensaje = txtMensaje.getText().toString();
                if(!nombre.isEmpty()){
                    if(!mensaje.isEmpty()){
                        try {
                            ChatMessage nuevomensaje = new ChatMessage(nombre, mensaje);
                            mWebSocketClient.send(new Gson().toJson(nuevomensaje));
                            txtMensaje.setText("");
                        }catch (Exception e){
                            Toast.makeText(ChatActivity.this,"Error al enviar mensaje",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else txtMensaje.setError("Ingrese un mensaje");
                }else txtNombre.setError("Ingrese un nombre");

            }
        });
        connectWebSocket();
    }

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://"+URL_CONNECTION);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ChatMessage nuevomensaje = new Gson().fromJson(message, ChatMessage.class);
                        chatAdapter.Add(nuevomensaje);

                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    private void BindUI(){
        btnEnviar = findViewById(R.id.btn_sendmessage_chat);
        rvChat = findViewById(R.id.rv_listchat_chat);
        txtNombre = findViewById(R.id.et_namesend_chat);
        txtMensaje = findViewById(R.id.et_message_chat);
        ctx = this;
    }
}
