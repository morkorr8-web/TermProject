import java.io.*;
import java.util.Scanner;
public class TermProject {
    static final int MAX = 100;
    static String[] id=new String[MAX],title=new String[MAX],author=new String[MAX],borrower=new String[MAX];
    static boolean[] borrowed=new boolean[MAX];
    static int n=0;
    static Scanner in=new Scanner(System.in);

    public static void main(String[]a){
        load();
        for(int c=0;c!=5;){
            System.out.print("\n1.หนังสือทั้งหมด 2.ยืมหนังสือ 3.คืนหนังสือ 4.บันทึกการยืม 5.ปิด\nตัวเลือก: ");
            try{c=Integer.parseInt(in.nextLine());}catch(Exception e){System.out.println("Enter number.");continue;}
            switch(c){
                case 1:show();break;
                case 2:borrow();break;
                case 3:ret();break;
                case 4:summary();break;
                case 5:save();c=5;break;
                default:System.out.println("Choose 1-5");
            }
        }
    }
    static int find(String s){for(int i=0;i<n;i++)if(id[i].equalsIgnoreCase(s))return i;return -1;}

    static void show(){
        if(n==0){System.out.println("No books.");return;}
        for(int i=0;i<n;i++)
            System.out.println(id[i]+" | "+title[i]+" | "+author[i]+" | "+(borrowed[i]?"Borrowed by "+borrower[i]:"Available"));
    }
    static void borrow(){
        System.out.print("Book ID: ");int i=find(in.nextLine().trim());
        if(i<0||borrowed[i]){System.out.println("Not found or borrowed.");return;}
        System.out.print("Your name: ");String nm=in.nextLine().trim();
        if(nm.isEmpty()){System.out.println("Name empty.");return;}
        borrowed[i]=true;borrower[i]=nm;save();System.out.println("Borrowed OK");
    }
    static void ret(){
        System.out.print("Book ID: ");int i=find(in.nextLine().trim());
        if(i<0||!borrowed[i]){System.out.println("Not found or not borrowed.");return;}
        borrowed[i]=false;borrower[i]="-";save();System.out.println("Returned OK");
    }
    static void summary(){
        int b=0;for(int i=0;i<n;i++)if(borrowed[i])b++;
        System.out.println("\nหนังสือทั้งหมด: "+n+" | ยังไม่จอง: "+(n-b)+" | กำลังยืม: "+b);
        if(b>0){System.out.println("--- ชื่อคนยืม:");
            for(int i=0;i<n;i++)if(borrowed[i])System.out.println("- "+title[i]+" → "+borrower[i]);}
    }
    static void save(){
        try(FileWriter w=new FileWriter("books.txt")){
            for(int i=0;i<n;i++)w.write(id[i]+"|"+title[i]+"|"+author[i]+"|"+borrowed[i]+"|"+borrower[i]+"\n");
        }catch(IOException e){}
    }
    static void load(){
        File f=new File("books.txt");
        if(!f.exists()){setBooks();save();return;}
        try(Scanner r=new Scanner(f)){while(r.hasNextLine()&&n<MAX){
            String[]d=r.nextLine().split("\\|");
            if(d.length>=5){id[n]=d[0];title[n]=d[1];author[n]=d[2];borrowed[n]=Boolean.parseBoolean(d[3]);borrower[n]=d[4];n++;}
        }}catch(IOException e){}
        if(n==0){setBooks();save();}
    }
    static void setBooks(){
        n=0;
        String[][]list={{"B001","หนังสือกระต่ายกับเต่า","อีสป"},{"B002","หนังสือพระอภัยมณี","สุนทรภู่"},
            {"B003","หนังสือขุนช้างขุนแผน","สุนทรภู่"},{"B004","หนังสือลักษณวงศ์","สุนทรภู่"},{"B005","หนังสือ1รามเกียรติ์ ","ฤๅษีวาลมีกิ"}};
        for(String[]x:list){id[n]=x[0];title[n]=x[1];author[n]=x[2];borrowed[n]=false;borrower[n]="-";n++;}
    }
}