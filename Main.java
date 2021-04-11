import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
	Scanner input = new Scanner(System.in);
	
	static int max=30;
	int dateIncrement=0;
	int totalAircraft=0;
	int launchpad=0;
	int success=0;
	int failed=0;
	int successAveragePrice=0;
	int failedAveragePrice=0;
	int launchIndex=max+1;
	
	static String[] spacecraft = new String[max];
	static String[] description = new String[max];
	static int[] price = new int[max];
	static float[] failureRate = new float[max];
	static int[] status = new int[max];
	static String[] dateCreated = new String[max];
	
	int[] result = new int[max];
	
	public Main() {
		int menu = 0;
		do {
			cls();
			printMenu();
			try {
				menu = input.nextInt(); input.nextLine();
			} catch (Exception e) {
				System.out.println("You should input integer!");
			}
			if(menu==1) GoToLaunchPad();
			else if(menu==2) VehicleAssemblyBuilding();
			else if(menu==3) TrackingStation();
			else if(menu==4) NextDay();
			else if(menu==5) break;
		}while(true);
	}
	
	private void printMenu() {
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd MMMM yyyy");  
		LocalDateTime today = LocalDateTime.now(); 
		
		System.out.println("Current date: " + formater.format(today.plusDays(dateIncrement)));
		System.out.println();
		System.out.println("1. Go to launch pad");
		System.out.println("2. Vehicle assembly building");
		System.out.println("3. Tracking station");
		System.out.println("4. Next day");
		System.out.println("5. Exit");
		System.out.print(">> ");
	}
	
	private void GoToLaunchPad() {
		int option=0;
		do {
			cls();
			printAircraftRTL();
			printMenu1();
			try {
				option = input.nextInt(); input.nextLine();		
			}
			catch (Exception e) {
				
			}
			if(option == 1) Launch(launchIndex);
			else if(option == 2) MoveToAssemblyBuilding();
			else if(option == 3) break;
		}while(true);
	}
	
	private void MoveToAssemblyBuilding() {
		if(launchpad == 0) ;
		else if(launchpad == 1) {
			launchpad=0;
			for (int i = 0; i < totalAircraft; i++) {
				if(status[i] == 2) {
					status[i] = 1 ;		
				}
			}
		}
	}

	private void Launch(int i) {
		if(launchpad == 0) return;
		else if(launchpad == 1){
			String random = generateRandomNumber();
			String num;
			do {
				System.out.println("Launch code = [" + random + "]");
				System.out.print("Please retype the launch code: ");
				num = input.nextLine();
			}while(!num.equals(random));
			
			float random2 = (float)Math.random();
			
			if(random2 >= failureRate[i]) {
				System.out.println("Litoff!!");				
				countdown();
				status[i] = 4;
				System.out.println(spacecraft[i] + "Is now orbiting the earth.");
				success++;
				if(success == 1) successAveragePrice = price[i]; 
				else successAveragePrice = (successAveragePrice + price[i])/2;
			}
			else if(random2 < failureRate[i]) {
				status[i] = 3;
				System.out.println();
				System.out.println("Spacecraft  : " + spacecraft[i]);
				System.out.println("Description : " + description[i]);
				System.out.println("Price       : $" + price[i] + "M");
				System.out.println("Failure rate: " + failureRate[i]);
				System.out.println("Status      : " + getStatus(status[i]));
				System.out.println();
				System.out.println("The spacecraft exploded due to a system failure.");
				failed++;
				if(failed == 1) failedAveragePrice = price[i];
				else failedAveragePrice = (failedAveragePrice + price[i])/2;
			}
			launchpad = 0;
		}
	}

	private void countdown() {
		int random = (int)(Math.random()*10)+5;
		for (int i = random; i >= 0; i--) {
			System.out.println("Estimated time to reach the orbit: "+ i +"H");
			try{		
			   Thread.sleep(1000);
			}
			catch(InterruptedException ex){
				Thread.currentThread().interrupt();
			}
		}
	}

	private String generateRandomNumber() {
		int num1 = (int)(Math.random()*('Z'-'A'))+'A';
		int num2 = (int)(Math.random()*('z'-'a'))+'a';
		int num3 = (int)(Math.random()*('Z'-'A'))+'A';
		int num4 = (int)(Math.random()*('9'-'0'))+'0';
		int num5 = (int)(Math.random()*('9'-'0'))+'0';
		return "" + (char)num1 + (char)num2 + (char)num3 + (char)num4 + (char)num5;
	}

	private void printAircraftRTL() {
		int flag=0;
		for (int i = 0; i < totalAircraft; i++) {
			if(status[i] == 2) {
				flag=1;
				System.out.println("Spacecraft  : " + spacecraft[i]);
				System.out.println("Description : " + description[i]);
				System.out.println("Price       : $" + price[i] + "M");
				System.out.println("Failure rate: " + (int)(failureRate[i]*100) +"%");
				System.out.println("Status      : " + getStatus(status[i]));
				System.out.println("Date created: " + dateCreated[i]);
				System.out.println("Lifetime    : " + getLifeTime(dateCreated[i]) + " day(s)");				
			}
		}
		if(flag==0) System.out.println("Launch Pad is empty.");
	}

	private void printMenu1() {
		System.out.println();
		System.out.println("1. Launch");
		System.out.println("2. Move to assembly building");
		System.out.println("3. Back");
		System.out.print(">> ");
	}

	private void VehicleAssemblyBuilding() {
		int option=0;
		do {
			cls();
			printSpaceCraftInAssemblyBuilding();
			printMenu2();
			try {
				option = input.nextInt(); input.nextLine();		
			}
			catch (Exception e) {
				System.out.println("You should input integer! (1-5)");
			}
			if(option == 1) BuildSpaceCraft();
			else if(option == 2) SetSpaceCraft();
			else if(option == 3) SortByNameAlphabetically();
			else if(option == 4) SortByHighestFailureRate();
			else if(option == 5) break;
		}while(true);
	}
	
	private void printSpaceCraftInAssemblyBuilding() {
		int flag=0;
		for (int i = 0; i < totalAircraft; i++) {
			if(status[i] == 1) {
				flag=1;
				System.out.println("Spacecraft  : " + spacecraft[i]);
				System.out.println("Description : " + description[i]);
				System.out.println("Price       : $" + price[i] + "M");
				System.out.println("Failure rate: " + (int)(failureRate[i]*100) +"%");
				System.out.println("Status      : " + getStatus(status[i]));
				System.out.println("Date created: " + dateCreated[i]);
				System.out.println("Lifetime    : " + getLifeTime(dateCreated[i]) + "day(s)");
				System.out.println();
			}
		}
		if(flag == 0) System.out.println("Assembly building is empty.");
	}
	
	private long getLifeTime(String end_date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");  

		LocalDateTime today = LocalDateTime.now(); 
		String start_date = formater.format(today.plusDays(dateIncrement));
		
		long difference_In_Days = 0;
		long difference_In_Time = 0;

		try {
	    	Date d1 = sdf.parse(end_date);
	        Date d2 = sdf.parse(start_date);
	
	        difference_In_Time = d2.getTime() - d1.getTime();
	
	        difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24));
	    }
	    catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return difference_In_Days;
	}

	private void printMenu2() {
		System.out.println("1. Build space craft");
		System.out.println("2. Set space craft");
		System.out.println("3. Sort by name alphabetically");
		System.out.println("4. Sort by highest failure rate");
		System.out.println("5. Exit");
		System.out.print(">> ");
	}

	private void BuildSpaceCraft() {
		do {
			System.out.print("Name [2-20 characters & unique]: ");
			spacecraft[totalAircraft] = input.nextLine();
		}while(spacecraft[totalAircraft].length() < 2);
		do {
			System.out.print("Price (in million dollar) [50 - 5000]: ");
			price[totalAircraft] = input.nextInt(); input.nextLine();
		}while(price[totalAircraft] < 50 || price[totalAircraft] > 5000);
		do {
			System.out.print("Description [10 -50 characters]: ");
			description[totalAircraft] = input.nextLine();
		}while(description[totalAircraft].length() < 10 || description[totalAircraft].length() > 50);
		do {
			System.out.print("Input creation date [dd/mm/yyyy] [ex. 26/02/2021]: ");
			dateCreated[totalAircraft] = input.next(); input.nextLine();
			
			if(Pattern.matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]", dateCreated[totalAircraft])) {
				String[] splitedDate = dateCreated[totalAircraft].split("/", 3);
				int day = Integer.parseInt(splitedDate[0]);
				int month = Integer.parseInt(splitedDate[1]);
				int year = Integer.parseInt(splitedDate[2]);
				
				if(isAllowed(year, month, day, dateCreated[totalAircraft])) break;
			}
			else if(!Pattern.matches("[0-9][0-9][0-9][0-9]/[0-9][0-9]/[0-9][0-9]", dateCreated[totalAircraft])) {
				System.out.println("Wrong input format!");					
			}
		}while(true);
		
		status[totalAircraft] = 1;
		
		if(price[totalAircraft] >= 0 && price[totalAircraft] <= 1600)
			failureRate[totalAircraft] = (float)(((Math.random())*(99-66))+66)/100;
		else if(price[totalAircraft] >= 1601 && price[totalAircraft] <= 3200)
			failureRate[totalAircraft] = (float)(((Math.random())*(66-34))+34)/100;
		else if(price[totalAircraft] >= 3201 && price[totalAircraft] <= 5000)
			failureRate[totalAircraft] = (float)(((Math.random())*(33-1))+1)/100;
	
		totalAircraft++;
	}

	private void SetSpaceCraft() {
		int option=0;
		do {
			printMenu2_2();
			try {
				option = input.nextInt(); input.nextLine();		
			}
			catch (Exception e) {
				System.out.println("You should input integer! (1-3)");
			}
			if(option == 1) UpdateSpaceCraft();
			else if(option == 2) MoveToLaunchPad();
			else if(option == 3) break;
		}while(true);
	}

	private void printMenu2_2() {
		System.out.println("1. Space space craft");
		System.out.println("2. Move to launch pad");
		System.out.println("3. Back");
		System.out.print(">> ");
	}

	private void UpdateSpaceCraft() {
		String nameToUpdate, name, description_;
		int price_, i;
		do {
			System.out.print("Space craft name to update [abort]: ");
			nameToUpdate = input.nextLine();
			if(nameToUpdate.equals("abort")) return;
			int flag =0;
			for (i = 0; i < totalAircraft; i++) {
				if(spacecraft[i].equals(nameToUpdate)) { 
					flag = 1;
					break;
				}
			}
			if(flag==0) break;
		}while(true);
		do {
			System.out.print("Name [2 - 20 characters & unique]: ");
			name = input.nextLine();
		}while(name.length() < 2 || name.length() > 20);
		do {
			System.out.print("Price (in million dollar) [50-5000]: ");
			price_ = input.nextInt();
		}while(price_ <= 50 || price_ >= 5000);
		do {
			System.out.print("Description [10-50 characters]: ");
			description_ = input.nextLine();
		}while(description_.length() < 10 || description_.length() > 50);
		
		spacecraft[i] = name;
		price[i] = price_;
		description[i] = description_;
		
		if(price[i] >= 0 && price[i] <= 1600)
			failureRate[i] = (float)(((Math.random())*(99-66))+66)/100;
		else if(price[i] >= 1601 && price[i] <= 3200)
			failureRate[i] = (float)(((Math.random())*(66-34))+34)/100;
		else if(price[i] >= 3201 && price[i] <= 5000)
			failureRate[i] = (float)(((Math.random())*(33-1))+1)/100;
	
		
		System.out.println("Update successful");
		input.nextLine();
	}

	private void MoveToLaunchPad() {
		if(launchpad == 1)
			System.out.println("There is a space craft on the launch pad.");
		else if(launchpad == 0) {
			String nameToMove;
			int i;
			do {
				System.out.print("Space craft name to move [abort]: ");
				nameToMove = input.nextLine();
				if(nameToMove.equals("abort")) return;
				int flag =0;
				for (i = 0; i < totalAircraft; i++) {
					if(spacecraft[i].equals(nameToMove)) { 
						flag = 1;
						status[i] = 2;
						launchpad = 1;
						launchIndex = i;
						break;
					}
				}
				if(flag==0) break;
			}while(true);			
		}
	}

	private void SortByNameAlphabetically() {
		quickSort(spacecraft, 0, totalAircraft-1);
	}

	private void SortByHighestFailureRate() {
		quickSort(failureRate, 0, totalAircraft-1);
	}
	

	private void TrackingStation() {
		if(success == 0) 
			System.out.println("There is no spacecraft in the orbit.");
		else {
			System.out.println("== Launch Summary ==");
			System.out.println("====================");
			System.out.println("Successful Flight");
			System.out.println(" Total Flight       : " + success + " craft(s)");
			System.out.println(" Success launch rate: " + ((float)success/((float)success+(float)failed))*100 + "%");
			System.out.println(" Average price      : $" + successAveragePrice + "M");
			System.out.println();
			System.out.println("Failed Flight");
			System.out.println(" Total Flight       : " + failed + " craft(s)");
			System.out.println(" Failed launch rate : " + ((float)failed/((float)success+(float)failed))*100 + "%");
			System.out.println(" Average price      : $" + failedAveragePrice + "M");
			System.out.println();
			System.out.println("== In Orbit ==");
			System.out.println("==============");
			for (int i = 0; i < totalAircraft; i++) {
				if(status[i]==4) {
					System.out.println("Spacecraft  : " + spacecraft[i]);
					System.out.println("Desctiption : " + description[i]);
					System.out.println("Price       : $" + price[i] + "M");
					System.out.println("Failure rate: " + (int)(failureRate[i]*100) +"%");
					System.out.println("Status      : " + getStatus(status[i]));
					System.out.println("Date created: " + dateCreated[i]);
					System.out.println("Lifetime    : " + getLifeTime(dateCreated[i]) + " day(s)");
				}
			}
		}
		input.nextLine();
	}
	
	private void NextDay() {
		dateIncrement++;
	}
	
	private String getStatus(int i) {
		if(i==1) return "Ready to move to launchpad";
		else if(i==2) return "Ready to launch";
		else if(i==3) return "Destroyed";
		else if(i==4) return "In orbit";
		return null;
	}
	
	static void swap(String[] arr, int i, int j){
		String temp = String.valueOf(arr[i]);
	    arr[i] = String.valueOf(arr[j]);
	    arr[j] = String.valueOf(temp);
	}
	
	static void swap(int[] arr, int i, int j){
		int temp = arr[i];
	    arr[i] = arr[j];
	    arr[j] = temp;
	}
	
	static void swap(float[] arr, int i, int j){
		float temp = arr[i];
	    arr[i] = arr[j];
	    arr[j] = temp;
	}
	
	static int partition(String[] arr, int low, int high){
		String pivot = arr[high];
	     
	    int i = (low - 1);
	 
	    for(int j = low; j <= high - 1; j++){
	        if (arr[j].compareTo(pivot) <= 0){
	            i++;
	            swap(arr, i, j);
	            swap(description, i, j);
	            swap(price, i, j);
	            swap(failureRate, i, j);
	            swap(status, i, j);
	            swap(dateCreated, i, j);
	        }
	    }
	    swap(arr, i + 1, high);
	    swap(description, i + 1, high);
        swap(price, i + 1, high);
        swap(failureRate, i + 1, high);
        swap(status, i + 1, high);
        swap(dateCreated, i + 1, high);
	    return (i + 1);
	}
	 
	static void quickSort(String[] arr, int low, int high){
	    if (low < high){ 
	        int pi = partition(arr, low, high);
	 
	        quickSort(arr, low, pi - 1);
	        quickSort(arr, pi + 1, high);
	    }
	}
	
	static int partition(float[] arr, int low, int high){
		float pivot = arr[high];
	     
	    int i = (low - 1);
	 
	    for(int j = low; j <= high - 1; j++){
	        if ((float)arr[j] < (float)pivot){
	            i++;
	            swap(arr, i, j);
	            swap(spacecraft, i , j);
	            swap(description, i, j);
	            swap(price, i, j);
	            swap(status, i, j);
	            swap(dateCreated, i, j);
	        }
	    }
	    swap(arr, i + 1, high);
	    swap(spacecraft, i + 1, high);
	    swap(description, i + 1, high);
        swap(price, i + 1, high);
        swap(status, i + 1, high);
        swap(dateCreated, i + 1, high);
	    return (i + 1);
	}
	 
	static void quickSort(float[] arr, int low, int high){
	    if (low < high){ 
	        int pi = partition(arr, low, high);
	 
	        quickSort(arr, low, pi - 1);
	        quickSort(arr, pi + 1, high);
	    }
	}
	
	private void cls() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}
	
	private boolean isAllowed(int year, int month, int day, String date) {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
		int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		if(year < currentYear ) {
			if(day >= 1 && day <= 31) 
				if(month >= 1 && month <= 12) 
					if(isValidDate(date)) return true;
		}
		else if(year == currentYear) {
			if(month == currentMonth) 
				if(day <= currentDay && day > 0) 
					if(isValidDate(date)) return true;
			if(month < currentMonth && month > 0)
				if(day >= 1 && day <= 31)
					if(isValidDate(date)) return true;
		}					
		if(!isValidDate(date)) return false;
		return false;
	}
	
	private static boolean isValidDate(String input) {
        String formatString = "dd/MM/yyyy";

        SimpleDateFormat format = new SimpleDateFormat(formatString);
        format.setLenient(false);
        try {
            format.parse(input);
        } catch (ParseException e) {
//        	System.out.println("Date is not valid!");
            return false;
        } catch (IllegalArgumentException e) {
//        	System.out.println("Date is not valid!");
        	return false;
        }
        return true;
    }
	
	public static void main(String[] args) {
		new Main();
	}

}
