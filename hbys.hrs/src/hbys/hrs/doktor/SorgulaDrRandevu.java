package hbys.hrs.doktor;

import hbys.hrs.database.DatabaseConnection;
import hbys.hrs.dto.DTO;
import hbys.hrs.other.ComboDoldur;
import hbys.hrs.yonetici.YoneticiMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class SorgulaDrRandevu extends JFrame
{
    DTO dto = new DTO();

    private JComboBox cmb_pol;
    private JComboBox cmb_dr;

    private JButton btn_listele = null;
    private JButton btn_geri = null;

    public Object item = null;
    public String deger = "";

    public SorgulaDrRandevu()
    {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout);

        setTitle("Doktor Randevu Planı");
        setSize(390, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel pol_yazi = new JLabel("Poliklinik   ");
        gc.fill= GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(pol_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(pol_combo(),gc);

        JLabel dr_yazi = new JLabel("Doktor   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(dr_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(dr_combo(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=10;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(geri_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=10;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(listele_buton(),gc);


    }

    public JButton listele_buton()
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
                            try
                            {
                                ListeleDrRandevu drliste = new ListeleDrRandevu();
                                drliste.setVisible(true);
                                ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                                drliste.addWindowListener(new WindowAdapter()
                                {
                                    @Override
                                    public void windowClosing(WindowEvent e)
                                    {
                                        drliste.setVisible(false);
                                        setVisible(true);
                                    }
                                });
                                drliste.setIconImage(image.getImage());
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }//IF SONU
        return btn_listele;
    }

    public JComboBox pol_combo()
    {
        cmb_pol = new JComboBox();
        String sql = "SELECT HRS_POL_ID,HRS_POL_ADI FROM HRS_POLIKLINIK";

        DatabaseConnection db_hasta_ara = new DatabaseConnection();

        ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

        try
        {
            String temp_pol_id;
            String temp_pol_adi;
            cmb_pol.addItem(new ComboDoldur("Seçiniz","-1"));

            while(gelen_veriler.next())
            {
                temp_pol_id = gelen_veriler.getString("HRS_POL_ID");
                temp_pol_adi = gelen_veriler.getString("HRS_POL_ADI");

                cmb_pol.addItem(new ComboDoldur(temp_pol_adi,temp_pol_id));
            }

            cmb_pol.addActionListener (new ActionListener ()
            {
                public void actionPerformed(ActionEvent e)
                {
                    item = cmb_pol.getSelectedItem();
                    deger = ((ComboDoldur)item).getValue();
                    cmb_dr.setEnabled(true);//düzenlenebilir mi?

                    String sql = " SELECT  HRS_DR_AD,HRS_DR_SOYAD,HRS_DR_TC_KIMLIK_NO FROM HRS_DR_KIMLIK_BILGI  "
                            + " WHERE HRS_DR_POLIKLINIK_ID='" +deger+ "'";

                    DatabaseConnection db_hasta_ara = new DatabaseConnection();

                    ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

                    try
                    {
                        String temp_dr_ad = "";
                        String temp_dr_soyad = "";
                        String temp_dr_ad_soyad = "";
                        String temp_dr_tc = "";
                        cmb_dr.removeAllItems();

                        cmb_dr.addItem(new ComboDoldur("Seçiniz","-1"));
                        while(gelen_veriler.next())
                        {
                            temp_dr_ad = gelen_veriler.getString("HRS_DR_AD");
                            temp_dr_soyad = gelen_veriler.getString("HRS_DR_SOYAD");
                            temp_dr_ad_soyad=temp_dr_ad+" "+temp_dr_soyad;
                            temp_dr_tc = gelen_veriler.getString("HRS_DR_TC_KIMLIK_NO");

                            cmb_dr.addItem(new ComboDoldur(temp_dr_ad_soyad,temp_dr_tc));
                        }
                    }
                    catch (Exception x)
                    {
                        x.printStackTrace();
                    }
                }
            });
        }
        catch (Exception x)
        {
            x.printStackTrace();
        }
        return cmb_pol;
    }

    public JComboBox dr_combo()
    {
        cmb_dr = new JComboBox();
        cmb_dr.addItem(new ComboDoldur("Seçiniz","-1"));


        cmb_dr.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent arg0)
            {
                if (cmb_dr.getSelectedItem()!=null)
                {
                    item = cmb_dr.getSelectedItem();
                    deger = ((ComboDoldur) item).getValue();
                    dto.setDr_tc(deger);
                    ListeleDrRandevu.dr_tc = deger;
                }
            }
        });

        return cmb_dr;
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

