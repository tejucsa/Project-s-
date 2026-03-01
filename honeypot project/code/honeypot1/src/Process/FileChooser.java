/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Process;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;

/**
 *
 * @author user
 */
public class FileChooser {
public JFileChooser filechooser;
public int chooser;
public String path,data;
public StringTokenizer str;
public FileInputStream fis;
public String selectmsg() throws IOException {
	String data="";
    filechooser=new JFileChooser();
    chooser=filechooser.showOpenDialog(filechooser);
    if(chooser==JFileChooser.APPROVE_OPTION){
        path=filechooser.getSelectedFile().getAbsolutePath();
        data=filechooser.getSelectedFile().getName();
        str= new StringTokenizer(data,".");
        str.nextToken();
        String fr=str.nextToken();
        if(fr.equals("txt") ||fr.equals("java") ||fr.equals("html")||fr.equals("jsp")){
            try{
                fis= new FileInputStream(path);
                byte[] fb=new byte[fis.available()];
                fis.read(fb);
                data = new String(fb);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
   }
    return data;
}

}
