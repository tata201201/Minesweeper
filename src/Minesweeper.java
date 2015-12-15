import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends Applet implements MouseListener
{
        public Minesweeper() {
        }
    Button table[] [];      //Array of Buttons
    boolean bomb[] [];      //array true if bomb is present on button [x][y]
    boolean flag[] [];      //array true if flag is present at button [x][y]
    boolean exposed[] [];   //used for exposing 0's. If true then a 0 has been exposed
    boolean checkwinbool[] [];  // if [x][y] = true then the button has a number on it or it is a bomb (used for checking if game is over)
    int count = 0, bombsremaining;          //counting the number of bombs placed
    String surbombs;        //number of bombs surrounding button [x][y] (is a string so that we can setLabel for the button)
    int randx, randy;       //random ints for bombs
    int row = 15, col = 23, numbombs = 49, Setup = 3; //number of rows columns, and bombs
    int round = 0;
    int score[];
    Label ScoreLabel;
    int sizex = 800, sizey = 600;
    Font font;
    Panel P, P2, PB, P3;
    Label Bombs, TimeRemaning, NG, TURN;
    Button RestartE, RestartM, RestartH;
    GridLayout gl;
    public void init ()
    {
        setLayout (new BorderLayout ());
        gl = new GridLayout (row, col);
        P = new Panel (gl);
        font = new Font ("ComicSans", Font.BOLD, 17);
        setFont (font);
        P2 = new Panel (new BorderLayout ());
        P3 = new Panel ();
        TimeRemaning = new Label ("");
        NG = new Label ("New Game");
        TURN = new Label ("XXX");
        ScoreLabel = new Label (" Score : A [0] | B [0]      ");
        round = 0;
        score = new int[2];
        //RestartE = new Button ("Easy");
        //RestartE.addMouseListener (this);
        //RestartM = new Button ("Medium");
        //RestartM.addMouseListener (this);
        RestartH = new Button ("Restart");
        RestartH.addMouseListener (this);
        PB = new Panel ();
        bombsremaining = numbombs;
        Bombs = new Label ("  Bomb : " + Integer.toString (bombsremaining));
        table = new Button [row] [col];
        bomb = new boolean [row] [col];
        flag = new boolean [row] [col];
        exposed = new boolean [row] [col];
        checkwinbool = new boolean [row] [col];
        for (int x = 0 ; x < row ; x++)
        {
            for (int y = 0 ; y < col ; y++)
            {
                table [x] [y] = new Button ();
                table [x] [y].addMouseListener (this);
                P.add (table [x] [y]);
            }
        }
        //these for loops set up the buttons and sets all the boolean arrays to = false
        add (P2, "North");
        add (P, "Center");
        P2.add (Bombs, "West");
        P2.add (ScoreLabel, "East");
        P2.add (P3, "North");
        //P3.add (NG);
        P3.add (TURN);
        P2.add (PB, "Center");
        //PB.add (RestartE);
        //PB.add (RestartM);
        PB.add (RestartH);
        P2.setBackground(Color.lightGray);
        //NG.setBackground(Color.lightGray);
        //NG.setForeground(Color.black);
        Bombs.setBackground(Color.lightGray);
        Bombs.setForeground(Color.white);
        ScoreLabel.setBackground(Color.lightGray);
        ScoreLabel.setForeground(Color.white);
        Restart_Game (row, col, numbombs, row, col, sizex, sizey);
    }


    public void Restart_Game (int row, int col, int numbombs, int prow, int pcol, int sizex, int sizey)
    {
        //mBar.Restart (table, row, col);
        //mBar.SetupMenu ();
        setSize (sizex, sizey);
        invalidate();
        validate();
        gl.setRows (row);
        gl.setColumns (col);
        int count = 0;
        bombsremaining = numbombs;
        Bombs.setText ("  Bomb : " + Integer.toString (bombsremaining));
        TURN.setText ("Player A");
        ScoreLabel.setText (" Score : A [0] | B [0]      ");
        score[0] = 0;
        score[1] = 0;
        round = 0;
        for (int x = 0 ; x < prow ; x++)
        {
            for (int y = 0 ; y < pcol ; y++)
            {
                P.remove (table [x] [y]);
            }
        }
        for (int x = 0 ; x < row ; x++)
        {
            for (int y = 0 ; y < col ; y++)
            {

                table [x] [y].setEnabled (true);
                table [x] [y].setLabel ("");
                table [x] [y].setBackground (Color.gray);
                table [x] [y].setForeground (Color.white);
                P.add (table [x] [y]);
                bomb [x] [y] = false;
                flag [x] [y] = false;
                exposed [x] [y] = false;
                checkwinbool [x] [y] = false;
            }
        }
        setSize (sizex, sizey);
        invalidate();
        validate();
        //adds the bombs to random places on the grid
        while (count < numbombs)
        {
            randx = (int) (Math.random () * (row));
            randy = (int) (Math.random () * (col));
            if (bomb [randx] [randy] == false)
            {
                bomb [randx] [randy] = true;
                checkwinbool [randx] [randy] = true;
                count++;
            }
        }
    }


    public void mouseClicked (MouseEvent e)
    {
        int prow = 0, pcol = 0;
        if (e.getSource () == RestartH)
        {
            //row = 16;
            //col = 30;
            //numbombs = 99;
            //sizex = 780;
            //sizey = 492;
            if (Setup == 1)
            {
                prow = 10;
                pcol = 10;
                Setup = 3;
            }
            else if (Setup == 2)
            {
                prow = 16;
                pcol = 16;
                Setup = 3;
            }
        }
        if (/*e.getSource () == RestartE || e.getSource () == RestartM || */e.getSource () == RestartH)
            Restart_Game (row, col, numbombs, prow, pcol, sizex, sizey);
        boolean gameover = false;
        for (int x = 0 ; x < row ; x++)
        {
            for (int y = 0 ; y < col ; y++)
            {
                if (e.getSource () == table [x] [y])
                {
                    if (e.getButton () == e.BUTTON1 && canClick(x,y)) //if left click, and there is no flag on the button
                    {
                    	if(round == 1) break;
                        if(clickBoard(x, y)) break;
                    }
                }

            }
        }
        getTable();
        checkwin ();
    }
    
    public boolean clickBoard(int x, int y){
    	if(!canClick(x,y)) return false;
    	if (bomb [x] [y] == true)  // if you you click on a bomb, results in game over
        {
            //table [x] [y].setLabel ("*");
            //gameover ();
            //table [x] [y].setBackground (Color.black);
            //gameover = true;
        	table [x] [y].setLabel ("|>");
            //table [x] [y].setForeground (Color.red);
            table [x] [y].setBackground (Color.red);
            table [x] [y].setForeground (Color.white);
            flag [x] [y] = true;
            checkwinbool [x] [y] = true;
            exposed [x] [y] = true;
            bombsremaining--;
            Bombs.setText ("  Bomb : " + Integer.toString (bombsremaining));
            score[round]++;
            String TempLabel = " Score : A [" + score[0] + "] | B [" + score[1] + "]";
            ScoreLabel.setText (TempLabel);
            System.out.println(round);
            System.out.println(score[round]);
            System.out.println(TempLabel);
            clicked();
            return true;

        }
        round ^= 1;
        if(round == 0) TURN.setText ("Player A");
        else  TURN.setText ("Player B");
        exposed [x] [y] = true;
        checkwinbool [x] [y] = true; // these set to true mean that the button has been clicked
        surbombs = Integer.toString (surroundingBombs (x, y)); //gets the number of surrounding buttons with bombs and sets it to a string so that it is possible to setLabel
        table [x] [y].setLabel (surbombs); // sets the label to be the number of bombs in the 8 surrounding buttons
        if (surroundingBombs (x, y) == 0)
        {
            check (x, y);          //calls a recursive method so that if a "0" is there the surrounding 8 buttins must be exposed and if one of those is "0" it too must be exposed and so on
        }
        clicked();
        return false;
    }
    
    public int[][] getTable(){
    	int tab[][] = new int[row][col];
    	for(int i=0;i<row;i++){
    		for(int j=0;j<col;j++){
    			String temp = table[i][j].getLabel();
    			if(temp.equals("") && table[i][j].getBackground() == Color.darkGray){
    				temp ="0";
    				tab[i][j] = 0;
    			}else if(temp.equals("")){
    				temp ="?";
    				tab[i][j] = -1;
    			}else if(temp.equals("|>")){
    				temp ="*";
    				tab[i][j] = 9;
    			}else{
    				tab[i][j] = Integer.parseInt(temp);
    			}
    			
    			System.out.print(temp);
    		}
    		System.out.println();
    	}
    	return tab;
    }
    
    public boolean canClick(int x, int y){
    	return (table[x][y].getBackground() != Color.darkGray && flag [x] [y] == false && exposed[x][y] == false);
    }
    
    public void callbot(){
    	int r=0 ,c=0;
    	for(r=0;r<row;r++){
    		for(c=0;c<col;c++){
    			if(canClick(r,c)) break;
    		}
			if(c!=col) break;
    	}
    	System.out.println("BOT : " +r + " " + c);
    	clickBoard(r,c);
        getTable();
        checkwin ();
    	
    }

    public void clicked ()     //changes the color of the buttons and if [x][y] is "0" set the label to nothing
    {
        for (int x = 0 ; x < row ; x++)
        {
            for (int y = 0 ; y < col ; y++)
            {
                if (checkwinbool [x] [y] == true && flag [x] [y] == false && bomb [x] [y] == false)
                    table [x] [y].setBackground (Color.darkGray);
                if (flag [x] [y] == false && surroundingBombs (x, y) == 0)
                    table [x] [y].setLabel ("");
            }
        }
    }


    public int flags (int x, int y)  // get the number of surrounding flags
    {
        int surFlags = 0;
        for (int q = x - 1 ; q <= x + 1 ; q++)
        {
            for (int w = y - 1 ; w <= y + 1 ; w++)
            {
                while (true)
                {
                    if (q < 0 || w < 0 || q >= row || w >= col) // makes sure that it wont have an error for buttons next to the wall
                        break;

                    if (flag [q] [w] == true)
                    {
                        surFlags++;
                    }
                    break;
                }
            }
        }
        return surFlags;
    }


    public int surroundingBombs (int x, int y)  // checks surrounding 8 squares for number of bombs (it does include itself, but has already been checked for a bomb so it won't matter)
    {
        int surBombs = 0;
        for (int q = x - 1 ; q <= x + 1 ; q++)
        {
            for (int w = y - 1 ; w <= y + 1 ; w++)
            {
                while (true)
                {
                    if (q < 0 || w < 0 || q >= row || w >= col) // makes sure that it wont have an error for buttons next to the wall
                        break;
                    if (bomb [q] [w] == true)
                        surBombs++;
                    break;
                }
            }
        }
        return surBombs;
    }


    public void expose (int x, int y)  // exposes the surrounding 8 buttons
    {
        String surrbombs;
        exposed [x] [y] = true;
        for (int q = x - 1 ; q <= x + 1 ; q++)
        {
            for (int w = y - 1 ; w <= y + 1 ; w++)
            {
                while (true)
                {
                    if (q < 0 || w < 0 || q >= row || w >= col) // makes sure that it wont have an error for buttons next to the wall
                        break;
                    if (flag [q] [w] == true)
                        break;

                    checkwinbool [q] [w] = true;
                    surrbombs = Integer.toString (surroundingBombs (q, w));
                    table [q] [w].setLabel (surrbombs);
                    break;

                }
            }
        }
    }


    public void surr (int x, int y)  //this is what checks if a surrounding button has "0" is so expose it and check around the exposed buttons until there is no more "0"'s
    {
        String surrbombs;
        for (int q = x - 1 ; q <= x + 1 ; q++)
        {
            for (int w = y - 1 ; w <= y + 1 ; w++)
            {
                while (true)
                {
                    if (q < 0 || w < 0 || q >= row || w >= col) // makes sure that it wont have an error for buttons next to the wall
                        break;
                    if (flag [q] [w] == true)
                        break;
                    if (exposed [q] [w] == false && surroundingBombs (q, w) == 0)
                    {
                        expose (q, w);
                        check (q, w);
                    }
                    break;
                }
            }
        }
    }


    public void check (int x, int y)  //calls the surr method but is neccesary because of the expose first
    {
        expose (x, y);
        surr (x, y);
    }


    public void checkwin ()    //checks if all the button without bombs have been pressed
    {
        boolean allexposed = true;
    	/*
        for (int x = 0 ; x < row ; x++)
        {
            for (int y = 0 ; y < col ; y++)
            {
                if (flag [x] [y] == true && bomb [x] [y] == false)
                    allexposed = false;
                if (checkwinbool [x] [y] == false)
                {
                    allexposed = false;
                    break;
                }
            }
        }
        */
        if(bombsremaining > 0) allexposed = false;
        if (allexposed != false)
        {
            //gameover ();
            int x2 = (int) col / 2;
            int y2 = (int) row / 2;
            table [y2] [x2 - 4].setLabel (" ");
            if(score[0] > score[1]) 	table [y2] [x2 - 3].setLabel ("A");
            else 					 	table [y2] [x2 - 3].setLabel ("B");
            table [y2] [x2 - 2].setLabel (" ");
            table [y2] [x2 - 1].setLabel ("W");
            table [y2] [x2].setLabel ("I");
            table [y2] [x2 + 1].setLabel ("N");
            table [y2] [x2 + 2].setLabel ("S");
            table [y2] [x2 + 3].setLabel ("!");
            table [y2] [x2 + 4].setLabel ("!");
            for (int i = -4 ; i < 5 ; i++)
            {
                table [y2] [x2 + i].setBackground (Color.black);
                table [y2] [x2 + i].setForeground (Color.white);
            }
        }else{
        	if(round == 1){
        		callbot();
        	}
        }
    }


    public void gameover ()  // is called if bomb is clicked or on the double click if flag is not on a bomb
    {
        for (int x = 0 ; x < row ; x++)
        {
            for (int y = 0 ; y < col ; y++)
            {
                if (bomb [x] [y] == true)
                {
                    table [x] [y].setLabel ("*"); //exposes all bombs
                    table [x] [y].setBackground (Color.red);
                }
                table [x] [y].setEnabled (false); //disable all buttons
            }
        }
        int x2 = (int) col / 2;
        int y2 = (int) row / 2;
        table [y2] [x2 - 4].setLabel ("Y");
        table [y2] [x2 - 3].setLabel ("O");
        table [y2] [x2 - 2].setLabel ("U");
        table [y2] [x2 - 1].setLabel ("");
        table [y2] [x2].setLabel ("L");
        table [y2] [x2 + 1].setLabel ("O");
        table [y2] [x2 + 2].setLabel ("S");
        table [y2] [x2 + 3].setLabel ("E");
        table [y2] [x2 + 4].setLabel ("!");
        for (int i = -4 ; i < 5 ; i++)
        {
            table [y2] [x2 + i].setBackground (Color.black);
            table [y2] [x2 + i].setForeground (Color.white);
        }
    }


    /////////////////////////////////////////////////////////////////
    //These are not used but are necessary for mouseListener
    public void mouseEntered (MouseEvent e)
    {
    }


    public void mouseExited (MouseEvent e)
    {
    }


    public void mousePressed (MouseEvent e)
    {
    }


    public void mouseReleased (MouseEvent e)
    {
    }
}