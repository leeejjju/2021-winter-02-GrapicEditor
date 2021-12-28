package winterProject2_graphicEditor;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;


//메인 클래스 
public class main extends JFrame {
   /*
    *정보
    2021-12-23-12:00~ 2021-12-28-17:00
    2021-winter 과제2, 그래픽 에디터 만들기 
    HGU 전산전자공학부_ 22100579 이진주
    */
   private static final long serialVersionUID = 1L;

   /*
    구현해야 할 기능들은..
    1. 그리기 기능
       라인, 사각형, 원, 스케치, 텍스트 base +패턴(스탬프)
       폴리라인, 스프레이,  option
    2. 속성설정 기능
       색상, 굵기 base
       스타일(점선 실선 등), 채우기(폐곡선) option
    3. 부가기능
       지우개(픽셀단위, 오브젝단위), 세이브앤로드 base  (안되면 그룹핑)
       드래그앤그랍, 러버밴드, 리사이즈, 로테이트, 뒤로가기, 그룹핑, 복붙 option
    4. 하나 더!! 뭐할까... 역시 뒤로가기? 
    
     */

   
   //캔버스 만들 정보 입력받는 창
   public static void makeFrame() {
      
      //기본프레임 
      JFrame setting = new JFrame("setting"); //새로운 프레임 생성 
      setting.setSize(400, 200); //프레임의 사이즈 설정
      setting.setResizable(false);//사용자가 임의로 프레임의 크기를 변경시킬 수 있는가>> 앙대
      setting.setLocationRelativeTo(null);//화면의 어느 위치에서 첫 등장할지>> null이면 자동 센터지정 
      setting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//프레임 닫으면 프로그램도 같이 종료.
      setting.setLayout(null); //레이아웃설정
      
      JLabel Title = new JLabel("INPUT THE TITLE AND SIZE OF NEW CANVAS"); //라벨로 설명 
      Title.setBounds(60, 0, 300, 50);
      setting.add(Title); //라벨 프레임에 넣기
      setting.setVisible(true);
      
      //입력창과 라벨.. 
      JLabel giveMeTitle = new JLabel("title: ");
      giveMeTitle.setBounds(30,40,100,50);
      JLabel giveMeSize = new JLabel("size: ");
      giveMeSize.setBounds(30,65,100,50);
      JLabel wh = new JLabel("width:height");
      wh.setBounds(170, 78, 100, 25);
      setting.add(giveMeTitle);
      setting.add(giveMeSize);
      setting.add(wh);
      
      //선택버튼
      JButton confirm = new JButton("confirm");
      confirm.setBounds(100,110,200,30);
      confirm.setBackground(Color.LIGHT_GRAY);
      setting.add(confirm);   
      
      //*********************whyrano*******************************************************
      JTextField getTitle = new JTextField(50);
      getTitle.setBounds(70,55,270,25);
      JTextField getSizeX = new JTextField();
      getSizeX.setBounds(70,80,100,25);
      JTextField getSizeY = new JTextField();
      getSizeY.setBounds(240,80,100,25);

      setting.add(getTitle);
      setting.add(getSizeX);
      setting.add(getSizeY);
      
      
      //버튼의 동장: 누르면 입력값 저장되고 세팅창 닫히고 입력정보 토대로 생성된 그림판 불러오기 
      confirm.addActionListener(event ->{
         
         String title = "newCanvas";
         int sizeX = 840, sizeY = 540;
         
         if(getTitle.getText().length() != 0) {
            title = getTitle.getText();
         }else {
            System.out.println("캔버스 이름이 초기값으로 설정됩니다");
         }
         
         if(getSizeX.getText().length() != 0) {
            sizeX = Integer.parseInt(getSizeX.getText());         
         }else {
            System.out.println("캔버스 가로길이가 초기값으로 설정됩니다");
         }
         
         if(getSizeY.getText().length() != 0) {
            sizeY = Integer.parseInt(getSizeY.getText());         
         }else {
            System.out.println("캔버스 세로길이가 초기값으로 설정됩니다");
         }
         
         
         setting.setVisible(false); //세팅창 닫기
         
         System.out.println("Enjoy your drawing :D"); //따땃한 말한마디
         
         new GraphicEditor(title, sizeX, sizeY); //그림판을 소환하고 턴을 마친다
         GraphicEditor.graphicEditor();
   
      });
      
   }
   
   
   //메인 메소드 - 그저 모든걸 실행함...  
   public static void main(String[] args) {
      makeFrame();
      
   }

   //해치웠나?
}

//************************************************************************

class state{
   //현재 프로그램의 색, 모드, 굵기를 계속 저장해둘 공간. 
   static int m = 0; //모드넘버 
   static int c = 0; //컬러넘버
   static int t = 3; //굵기넘버 
   static int mS = 0; //스탬프 모양... 0: 원, 1:사각형 
   static int mL = 0; //펜 타입. 0:실선 1:점선 
   
}

//그림판...? 
class GraphicEditor{
   //기본설정
   static String title;
   static int sizeX, sizeY;
   static JFrame frame;
   //static JPanel newCanvas;
   static JLabel newCanvas;
   static BufferedImage B;
   static JTextField newTextField;
   static JLabel newLabel;
   static ImageIcon Board;
   static BufferedImage preB;
   
   
   //생성자로 초기화 
   GraphicEditor(String t, int x, int y){
      if(t.length() != 0) title = t;
      if(x != 0) sizeX = x;
      if(y != 0) sizeY = y;
   }
   
   //그림판 틀 
   
   public static void graphicEditor() {
      //기본프레임
      frame = new JFrame(title);
      frame.setSize(sizeX,sizeY);
      frame.setVisible(true);
      frame.setResizable(false);//사용자가 임의로 프레임의 크기를 변경시킬 수 있는가>> 앙대
      frame.setLocationRelativeTo(null);//화면의 어느 위치에서 첫 등장할지>> null이면 자동 센터지정 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//프레임 닫으면 프로그램도 같이 종료.
      frame.setLayout(new BorderLayout()); //레이아웃설정 볼더로

      canvas(); //그리기영역 소환
      
      
      //하단 인포영역 표시할 패널 만들고 거따 집어넣어준당 
      JPanel info = new JPanel(); //패널만들고
      info.setLayout(new FlowLayout(FlowLayout.LEFT)); //레이아웃 좌측정렬로 지정하구
      frame.add(info,BorderLayout.SOUTH); //프레임 아래쪽에 넣어주기
      new showInfo(); //속에 요소들로 쓸 인스턴트 생성
      showInfo.inputOptions(info); //옵션표시 집어넣어두기 

      
      //버튼들..? 들어갈 툴바 
      JMenuBar tools = new JMenuBar(); //패널로 만들구
      tools.setLayout(new GridLayout(1,0));//한줄배치되는 그리드레이아웃으로  
      frame.add(tools, BorderLayout.NORTH); //위쪾에 바짝붙이기 
      
      
      //버튼들을 반복문+배열로 넣으면 중복이 좀 줄어드려나? 아님 버튼을 추가하는 메소드를 만드까 
      JButton tool[] = new JButton[8]; //추가한 버튼들 차례대로 저장할 배열 
      String[] nameOfTools = {"back", "#지우개", "#펜", "#스탬프", "#직선", "#네모 ", "#원", "T"};
      for(int i = 0; i < 8; i++) {
         tool[i] = addButton(nameOfTools[i], i); //키워드의 이름으로 버튼을 만들고
         tools.add(tool[i]);//패널에 추가 
         final int n = i;
         //초간단 기능추가.. 
         if(i == 0) {
            tool[i].addActionListener(event ->{ 
               //버퍼이미지에다가 파일에서 읽어온 저장된이미지 넣고 
               preB = null;
               try {
               preB = ImageIO.read(new File("C:\\Java\\workspace\\winterProject2_graphicEditor\\imagesForButton\\preWork.jpg"));
            } catch (IOException e1) {
               e1.printStackTrace();
            }
 
               //그놈을 메인버퍼이미지랑 뉴캔버스 라벨에 그려주기... 화질구지네요 
               Graphics2D a = (Graphics2D)newCanvas.getGraphics();
               Graphics2D pre = (Graphics2D)B.getGraphics();
               a.drawImage(preB, null, 0,0);
               pre.drawImage(preB, null, 0,0);
   
            });
         }else{
            tool[i].addActionListener(event ->{ //초간단 기능추가.. 
               state.m = n; //모드변경
               //스탬프면 거시기 누를때마다 모양바뀌는것도 추가
               if(n == 3){
            	   if(state.mS < 2) {
                       state.mS++;
                    }else{
                       state.mS = 0;
                    }
                  img = new ImageIcon(path+nameOfStamps[state.mS]);
                  tool[n].setIcon(img);
               }
               //라인타입 바꿔주는 거시긴디... 
               else if(n == 2) {
                  if(state.mL == 0) {
                     state.mL = 1;
                  }else if(state.mL == 1) {
                     state.mL = 0;
                  }
                  img = new ImageIcon(path+nameOfPens[state.mL]);
                  tool[n].setIcon(img);
               }
               showInfo.F5info(); //인포 새로고침
            });
         }
      }
   

      //마우스이벤트!!
      newCanvas.addMouseListener(new MouseAdapter() { 
         //좌표로 쓸거에용 
         int x1, x2, y1, y2;
   
         //마우스클릭시 
         public void mouseClicked(MouseEvent e) {
            //해당 순간의 좌표를 여따 담아두고 
            x1 = e.getX();
            y1 = e.getY();
            
            //스탬프
            if(state.m == 3) {
               Graphics2D A = (Graphics2D)newCanvas.getGraphics();
               Graphics2D b = (Graphics2D)B.getGraphics();
               showInfo.setState(A, false);
               showInfo.setState(b, false);

               if(state.mS == 0) { //원모양 스탬프
                  A.fillOval(x1-state.t/3*10, y1-state.t/3*10, state.t/3*20, state.t/3*20); 
                  b.fillOval(x1-state.t/3*10, y1-state.t/3*10, state.t/3*20, state.t/3*20); 
               }else if(state.mS == 1) { //사각형 스탬프
                  A.fillRect(x1-state.t/3*10, y1-state.t/3*10, state.t/3*20, state.t/3*20);
                  b.fillRect(x1-state.t/3*10, y1-state.t/3*10, state.t/3*20, state.t/3*20);
               }else if(state.mS == 2) { //둥근사각 스탬프
                  A.fillRoundRect(x1-state.t/3*10, y1-state.t/3*10, state.t/3*20, state.t/3*20, state.t/3*10, state.t/3*10);
                  b.fillRoundRect(x1-state.t/3*10, y1-state.t/3*10, state.t/3*20, state.t/3*20, state.t/3*10, state.t/3*10);
               }
               A.dispose();
               b.dispose();
            }
            
            //텍스트
            else if(state.m == 7) {
               newTextField = new JTextField(); //텍스트필드 만들고
               newTextField.setBorder(null); //테두리 없애주고
               newTextField.setOpaque(false); //투명하게?해준다는데?머가적용된거람 
               newTextField.setBounds(x1-10, y1-10, 200, 20); //클릭한곳에서 배치하고... 
               newCanvas.add(newTextField); //캔버스에 추가... 

               //텍스트필드에서 엔터 클릭되었을 때
               newTextField.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                     //엔터누르면>> 텍스트필드 내용 저장하고 초기화하고 숨기기, 저장한 내용으로 라벨만들어 거따 부착하기. 
                     String newTextString = newTextField.getText(); //로컬 문자열에 내용 임시저장한다음
                     newTextField.setVisible(false); //텍필 안보이게하고
                     newCanvas.remove(newTextField);//텍스트필드 없애버리고
                     
                     JLabel label = new JLabel(newTextString); //라벨에 위치내용 그대로 쓰기
                     label.setBounds(x1-10, y1-10, 200, 20);
                     newCanvas.add(label);

                     //버퍼이미지에도 글씨 넣고싶은데 웨않됀데@@@@@@@@@@@@@@@@@@@@@@@
                     Graphics2D b = (Graphics2D)B.getGraphics();
                     b.drawString(newTextString, x1-10, y1-10);
                     b.dispose();
                     
                     
                     
                  }
               });
            }   
         }
         
         //마우스가 눌렸을 떄
         public void mousePressed(MouseEvent e) { 
            x1 = e.getX(); //야무지개 그순간의 좌표 저장 
            y1 = e.getY();
            PreSave.preSave();

         }
         
         //마우스가 떨어졌을 때
         public void mouseReleased(MouseEvent e) {
            x2 = e.getX(); //역시 그순간의 좌표 저장
            y2 = e.getY();
            
            Graphics2D A = (Graphics2D)newCanvas.getGraphics();
            Graphics2D b = (Graphics2D)B.getGraphics();
            showInfo.setState(A, false);
            showInfo.setState(b, false);
            
            //직선
            if(state.m == 4) {
               A.drawLine(x1, y1, x2, y2);
               b.drawLine(x1, y1, x2, y2);
               
            }
            //사각형
            else if(state.m == 5) { 
               A.drawRect((x1<x2)? x1:x2, (y1<y2)? y1:y2, Math.abs(x2-x1), Math.abs(y2-y1));
               b.drawRect((x1<x2)? x1:x2, (y1<y2)? y1:y2, Math.abs(x2-x1), Math.abs(y2-y1));
               
            }
            //원
            else if(state.m == 6) {
               A.drawOval((x1<x2)? x1:x2, (y1<y2)? y1:y2, Math.abs(x2-x1), Math.abs(y2-y1));
               b.drawOval((x1<x2)? x1:x2, (y1<y2)? y1:y2, Math.abs(x2-x1), Math.abs(y2-y1));
               
            }
            
            A.dispose();
            b.dispose();

         }
      
      });

      
      //색상선택 패널
      JPanel palette1 = new JPanel();
      palette1.setLayout(new GridLayout(2,3));
      tools.add(palette1);
      JPanel palette2 = new JPanel();
      palette2.setLayout(new GridLayout(2,3));
      tools.add(palette2);
      
      //색선택하는 버튼들 12개(6*2)
      JButton colors[] = new JButton[12]; //추가한 버튼들 차례대로 저장할 배열 
      for(int i = 0; i < 12; i++) {
         //버튼만들고 패널에 추가 
         colors[i] = new JButton(); 
         if(i < 6) {
            palette1.add(colors[i]);
         }else {
            palette2.add(colors[i]);
         }
         final int n = i;
         colors[i].addActionListener(event ->{ //초간단 기능추가.. 
            state.c = n; //컬러설정 변경 
            showInfo.F5info(); //라벨 새로고침 
         });
      }
   
      //버튼들 색 지정..(반복문으로 처리하려면.. rgb코드 써야하려나..)
      colors[0].setBackground(Color.black);
      colors[1].setBackground(Color.DARK_GRAY);
      colors[2].setBackground(Color.LIGHT_GRAY);
      colors[3].setBackground(Color.red);
      colors[4].setBackground(Color.orange);
      colors[5].setBackground(Color.yellow);
      colors[6].setBackground(Color.WHITE);
      colors[7].setBackground(Color.cyan);
      colors[8].setBackground(Color.PINK);
      colors[9].setBackground(Color.green);
      colors[10].setBackground(Color.blue);
      colors[11].setBackground(Color.magenta);

      
      //굵기선택
      JButton thickness = addButton("#thicknes", 8);
      tools.add(thickness);
      thickness.addActionListener(event ->{ //초간단 기능추가.. 
         if(state.t < 15) {
            state.t += 3;
         }else{
            state.t = 3;
         }
         showInfo.F5info(); //라벨 새로고침 
         img = new ImageIcon(path+nameOfThickness[state.t/3-1]);
         thickness.setIcon(img);
      });
      
      
      //초기화 버튼
      JButton eraseAll =addButton("clear", 0);
      tools.add(eraseAll);
      eraseAll.addActionListener(event->{
         newCanvas.removeAll(); //컴포넌트(텍슽) 지우고
         newCanvas.repaint(); //그림그린것들도 지우고
         Graphics2D BG = B.createGraphics(); //버퍼이미지도 흰색으로 세탕 
         BG.setColor(Color.white);
         BG.fillRect(0,0,sizeX,sizeY);
         BG.dispose();
      });
      
      
      //저장버튼 
      JButton save =addButton("save", 0);
      tools.add(save);
      save.addActionListener(event->{
    	 String path = "C:\\Java\\workspace\\winterProject2_graphicEditor\\imagesForButton\\";

         try{
         File file = new File(path+title+".jpg");        // 파일의 이름을 설정한다
         ImageIO.write(B, "jpg", file);                     // write메소드를 이용해 파일을 만든다
         System.out.println(title+" 제목으로 저장되었습니다");
         
         }catch(Exception e){
            e.printStackTrace();
         }
      });
      
   }
   

   static String[] nameOfTools = {"", "erase.png", "pen.png", "stamp.png", "line.png", "rect.png", "circle.png", "", "thicknesss.png"};
   static String[] nameOfPens = {"pen01.png", "pen02.png"};
   static String[] nameOfStamps = {"stamp02.png", "stamp03.png", "stamp01.png"};
   static String[] nameOfThickness = {"thicknesss01.png","thicknesss02.png","thicknesss03.png","thicknesss04.png","thicknesss05.png"};
   static String path = "C:\\Java\\workspace\\winterProject2_graphicEditor\\imagesForButton\\";
   static ImageIcon img;

   //기본 버튼 생성 메소드. 하얀색의, 선택한 키워드로 네이밍된 버튼 포인터 반환. 단, 네이밍 앞이 #이면 이미지로.. 
   public static JButton addButton(String name, int i) {

      
      if(name.charAt(0) == '#') {
         
         if(i == 2) { //펜
        	 img = new ImageIcon(path+nameOfPens[state.mL]);
         }else if(i == 3) { //스탬프
        	 img = new ImageIcon(path+nameOfStamps[state.mS]);
         }else if(i == 8) { //굵기
        	 img = new ImageIcon(path+nameOfThickness[state.t/3-1]);
         }else {
        	 img = new ImageIcon(path+nameOfTools[i]);
         }

         JButton tool = new JButton(img); //해당 이미지의 버튼 하나 만들고
         tool.setBackground(Color.white); //버.꾸 하고
         return tool; //방금만든거랑 연동된 주,,소..? 반환하기
      }else {
         JButton tool = new JButton(name); //인자로 받은 이름의 버튼 하나 만들고
         tool.setBackground(Color.white); //버.꾸 하고
         return tool; //방금만든거랑 연동된 주,,소..? 반환하기
      }
      
   }
   
   // 캔버스영역...? ㅇㅒ는 창 종료 전까진 계속 유지되어야함 
   public static void canvas() {
      
      //라벨에 넣어줄 버퍼이미지
      B = new BufferedImage(sizeX,sizeY, BufferedImage.TYPE_INT_RGB);
      //캔버스역할 하는 라벨 하나 만들어서 버퍼이미지 추가
      GraphicEditor.Board = new ImageIcon(B);
      newCanvas = new JLabel(Board);
      
      Graphics2D BG = B.createGraphics(); //흰색으로 세탕 
      BG.setColor(Color.white);
      BG.fillRect(0,0,sizeX,sizeY);
      BG.dispose();
      
      
      frame.add(newCanvas, BorderLayout.CENTER);
      newCanvas.setLayout(null);
      newCanvas.setBackground(Color.WHITE);   

      
      //위에 툴바 추가하고
      JMenuBar tools = new JMenuBar();
      frame.setJMenuBar(tools);
       
      //마우스 이벤트(드래그, 움직임)에 따라 죄측하단에 좌표표시. 
      newCanvas.addMouseMotionListener(new MouseMotionAdapter() { 
         int ox, oy; //직전 x,y좌표 임시저장할 공간
         public void mouseDragged(MouseEvent e) {  // 마우스를 누르고 드래그할때마다
  
            Graphics2D A = (Graphics2D)newCanvas.getGraphics();
            Graphics2D b = (Graphics2D)B.getGraphics();
            showInfo.setState(A, false);
            showInfo.setState(b, false);

            //자유선(펜)긋기 
            if(state.m == 2) {
               
               if(state.mL == 1) { //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@점선 손보기 
                  float[] dash=new float[]{state.t/3,state.t/3,state.t/3*5,state.t/3*7};
                  
                  try {
                     Thread.sleep(50);
                  } catch (InterruptedException e1) {
                     // TODO Auto-generated catch block
                     e1.printStackTrace();
                  }
                  
                   A.setStroke(new BasicStroke(state.t, 1,BasicStroke.CAP_ROUND, 0,dash, 30));
                   b.setStroke(new BasicStroke(state.t, 1,BasicStroke.CAP_ROUND, 0,dash, 30));
               }
               
               A.drawLine(e.getX(), e.getY(), ox, oy); 
               b.drawLine(e.getX(), e.getY(), ox, oy); 
            }
            //지우개
            else if(state.m == 1) { 
               showInfo.setState(A, true);
               A.drawLine(e.getX(), e.getY(), ox, oy);
               b.drawLine(e.getX(), e.getY(), ox, oy);
            }
            ox = e.getX();
            oy = e.getY();
            
            //좌표표시
            showInfo.currentPoint.setText("[x: "+e.getX()+", y: "+e.getY()+"]");
         }            
         public void mouseMoved(MouseEvent e) {  // 마우스를 움직일때마다
            ox = e.getX();
            oy = e.getY();
            //좌표표시 
            showInfo.currentPoint.setText("[x: "+e.getX()+", y: "+e.getY()+"]");
         }   
      }); 

   }
}


//현재 좌표랑 선택중 옵션 등을 표시하는 스태틱덩어리 클래스. 계속 외부에서 간섭해서 값이 바뀌어야함 
class showInfo {
   
   //나중에 버튼이벤트에 맞춰 이 값을 바꿀 수있도록. 인덱스와 일치함 
   static String nameOfColors[] = {"black", "dark gray", "lght gray", "red", "orange", "yellow", "white", "cyan", "pink", "green", "blue", "magenta"
   };
   static String nameOfMode[] = {"default", "eraser", "pen", "stamp", "line", "rectangle", "circle", "text"};
   static String nameOfStamp[] = {"circle", "rectangle", "round rectangle"};
   static String nameOfLine[] = {"Solid", "dotted"};
   
   static JLabel currentOption;
   static JLabel currentColor;
   static JLabel currentThickness;
   static JLabel currentPoint;
   
   showInfo(){
      currentOption = new JLabel(" | mode: "+nameOfMode[state.m]);
      currentColor = new JLabel(" | color: "+nameOfColors[state.c]);
      currentThickness = new JLabel(" | thickness: "+(state.t+1)*3+" px |");
      currentPoint = new JLabel();
   }

   // 일단 최초한번 넣어주고... 
   public static void inputOptions(JPanel info) {
      info.add(currentPoint);
      info.add(currentOption);
      info.add(currentColor);
      info.add(currentThickness);
   }
   
   //갱신? 새로고침? 
   public static void F5info() {
	   
      if(state.m == 3) { //스탬프모드일땐 그 도형상태까지 추가해서 표시되도록
         currentOption.setText(" | mode: "+nameOfMode[state.m]+" ("+nameOfStamp[state.mS]+")");
      }else if(state.m == 2){ //펜모드일때 이하생략
         currentOption.setText(" | mode: "+nameOfMode[state.m]+" ("+nameOfLine[state.mL]+")");
      }else {
         currentOption.setText(" | mode: "+nameOfMode[state.m]);
      }
      currentColor.setText(" | color: "+nameOfColors[state.c]);
      currentThickness.setText(" | thickness: "+state.t+" px |");
   }
   
   //속성설정 메소드
   public static void setState(Graphics2D a, boolean e){
      
      //선굵기 설정
      a.setStroke(new BasicStroke(state.t,BasicStroke.CAP_ROUND, 0));
      
      //색상인데 이거 반복문으로 안되나ㅜ 
      if(e) {
         a.setColor(Color.white);
         return;
      }
      if(state.c == 0) {
         a.setColor(Color.black);
      }else if(state.c == 1) {
         a.setColor(Color.DARK_GRAY);
      }else if(state.c == 2) {
         a.setColor(Color.LIGHT_GRAY);
      }else if(state.c == 3) {
         a.setColor(Color.red);
      }else if(state.c == 4) {
         a.setColor(Color.ORANGE);
      }else if(state.c == 5) {
         a.setColor(Color.YELLOW);
      }else if(state.c == 6) {
         a.setColor(Color.white);
      }else if(state.c == 7) {
         a.setColor(Color.CYAN);
      }else if(state.c == 8) {
         a.setColor(Color.PINK);
      }else if(state.c == 9) {
         a.setColor(Color.green);
      }else if(state.c == 10) {
         a.setColor(Color.BLUE);
      }else if(state.c == 11) {
         a.setColor(Color.magenta);
      } 
   }
}

class PreSave {
   public static void preSave() {
       try{
           File file = new File("C:\\Java\\workspace\\winterProject2_graphicEditor\\imagesForButton\\preWork.jpg");        // 파일의 이름을 설정한다
           ImageIO.write(GraphicEditor.B, "jpg", file); 
           
       }catch(Exception e1){
              e1.printStackTrace();
       }
   } 
}



//해치웠나? 


