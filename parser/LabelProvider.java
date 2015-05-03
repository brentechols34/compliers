/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author NinjaKL
 */
public class LabelProvider {
    int labelNumber;
    
    public LabelProvider(){
        labelNumber = 0;
    }
    
    public String nextLabel(){
        return "L" + labelNumber++;
    }
     
    public String peekLabel(int offset){
        return "L" + (labelNumber + offset);
    }
}
