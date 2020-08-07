package hbys.hrs.other;

public class ComboDoldur
{
    private String metin;
    private String deger;

    public ComboDoldur(String metin, String deger)
    {
        this.metin = metin;
        this.deger = deger;
    }

    @Override
    public String toString()
    {
        return metin;
    }

    public String getKey()
    {
        return metin;
    }

    public String getValue()
    {
        return deger;
    }
}
