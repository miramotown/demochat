package lixang.me.about.demochat.data.entity;

public class ChatMessage {

    private String nameSender;
    private String message;

    public ChatMessage() {
    }

    public ChatMessage(String nameSender, String message) {
        this.nameSender = nameSender;
        this.message = message;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}