/**
 * Created by qlp on 2014/6/20.
 */
public class TestSubString {
    public static void main(String[] args){
        StringBuilder sb = new StringBuilder("abc,");
        String s =sb.substring(0,sb.length()-1);
        //sb.substring(0,sb.length()-1);
        //sb.toString;
        System.out.print(s);
    }
}
