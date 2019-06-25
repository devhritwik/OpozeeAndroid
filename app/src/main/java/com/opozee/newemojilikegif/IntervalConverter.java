package com.opozee.newemojilikegif;


public class IntervalConverter
{
    private float x, a, b;

    private IntervalConverter(float x)
    {
        this.x = x;
    }

    public static IntervalConverter convertNumber(float x)
    {
        return new IntervalConverter(x);
    }

    public IntervalConverter fromInterval(float a, float b)
    {
        this.a=a;
        this.b=b;
        return this;
    }

    public float toInterval(float c, float d)
    {
        return c+((d-c)/(b-a))*(x-a);
    }
}
