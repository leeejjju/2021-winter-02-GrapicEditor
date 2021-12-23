package winterProject2_graphicEditor;
import javax.swing.*;
import java.awt.*;

//메인 클래스 
public class main extends JFrame {
	/*
	 *정보
	 2021-12-23-12:00~ 2021-12-28-17:00
	 2021-winter 과제2, 그래픽 에디터 만들기 
	 HGU 전산전자공학부_ 22100579 이진주
	 깃헙연동됨!
	 */
	private static final long serialVersionUID = 1L;

	/*
	 구현해야 할 기능들은..
	 1. 그리기 기능
	 	라인, 사각형, 원, 스케치, 텍스트 base
	 	폴리라인, 스프레이, 패턴(스탬프)
	 2. 속성설정 기능
	 	색상, 굵기 base
	 	스타일(점선 실선 등), 채우기(폐곡선)
	 3. 부가기능
	 	지우개(픽셀단위, 오브젝단위), 세이브앤로드
	 	드래그앤그랍, 러버밴드, 리사이즈, 로테이트, 뒤로가기, 그룹핑, 복붙
	 4. 하나 더!! 뭐할까... 역시 뒤로가기?
	 
	  */
	
	//속성설정 창이 먼저 뜨게해서... 그림판 사이즈랑 타이틀 입력받고 생성할까???? 
	static String title = "new work";
	static int sizeX = 700, sizeY = 500;
	
	public static void makeFrame() {
		
		//기본프레임 
		JFrame setting = new JFrame("setting"); //새로운 프레임 생성 
		setting.setSize(400, 200); //프레임의 사이즈 설정
		setting.setResizable(false);//사용자가 임의로 프레임의 크기를 변경시킬 수 있는가>> 앙대
		setting.setLocationRelativeTo(null);//화면의 어느 위치에서 첫 등장할지>> null이면 자동 센터지정 
		setting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//프레임 닫으면 프로그램도 같이 종료.
		setting.setLayout(new FlowLayout()); //레이아웃설정 플로우로 
		JLabel Title = new JLabel("_____________________ INPUT THE TITLE AND SIZE _____________________"); //라벨로 설명 
		setting.add(Title); //라벨 프레임에 넣기
		setting.show();
		
		
		//입력창부분 관리할 패널 
		JPanel inputs = new JPanel(); //생성
		inputs.setLayout(new FlowLayout()); //레이아웃설정 플로우로 
		inputs.setSize(400,200);
		setting.add(inputs); //프레임에 넣기 
		
		JLabel giveMeTitle = new JLabel("title: ");
		JLabel giveMeSize = new JLabel("size: ");
		inputs.add(giveMeTitle);
		inputs.add(giveMeSize);
		
		
		//선택버튼. 누르면 세팅창 닫히고 입력된 정보 토대로 그림판 불러오기 
		JButton confirm = new JButton("confirm");
		confirm.setBackground(Color.LIGHT_GRAY);
		inputs.add(confirm);	
		confirm.addActionListener(event ->{
			setting.hide();
			System.out.println("Enjoy your drawing :D");
			graphicEditor();
		});
		
		
		//*********************whyrano
		JTextField getTitle = new JTextField(title);
		//getTitle.setHorizontalAlignment(JTextField.RIGHT); 
		JTextField getSizeX = new JTextField(sizeX);
		//getSizeX.setHorizontalAlignment(JTextField.RIGHT); 
		JTextField getSizeY = new JTextField(sizeY);
		//getSizeY.setHorizontalAlignment(JTextField.RIGHT); 
		inputs.add(getTitle);
		inputs.add(getSizeX);
		inputs.add(getSizeY);
		
		
	}
	
	//그림판 틀 
	public static void graphicEditor() {
		
		//기본프레임
		JFrame frame = new JFrame(title);
		frame.setSize(sizeX,sizeY);
		frame.show();
		frame.setResizable(false);//사용자가 임의로 프레임의 크기를 변경시킬 수 있는가>> 앙대
		frame.setLocationRelativeTo(null);//화면의 어느 위치에서 첫 등장할지>> null이면 자동 센터지정 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//프레임 닫으면 프로그램도 같이 종료.
		frame.setLayout(new BorderLayout()); //레이아웃설정 볼더로
		
		//버튼들..? 들어갈 패널 
		JPanel tools = new JPanel();
		tools.setLayout(new GridLayout(0, 1));//한줄배치되는 그리드레이아웃으로  
		frame.add(tools, BorderLayout.NORTH);
		
		//버튼들을 반복문으로.. 배열처리해서 넣으면 중복이 좀 줄어드려나? 아님 버튼을 추가하는 메소드를 ㅏㅁㄴ들까 
		JButton line = new JButton("line");
		line.setBackground(Color.white);
		tools.add(line);
		
		//akdsadf
		
		
		

		
	}
	
	//지정한 이름의 버튼을 생성
	public void addButton(String name) {
		
	}
	

	//메인 메소드 
	public static void main(String[] args) {
		
		System.out.println("Start!");
		makeFrame();
		
	}
	
	
	//해치웠나?
}

