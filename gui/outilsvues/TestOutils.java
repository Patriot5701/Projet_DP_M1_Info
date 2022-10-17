package outilsvues;

import java.awt.Frame;

public class TestOutils
{

public static void main(String[] args)
{
Frame frame = new Frame("blabla");
Outils.place(frame, 0.35, 0.25, 0.4, 0.6);


/*frame.setLocation(50,100);
frame.setSize(200, 150);
*/
frame.setVisible(true);
}

}
