package winterProject2_graphicEditor;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.event.*;


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
		getTitle.setVisible(true);
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
	static int mS = 1; //스탬프 모양... 0: 원, 1:사각형 
	static int mL = 1; //펜 타입. 0:실선 1:점선 
	
}

//그림판...? 
class GraphicEditor{
	//기본설정
	static String title;
	static int sizeX, sizeY;
	static JFrame frame;
	static JPanel newCanvas;
	static JTextField newTextField;
	static JLabel newLabel;
	
	//생성자로 초기화 
	GraphicEditor(String t, int x, int y){
		if(t.length() != 0) title = t;
		if(x != 0) sizeX = x;
		if(y != 0) sizeY = y;
	}
	
	//그림판 틀 
	
	public static void graphicEditor() {
		System.out.println("그림판 소환!!!");
		
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
					System.out.println("되돌렸습니다 라고 하고싶슴니다");
					state.m = 0; //모드변경
					showInfo.F5info();	//인포 새로고침		
				});
			}else{
				tool[i].addActionListener(event ->{ //초간단 기능추가.. 
					state.m = n; //모드변경
					showInfo.F5info(); //인포 새로고침
					//스탬프면 거시기 누를때마다 모양바뀌는것도 추가
					if(n == 3){
						if(state.mS == 0) {
							state.mS = 1;
						}else if(state.mS == 1) {
							state.mS = 2;
						}else if(state.mS == 2) {
							state.mS = 0;
						}
					}
					//라인타입 바꿔주는 거시긴디... 
					else if(n == 2) {
						if(state.mL == 0) {
							state.mL = 1;
						}else if(state.mL == 1) {
							state.mL = 0;
						}
					}
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
					Graphics2D a = (Graphics2D) newCanvas.getGraphics(); //새 그래픽스 만들구
					showInfo.setState(a, false);

					if(state.mS == 0) { //원모양 스탬프
						a.fillOval(x1-state.t/3*10, y1-state.t/3*10, state.t/3*20, state.t/3*20); 
					}else if(state.mS == 1) { //사각형 스탬프
						a.fillRect(x1-state.t/3*10, y1-state.t/3*10, state.t/3*20, state.t/3*20);
					}else if(state.mS == 2) { //둥근사각 스탬프
						a.fillRoundRect(x1-state.t/3*10, y1-state.t/3*10, state.t/3*20, state.t/3*20, state.t/3*10, state.t/3*10);
					}
				}
				
				//텍스트
				else if(state.m == 7) {
					newTextField = new JTextField(); //텍스트필드 만들고
					newTextField.setBorder(null); //테두리 없애주고
					newTextField.setOpaque(false); //투명하게?해준다는데?머가적용된거람 
					newTextField.setBounds(x1-10, y1-10, 200, 20); //클릭한곳에서 배치하고... 
					newCanvas.add(newTextField); //캔버스에 추가... 
					
					//텍스트컬러 외주맡기고싶은딩... 
					
					//텍스트필드에서 엔터 클릭되었을 때
					newTextField.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//엔터누르면>> 텍스트필드 내용 저장하고 초기화하고 숨기기, 저장한 내용으로 라벨만들어 거따 부착하기. 
							String newTextString = newTextField.getText(); //로컬 문자열에 내용 임시저장한다음
							newCanvas.remove(newTextField);//텍스트필드 없애버리고 
							System.out.println(newTextString);
							
							//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@whyranoㅠㅠ 라벨은 안보이고 텍필도 안사라짐 
							newLabel = new JLabel("sfsdfsdf"); //그 문자열 내용을 담은 라벨 만들어서
							newLabel.setBounds(x1, y1, 200, 20); //위치에 배치해주고.. 
							newCanvas.add(newLabel); //추가해준당
						}
					});
				}	
			}
			
			//마우스가 눌렸을 떄
			public void mousePressed(MouseEvent e) { 
				x1 = e.getX(); //야무지개 그순간의 좌표 저장 
				y1 = e.getY();
			}
			
			//마우스가 떨어졌을 때
			public void mouseReleased(MouseEvent e) {
				x2 = e.getX(); //역시 그순간의 좌표 저장
				y2 = e.getY();
				
				Graphics a = newCanvas.getGraphics(); //그래픽스 만들어서 색설정 해주고 
				Graphics2D A = (Graphics2D)a;
				showInfo.setState(A, false);
				
				//직선
				if(state.m == 4) {
					A.drawLine(x1, y1, x2, y2);
				}
				//사각형
				else if(state.m == 5) { 
					A.drawRect((x1<x2)? x1:x2, (y1<y2)? y1:y2, Math.abs(x2-x1), Math.abs(y2-y1));
				}
				//원
				else if(state.m == 6) {
					A.drawOval((x1<x2)? x1:x2, (y1<y2)? y1:y2, Math.abs(x2-x1), Math.abs(y2-y1));
				}
			}
			
			Graphics r = newCanvas.getGraphics(); //그래픽스 만들어서 색설정 해주고 
			Graphics2D R = (Graphics2D)r;
			int rx, ry;
			
			//미리보기?같은걸 구현해보고싶었는데 잘 안되네 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			public void mouseDraged(MouseEvent e) {
				
				showInfo.setState(R, false);
				rx = e.getX(); //역시 그순간순간의 좌표 저장
				ry = e.getY();
				
				if(state.m == 4) {
					R.drawLine(x1, y1, rx, ry);
				}else if(state.m == 5) {
					
				}else if(state.m == 6) {
					
				}
				
				
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

		
		//굵기선택을 버튼으로 한다면? @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		JButton thickness = addButton("#thicknes", 8);
		tools.add(thickness);
		thickness.addActionListener(event ->{ //초간단 기능추가.. 
			if(state.t < 15) {
				state.t += 3;
			}else{
				state.t = 3;
			}
			showInfo.F5info(); //라벨 새로고침 
		});
		
		
		//초기화 버튼
		JButton eraseAll =addButton("clear", 0);
		tools.add(eraseAll);
		eraseAll.addActionListener(event->{
			//일케하는게 맞나싶음 근데ㅋㅋ 무지성초기화.. 
			newCanvas.setBackground(Color.black);
			newCanvas.setBackground(Color.white);
			newCanvas.removeAll();
		});
		
		
		//저장버튼
		JButton save =addButton("save", 0);
		tools.add(save);
		save.addActionListener(event->{
			System.out.println("저장되었다고 하고싶습니다.");
			//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@저장 구현...해야대는디... 
		});


	}


	//기본 버튼 생성 메소드. 하얀색의, 선택한 키워드로 네이밍된 버튼 포인터 반환. 단, 네이밍 앞이 #이면 이미지로.. 
	public static JButton addButton(String name, int i) {
		String[] nameOfTools = {"", "erase.png", "pen.png", "stamp.png", "line.png", "rect.png", "circle.png", "", "thicknesss.png"};
				
		if(name.charAt(0) == '#') {
			String path = "C:\\Java\\workspace\\winterProject2_graphicEditor\\imagesForButton\\";
			ImageIcon img = new ImageIcon(path+nameOfTools[i]);
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
		
		//캔버스역할 하는 패널 하나 만들어 하얗게 세팅하고 
		newCanvas = new JPanel();
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
  
				Graphics a = newCanvas.getGraphics();
				Graphics2D A = (Graphics2D)a;
				showInfo.setState(A, false);

				//자유선(펜)긋기 
				if(state.m == 2) {
					if(state.mL == 1) { //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@점선 손보기 
						float[] dash=new float[]{state.t/3,state.t/3,state.t/3*5,state.t/3*7};
					    A.setStroke(new BasicStroke(state.t, 1,BasicStroke.CAP_ROUND, 0,dash, 30));
					}
					A.drawLine(e.getX(), e.getY(), ox, oy); 
				}
				//지우개
				else if(state.m == 1) { 
					showInfo.setState(A, true);
					A.drawLine(e.getX(), e.getY(), ox, oy);
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
	static String nameOfStamp[] = {"rectangle", "round rectangle", "circle"};
	static String nameOfLine[] = {"dotted", "Solid"};
	
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
	
	//필요시 m,c,t에 직접 접근해서 값 변경시킨후 새로고침호출하면 될듯..? 
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
	
	//색상설정 메소드
	public static void setState(Graphics2D a, boolean e){
		
		a.setStroke(new BasicStroke(state.t,BasicStroke.CAP_ROUND, 0));
		
		//이거 반복문으로 안되나ㅜ 
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



/*
//지우개랑 펜, 스프레이 등 그리기도구가 갖구있어야 할 것들
interface drawingTools{
	
	
	
}

//라인, 사각형, 원 등 도형도구가 갖구있어야 할 것들
interface shapeTools {
	
	//조정값미리보기?(흐리게하면 좋고) 
	static void seePreview() {
		
	}
	
	//사이즈조절
	static void controlSize(){
		
	}
	
	//각도조절
	static void controlRotate(int rotate) {
		
	}
	
	//도형 지우기 
	static void deletShape() {
		
	}
	
	//색상변경
	static void changeColor(String color) {
		
	}
	
	//굵기변경
	static void changeThickness(int thickness) {
		
	}
	
}
*/

/*
 * 
 
 버튼, 메뉴리스트아이템, 텍스트필드의 이벤트객체 ActionEvent 
 텍스트필드나 아레아 전용이벤트  TextEvent
 컴포넌트(아마패널)에 작용할 마우스클릭 이벤트객체 MouseEvent
 
 	MouseListner 상속받으면 구현해야하는 어쩌구들 
	- mouseClicked(MouseEvent e) : 마우스를 클릭했을 때 호출
	- mouseEntered(MouseEvent e) : 마우스 커서가 컴포넌트 영역에 들어오면 호출
	- mouseExited(MouseEvent e) : 마우스 커서가 컴포넌트 영역에서 벗어나면 호출
	- mousePressed(MouseEvent e) : 마우스 버튼이 눌러지면 호출
	- mouseReleased(MouseEvent e) : 마우스 버튼이 눌러졌다 띄어지면 호출

 * 필드
 	MOUSE_CLICKED 마우스 버튼이 클릭된 경우 발생되는 이벤트 
 	MOUSE_ENTERED 마우스 커서가 컴포넌트 영역으로 들어왔을 때 발생되는 이벤트
	MOUSE_EXITED 마우스 커서가 컴포넌트 영역 밖으로 나가면 발생되는 이벤트 
	MOUSE_PRESSED 마우스 버튼이 눌러졌을 때 발생하는 이벤트 
	MOUSE_RELEASED 마우스 버튼이 눌렀다 띄었졌을 때 발생하는 이벤트 
	MOUSE_DRAGGED 마우스 버튼이 클릭된 상태에서 움직일 때 발생되는 이벤트 
	MOUSE_ MOVED 마우스 커서가 움직일 때 발생되는 이벤트 
 	
 *  메소드
	 getPoint() 마우스 이벤트가 발생한 좌표를 얻어온다. 
	 getX() 마우스 이벤트가 발생한 X좌표를 얻어온다. 
	 getY() 마우스 이벤트가 발생한 Y좌표를 얻어온다. 


 */
