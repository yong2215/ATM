package hw_1012;

import java.util.Scanner;

class Account
{
	String name; //������
	String AccNo; //���¹�ȣ
	int balance;  //�ܰ�
	String password; //��й�ȣ
	Account(String name, String AccNo, int balance, String password)
	{ // ���¸� �ʱ�ȭ.
		this.name = name; this.AccNo = AccNo; this.balance = balance;
		this.password = password;
	}
	void Deposit(int money)
	{
		System.out.println("���� : " + AccNo + "�� " + money + "���� �ԱݵǾ����ϴ�");
		balance += money; //�Աݾ׸�ŭ �ܰ� ����
	}
	int Withraw(int money, String password)
	{
		if(this.password.equals(password) == false)// ��й�ȣȮ��
		{
			System.out.println("��й�ȣƲ��");
			return 0;
		}
		if(money == 0)
		{
			System.out.println("0���� ������ �����ϴ�");
			return 0;
		}
		if(balance < money)// �ܰ�Ȯ��
		{
			System.out.println("�ܰ����");
			return 0;
		}
		balance -= money; //��ݾ׸�ŭ �ܰ� ����
		System.out.println("���� : " + AccNo + "���� " + money + "���� ��ݵǾ����ϴ�");
		return money; //��ݾ� ��ȯ
	}
	boolean Send(Account acc, int money, String password)
	{
		int sendmoney = Withraw(money, password);
		if (sendmoney > 0)
		{
			acc.Deposit(sendmoney);
			System.out.println("�۱��� �޷�Ǿ����ϴ�");
			return true; 
		}
		System.out.println("�۱��� ��ҵǾ����ϴ�");
		return false;
	}
	void ShowInfo() 
	{
		System.out.println("������ : " + name);
		System.out.println("���¹�ȣ : " + AccNo);
		System.out.println("�ܾ� : " + balance);
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
			System.out.println("������ ȭ��");
			System.out.print("1. ���»���  2.���»���   3.������Ȳ   4.���ư��� : ");
			int select = in.nextInt();
			switch(select)
			{
			case 1: //���»���
				int check = 0;
				in.nextLine(); //��¹��ۻ��
				System.out.println("������ : "); name = in.nextLine();
				System.out.println("���¹�ȣ : "); AccNo = in.nextLine();
				System.out.println("��й�ȣ : "); password = in.nextLine();
				System.out.println("�ܰ� : "); balance = in.nextInt();
				for(int i = 0;i < cnt;i++) {
					if(AccNo.equals(acc[i].AccNo)) {
					System.out.println("�̹� �����ϴ� �����Դϴ�.");
					check=1;
					break;
					}
				}
					if(check==0) {
					acc[cnt++] = new Account(name,AccNo,balance,password);
					System.out.println("���°� �����Ǿ����ϴ�.");
					break;
					}
				break;
			case 2:
				in.nextLine();
				int check1 = 0;
				System.out.println("������ ���¹�ȣ");
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
							System.out.println("���°� �����Ǿ����ϴ�");
							cnt = cnt - 1;
							break;
						}
					}	
				}
				else
					System.out.println("������ ���°� �����ϴ�");
				break;
			case 3: 
				System.out.println("������\t���¹�ȣ");
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
			System.out.println("��ȭ��");
			System.out.print("1.�Ա�  2.���  3.�۱�  4.��ȸ  5.�α׾ƿ� : ");
			int select = in.nextInt();
			switch(select)
			{
			case 1:
				System.out.println("�Ա� �ݾ� : ");
				money = in.nextInt();
				user.Deposit(money);
				break;
			case 2: 
				System.out.println("��� �ݾ� : ");
				money = in.nextInt();
				in.nextLine();
				System.out.println("��й�ȣ : ");
				password = in.nextLine();
				user.Withraw(money, password);
				break;
			case 3: 
				in.nextLine();
				System.out.println("���� �Է� : ");
				AccNo = in.nextLine();
				s = findAccountIndex(acc, cnt, AccNo);
				System.out.println("�۱� �ݾ� : ");
				money = in.nextInt();
				in.nextLine();
				System.out.println("��й�ȣ  : ");
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
		int cnt=0; //�ʱ� ���¼�
		Scanner in = new Scanner(System.in); //�Է��� ���� scanner ��ü
		
		//acc[0] = new Account("�����", "1234-1234", 10000, "1234");
		while(true)//����ȭ��� �޴� �ޱ�
		{
			System.out.println("����ȭ��");
			System.out.print("1.������ȭ��   2. �α���   3. ���� : ");
			int select = in.nextInt();
			switch(select)
			{
			case 1: cnt = ManagerMonitor(acc, cnt);
				break;
			case 2: 
				// �����Է�
				in.nextLine();// ��� ���� �ذ�
				System.out.print("���¹�ȣ : ");
				String AccNo = in.nextLine();
				
				// ���°� �ִ��� Ȯ��.
				int index = findAccountIndex(acc, cnt, AccNo);
				if(index >= 0) // ���°� �����ϸ� ��ȭ�� ȣ��
				{
					ClientMonitor(acc, cnt, acc[index]);
				}
				//
				break;
			case 3: return; //����
			}
		}
	}
}