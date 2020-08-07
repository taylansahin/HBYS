package hbys.hrs.hasta;

import hbys.hrs.database.DatabaseConnection;
import hbys.hrs.dto.DTO;
import hbys.hrs.other.ComboDoldur;
import hbys.hrs.yonetici.YoneticiMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DuzenleHstRandevu extends JFrame
{
    DTO dto = new DTO();
    private JButton btn_listele = null;
    private JButton btn_geri = null;
    private JButton btn_sil = null;
    private JButton btn_guncelle = null;

    private JTextField textbox_tc = new JTextField(11);
    private JTextField textbox_ad = new JTextField(30);
    private JTextField textbox_soyad = new JTextField(30);

    private JComboBox cmb_trh = null;
    private JComboBox cmb_saat = null;

    static boolean dogru_tc = false;

    static String dr_ad = "";
    static String dr_soyad = "";
    static String hst_tc = "";
    static String hst_ad = "";
    static String hst_soyad = "";
    static String pol_id = "";
    static String pol_ad = "";
    static String dr_tc = "";
    static String rndv_trh = "";
    static String rndv_saat = "";

    public Object item = null;
    public String deger = "";


    public DuzenleHstRandevu()
    {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout);

        setTitle("Randevu Düzenle");
        setSize(390, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel tc_yazi = new JLabel("TC Kimlik No   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=0;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(tc_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=0;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_tc,gc);

        JLabel ad_yazi = new JLabel("Ad");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=1;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(ad_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=1;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_ad,gc);
        textbox_ad.setEditable(false);

        JLabel soyad_yazi = new JLabel("Soyad");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=2;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(soyad_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=2;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_soyad,gc);
        textbox_soyad.setEditable(false);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=10;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(geri_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=10;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(listele_buton(),gc);
        btn_listele.setEnabled(false);

        textbox_tc.addKeyListener(new java.awt.event.KeyAdapter()    //ENTER BASILDIĞINI ANLAYAN KOD
        {
            public void keyPressed(java.awt.event.KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    String tc_temp = textbox_tc.getText();

                    if (Long.parseLong(tc_temp) < 10000000000L || Long.parseLong(tc_temp) > 99999999999L)
                    {
                        JOptionPane.showMessageDialog(null, "Lütfen Geçerli TC Kimlik Numarası Giriniz!" , "Randevu Düzenle", 0);
                    }
                    else
                    {
                        String sql = "SELECT HRS_HST_AD, HRS_HST_SOYAD FROM HRS_HASTA_KIMLIK_BILGI WHERE HRS_HST_TC_KIMLIK_NO =" + "'"+tc_temp+"'";

                        DatabaseConnection db_hasta_ara = new DatabaseConnection();

                        ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

                        try
                        {
                            if (gelen_veriler.next())
                            {
                                gelen_veriler.beforeFirst();
                                while(gelen_veriler.next())
                                {
                                    hst_tc = tc_temp;
                                    textbox_ad.setText(gelen_veriler.getString("HRS_HST_AD"));
                                    hst_ad = gelen_veriler.getString("HRS_HST_AD");
                                    textbox_soyad.setText(gelen_veriler.getString("HRS_HST_SOYAD"));
                                    hst_soyad = gelen_veriler.getString("HRS_HST_SOYAD");
                                    textbox_tc.setEditable(false);
                                    dogru_tc = true;
                                    btn_listele.setEnabled(true);
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "TC Kimlik Numarası Sistemde Kayıtlı Değildir!" , "Randevu Düzenle", 0);
                            }

                        }
                        catch (Exception x)
                        {
                            // TODO: handle exception
                            x.printStackTrace();
                        }
                    }


                }
            }
        });
    }

    public DuzenleHstRandevu(boolean ikinci)
    {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout);

        setTitle("Randevu Düzenle");
        setSize(390, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel tc_yazi = new JLabel("TC Kimlik No   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=0;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(tc_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=0;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_tc,gc);
        textbox_tc.setText(hst_tc);

        JLabel ad_yazi = new JLabel("Ad");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=1;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(ad_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=1;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_ad,gc);
        textbox_ad.setEditable(false);
        textbox_ad.setText(hst_ad);

        JLabel soyad_yazi = new JLabel("Soyad");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=2;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(soyad_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=2;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_soyad,gc);
        textbox_soyad.setEditable(false);
        textbox_soyad.setText(hst_soyad);

        JLabel pol_yazi = new JLabel("Poliklinik   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(pol_yazi,gc);

        JLabel pol_yazi1 = new JLabel(pol_ad);
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(pol_yazi1,gc);

        JLabel dr_yazi = new JLabel("Doktor   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(dr_yazi,gc);

        JLabel dr_yazi1 = new JLabel(dr_ad+" "+dr_soyad);
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(dr_yazi1,gc);

        JLabel eski_tarih_yazi = new JLabel("Eski Tarih    ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=5;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(eski_tarih_yazi,gc);

        JLabel eski_tarih = new JLabel(rndv_trh);
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=5;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(eski_tarih,gc);

        JLabel tarih_yazi = new JLabel("Yeni Tarih   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=6;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(tarih_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=6;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(tarih_combo(),gc);

        JLabel eski_saat_yazi = new JLabel("Eski Saat   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=7;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(eski_saat_yazi,gc);

        JLabel eski_saat = new JLabel(rndv_saat);
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=7;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(eski_saat,gc);

        JLabel saat_yazi = new JLabel("Yeni Saat   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=8;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(saat_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=8;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(saat_combo(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=9;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(sil_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=9;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(guncelle_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=10;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(geri_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=10;//satır
        gc.gridwidth=4;//genişlik
        gc.gridheight=1;//yükseklik
        add(listele_buton(),gc);
        btn_listele.setEnabled(false);

        textbox_tc.addKeyListener(new java.awt.event.KeyAdapter()    //ENTER BASILDIĞINI ANLAYAN KOD
        {
            public void keyPressed(java.awt.event.KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    String tc_temp = textbox_tc.getText();

                    String sql = "SELECT HRS_HST_AD, HRS_HST_SOYAD FROM HRS_HASTA_KIMLIK_BILGI WHERE HRS_HST_TC_KIMLIK_NO =" + "'"+tc_temp+"'";

                    DatabaseConnection db_hasta_ara = new DatabaseConnection();

                    ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

                    try
                    {
                        while(gelen_veriler.next())
                        {
                            hst_tc = tc_temp;
                            textbox_ad.setText(gelen_veriler.getString("HRS_HST_AD"));
                            hst_ad = gelen_veriler.getString("HRS_HST_AD");
                            textbox_soyad.setText(gelen_veriler.getString("HRS_HST_SOYAD"));
                            hst_soyad = gelen_veriler.getString("HRS_HST_SOYAD");
                            textbox_tc.setEditable(false);
                            dogru_tc = true;
                            btn_listele.setEnabled(true);
                        }
                    }
                    catch (Exception x)
                    {
                        // TODO: handle exception
                        x.printStackTrace();
                    }
                }
            }
        });
    }

    public JComboBox tarih_combo()
    {
        cmb_trh = new JComboBox();
        cmb_trh.addItem(new ComboDoldur("Seçiniz","-1"));
        String zaman = new SimpleDateFormat("yyyyMMdd_HHmm").format(Calendar.getInstance().getTime());
        int ay_kac_gun = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        int c_yil = Integer.parseInt(zaman.substring(0,4)); //hangi yıldayız
        int c_ay = Integer.parseInt(zaman.substring(4,6));  //hangi ayda olduğumuz
        int c_gun = Integer.parseInt(zaman.substring(6,8)); //ayın kaçıncı gününde olduğumuz
        int c_saat = Integer.parseInt(zaman.substring(9,11));   //şu anki saat
        int c_dakika = Integer.parseInt(zaman.substring(11,13));
        int on_bes_gun = 15;
        for (int i=c_gun ; i<=ay_kac_gun ; i++)
        {
            if (c_saat > 17)
            {
                continue;
            }
            if (i<10)
            {
                cmb_trh.addItem(new ComboDoldur(("0"+i+"."+c_ay+"."+c_yil),(""+c_yil+"-"+c_ay+"-0"+i)));
                on_bes_gun--;
            }
            else if (i>=10)
            {
                cmb_trh.addItem(new ComboDoldur((""+i+"."+c_ay+"."+c_yil),(""+c_yil+"-"+c_ay+"-"+i)));
                on_bes_gun--;
            }

            if (on_bes_gun==0)
                break;
        }
        if (on_bes_gun>=0)
        {
            for (int i=1 ; on_bes_gun>0 ; i++)
            {
                if (i<10)
                {
                    if (c_ay==12)
                    {
                        cmb_trh.addItem(new ComboDoldur(("0"+i+"."+"01"+"."+(c_yil+1)),(""+(c_yil+1)+"-"+"01"+"-0"+i)));
                        on_bes_gun--;
                    }
                    else if (c_ay<12)
                    {
                        cmb_trh.addItem(new ComboDoldur(("0"+i+"."+(c_ay+1)+"."+c_yil),(""+c_yil+"-"+(c_ay+1)+"-0"+i)));
                        on_bes_gun--;
                    }
                }
                else if (i>=10)
                {
                    if (c_ay==12)
                    {
                        cmb_trh.addItem(new ComboDoldur((""+i+"."+"01"+"."+(c_yil+1)),(""+c_yil+"-"+c_ay+"-"+i)));
                        on_bes_gun--;
                    }
                    else if (c_ay<12)
                    {
                        cmb_trh.addItem(new ComboDoldur((""+i+"."+(c_ay+1)+"."+c_yil),(""+c_yil+"-"+c_ay+"-"+i)));
                        on_bes_gun--;
                    }
                }

            }
        }

        cmb_trh.addActionListener (new ActionListener ()
        {
            public void actionPerformed(ActionEvent e)
            {
                item = cmb_trh.getSelectedItem();
                deger = ((ComboDoldur)item).getValue();

                dto.setRandevu_trh(deger);

                String sql = " SELECT HRS_RANDEVU_SAAT FROM HRS_DR_RANDEVU "
                        + " WHERE HRS_RANDEVU_TRH='"+deger+"' AND DR_TC_KIMLIK_NO='"+dr_tc+"' AND HRS_RANDEVU_DURUM='"+"-9"+"'";

                DatabaseConnection db_hasta_ara = new DatabaseConnection();

                ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

                try
                {
                    cmb_saat.removeAllItems();
                    cmb_saat.addItem(new ComboDoldur("Seçiniz", "-1"));
                    String gorunen_saat = "";
                    String db_saat = "";
                    String db_dakika = "";
                    String secilen_gun = deger.substring(8,10);

                    while (gelen_veriler.next())
                    {
                        gorunen_saat = gelen_veriler.getString("HRS_RANDEVU_SAAT").substring(0,5);
                        db_saat = gorunen_saat.substring(0,2);
                        db_dakika = gorunen_saat.substring(3,5);
                        if (c_gun==Integer.parseInt(secilen_gun))
                        {
                            if (Integer.parseInt(db_saat) < c_saat)
                            {
                                continue;
                            }
                            if (c_saat == Integer.parseInt(db_saat) && Integer.parseInt(db_dakika) <= c_dakika)
                            {
                                continue;
                            }
                        }
                        cmb_saat.addItem(new ComboDoldur(gorunen_saat,gelen_veriler.getString("HRS_RANDEVU_SAAT")));
                    }
                }

                catch (Exception x)
                {
                    // TODO: handle exception
                    x.printStackTrace();
                }
            }
        });
        return cmb_trh;
    }

    public JComboBox saat_combo()
    {
        cmb_saat = new JComboBox();
        cmb_saat.addItem(new ComboDoldur("Seçiniz","-1"));
        return cmb_saat;
    }

    public JButton guncelle_buton()
    {
        if (btn_guncelle == null)
        {
            btn_guncelle = new JButton();
            btn_guncelle.setBounds(103, 110, 100, 54);
            btn_guncelle.setText("Güncelle");

            btn_guncelle.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if (cmb_saat.getSelectedIndex()==0 || cmb_trh.getSelectedIndex()==0)
                    {
                        JOptionPane.showMessageDialog(null, "Alanlar Boş Geçilemez!", "Randevu Düzenle", 0);
                    }
                    else
                    {
                        item = cmb_trh.getSelectedItem();
                        deger = ((ComboDoldur)item).getValue();
                        String tarih = deger;

                        item = cmb_saat.getSelectedItem();
                        deger = ((ComboDoldur)item).getValue();

                        String sql0 = "UPDATE HRS_DR_RANDEVU SET HRS_RANDEVU_DURUM=9 "  //RANDEVU VER
                                + "WHERE DR_TC_KIMLIK_NO='"+DuzenleHstRandevu.dr_tc+"' AND HRS_RANDEVU_TRH='"+tarih+"'AND HRS_RANDEVU_SAAT='"+deger+"'";

                        DatabaseConnection con_hst_ekle = new DatabaseConnection();
                        String hata_durum0  = con_hst_ekle.database_sql(sql0);

                        String sql1 = "UPDATE HRS_DR_RANDEVU SET HRS_RANDEVU_DURUM=-9 " //ESKİSİNİ BOŞALT
                                + "WHERE DR_TC_KIMLIK_NO='"+DuzenleHstRandevu.dr_tc+"' AND HRS_RANDEVU_TRH='"+DuzenleHstRandevu.rndv_trh+"'AND HRS_RANDEVU_SAAT='"+DuzenleHstRandevu.rndv_saat+"'";

                        String hata_durum1  = con_hst_ekle.database_sql(sql1);

                        String sql2 = "UPDATE HRS_RANDEVU SET HRS_RANDEVU_TRH='"+tarih+"', HRS_RANDEVU_SAAT='"+deger+"' "
                                + "WHERE HRS_DR_TC='"+DuzenleHstRandevu.dr_tc+"' AND HRS_RANDEVU_TRH='"+DuzenleHstRandevu.rndv_trh+"'AND HRS_RANDEVU_SAAT='"+DuzenleHstRandevu.rndv_saat+"'";

                        String hata_durum2  = con_hst_ekle.database_sql(sql2);

                        JOptionPane.showMessageDialog(null, ""+hata_durum2 , "Randevu Düzenle", -1);
                        if (hata_durum2.equals("İşlem Başarılı."))
                        {
                            DuzenleHstRandevu yeni = new DuzenleHstRandevu();
                            yeni.setVisible(true);
                            setVisible(false);
                        }
                    }
                }
            });
        }//IF SONU
        return btn_guncelle;
    }

    public JButton sil_buton()
    {
        if (btn_sil == null)
        {
            btn_sil = new JButton();
            btn_sil.setBounds(103, 110, 100, 54);
            btn_sil.setText("Sil");

            btn_sil.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    String sql0 = "UPDATE HRS_DR_RANDEVU SET HRS_RANDEVU_DURUM=-9 "  //ESKİSİNİ BOŞALT
                            + "WHERE DR_TC_KIMLIK_NO='"+DuzenleHstRandevu.dr_tc+"' AND HRS_RANDEVU_TRH='"+DuzenleHstRandevu.rndv_trh+"'AND HRS_RANDEVU_SAAT='"+DuzenleHstRandevu.rndv_saat+"'";

                    DatabaseConnection con_hst_ekle = new DatabaseConnection();
                    String hata_durum0  = con_hst_ekle.database_sql(sql0);

                    String sql1 = "DELETE FROM HRS_RANDEVU  "   //RANDEVU LİSTESİNDEN SİL
                            + "WHERE HRS_DR_TC='"+DuzenleHstRandevu.dr_tc+"' AND HRS_RANDEVU_TRH='"+DuzenleHstRandevu.rndv_trh+"'AND HRS_RANDEVU_SAAT='"+DuzenleHstRandevu.rndv_saat+"'";

                    String hata_durum1  = con_hst_ekle.database_sql(sql1);

                    JOptionPane.showMessageDialog(null, ""+hata_durum1 , "Randevu Düzenle", -1);
                }
            });
        }//IF SONU
        return btn_sil;
    }

    public JButton listele_buton()    //KAPAT BUTONU
    {
        if (btn_listele == null)
        {
            btn_listele = new JButton();
            btn_listele.setBounds(103, 110, 100, 54);
            btn_listele.setText("Listele");
            Color renk = new Color(137, 200, 116);
            btn_listele.setBackground(renk);

            btn_listele.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    EventQueue.invokeLater(new Runnable()
                    {
                        public void run()
                        {
                            if (dogru_tc)
                            {
                                try
                                {
                                    SecHstRandevu.hst_tc = textbox_tc.getText();
                                    SecHstRandevu rndvsec = new SecHstRandevu();
                                    rndvsec.setVisible(true);
                                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                                    rndvsec.setIconImage(image.getImage());
                                    rndvsec.addWindowListener(new WindowAdapter()
                                    {
                                        @Override
                                        public void windowClosing(WindowEvent e)
                                        {
                                            rndvsec.setVisible(false);
                                            setVisible(true);
                                        }
                                    });
                                    setVisible(false);
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
                }
            });
        }//IF SONU
        return btn_listele;
    }

    public JButton geri_buton()    //KAPAT BUTONU
    {
        if (btn_geri == null)
        {
            btn_geri = new JButton();
            btn_geri.setBounds(103, 110, 100, 54);
            btn_geri.setText("Geri");
            Color renk = new Color(201, 93, 81);
            btn_geri.setBackground(renk);

            btn_geri.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    YoneticiMenu ynt_ekran = new YoneticiMenu();
                    ynt_ekran.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    ynt_ekran.setIconImage(image.getImage());
                    setVisible(false);
                }
            });
        }//IF SONU
        return btn_geri;
    }//KAPAT BUTONU METOD SONU
}

