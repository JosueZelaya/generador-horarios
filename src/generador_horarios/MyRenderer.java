/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author alexander
 */
public class MyRenderer implements TableCellRenderer{

    Color color = Color.WHITE;
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
            
                JLabel texto = new JLabel();
                texto.setBackground(color);
                if(value != null)
                    texto.setText(value.toString());
                return texto;
                //JPanel middlePanel = new JPanel ();
                //middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Display Area" ) );

                // create the middle panel components

//                JTextArea display = new JTextArea ();
//                if(value != null)
//                    display.setText(value.toString());
//                display.setEditable ( false ); // set textArea non-editable
//                JScrollPane scroll = new JScrollPane ( display );
//                scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
//
//                //Add Textarea in to middle panel
//                middlePanel.add ( scroll );
//                return middlePanel;
            
//            JTextArea display = new JTextArea();
//            display.setEditable(false);
//            if(value != null)
//                display.setText(value.toString());                        
//            JScrollPane panel = new JScrollPane(display);
//            return panel;
            
//            
//          JTextArea display = new JTextArea();
//          if (value != null)
//            display.setText(value.toString());
//          display.setEditable(false);
//          display.setBackground((row % 2 == 0) ? Color.white : new Color(211,211,211));
//          return display;
        }
        
        public void cambiarColorFondo(Color color){
            this.color = color;
        }
    
}
