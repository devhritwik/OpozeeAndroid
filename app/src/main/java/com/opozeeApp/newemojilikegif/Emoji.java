package com.opozeeApp.newemojilikegif;


public class Emoji
{
    private int drawable;

    private String description;

    private Object tag;

    public Emoji(int drawable, String description) {
        this.drawable = drawable;
        this.description = description;
    }

    public Emoji(int drawable, String description, Object tag) {
        this.drawable = drawable;
        this.description = description;
        this.tag = tag;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
