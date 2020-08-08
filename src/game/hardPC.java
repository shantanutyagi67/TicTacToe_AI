package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class hardPC extends JComponent implements Runnable, MouseListener, KeyListener{
	
	hardPC(){
		addMouseListener(this);
	}
	
	static JFrame frame = new JFrame("Tic Tac Toe");
	String board[][] = {{"" , "" , ""},
						{"" , "" , ""},
						{"" , "" , ""}};
	
	public static void main(String args[]) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,640,700);
		frame.getContentPane().add(new hardPC());
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setVisible(true);
		//System.out.println("TURN: X");
		//end.add(-1);
		
	}
	double scl=1;
	int scorex=0,scoreo=0;
	Scanner sc = new Scanner(System.in);
	boolean over=true;
	boolean mouseexists = false;
//	boolean scale = false;
	double w=600;//=frame.getWidth();
	double h=600;//=frame.getHeight();
	Vector<Integer> end = new Vector<Integer>(0);
	static int turn = 1;//1 -> X and 0 -> O
	int start =1;
	boolean res = false;
	public void paint(Graphics g)
	{
		if(!mouseexists) {
//			addMouseListener(this);
			//addKeyListener(this);
			mouseexists=true;
		}
		
		Graphics2D g2D = (Graphics2D) g;
		//g2D.translate(50,50);
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    	rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    	g2D.setRenderingHints(rh);
//    	while(scale==false) {
		g2D.setColor(Color.WHITE);
		g2D.setStroke(new BasicStroke(4));
		g2D.draw(new Line2D.Double(w/3, 0, w/3, h));
		g2D.draw(new Line2D.Double(2*w/3, 0, 2*w/3, h));
		g2D.draw(new Line2D.Double(0, h/3, w, h/3));
		g2D.draw(new Line2D.Double(0, 2*h/3, w, 2*h/3));
//		g2D.draw(new Line2D.Double(0, 0, 0, h));
//		g2D.draw(new Line2D.Double(w, 0, w, h));
//		g2D.draw(new Line2D.Double(0, 0, w, 0));
//		g2D.draw(new Line2D.Double(0, h, w, h));
		if(start==0&&turn==0&&end.isEmpty()) {
			computer();
			end.addAll(check());
			mouseexists=true;
		}
		draw(g2D);
		
		g2D.setFont(new Font("TimesRoman", Font.BOLD, 20));
		if(turn==1) {
			g2D.drawString("TURN: X", (float) (w/2)-40, 640);
			}
		else if(turn==0) {
			g2D.drawString("TURN: O", (float) (w/2)-40, 640);
			}
//		}
		
		if(end.isEmpty()) {
			
			//run();
			
			repaint();
		}
		else {

//			boolean over=true;
//			addKeyListener(this);
//			for(int i=0;i<1000;i++) {
				
//				//scale=true;
//				run();
//			}
			
			end(g2D);
			//g2D.scale(1, 1);
			resetbutton(g2D);
			//res=false;
			repaint();
			//reset();
			//res=true;
		}
		
//		if(res==true) {
//			//repaint();
//			System.out.print("Press R to REPLAY: ");
//	        char again = sc.nextLine().charAt(0);
//	        if(again=='r') {
//	        	reset();
//	        	repaint();
//	        }
//	        else System.exit(0);
//		}//was causing some unidentifiable bugs
		
	}
	private void resetbutton(Graphics2D g2D) {
		// TODO Auto-generated method stub
		g2D.fillRect((int) (w/2-40), (int)h, 120, 60);
		g2D.setColor(Color.WHITE);
		g2D.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g2D.drawString("  RESET  ", (float)w/2-40, 640);
	}
	
	private void end(Graphics2D g2D) {
//		g2D.scale(scl, scl);
//		scl+=0.001;
		// TODO Auto-generated method stub
		g2D.setColor(Color.BLACK);
		g2D.fillRect((int) (w/2-40), 600, 120, 60);
		g2D.setColor(Color.WHITE);
		if(turn==1&&end.elementAt(0)!=4) {//because the person with previous turn will be the winner hence this opposite mapping
			g2D.drawString("WINNER: O", 50, 640);
		}
		else if(turn==0&&end.elementAt(0)!=4) {
			g2D.drawString("WINNER: X", 50, 640);
		}
		g2D.setColor(Color.RED);
		g2D.setStroke(new BasicStroke(10));
		if(end.elementAt(0)==0) {//horizontal win
			g2D.draw(new Line2D.Double(0,end.elementAt(1)*h/3 + h/6,w,end.elementAt(1)*h/3 + h/6));
		}
		else if(end.elementAt(0)==1) {//vertical win
			g2D.draw(new Line2D.Double(end.elementAt(1)*w/3 + w/6,0,end.elementAt(1)*w/3 + w/6,h));
		}
		else if(end.elementAt(0)==2) {//main diagonal win
			g2D.draw(new Line2D.Double(0,0,w,h));
		}
		else if(end.elementAt(0)==3) {//other diagonal win
			g2D.draw(new Line2D.Double(w,0,0,h));
		}
		else if(end.elementAt(0)==4) {
			g2D.setColor(Color.WHITE);
			g2D.drawString("  A TIE  ", 50, 640);
		}
		//game over
		g2D.setColor(Color.BLACK);
		g2D.fillRect((int) 0, 190-40, (int)w, 100);
		g2D.setFont(new Font("TimesRoman", Font.BOLD, 90));
		g2D.setColor(Color.RED);
		g2D.drawString("GAME OVER", 25, 190+40);
		g2D.setColor(Color.BLACK);
		
	}
	private void draw(Graphics2D g2D) {
		// TODO Auto-generated method stub
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				double rad=50;
				double cx=j*w/3 + w/6;
				double cy=i*h/3 + h/6;
				if(board[i][j]=="X") {
					g2D.draw(new Line2D.Double(cx-rad, cy-rad, cx+rad, cy+rad));
					g2D.draw(new Line2D.Double(cx+rad, cy-rad, cx-rad, cy+rad));
				}
				else if(board[i][j]=="O") {
					g2D.draw(new Ellipse2D.Double(cx-rad, cy-rad, 2*rad, 2*rad));
				}
			}
		}
		return;
	}
	double x=1000,y=1000;
	@Override
	public void run() {
		
		try {
			Thread.sleep(2000);
			//repaint();
			//reset();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		arg0.consume();
		x=arg0.getX();
		y=arg0.getY();
		if(x>w/3&&x<2*w/3&&y>2*h/3&&y>3*h/3) {//reset condition
			reset();
			return;
		}
		if(start==1) {
			display();
			end.addAll(check());
			
			if(end.isEmpty()&&turn==0) {
				computer();
				end.addAll(check());
			}
		}
		else if(start==0){//computer will start even when mouse is not pressed so it should be written in paint() function instead
			//computer();
			//end.addAll(check());
			//repaint();
			if(end.isEmpty()) { // not really needed i guess
				mouseexists=false;
				display();
				end.addAll(check());
			}
		}
	}
	public void display() {
		// TODO Auto-generated method stub
		if(!end.isEmpty())// logic to avoid post game moves before reseting because that might change the turn
			return;
		//logic for display
		if(x<w/3&&y<h/3) {
			if(board[0][0]=="") {
				turn += 1;
				board[0][0]="X";
			}
		}
		else if(x>w/3&&x<2*w/3&&y<h/3) {
			if(board[0][1]=="") {
				turn += 1;
				board[0][1]="X";
			}
		}
		else if(x>2*w/3&&x<3*w/3&&y<h/3) {
			if(board[0][2]=="") {
				turn += 1;
				board[0][2]="X";
			}
		}
		else if(x<w/3&&y>h/3&&y<2*h/3) {
			if(board[1][0]=="") {
				board[1][0]="X";
				turn += 1;
			}
		}
		else if(x>w/3&&x<2*w/3&&y>h/3&&y<2*h/3) {
			if(board[1][1]=="") {
				turn += 1;
				board[1][1]="X";
			}
		}
		else if(x>2*w/3&&x<3*w/3&&y>h/3&&y<2*h/3) {
			if(board[1][2]=="") {
				board[1][2]="X";
				turn += 1;
			}
		}
		else if(x<w/3&&y>2*h/3&&y<3*h/3) {
			if(board[2][0]=="") {
				board[2][0]="X";
				turn += 1;
			}
		}
		else if(x>w/3&&x<2*w/3&&y>2*h/3&&y<3*h/3) {
			if(board[2][1]=="") {
				turn += 1;
				board[2][1]="X";
			}
		}
		else if(x>2*w/3&&x<3*w/3&&y>2*h/3&&y<3*h/3) {
			if(board[2][2]=="") {
				board[2][2]="X";
				turn += 1;
			}
		}
		//turn += 1;
		turn = turn%2;
		//System.out.println(turn);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	private void computer() {
		int moveI=-1,moveJ=-1,score=-99999999;
		int tempI = (new Random()).nextInt(3);
		int tempJ = (new Random()).nextInt(3);
		for(int j=tempI,cnti=0;cnti<3;j++,j%=3,cnti++) {//make it random because cold start will always be computer at (0,0)
			for(int k=tempJ,cntj=0;cntj<3;k++,k%=3,cntj++) {
				if(board[j][k]=="") {
					board[j][k]="O";
					end.clear();
					int temp = minimax(false);
					end.clear();
					board[j][k]="";
					if (temp>score) {
						score=temp;
						moveI=j;
						moveJ=k;
					}
				}
			}	
		}
		board[moveI][moveJ]="O";
		turn += 1;
		turn %= 2;
		return;
	}
	
	private int minimax(boolean isMax) {
		//terminating
		if(end.addAll(check())) {
			if(!end.isEmpty())
				return end.elementAt(2);
		}
		if(isMax) {
			int best = -9999999;
			for(int j=0;j<3;j++) {
				for(int k=0;k<3;k++) {
					if(board[j][k]=="") {
						board[j][k]="O";
						end.clear();
						int temp = minimax(false);
						end.clear();
						board[j][k]="";
						best = Math.max(best,temp);
					}
				}
			}
			return best;
		}
		else {
			int best = 9999999;
			for(int j=0;j<3;j++) {
				for(int k=0;k<3;k++) {
					if(board[j][k]=="") {
						board[j][k]="X";
						end.clear();
						int temp = minimax(true);
						end.clear();
						board[j][k]="";
						best = Math.min(best,temp);
					}
				}
			}
			return best;
		}
		
	}
	public Vector<Integer> check() {
		//draw condition
		int cnt=0;
		for(int j=0;j<3;j++)
			for(int k=0;k<3;k++)
				if(board[j][k]=="") cnt++;
		// checks for winning logic
		for(int i=0;i<board.length;i++) { //check all rows
			if(board[i][0]==board[i][1]&&board[i][1]==board[i][2]&&board[i][0]!="") { 
				//draw red line
				Vector<Integer> v= new Vector<Integer>();
				v.add(0);
				v.add(i);
				if(board[i][0]=="X") {
					v.add(-1);
				}
				else if(board[i][0]=="O") {
					v.add(1);
				}
				else v.add(0);
				return v;
			}
		}
		for(int i1=0;i1<board.length;i1++) { //check all columns
			if(board[0][i1]==board[1][i1]&&board[1][i1]==board[2][i1]&&board[0][i1]!="") {
				//draw red line
				
				Vector<Integer> v= new Vector<Integer>();
				v.add(1);
				v.add(i1);
				if(board[0][i1]=="X") {
					v.add(-1);
				}
				else if(board[0][i1]=="O") {
					v.add(1);
				}
				else v.add(0);
				return v;
			}
		}
		//check main diagonal
			if(board[0][0]==board[1][1]&&board[1][1]==board[2][2]&&board[0][0]!="") {
				//draw red line
				
				Vector<Integer> v = new Vector<Integer>();
				v.add(2);
				v.add(0);
				if(board[0][0]=="X") {
					v.add(-1);
				}
				else if(board[0][0]=="O") {
					v.add(1);
				}
				else v.add(0);
				return v;
			}
		//check other diagonal
			if(board[0][2]==board[1][1]&&board[1][1]==board[2][0]&&board[0][2]!="") {
				//draw red line
				
				Vector<Integer> v= new Vector<Integer>();
				v.add(3);
				v.add(0);
				if(board[1][1]=="X") {
					v.add(-1);
				}
				else if(board[1][1]=="O") {
					v.add(1);
				}
				else v.add(0);
				return v;
			}
			else if(cnt==0) {
				//turn=-1;
				Vector<Integer> v= new Vector<Integer>();
				v.add(4);
				v.add(0);
				v.add(0);
				return v;
			}
			else
				return end;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyChar()=='r') {
			//reset();
			//repaint();
		}
	}

	public void reset() {
			start+=1;
			start%=2;
			if(turn==1&&end.elementAt(0)!=4) {
					scoreo++;
			}
			else if(turn==0&&end.elementAt(0)!=4)
				scorex++;
        	for(int j=0;j<3;j++)
        		for(int k=0;k<3;k++)
        			board[j][k]="";
        	//turn =1;//mini functionality here to change first move to other player hence proper score record
//        	turn+=1;
//        	turn%=2;
        	turn = start;
        	scl=1;
        	mouseexists = false;
        	end.clear();
        	res=false;
        	//repaint();
        	System.out.println("New Game Started");
        	System.out.println("SCORE is X: "+scorex+", O: "+scoreo);
        	return;
        
        //else System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
