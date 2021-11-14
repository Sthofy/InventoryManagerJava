package inventorymanagerapp.others;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Suhajda Kristóf - IMVC5O
 */
public class ImageEditor {

    public static void ScaleImage(JLabel panel,String imageName) {
        ImageIcon MyLoginPageIcon = new ImageIcon(new ImageIcon("./src/inventorymanagerapp/images/"+imageName).getImage().getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_DEFAULT));
        panel.setIcon(MyLoginPageIcon);
    }

}
