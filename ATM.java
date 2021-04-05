package hw_1012;

import java.util.Scanner;

class Account
{
	String name; //예금주
	String AccNo; //계좌번호
	int balance;  //잔고
	String password; //비밀번호
	Account(String name, String AccNo, int balance, String password)
	{ // 계좌를 초기화.
		this.name = name; this.AccNo = AccNo; this.balance = balance;
		this.password = password;
	}
	void Deposit(int money)
	{
		System.out.println("계좌 : " + AccNo + "로 " + money + "원이 입금되었습니다");
		balance += money; //입금액만큼 잔고를 증가
	}
	int Withraw(int money, String password)
	{
		if(this.password.equals(password) == false)// 비밀번호확인
		{
			System.out.println("비밀번호틀림");
			return 0;
		}
		if(money == 0)
		{
			System.out.println("0원은 보낼수 없습니다");
			return 0;
		}
		if(balance < money)// 잔고확인
		{
			System.out.println("잔고부족");
			return 0;
		}
		balance -= money; //출금액만큼 잔고를 감소
		System.out.println("계좌 : " + AccNo + "에서 " + money + "원이 출금되었습니다");
		return money; //출금액 반환
	}
	boolean Send(Account acc, int money, String password)
	{
		int sendmoney = Withraw(money, password);
		if (sendmoney > 0)
		{
			acc.Deposit(sendmoney);
			System.out.println("송금이 왼료되었습니다");
			return true; 
		}
		System.out.println("송금이 취소되었습니다");
		return false;
	}
	void ShowInfo() 
	{
		System.out.println("예금주 : " + name);
		System.out.println("계좌번호 : " + AccNo);
		System.out.println("잔액 : " + balance);
	}
}

public class hw_1012_1 {

	static int ManagerMonitor(Account acc[], int cnt)
	{
		Scanner in = new Scanner(System.in);
		String name = "", AccNo = "", password = "";
		int balance = 0;
		while(true)
		{
			System.out.println("관리자 화면");
			System.out.print("1. 계좌생성  2.계좌삭제   3.계좌현황   4.돌아가기 : ");
			int select = in.nextInt();
			switch(select)
			{
			case 1: //계좌생성
				int check = 0;
				in.nextLine(); //출력버퍼사용
				System.out.println("예금주 : "); name = in.nextLine();
				System.out.println("계좌번호 : "); AccNo = in.nextLine();
				System.out.println("비밀번호 : "); password = in.nextLine();
				System.out.println("잔고 : "); balance = in.nextInt();
				for(int i = 0;i < cnt;i++) {
					if(AccNo.equals(acc[i].AccNo)) {
					System.out.println("이미 존재하는 계좌입니다.");
					check=1;
					break;
					}
				}
					if(check==0) {
					acc[cnt++] = new Account(name,AccNo,balance,password);
					System.out.println("계좌가 생성되었습니다.");
					break;
					}
				break;
			case 2:
				in.nextLine();
				int check1 = 0;
				System.out.println("삭제할 계좌번호");
				AccNo = in.nextLine();
				for(int k = 0 ; k < cnt ; k++) 
				{
					if(AccNo.equals(acc[k].AccNo)) 
					{
						check1 = 1;
						break;
					}
				}
				if(check1 == 1) 
				{
					for(int i = 0 ; i < cnt ; i++)
					{
						if(AccNo.equals(acc[i].AccNo)) 
						{
							for(int j = i ; j < cnt -1; j++) 
							{
								acc[j] = acc[j+1];
							}
							System.out.println("계좌가 삭제되었습니다");
							cnt = cnt - 1;
							break;
						}
					}	
				}
				else
					System.out.println("삭제할 계좌가 없습니다");
				break;
			case 3: 
				System.out.println("예금주\t계좌번호");
				System.out.println("=====================");
				for(int i=0; i<cnt; i++)
				{
					System.out.println(acc[i].name + "\t" + acc[i].AccNo);
				}
				break;
			case 4: 
				return cnt;
			}
		}
	}
	static void ClientMonitor(Account acc[], int cnt, Account user) {
		Scanner in = new Scanner(System.in);
		String name = "", AccNo = "", password = "";
		int money = 0;
		int s;
		while(true)
		{
			System.out.println("고객화면");
			System.out.print("1.입금  2.출금  3.송금  4.조회  5.로그아웃 : ");
			int select = in.nextInt();
			switch(select)
			{
			case 1:
				System.out.println("입금 금액 : ");
				money = in.nextInt();
				user.Deposit(money);
				break;
			case 2: 
				System.out.println("출금 금액 : ");
				money = in.nextInt();
				in.nextLine();
				System.out.println("비밀번호 : ");
				password = in.nextLine();
				user.Withraw(money, password);
				break;
			case 3: 
				in.nextLine();
				System.out.println("계좌 입력 : ");
				AccNo = in.nextLine();
				s = findAccountIndex(acc, cnt, AccNo);
				System.out.println("송금 금액 : ");
				money = in.nextInt();
				in.nextLine();
				System.out.println("비밀번호  : ");
				password = in.nextLine();
				user.Send(acc[s], money, password);
				break;
			case 4: 
				user.ShowInfo();
				break;
			case 5: return;
			}
		}
	}
	
	static int findAccountIndex(Account acc[], int cnt, String AccNo)
	{
		for(int i=0; i<cnt; i++)
		{
			if(acc[i].AccNo.equals(AccNo))
			{
				return i;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		Account acc[] = new Account[100];
		int cnt=0; //초기 계좌수
		Scanner in = new Scanner(System.in); //입력을 받을 scanner 객체
		
		//acc[0] = new Account("장용훈", "1234-1234", 10000, "1234");
		while(true)//시작화면과 메뉴 받기
		{
			System.out.println("시작화면");
			System.out.print("1.관리자화면   2. 로그인   3. 종료 : ");
			int select = in.nextInt();
			switch(select)
			{
			case 1: cnt = ManagerMonitor(acc, cnt);
				break;
			case 2: 
				// 계좌입력
				in.nextLine();// 출력 버퍼 해결
				System.out.print("계좌번호 : ");
				String AccNo = in.nextLine();
				
				// 계좌가 있는지 확인.
				int index = findAccountIndex(acc, cnt, AccNo);
				if(index >= 0) // 계좌가 존재하면 고객화면 호출
				{
					ClientMonitor(acc, cnt, acc[index]);
				}
				//
				break;
			case 3: return; //종료
			}
		}
	}
}