import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Random;
public class SudokuGame extends JFrame implements KeyListener
{
    
    public static final int GridSize=9;
    public static final int boxsize = (int)(Math.sqrt(GridSize));
    public static int pointerx = 0, pointery = 0;
    public static int[][] grid = {
                                {0,8,0,3,0,2,0,0,0},
                                {0,5,0,7,9,8,4,3,0},
                                {3,0,7,1,0,5,8,0,0},
                                {0,0,8,6,2,7,3,0,0},
                                {0,7,0,9,3,1,0,6,8},
                                {0,0,3,5,8,4,1,0,0},
                                {0,0,5,2,7,3,6,8,0},
                                {8,2,6,4,1,9,7,5,3},
                                {7,3,0,8,5,6,0,0,0}};
    public static void clrscr()// Function to clear screen
    {
        System.out.print('\u000C');
    }
    public static int NumberofUnknowns(int[][] grid){
        int count = 0;
        
        for(int i=0;i<GridSize;i++)
            for(int j=0;j<GridSize;j++)
                if(grid[i][j]==0)
                    count++;
        return count;    
    }
    public static boolean checkRow(int[][] grid, int x , int y){
        for(int i=0;i<GridSize;i++){
            if(grid[x][i]==grid[x][y] && i!=y && grid[x][i] != 0)
                return false;
        }
        return true;
    }
    public static boolean checkCol(int[][] grid, int x, int y){
        for(int i=0;i<GridSize;i++){
            if(grid[i][y]==grid[x][y] && i!=x && grid[i][y] != 0)
                return false;
        }
        return true;
    }
    public static int topofBox(int x){
        if(x==0)
            return 0;
        return x-(x%(boxsize));        
    }
    /*public static void checkfunction(){
        for(int i=0;i<GridSize;i++){
            System.out.print(" "+leftofBox(i));
        }
    }*/
    public static int leftofBox(int y){
        if(y==0)
            return 0;
        return y-(y%(boxsize));
    }
    
    public static int[][] getBox(int [][] grid, int x, int y){
        int[][] Box = new int[boxsize][boxsize];
        for(int i=0;i<boxsize;i++){
            for(int j=0;j<boxsize;j++){
                Box[i][j] = grid[i+topofBox(x)][j+leftofBox(y)];
            }
        }
        
        
        return Box;
    }
    public static int[] get1dBox(int [][] box){
        int arrBox[] = new int[boxsize*boxsize];
        int k=0;
        for(int i=0;i<boxsize;i++){
            for(int j=0;j<boxsize;j++,k++)arrBox[k]=box[i][j];   
        }
        return arrBox;
    }
    public static boolean checkBox(int[][] grid, int x, int y){
        int arrBox[] = get1dBox(getBox(grid,x,y));
        for(int i=0;i<GridSize;i++){
            for(int j=i+1;j<GridSize;j++){
                if(arrBox[i]==arrBox[j] && arrBox[i]!=0)
                    return false;
            }
        }
        
        
        return true;
        
    }
    
    public static boolean checkGrid(int[][] grid){
        for(int i=0;i<GridSize;i++){
            for(int j=0;j<GridSize;j++){
                if(!checkRow(grid,i,j) ){
                    System.out.println("Error row " + i + j);
                    return false;
                }
                    
                
                    
                if(!checkCol(grid,i,j)){
                    System.out.println("Error row " + i + j);
                    return false;
                }
                    
            }
        }
        for(int i=0;i<GridSize;i+=3){
            for(int j=0;j<GridSize;j+=3){
                if(!checkBox(grid,i,j))
                    return false;
            }
        }
        return true;
    }
    public static void printGrid( int grid[][]){
        System.out.print("+");
        for(int i=0;i<grid.length;i++){
            System.out.print("---+");
        }
        System.out.println();
        for(int i=0;i<grid[0].length;i++){
            System.out.print("|");
            for(int j=0;j<grid.length;j++){
                if(grid[i][j]==0)
                    System.out.print("   |");    
                else{
                    if(grid[i][j]<10)
                        System.out.print(" ");
                    System.out.print(grid[i][j]+" |");
                }
            }
            if(i==pointery)System.out.print("<--");
            
            System.out.println();
            System.out.print("+");
            for(int j=0;j<grid.length;j++){
                System.out.print("---+");
            }
            System.out.println();
        }
        for(int i=0;i<pointerx;i++)System.out.print("    ");
        System.out.println("  ^");
        System.out.println(checkGrid(grid));
        System.out.println("PointerX:"+pointerx+", PointerY:"+pointery);
    }
    public static void main(String[] args){
        
        JFrame frame = new JFrame();
        JPanel panel = new JPanel(new FlowLayout());
        JTextField field = new JTextField(20);
        field.addKeyListener(new SudokuGame());
        panel.add(field);
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500,500);
        frame.setVisible(true);
        
        
        
        
        
        Scanner in = new Scanner(System.in);
        int ch;                
        /*                
        int[][] grid = {{0,0,0,3},
                        {0,1,0,4},
                        {4,2,3,1},
                        {1,3,4,2}};
          */                  
        int NumberofUnknowns = NumberofUnknowns(grid);
        int[] solution = new int[NumberofUnknowns];
         /*
        
         
        for(int i=0;i<GridSize;i++)
            for(int j=0;j<GridSize;j++){
                for(int k=0;k<GridSize;k++)System.out.print(get1dBox(getBox(grid,i,j))[k]);
                System.out.println();
            }
            */ 
        
        
        while(true){
            clrscr();
            printGrid(grid);
            for(int i=0;i<3;i++)for(int j=6;j<9;j++)System.out.print(checkBox(grid,i,j));
            System.out.println("\n MENU : \n 8,6,2,4 : Cursor wasd\n 5 : Change a number\n");
            ch = in.nextInt();
            switch(ch){
                case 8: pointery--;
                        break;
                case 2: pointery++;
                        break;
                case 4: pointerx--;
                        break;
                case 6: pointerx++;
                        break;
                case 5: System.out.println("\n Enter new number to put ");
                        ch=in.nextInt();
                        grid[pointery][pointerx] = ch;
            }
            if(NumberofUnknowns(grid) == 0 && checkGrid(grid)){
                System.out.println("  GAME OVER YOU WIN YAYYYY ");    
            }
        }
    }
    public void keyTyped(KeyEvent e){
        char x = e.getKeyChar();
        switch(x){
            case 'w': pointery--;
                    break;
            case 's': pointery++;
                    break;
            case 'a': pointerx--;
                    break;
            case 'd': pointerx++;
                    break;
            case '1': grid[pointery][pointerx] = 1;
                    break;
            case '2': grid[pointery][pointerx] = 2;
                    break;
            case '3': grid[pointery][pointerx] = 3;
                    break;
            case '4': grid[pointery][pointerx] = 4;
                    break;
            case '5': grid[pointery][pointerx] = 5;
                    break;
            case '6': grid[pointery][pointerx] = 6;
                    break;
            case '7': grid[pointery][pointerx] = 7;
                    break;
            case '8': grid[pointery][pointerx] = 8;
                    break;
            case '9': grid[pointery][pointerx] = 9;
                    break;
            case '0': grid[pointery][pointerx] = 0;
                    break;
        }
        clrscr();
        printGrid(grid);

    }
    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
    
}