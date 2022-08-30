import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;


public class ProcesadorDeTexto {
    public static void main(String[]args){
        Frame procesador= new Frame();
    }
}
class Frame extends JFrame {
    public Frame() {
        setTitle("WordMachine");
        setBounds(100,50,1000,580);
        add(new PanelPrincipal());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
class PanelPrincipal extends JPanel {
    public PanelPrincipal() {
        setLayout(new BorderLayout());
        //-----------------Creamos la barra de herramientas--------------
        panelMenu= new JPanel();
        barraMenu= new JMenuBar();

        fuente= new JMenu("Fuente"); barraMenu.add(fuente);
        estilo= new JMenu("Estilo"); barraMenu.add(estilo);
        tamagno= new JMenu("Tamaño"); barraMenu.add(tamagno);

        arial= new JMenuItem("Arial"); fuente.add(arial);
        cambria= new JMenuItem("Cambria"); fuente.add(cambria);
        verdana= new JMenuItem("Verdana"); fuente.add(verdana);

        negrita= new JMenuItem("Bold",new ImageIcon("B.png")); estilo.add(negrita);
        cursiva= new JMenuItem("Italic", new ImageIcon("I.png")); estilo.add(cursiva);
        subrayado= new JMenuItem("Underline", new ImageIcon("U.png")); estilo.add(subrayado);

        ButtonGroup tamagnogrupo=new ButtonGroup();

        tamagno_12= new JRadioButton("12"); tamagnogrupo.add(tamagno_12); tamagno.add(tamagno_12);
        tamagno_16= new JRadioButton("16"); tamagnogrupo.add(tamagno_16); tamagno.add(tamagno_16);
        tamagno_18= new JRadioButton("18"); tamagnogrupo.add(tamagno_18); tamagno.add(tamagno_18);
        tamagno_20= new JRadioButton("20"); tamagnogrupo.add(tamagno_20); tamagno.add(tamagno_20);

        panelMenu.add(barraMenu);
        add(panelMenu, BorderLayout.NORTH);

        //------------------------contruimos el cuadro de texto--------------------

        JPanel panelTexto= new JPanel();

        hoja= new JTextPane();
        hoja.setPreferredSize(new Dimension(950,450));


        JScrollPane scroll= new JScrollPane(hoja);

        panelTexto.add(scroll);
        add(panelTexto, BorderLayout.CENTER);

        //----------------------------contruimos el JPopupMenu---------------------------------
        menuDeslizante=new JToolBar(JToolBar.VERTICAL);

        b= new JButton("b",new ImageIcon("B.png")); menuDeslizante.add(b);
        i= new JButton("i",new ImageIcon("I.png")); menuDeslizante.add(i);
        u= new JButton("u",new ImageIcon("U.png")); menuDeslizante.add(u);

        menuDeslizante.addSeparator();

        red=new JButton("red",new ImageIcon("red.png")); menuDeslizante.add(red);
        green=new JButton("green",new ImageIcon("green.png")); menuDeslizante.add(green);
        blue=new JButton("blue",new ImageIcon("blue.png")); menuDeslizante.add(blue);

        menuDeslizante.addSeparator();

        alinLeft=new JButton("alinleft",new ImageIcon("Izq.png"));menuDeslizante.add(alinLeft);
        alinCenter=new JButton("alincenter",new ImageIcon("Center.png"));menuDeslizante.add(alinCenter);
        alinRight=new JButton("alinright",new ImageIcon("Drch.png"));menuDeslizante.add(alinRight);

        menuDeslizante.addSeparator();

        save = new JButton("save",new ImageIcon("save.png")); menuDeslizante.add(save);
        load = new JButton("load",new ImageIcon("load.png")); menuDeslizante.add(load);

        add(menuDeslizante,BorderLayout.WEST);

        //--------------------------ponemos los items del menu suoerior a la escucha---------------------
        setListener();
        //--------------------borro los text de los botones para que no se reflejen en la interfaz,les puse text porque me ahorro codigo en el setlistener------------------
        JButton[]quitText={b, i, u, red, green, blue , alinLeft, alinCenter, alinRight, save, load};
        for(JButton boton:quitText) boton.setText("");

        //----------------ponemos atajos de teclado para los actionlisteners de los item-----------------
        cursiva.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        negrita.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,InputEvent.CTRL_DOWN_MASK));
        subrayado.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_DOWN_MASK));


    }

    private  void setListener() {
        AbstractButton[]items= {arial, cambria, verdana, cursiva, negrita, subrayado, tamagno_12, tamagno_16,
                tamagno_18, tamagno_20, b, i, u, red, green, blue, alinLeft, alinCenter, alinRight};

        for (AbstractButton item : items) {
            if(item.getText().equalsIgnoreCase("arial")||item.getText().equalsIgnoreCase("cambria")
                    ||item.getText().equalsIgnoreCase("verdana")) {

                item.addActionListener(new StyledEditorKit.FontFamilyAction("cambiaFuente",item.getText()));

            }

           else if(item.getText().equalsIgnoreCase("italic")||item.getText().equalsIgnoreCase("i"))
               item.addActionListener(new StyledEditorKit.ItalicAction());

           else if(item.getText().equalsIgnoreCase("bold")||item.getText().equalsIgnoreCase("b"))
               item.addActionListener(new StyledEditorKit.BoldAction());

           else if(item.getText().equalsIgnoreCase("underline")||item.getText().equalsIgnoreCase("u"))
               item.addActionListener(new StyledEditorKit.UnderlineAction());

           else if(item.getText().equalsIgnoreCase("red")) item.addActionListener(new StyledEditorKit.ForegroundAction("rojo",Color.RED));
           else if(item.getText().equalsIgnoreCase("green")) item.addActionListener(new StyledEditorKit.ForegroundAction("verde",Color.GREEN));
           else if(item.getText().equalsIgnoreCase("blue")) item.addActionListener(new StyledEditorKit.ForegroundAction("blue",Color.BLUE));
           else if(item.getText().equalsIgnoreCase("red")) item.addActionListener(new StyledEditorKit.ForegroundAction("rojo",Color.RED));

           else if(item.getText().equalsIgnoreCase("alinleft")) item.addActionListener(new StyledEditorKit.AlignmentAction("izq",0));
           else if(item.getText().equalsIgnoreCase("alincenter")) item.addActionListener(new StyledEditorKit.AlignmentAction("izq",4));
           else if(item.getText().equalsIgnoreCase("alinright")) item.addActionListener(new StyledEditorKit.AlignmentAction("drch",2));
           else  item.addActionListener(new StyledEditorKit.FontSizeAction("size",Integer.parseInt(item.getText())));

        }
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                     file= new PrintStream(JOptionPane.showInputDialog("Introduce nombre y extension del archivo:" +
                            "\n(.txt,.doc,etc)"));
                }catch(java.io.FileNotFoundException exception){
                    System.out.println(exception.getMessage());
                }
                file.println(hoja.getText());
                file.close();
            }
            PrintStream file;
        });
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    file= new FileInputStream(JOptionPane.showInputDialog("Introduce nombre y extension del archivo:" +
                            "\n(.txt,.doc,etc)"));
                    buffer=file.readAllBytes();
                    String text= new String(buffer, java.nio.charset.StandardCharsets.UTF_8);
                    hoja.setText(text);

                }catch(FileNotFoundException exception){
                    System.out.println(exception.getMessage());
                }catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            byte[]buffer=new byte [0];
            InputStream file;
        });

    }


    private JPanel panelMenu;
    private JMenuBar barraMenu;
    private JMenu fuente, estilo, tamagno;
    private JMenuItem arial,cambria , verdana, negrita, cursiva,subrayado;
    private JRadioButton tamagno_12, tamagno_16, tamagno_18, tamagno_20;
    private JToolBar menuDeslizante;
    private JButton b,i,u,red,green,blue,alinLeft,alinCenter,alinRight,save,load;
    private JTextPane hoja;

}