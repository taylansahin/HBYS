package hbys.hrs.main;

import hbys.hrs.yonetici.GirisYap;

import javax.swing.*;
import java.awt.*;
public class Main
{
    public static void main(String[] args)
    {
        GirisYap giris_yap_pencere = new GirisYap();
        giris_yap_pencere.setVisible(true);
        ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
        giris_yap_pencere.setIconImage(image.getImage());
    }
}
