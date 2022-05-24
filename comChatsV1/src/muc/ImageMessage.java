package muc;

import javafx.scene.image.Image;

public class ImageMessage extends Message{
    private Image img;

    public ImageMessage(Image img, Chat chat, User src, Type type) {
        super(chat, src, type);
        setImg(img);
    }

    public Image getImg() {
        return img;
    }

    private void setImg(Image img) {
        this.img = img;
    }
}